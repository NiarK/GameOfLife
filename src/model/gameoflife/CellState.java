/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

/**
 *
 * @author pierre
 */
public class CellState {
	
	private boolean _isLiving;
	
	public CellState(){
		this._isLiving = true;
	}
	
	public CellState(boolean live){
		this._isLiving = live;
	}

	public boolean isLiving() {
		return _isLiving;
	}

	public void live(boolean live) {
		this._isLiving = live;
	}
	
	
}
