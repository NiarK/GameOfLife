package model.gameoflife;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

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
		
		_cellsFragments.get(index).remove(place);
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
	
}
