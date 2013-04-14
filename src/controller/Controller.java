/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Point;
import java.util.Observable;
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
		Field field = new Field(new Point(52, 35));

		StandardRule rule = new StandardRule();
		rule.ruleStandard();

		rule.randomlyFill(field);

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
}
