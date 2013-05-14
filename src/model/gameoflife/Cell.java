package model.gameoflife;

import java.awt.Point;

/**
 * Cellule du jeu de la vie. Cette classe gère les différents états d'une cellule.
 * @author pierre
 */
public class Cell {
	
	private int _fragmentNumber;
	private Point _coordinate;
	private CellState _state;
	private CellState _nextState;
	
	/**
	 * Construit une cellule à une certaines position.
	 * @param coord Coordonnées de la cellule.
	 */
	public Cell(Point coord, int threadNumber){
		this._coordinate = coord;
		this._fragmentNumber = threadNumber;
		
		this._state = new CellState(true);
		this._nextState = new CellState(true);
	}
	
	public static Cell getEmergingCell(Point coord, int threadNumber) {
		Cell c = new Cell(coord, threadNumber);
		c._state = new CellState(false);
		return c;
	}
	
	/**
	 * Met à jour la cellule avec son prochain état.
	 */
	public void update(){
		
		this._state = this._nextState;
	}
	
	public Point getCoordinate() {
		return _coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this._coordinate = coordinate;
	}

	public CellState getState() {
		return _state;
	}

	/**
	 * Met à jour le prochain état de la cellule.
	 * @param state Le prochain état.
	 */
	public void setNextState(CellState state) {
		this._nextState = state;
	}

	public int getFragmentNumber() {
		return _fragmentNumber;
	}

	public void setFragmentNumber(int threadNumber) {
		this._fragmentNumber = threadNumber;
	}
	
	
	
}
