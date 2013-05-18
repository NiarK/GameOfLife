package model.gameoflife;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Classe contenant les cellules vivante du jeu de la vie.
 * Elle gère aussi l'appartenance des cellules au différents threads.
 * @author pierre
 */
public class Field extends Observable{

	private HashMap<Point, Cell> _cells;
	private HashMap<Point, Integer> _emergingPlaces; // coord d'une case vide -> nb voisin
	private ArrayList<HashMap<Point, Cell>> _cellsFragments;
	private ArrayList<HashMap<Point, Integer>> _emergingPlacesFragments;
	private Point _size;
	private int _fragmentNumber;
	private int _currentFragmentNumber;
	

	/**
	 * Constructeur par défaut.
	 * @param size La taille du terrain.
	 */
	public Field(Point size) {
		this._size = size;

		_fragmentNumber = 1;
		_currentFragmentNumber = -1;
		
		this._cells = new HashMap<>();
		this._emergingPlaces = new HashMap<>();
		
		_cellsFragments = new ArrayList<>();
		_emergingPlacesFragments = new ArrayList<>();
		
		for (int i = 0 ; i < _fragmentNumber ; ++i) {
			_cellsFragments.add(new HashMap<Point, Cell>());
			_emergingPlacesFragments.add(new HashMap<Point, Integer>());
		}
	}

	/**
	 * Récupère les cellules vivantes sur le terrain.
	 * @return Un objet HashMap contenant toute les cellules vivantes.
	 */
	public HashMap<Point, Cell> getCells() {
		return _cells;
	}

	/**
	 * Définit les cellules vivantes sur le terrain.
	 * @param cells Un objet HashMap contenant toute les cellules vivantes.
	 */
	public void setCells(HashMap<Point, Cell> cells) {
		this._cells = cells;
	}

	/**
	 * Récupère les positions où les cellules peuvent potentiellement naitres.
	 * @return Un objet hashmap contenant les positions.
	 */
	public HashMap<Point, Integer> getEmergingPlaces() {
		return _emergingPlaces;
	}

	/**
	 * Définit les positions où les cellules peuvent potentiellement naitres.
	 * @param emergingPlaces Un objet hashmap contenant les positions.
	 */
	public void setEmergingPlaces(HashMap<Point, Integer> emergingPlaces) {
		this._emergingPlaces = emergingPlaces;

	}

	/**
	 * Récupère la taille du terrain.
	 * @return 
	 */
	public Point getSize() {
		return _size;
	}

	/**
	 * Définit la taille du terrain. C'est une operation potentiellement longue car il faut probablement supprimer beaucoup de cellules.
	 * @param size La nouvelle taille du terrain.
	 */
	public synchronized void setSize(Point size) {
		_size = size;

		Iterator<Map.Entry<Point, Cell>> it = _cells.entrySet().iterator();

		while (it.hasNext()) {

			Cell cell = it.next().getValue();

			Point pos = cell.getCoordinate();

			if (pos.x < 0 || pos.y < 0 || pos.x >= _size.x || pos.y >= _size.y) {
				Cell c = _cells.get(pos);
				it.remove();
				this.removeCell(c);
			}
		}
	}


	/**
	 * Enregistre la position des cellules vivantes sous l'un forme d'un fichier XML.
	 * @param filepath Le chemin vers le fichier.
	 */
	public void save(String filepath) {
		File f = new java.io.File(filepath);
		try {
			f.createNewFile();
		}
		catch (IOException e) {
			System.err.println("Could not create file");
			e.printStackTrace();
			ErrorIO err = new ErrorIO("Could not create file");
			this.setChanged();
			this.notifyObservers(err);
		}
		try {
			FileOutputStream FOS = new java.io.FileOutputStream(f);
			Iterator<Map.Entry<Point, Cell>> it = _cells.entrySet().iterator();

			String title = "<cells>";
			String begin = "\n\t<cell>\n\t\t<x>";
			String end = "</y>\n\t</cell>";
			String middle = "</x>\n\t\t<y>";
			String titleend = "\n</cells>";

			byte[] bbegin = begin.getBytes();
			byte[] bmiddle = middle.getBytes();
			byte[] bend = end.getBytes();

			FOS.write(title.getBytes());
			while (it.hasNext()) {
				FOS.write(bbegin);
				Point cell = it.next().getKey();
				String x = Integer.toString(cell.x);
				FOS.write(x.getBytes());
				FOS.write(bmiddle);
				String y = Integer.toString(cell.y);
				FOS.write(y.getBytes());
				FOS.write(bend);
			}
			FOS.write(titleend.getBytes());
			this.setChanged();
			this.notifyObservers(1);
		}
		catch (FileNotFoundException e) {
			System.err.println("Can not find the file");
			e.printStackTrace();
			ErrorIO err = new ErrorIO("Can not find the file");
		}
		catch (IOException ex) {
			System.err.println("INPUT/OUTPUT exception");
			ex.printStackTrace();
			ErrorIO err = new ErrorIO("Error during the save\nMaybe you don't have the right for writing");
			this.setChanged();
			this.notifyObservers(err);
		}
	}

