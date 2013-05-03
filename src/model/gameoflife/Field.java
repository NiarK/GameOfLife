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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author pierre
 */
public class Field {
	
	private HashMap<Point, Cell> _cells;
	private HashMap<Point, Integer> _emergingPlaces; // coord d'une case vide -> nb voisin
	private ArrayList<HashMap<Point, Cell>> _cellsFragments;
	private ArrayList<HashMap<Point, Integer>> _emergingPlacesFragments;
	private Point _size;
	
	public static final int NB_FRAGMENT = 10;
	//private Rule _rule;

	/**
	 * Constructeur par défaut
	 * @param size La taille du terrain.
	 //* @param r Les règle du jeu.
	 */
	public Field(Point size) {
		this._size = size;
		
		this._cells = new HashMap<>();
		this._emergingPlaces = new HashMap<>();
		
		_cellsFragments = new ArrayList<>();
		_emergingPlacesFragments = new ArrayList<>();
		
		int fragment = (int) Math.ceil((_size.x / (double) Field.NB_FRAGMENT) * (_size.y / (double) Field.NB_FRAGMENT));
		for(int i = 0; i < fragment; ++i) {
			_cellsFragments.add(new HashMap<Point, Cell>());
			_emergingPlacesFragments.add(new HashMap<Point, Integer>());
		}
		
		//this._rule = r;
		
		//this._rule.randomlyFill(this._size, this._cells, this._nighCases);
	}
	
	public void addCell(Point place, Cell cell) {
		_cells.put(place, cell);
		
		int index = this.getFragmentIndex(place);
		
		_cellsFragments.get(index).put(place, cell);
	}
	
	public void removeCell(Point place) {
		_cells.remove(place);
		
		int index = this.getFragmentIndex(place);
		
//		_cellsFragments.get(index).remove(place);
	}
	
	public void addEmergingPlace(Point place, int neighborhood) {
		_emergingPlaces.put(place, neighborhood);
		
		int index = this.getFragmentIndex(place);
		
		_emergingPlacesFragments.get(index).put(place, neighborhood);
	}
	
	public int getFragmentIndex(Point place) {
		return place.y / Field.NB_FRAGMENT * (int) Math.ceil(_size.x / (double) Field.NB_FRAGMENT)
				+ place.x / Field.NB_FRAGMENT;
	}

	public HashMap<Point, Cell> getCells() {
		return _cells;
	}
	
	public void setCells(HashMap<Point, Cell> cells) {
		this._cells = cells;
	}

	public HashMap<Point, Integer> getEmergingPlaces() {
		return _emergingPlaces;
	}
	
	public void setEmergingPlaces(HashMap<Point, Integer> emergingPlaces) {
		this._emergingPlaces = emergingPlaces;
		
	}

	public Point getSize() {
		return _size;
	}
	
	public void setSize(Point size) {
		_size = size;
		
		Iterator<Map.Entry<Point, Cell>> it = _cells.entrySet().iterator();

		while(it.hasNext()) {

			Cell cell = it.next().getValue();

			Point pos = cell.getCoordinate();
			
			if(pos.x < 0 || pos.y < 0 || pos.x >= _size.x || pos.y >= _size.y) {
				it.remove();
				this.removeCell(pos);
			}
		}
	}
	
