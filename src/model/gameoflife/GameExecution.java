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
import java.util.Map.Entry;
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
	private Pattern _pattern;

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

			
			UpdatePlaceProcess upp = new UpdatePlaceProcess();
			Thread updatePlaceProcess = new Thread(upp);
			
			EmergingProcess ep = new EmergingProcess();
			Thread emergingProcess = new Thread(ep);

			EvolutionProcess Evp = new EvolutionProcess();
			Thread evolutionProcess = new Thread(Evp);

			Thread updateProcess = new Thread(new UpdateProcess());

			//System.out.println(_field);
			updatePlaceProcess.start();
			evolutionProcess.start();
			
			evolutionProcess.join(0);
			updatePlaceProcess.join(0);
			
			//System.out.println(_field);
			emergingProcess.start();
			emergingProcess.join(0);

			//System.out.println(_field);
			updateProcess.start();
			updateProcess.join(0);
			//System.out.println(_field);
			//System.out.println("---------------------------------------------");

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
					//_field.getCells().remove(position);
					_field.removeCell(_field.getCells().get(position));
				}
				else {
					//_field.getCells().put(position, new Cell(position));
					_field.addCell(position);
				}
			}
			else{
				Iterator<Point> it = _pattern.getCellsByMiddle().iterator();
				while (it.hasNext()) {
					Point temp = it.next();
					Point p = new Point(position.x + temp.x, position.y + temp.y);
					if(p.x >= 0 && p.x < _field.getSize().x && p.y >= 0 && p.y < _field.getSize().y) {
						//_field.getCells().put(p, new Cell(p));
						_field.addCell(p);
					}
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

	public Pattern getPattern() {
		return _pattern;
	}

	public void setPattern(Pattern _pattern) {
		this._pattern = _pattern;
		
	}
	
	public synchronized void setThreadNumber(int n) {
		
		_field.setFragmentNumber(n);
		
	}
	
	private abstract class Process implements Runnable {

		@Override
		public void run() {
			
			this.init();
			
			int threadNumber = _field.getFragmentNumber();
			
			Thread[] processes = new Thread[threadNumber];
			
			for(int i = 0; i < threadNumber; ++i) {
				
				final int index = i;
				processes[i] = new Thread(new Runnable() {

					@Override
					public void run() {
						
						exec(index);
					}
				});
				
				processes[i].start();
			}
			for(int i = 0; i < threadNumber; ++i) {
				try {
					processes[i].join();
				} catch (InterruptedException ex) {
					Logger.getLogger(GameExecution.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		protected void init(){}
		
		protected abstract void exec(int fragmentIndex);
		
	}
	
	private class UpdatePlaceProcess extends Process {

		@Override
		protected void init() {
			super.init();
			
			_field.clearEmergingPlaces();
		}
		
		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Cell> fragment = _field.getCellsFragments(fragmentIndex);
			_rule.updateEmergingPlaces(fragment, _field);
		}
	}
	
	private class EmergingProcess extends Process  {


		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Integer> fragment = _field.getEmergingPlacesFragments(fragmentIndex);
			_rule.calculEmergingCells(fragment, _field);
		}

	}

	private class EvolutionProcess extends Process  {

		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Cell> fragment = _field.getCellsFragments(fragmentIndex);
			_rule.calculNextCellsGeneration(fragment, _field);
		}

	}

	private class UpdateProcess extends Process  {

		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Cell> fragment = _field.getCellsFragments(fragmentIndex);
			_rule.updateCellsState(fragment, _field);
		}

	}
}
