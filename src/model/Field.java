package model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pierre
 */
public class Field {
	
	private HashMap<Point, Cell> _cells;
	private HashMap<Point, Integer> _emergingPlaces; // coord d'une case vide -> nb voisin
	private Point _size;
	//private Rule _rule;

	/**
	 * Constructeur par défaut
	 * @param size La taille du terrain.
	 //* @param r Les règle du jeu.
	 */
	public Field(Point size/*, Rule r*/) {
		this._size = size;
		
		this._cells = new HashMap<>();
		this._emergingPlaces = new HashMap<>();
		
		//this._rule = r;
		
		//this._rule.randomlyFill(this._size, this._cells, this._nighCases);
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
	
	public void setEmergingPlaces(HashMap<Point, Integer> nighCases) {
		this._emergingPlaces = nighCases;
	}

	public Point getSize() {
		return _size;
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
			
			cells[c.y][c.x] = "~";
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
	
}
