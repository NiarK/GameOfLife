/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Classe gérant un modèle de cellule.
 * @author Quentin
 */
public class Pattern extends Observable{

	private HashSet<Point> Cells;
	private Point Middle;
	private HashSet<Point> CellsByMiddle;

	/**
	 * Constructeur par défaut.
	 */
	public Pattern() {
	}
	
	/**
	 * Récupère le modèle dont la cellule centrale se trouve en (0, 0).
	 * @return Le modèle.
	 */
	public HashSet<Point> getCellsByMiddle() {
		return CellsByMiddle;
	}

	/**
	 * Récupère le modèle dont la cellule centrale se trouve en (0, 0).
	 * @param CellsByMiddle Le modèle.
	 */
	private void setCellsByMiddle(HashSet<Point> CellsByMiddle) {
		this.CellsByMiddle = CellsByMiddle;
	}

	/**
	 * Récupère la position centrale du modèle.
	 * @return La position centrale.
	 */
	public Point getMiddle() {
		return Middle;
	}

	/**
	 * définit le centre du modèle.
	 * @param Middle 
	 */
	private void setMiddle(Point Middle) {
		this.Middle = Middle;
	}

	/**
	 * Récupère la liste des positions des cellules du modèle.
	 * @return Un objet HashSet contenant la liste des positions.
	 */
	public HashSet<Point> getCells() {
		return Cells;
	}

	/**
	 * Définit les positions des cellules du modèle.
	 * @param hs Un objet HashSet contenant les positions.
	 */
	private void setCells(HashSet<Point> hs) {
		this.Cells = hs;
	}


	/**
	 * Charge le modèle depuis un fichier XML.
	 * @param name Nom du fichier XML à charger (sans extension).
	 */
	public void loadPattern(String name) {
		try {
			// création d'une fabrique de documents
			DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

			// création d'un constructeur de documents
			DocumentBuilder DB = DBF.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File("resources/Patterns/" + name + ".cells");
			Document document = DB.parse(xml);

			//traitement du document
			Element root = document.getDocumentElement();
			String tag = "cell";
			NodeList liste = root.getElementsByTagName(tag);
			HashSet<Point> hs = new HashSet();
			for (int i = 0 ; i < liste.getLength() ; i++) {
				Element e = (Element) liste.item(i);
				NodeList coordXElement = e.getElementsByTagName("x");
				NodeList coordYElement = e.getElementsByTagName("y");
				int x = Integer.parseInt(coordXElement.item(0).getTextContent());
				int y = Integer.parseInt(coordYElement.item(0).getTextContent());
				hs.add(new Point(x, y));
			}
			this.setCells(hs);
			this.findMiddle();
			this.makeCellsByMiddle();
		}
		catch (ParserConfigurationException pce) {
			System.err.println("Configuration error DOM parser");
			System.err.println("when calling to DBF.newDocumentBuilder();");
			ErrorIO err = new ErrorIO("Configuration error DOM parser");
			this.setChanged();
			this.notifyObservers(err);
		}
		catch (SAXException se) {
			System.err.println("Error while parsing the document");
			System.err.println("when calling to DB.parse(xml)");
			ErrorIO err = new ErrorIO("Error while parsing the document");
			this.setChanged();
			this.notifyObservers(err);
		}
		catch (IOException ioe) {
			System.err.println("Error I/O");
			System.err.println("when calling to DB.parse(xml)");
			ErrorIO err = new ErrorIO("Error I/O");
			this.setChanged();
			this.notifyObservers(err);
		}
	}
	
	/**
	 * Calcule le milieu du modèle.
	 */
	public void findMiddle() {
		int xmin = Integer.MAX_VALUE;
		int xmax = 0;
		int ymin = Integer.MAX_VALUE;
		int ymax = 0;
		Iterator<Point> it = Cells.iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			if (xmin > temp.x) {
				xmin = temp.x;
			}
			if (xmax < temp.x) {
				xmax = temp.x;
			}
			if (ymin > temp.y) {
				ymin = temp.y;
			}
			if (ymax < temp.y) {
				ymax = temp.y;
			}
		}
		this.setMiddle(new Point((xmin + xmax) / 2, (ymin + ymax) / 2));
	}
	
	/**
	 * Calcule le modèle en fonction du milieu pour permettre l'inclusion dans le terrain.
	 */
	public void makeCellsByMiddle(){
		HashSet<Point> hs = new HashSet();
		Iterator<Point> it = Cells.iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			hs.add(new Point (temp.x - this.getMiddle().x, temp.y - this.getMiddle().y));
		}
		this.setCellsByMiddle(hs);
	}
	
	/**
	 * Calcule la symétrie horizontale du modèle.
	 */
	public void horizontalSymmetry(){
		HashSet<Point> hs = new HashSet();
		Iterator<Point> it = this.getCellsByMiddle().iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			hs.add(new Point (temp.x, -temp.y));
		}
		this.setCellsByMiddle(hs);
	}
	
	/**
	 * Calcule la symétrie verticale du modèle.
	 */
	public void verticalSymmetry(){
		HashSet<Point> hs = new HashSet();
		Iterator<Point> it = this.getCellsByMiddle().iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			hs.add(new Point (-temp.x, temp.y));
		}
		this.setCellsByMiddle(hs);
	}
	/**
	 * Effectue une rotation du modèle dans le sens inverse des aiguilles d'une montre.
	 */
	public void rotateLeft(){
		HashSet<Point> hs = new HashSet();
		Iterator<Point> it = this.getCellsByMiddle().iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			hs.add(new Point (temp.y, -temp.x));
		}
		this.setCellsByMiddle(hs);
	}
	
	/**
	 * Effectue une rotation du modèle dans le sens des aiguilles d'une montre.
	 */
	public void rotateRight(){
		HashSet<Point> hs = new HashSet();
		Iterator<Point> it = this.getCellsByMiddle().iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			hs.add(new Point (-temp.y, temp.x));
		}
		this.setCellsByMiddle(hs);
	}
}