	public String toString(){
		
		String str = new String();
		
		String[][] cells = new String[this._size.y][this._size.x];
		String[][] emergingPlaces = new String[this._size.y][this._size.x];
		
		for(int y = 0; y < this._size.y; ++y) {
			for(int x = 0; x < this._size.x; ++x) {
			
				cells[y][x] = ".";
				emergingPlaces[y][x] = ".";
			}
		}
		
		
		for(Map.Entry<Point, Cell> entry : this._cells.entrySet()) {
			
			Point c = entry.getKey();
			
			cells[c.y][c.x] = "O";
		}
		
		for(Map.Entry<Point, Integer> entry : this._emergingPlaces.entrySet()) {
			
			Point ep = entry.getKey();
			
			emergingPlaces[ep.y][ep.x] = Integer.toString(entry.getValue());
		}
		
		/*for(String[] row : cells) {
			str += "[ ";
			for(String place : row) {
			
				str += place + " ";
			}
			str += "]\n";
		}
		
		str += "\n";
		
		for(String[] row : emergingPlaces) {
			str += "[ ";
			for(String place : row) {
			
				str += place + " ";
			}
			str += "]\n";
		}*/
		
		for(int y = 0; y < this._size.y; ++y) {
			str += "[ ";
			for(int x = 0; x < this._size.x; ++x) {
			
				str += cells[y][x] + " ";
			}
			str += "]\t [";
			
			for(int x = 0; x < this._size.x; ++x) {
			
				str += emergingPlaces[y][x] + " ";
			}
			str += "]\n";
		}
		
		return str;
	}
	
        public void save(String name){
            File monFichier = new java.io.File(name); 
            try 
            {
                monFichier.createNewFile(); // Cette fonction doit être appelée au sein d'un bloc TRY 
            }
            catch (IOException e) 
            {
                System.out.println("Impossible de créer le fichier"); 
            } 
            try 
            { 
                FileOutputStream monFluxFichier = new java.io.FileOutputStream(monFichier);
                Iterator<Map.Entry<Point, Cell>> it = _cells.entrySet().iterator();

                String title = "<cells>";
                String begin = "\n\t<cell>\n\t\t<x>";
                String end = "</y>\n\t</cell>";
                String middle = "</x>\n\t\t<y>";
                String titleend = "\n</cells>";
                
                byte[] bbegin = begin.getBytes();
                byte[] bmiddle = middle.getBytes();
                byte[] bend = end.getBytes();
                
                monFluxFichier.write(title.getBytes());
		while(it.hasNext()) {
                    monFluxFichier.write(bbegin);
                    Point cell = it.next().getKey();
                    String x = Integer.toString(cell.x);
                    monFluxFichier.write(x.getBytes());
                    monFluxFichier.write(bmiddle);
                    String y = Integer.toString(cell.y);
                    monFluxFichier.write(y.getBytes());
                    monFluxFichier.write(bend);
		}
                monFluxFichier.write(titleend.getBytes());
            }
            catch (FileNotFoundException e) 
            { 
            System.out.println("Impossible de trouver le fichier"); 
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        public void load(String name){
            try{
                // création d'une fabrique de documents
                DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

                // création d'un constructeur de documents
                DocumentBuilder constructeur = fabrique.newDocumentBuilder();

                // lecture du contenu d'un fichier XML avec DOM
                File xml = new File(name);
                Document document = constructeur.parse(xml);

                //traitement du document
                Element racine = document.getDocumentElement();
		String tag = "cell";
		NodeList liste = racine.getElementsByTagName(tag);
		HashMap<Point,Cell> hm = new HashMap();
		for(int i=0; i<liste.getLength(); i++){
			Element e = (Element)liste.item(i);
                        NodeList coordXElement = e.getElementsByTagName("x");
                        NodeList coordYElement = e.getElementsByTagName("y");
                        int x = Integer.parseInt(coordXElement.item(0).getTextContent());
                        int y = Integer.parseInt(coordYElement.item(0).getTextContent());
                        Point coord = new Point(x,y);
                        hm.put(coord, new Cell(coord));
		}
                this.setCells(hm);
            }catch(ParserConfigurationException pce){
                System.out.println("Erreur de configuration du parseur DOM");
                System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
            }catch(SAXException se){
                System.out.println("Erreur lors du parsing du document");
                System.out.println("lors de l'appel à construteur.parse(xml)");
            }catch(IOException ioe){
                System.out.println("Erreur d'entrée/sortie");
                System.out.println("lors de l'appel à construteur.parse(xml)");
            }
        }
}
