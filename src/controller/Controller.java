/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Point;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.gameoflife.Field;
import model.gameoflife.GameExecution;
import model.gameoflife.StandardRule;
import model.simulator.Simulator;
import view.Window;

/**
 *
 * @author pierre
 */
public class Controller {
	
	private GameExecution _game;
	private Simulator _simulator;
	
	public Controller() {
		Field field = new Field(new Point(50, 50));

		StandardRule rule = StandardRule.gameOfLifeRule();
		//StandardRule rule = StandardRule.highLifeRule();
		//StandardRule rule = StandardRule.seedsRule();
		//StandardRule rule = StandardRule.B1S12Rule();

		_game = new GameExecution(field, rule);
		
		try {
			_simulator = new Simulator(_game, 500);
			_simulator.start();
		} catch (InterruptedException ex) {
			Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Ajoute un observateur au jeu.
	 * @param o L'observateur en question.
	 */
	public void addObserverToGame(Observer o) {
		_game.addObserver(o);
	}
	
	public void pause() {
		_simulator.pause();
	}

	public void play() {
		_simulator.play();
	}
	
	public void next() {
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_simulator.next();
			}
		});
		
		t.start();
	}

	public void stop() {
		_simulator.terminate();
	}

	public void randomlyFill() {
		_simulator.pause();
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.randomlyFill();
			}
		});
		
		t.start();
	}

	public void empty() {
		_simulator.pause();
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.empty();
			}
		});
		
		t.start();
	}

	public void toggleCell(final Point position) {
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.toggleCell(position);
			}
		});
		
		t.start();
	}
}
