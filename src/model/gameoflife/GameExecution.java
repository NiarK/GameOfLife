/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;

/**
 *
 * @author pierre
 */
public class GameExecution extends Observable implements Runnable {

	private Field _field;
	private Rule _rule;
	
	public GameExecution(Field field, Rule rule) {
		this._field = field;
		this._rule = rule;
	}
	
	@Override
	public void run() {
		this._rule.update(this._field);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void empty() {
		_rule.empty(_field);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void randomlyFill() {
		_rule.randomlyFill(_field);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public HashMap<Point, Cell> getCells() {
		return this._field.getCells();
	}
	
	public Point getSize() {
		return this._field.getSize();
	}
	
	public void toggleCell(Point position) {
		
		if(
				position.x >= 0 && 
				position.x < _field.getSize().x && 
				position.y >= 0 && 
				position.y < _field.getSize().y
				) {
		
			if( _field.getCells().containsKey(position) ) {
				_field.getCells().remove(position);
			}
			else {
				_field.getCells().put(position, new Cell(position));
			}

			//_rule.updateEmergingPlace(position, _field);

			this.setChanged();
			this.notifyObservers();
		}
	}
}