	/**
	 * Charge un fichier XML contenant la position de cellules vivantes.
	 * @param filepath Le chemin vers le fichier.
	 */
	public void load(String filepath) {
		try {
			// création d'une fabrique de documents
			DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

			// création d'un constructeur de documents
			DocumentBuilder DB = DBF.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File(filepath);
			Document document = DB.parse(xml);

			//traitement du document
			Element root = document.getDocumentElement();
			String tag = "cell";
			NodeList liste = root.getElementsByTagName(tag);
			this.clearCells();
			for (int i = 0 ; i < liste.getLength() ; i++) {
				Element e = (Element) liste.item(i);
				NodeList coordXElement = e.getElementsByTagName("x");
				NodeList coordYElement = e.getElementsByTagName("y");
				int x = Integer.parseInt(coordXElement.item(0).getTextContent());
				int y = Integer.parseInt(coordYElement.item(0).getTextContent());
				Point coord = new Point(x, y);
				this.addCell(coord);
			}
		}
		catch (ParserConfigurationException pce) {
			System.err.println("Configuration error DOM parser");
			System.err.println("when calling to DBF.newDocumentBuilder();");
			pce.printStackTrace();
			ErrorIO err = new ErrorIO("Configuration error DOM parser");
			this.setChanged();
			this.notifyObservers(err);
		}
		catch (SAXException se) {
			System.err.println("Error while parsing the document");
			System.err.println("when calling to DB.parse(xml)");
			se.printStackTrace();
			ErrorIO err = new ErrorIO("Error while parsing the document");
			this.setChanged();
			this.notifyObservers(err);
		}
		catch (IOException ioe) {
			System.err.println("Error I/O");
			System.err.println("when calling to DB.parse(xml)");
			ioe.printStackTrace();
			ErrorIO err = new ErrorIO("Can not found the file \nor the file is not in a good format");
			this.setChanged();
			this.notifyObservers(err);
		}
	}

	/**
	 * Crée une liste des patterns existants dans le dossier
	 * @param repertory dossier où l'on veut lire
	 * @return ArrayList qui contient les différents nom de fichier .cells sans l'extension
	 */
	public ArrayList patternList(String repertory) {
		String[] list;
		ArrayList result = new ArrayList();
		int i;
		File f = new File("resources/Patterns/"+repertory+"/");

		list = f.list();
		for (i = 0 ; i < list.length ; i++) {
			if (list[i].endsWith(".cells")) {
				result.add(list[i].substring(0, list[i].length() - 6));
			}
		}
		return result;
	}

	/**
	 * Fonction qui cherche les dossiers présents dans les ressources
	 * @return ArrayList qui contient les noms des dossiers
	 */
	public ArrayList patternRepertoryList() {
		String[] list;
		ArrayList result = new ArrayList();
		int i;
		File f = new File("resources/Patterns/");

		list = f.list();
		for (i = 0 ; i < list.length ; i++) {
			result.add(list[i]);
		}
		return result;
	}

	@Override
	public String toString() {

		String str = new String();

		String[][] cells = new String[this._size.y][this._size.x];
		String[][] emergingPlaces = new String[this._size.y][this._size.x];

		for (int y = 0 ; y < this._size.y ; ++y) {
			for (int x = 0 ; x < this._size.x ; ++x) {

				cells[y][x] = ".";
				emergingPlaces[y][x] = ".";
			}
		}


		for (Map.Entry<Point, Cell> entry : this._cells.entrySet()) {

			Point c = entry.getKey();

			if (entry.getValue().getState().isAlive()) {
				cells[c.y][c.x] = "0";
			}
			else {
				cells[c.y][c.x] = "X";
			}
		}

		for (Map.Entry<Point, Integer> entry : this._emergingPlaces.entrySet()) {

			Point ep = entry.getKey();

			emergingPlaces[ep.y][ep.x] = Integer.toString(entry.getValue());
		}

		for (int y = 0 ; y < this._size.y ; ++y) {
			str += "[ ";
			for (int x = 0 ; x < this._size.x ; ++x) {

				str += cells[y][x] + " ";
			}
			str += "]\t [";

			for (int x = 0 ; x < this._size.x ; ++x) {

				str += emergingPlaces[y][x] + " ";
			}
			str += "]\n";
		}

		return str;

	}
	
	/**
	 * Renvoi le fragment suivant
	 * @return  numéro du fragment
	 */
	private synchronized int getNextFragmentNumber() {
		
		_currentFragmentNumber++;
		if(_currentFragmentNumber >= _fragmentNumber) {
			_currentFragmentNumber = 0;
		}
		
		return _currentFragmentNumber;
	}
	
	/**
	 * Permet d'ajouter une cellule au field
	 * @param coord coordonnées de la cellule
	 */
	public synchronized void addCell(Point coord){
		
		coord = new Point(coord);
		
		int thread = this.getNextFragmentNumber();
		
		Cell c =  new Cell(coord, thread);
		
		this.add(c);
	}
	
