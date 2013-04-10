package model;

import java.awt.Point;

/**
 *
 * @author pierre
 */
public class Cell {
	
	private Point _coordinate;
	private CellState _state;
	private CellState _nextState;
	
	public Cell(Point coord){
		this._coordinate = coord;
		
		this._state = null;
		this._nextState = new CellState();
	}
	
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

	public void setNextState(CellState state) {
		this._nextState = state;
	}
	
	
	
	
}
