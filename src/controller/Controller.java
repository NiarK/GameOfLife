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
	
	public static final int SPEED_VERY_FAST	= 10;
	public static final int SPEED_FAST		= 50;
	public static final int SPEED_MEDIUM	= 200;
	public static final int SPEED_LOW		= 500;
	public static final int SPEED_VERY_LOW	= 1000;
	
	private GameExecution _game;
	private Simulator _simulator;
	
	public Controller() {

		StandardRule rule = StandardRule.gameOfLifeRule();
		//StandardRule rule = StandardRule.highLifeRule();
		//StandardRule rule = StandardRule.seedsRule();
		//StandardRule rule = StandardRule.B1S12Rule();

		_game = new GameExecution(new Point(50, 50), rule);
		
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
	
	public boolean isPlayed() {
		return _simulator.isPlayed();
	}
	
	public void setFieldSize(final Point size) {
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.setFieldSize(size);
			}
		});
		
		t.start();
	}
	
	public void setSpeed(int speed) {
		_simulator.setPeriod(speed);
	}
	
	public static Integer[] getSpeeds() {
		
		Integer[] speeds = new Integer[5];
		
		speeds[0] = Controller.SPEED_VERY_LOW;
		speeds[1] = Controller.SPEED_LOW;
		speeds[2] = Controller.SPEED_MEDIUM;
		speeds[3] = Controller.SPEED_FAST;
		speeds[4] = Controller.SPEED_VERY_FAST;
		
		return speeds;
	}
}