	/**
	 * Permet d'ajouter une cellule émergeante
	 * @param coord coordonnées de la cellule
	 */
	public synchronized void addEmergingCell(Point coord){
		coord = new Point(coord);
		
		int thread = this.getNextFragmentNumber();
		
		Cell c =  Cell.getEmergingCell(coord, thread);
		
		this.add(c);
	}
	
	/**
	 * Fonction d'ajout de la cellule dans le field
	 * @param c Cellule à ajouter
	 */
	private void add(Cell c) {
		
		if( _cells.containsKey(c.getCoordinate())) {
			c.setFragmentNumber(_cells.get(c.getCoordinate()).getFragmentNumber());
		}
		
		_cells.put(c.getCoordinate(), c);
		_cellsFragments.get(c.getFragmentNumber()).put(c.getCoordinate(), c);
	}
	
	/**
	 * Fonction de suppression de la cellule dans le field
	 * @param c Cellule à supprimer
	 */
	public synchronized void removeCell(Cell c) {
		
		if(_cellsFragments.get(c.getFragmentNumber()).get(c.getCoordinate()) != null) {
			_cellsFragments.get(c.getFragmentNumber()).remove(c.getCoordinate());
		}
		
		if(_cells.get(c.getCoordinate()) != null) {
			_cells.remove(c.getCoordinate());
		}
	}
	
	/**
	 * Fonction pour vider les cellules
	 */
	public synchronized void clearCells() {
		_cells.clear();
		int fragmentNumber = _cellsFragments.size();
		for(int i = 0; i < fragmentNumber; ++i) {
			_cellsFragments.get(i).clear();
		}
	}
	
	/**
	 * Fonction pour vider les cellules emergeantes
	 */
	public synchronized void clearEmergingPlaces() {
		_emergingPlaces.clear();
		int fragmentNumber = _emergingPlacesFragments.size();
		for(int i = 0; i < fragmentNumber; ++i) {
			_emergingPlacesFragments.get(i).clear();
		}
	}
	
	/**
	 * Fonction d'ajout d'une emergeante
	 * @param coord Coordonnées de la cellule
	 * @param neighborNumber Nombre de voisins vivants de cette cellule
	 */
	public synchronized void addEmergingPlace(Point coord, Integer neighborNumber){
		coord = new Point(coord);
		
		_emergingPlaces.put(coord, neighborNumber);
		
		int thread = (coord.x + coord.y) % _fragmentNumber;
		
		_emergingPlacesFragments.get(thread).put(coord, neighborNumber);
	}
	
	/**
	 * Récupère les cellules du fragment
	 * @param threadNumber fragment à récupérer
	 * @return HashMap avec les cellules
	 */
	public HashMap<Point, Cell> getCellsFragments(int threadNumber) {
		return _cellsFragments.get(threadNumber);
	}

	/**
	 * Récupère les cellules émergeantes du fragment
	 * @param threadNumber numéro de fragment
	 * @return Hashmap avec les cellules
	 */
	public HashMap<Point, Integer> getEmergingPlacesFragments(int threadNumber) {
		return _emergingPlacesFragments.get(threadNumber);
	}

	/**
	 * Récupère le numéro de fragment
	 * @return numéro du fragment
	 */
	public int getFragmentNumber() {
		return _fragmentNumber;
	}

	/**
	 * Vide tout le field
	 */
	public synchronized void empty() {
		
		this._cells = new HashMap<>();
		this._emergingPlaces = new HashMap<>();
		
		_cellsFragments = new ArrayList<>();
		_emergingPlacesFragments = new ArrayList<>();
		
		for (int i = 0 ; i < _fragmentNumber ; ++i) {
			_cellsFragments.add(new HashMap<Point, Cell>());
			_emergingPlacesFragments.add(new HashMap<Point, Integer>());
		}
	}

	public synchronized void setFragmentNumber(int n) {
		if(n > 0) {
			
			int oldFragmentNumber = _fragmentNumber;
			_fragmentNumber = n;
			
			ArrayList<HashMap<Point,Cell>> temp = _cellsFragments;
			
			_cellsFragments = new ArrayList<>();
			_emergingPlacesFragments = new ArrayList<>();
			
			for (int i = 0 ; i < n ; ++i) {
				if( i < temp.size() ) {
					_cellsFragments.add(temp.get(i));
				}
				else {
					_cellsFragments.add(new HashMap<Point, Cell>());
				}
				_emergingPlacesFragments.add(new HashMap<Point, Integer>());
			}
			
			if (n < oldFragmentNumber) {
			
				for (Cell cell : _cells.values()) {
					if(cell.getFragmentNumber() >= n) {
						int number = this.getNextFragmentNumber();
						cell.setFragmentNumber(number);
						this.add(cell);
					}
				}
			}
		}
	}
	
	
}
