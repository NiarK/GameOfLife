/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Quentin
 */
public class Pattern {

	private HashSet<Point> Cells;
	private Point Middle;

	public Point getMiddle() {
		return Middle;
	}

	private void setMiddle(Point Middle) {
		this.Middle = Middle;
	}

	public HashSet<Point> getCells() {
		return Cells;
	}

	private void setCells(HashSet<Point> hs) {
		this.Cells = hs;
	}

	public int loadPattern(String name) {
		try {
			// création d'une fabrique de documents
			DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

			// création d'un constructeur de documents
			DocumentBuilder DB = DBF.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File("src/resources/" + name + ".cells");
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
			return 1;
		}
		catch (ParserConfigurationException pce) {
			System.err.println("Configuration error DOM parser");
			System.err.println("when calling to DBF.newDocumentBuilder();");
			return 2;
		}
		catch (SAXException se) {
			System.err.println("Error while parsing the document");
			System.err.println("when calling to DB.parse(xml)");
			return 3;
		}
		catch (IOException ioe) {
			System.err.println("Error I/O");
			System.err.println("when calling to DB.parse(xml)");
			return 4;
		}
	}

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
}
