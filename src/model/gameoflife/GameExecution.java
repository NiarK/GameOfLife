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
	
	public GameExecution(Point size, Rule rule) {
		this._field = new Field(size);
		this._rule = rule;
	}
	
	@Override
	public synchronized void run() {
		
		this._rule.updateEmergingPlaces(this._field);
		this._rule.calculNextCellsGeneration(this._field);
		this._rule.calculEmergingCells(this._field);
		this._rule.updateCellsState(this._field.getCells());
		
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public synchronized void empty() {

		_rule.empty(_field);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public synchronized void randomlyFill() {
		
		_rule.randomlyFill(_field);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public synchronized HashMap<Point, Cell> getCells() {
		return (HashMap<Point, Cell>)this._field.getCells().clone();
	}
	
	public Point getFieldSize() {
		return this._field.getSize();
	}
	
	public synchronized void toggleCell(Point position) {

		if (position.x >= 0
			&& position.x < _field.getSize().x
			&& position.y >= 0
			&& position.y < _field.getSize().y) {

			if (_field.getCells().containsKey(position)) {
				_field.getCells().remove(position);
			} else {
				_field.getCells().put(position, new Cell(position));
			}

			//_rule.updateEmergingPlace(position, _field);

			this.setChanged();
			this.notifyObservers();
		}

	}
	
	public synchronized void setFieldSize(Point size) {
		_field.setSize(size);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public synchronized void setRule(Rule rule) {
		_rule = rule;
	}
        
        public void save(String name){
            _field.save(name);
        }
        
        public void load(String name){
            _field.load(name);
            this.setChanged();
            this.notifyObservers();
        }
}
