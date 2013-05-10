/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pierre
 */
public class GameExecution extends Observable implements Runnable {

	private Field _field;
	private Rule _rule;
	private HashSet<Point> _pattern;

	public GameExecution(Point size, Rule rule) {
		this._field = new Field(size);
		this._rule = rule;
	}

	@Override
	public synchronized void run() {
		try {
			/*this._rule.updateEmergingPlaces(this._field);
			this._rule.calculNextCellsGeneration(this._field);
			this._rule.calculEmergingCells(this._field);
			this._rule.updateCellsState(this._field.getCells());*/

			EmergingProcess ep = new EmergingProcess();
			ep.setCells(_field.getCells());
			Thread emergingProcess = new Thread(ep);

			EvolutionProcess Evp = new EvolutionProcess();
			Evp.setCells(_field.getCells());
			Thread evolutionProcess = new Thread(Evp);

			Thread updateProcess = new Thread(new UpdateProcess());

			emergingProcess.start();
			evolutionProcess.start();

			evolutionProcess.join(0);
			emergingProcess.join(0);

			updateProcess.start();
			updateProcess.join(0);

			this.setChanged();
			this.notifyObservers();
		}
		catch (InterruptedException ex) {
			Logger.getLogger(GameExecution.class.getName()).log(Level.SEVERE, null, ex);
		}
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
		return (HashMap<Point, Cell>) this._field.getCells().clone();
	}

	public Point getFieldSize() {
		return this._field.getSize();
	}

	public synchronized void toggleCell(Point position) {

		if (position.x >= 0
			&& position.x < _field.getSize().x
			&& position.y >= 0
			&& position.y < _field.getSize().y) {
			if(_pattern == null){
				if (_field.getCells().containsKey(position)) {
					_field.getCells().remove(position);
				}
				else {
					_field.getCells().put(position, new Cell(position));
				}
			}
			else{
				Iterator<Point> it = _pattern.iterator();
				while (it.hasNext()) {
					Point temp = it.next();
					Point p = new Point(position.x + temp.x, position.y + temp.y);
					if(p.x >= 0 && p.x < _field.getSize().x && p.y >= 0 && p.y < _field.getSize().y)
					_field.getCells().put(p, new Cell(p));
				}
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

	public int save(String name) {
		return _field.save(name);
	}

	public int load(String name) {
		int value = _field.load(name);
		this.setChanged();
		this.notifyObservers();
		return value;
	}

	public ArrayList patternList() {
		return _field.patternList();
	}

	private class EmergingProcess implements Runnable {

		HashMap<Point, Cell> _cloneCells;
		HashMap<Point, Integer> _cloneEmergingPlaces;

		public void setCells(HashMap<Point, Cell> cells) {
			_cloneCells = (HashMap<Point, Cell>) cells.clone();
		}

		public void setEmergingPlaces(HashMap<Point, Integer> emergingPlaces) {
			_cloneEmergingPlaces = (HashMap<Point, Integer>) emergingPlaces.clone();
		}

		@Override
		public void run() {
			_rule.updateEmergingPlaces(_cloneCells, _field);
			this.setEmergingPlaces(_field.getEmergingPlaces());
			_rule.calculEmergingCells(_cloneEmergingPlaces, _field);
		}
	}

	private class EvolutionProcess implements Runnable {

		HashMap<Point, Cell> _cloneCells;

		public void setCells(HashMap<Point, Cell> cells) {
			_cloneCells = (HashMap<Point, Cell>) cells.clone();
		}

		@Override
		public void run() {

			_rule.calculNextCellsGeneration(_cloneCells, _field);
		}
	}

	private class UpdateProcess implements Runnable {

		@Override
		public void run() {
			_rule.updateCellsState(_field.getCells());
		}
	}

	public HashSet<Point> getPattern() {
		return _pattern;
	}

	public void setPattern(HashSet<Point> _pattern) {
		this._pattern = _pattern;
		
	}
}
