package model;

import java.awt.Point;

/**
 *
 * @author pierre
 */
public class Cell {
	
	private Point _coordinate;
	private CellState _state;
	private CellState _prevState;
	
	public Cell(Point coord, CellState state){
		this._coordinate = coord;
		
		this._state = state;
		this._prevState = null;
	}
	
	public void update(CellState state){
		this._prevState = this._state;
		this._state = state;
	}
	
	
}
