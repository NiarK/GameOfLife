/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pierre
 */
public class CellState {
	
	private int _value;
	
	public CellState(){
		this._value = 1;
	}
	
	public CellState(int val){
		this._value = val;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int val) {
		this._value = val;
	}
	
	
}
