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
import model.gameoflife.search.StandardSearch;
import model.simulator.Simulator;
import view.Window;

/**
 *
 * @author pierre
 */
public final class Controller {
	
	public static final int SPEED_VERY_FAST	= 10;
	public static final int SPEED_FAST		= 50;
	public static final int SPEED_MEDIUM	= 200;
	public static final int SPEED_LOW		= 500;
	public static final int SPEED_VERY_LOW	= 1000;
	
	private GameExecution _game;
	private Simulator _simulator;
	
	private RuleParameter[] _rules;
	private String[][] _searchName;
	private String[] _typeName;
	
	public Controller() {

		_typeName = new String[3];
		_typeName[0] = "Square";
		_typeName[1] = "Hexagone";
		_typeName[2] = "Triangle";
		
		_searchName = new String[_typeName.length][];
		_searchName[0] = new String[4];
		_searchName[0][0] = "Round";
		_searchName[0][1] = "Plus";
		_searchName[0][2] = "Cross";
		_searchName[0][3] = "Large";
		_searchName[1] = new String[2];
		_searchName[1][0] = "Small";
		_searchName[1][1] = "Large";
		_searchName[2] = new String[2];
		_searchName[2][0] = "Small";
		_searchName[2][1] = "Large";
		
		this.initRuleParameter();
		
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
	
	public void initRuleParameter() {
		/*_rulesName = new String[4];
		_rulesName[0] = "Game of life";
		_rulesName[1] = "High life";
		_rulesName[2] = "Seeds";
		_rulesName[3] = "B1S12";*/
		
		_rules = new RuleParameter[4];
		
		StandardRule rule = StandardRule.gameOfLifeRule();
		
		_rules[0] = new RuleParameter();
		_rules[0].setName("Game of life");
		_rules[0].setBorn(rule.getBorn());
		_rules[0].setSurvive(rule.getSurvive());
		
		rule = StandardRule.highLifeRule();
		_rules[1] = new RuleParameter();
		_rules[1].setName("High life");
		_rules[1].setBorn(rule.getBorn());
		_rules[1].setSurvive(rule.getSurvive());
		
		rule = StandardRule.seedsRule();
		_rules[2] = new RuleParameter();
		_rules[2].setName("Seeds");
		_rules[2].setBorn(rule.getBorn());
		_rules[2].setSurvive(rule.getSurvive());
		
		rule = StandardRule.B1S12Rule();
		_rules[3] = new RuleParameter();
		_rules[3].setBorn(rule.getBorn());
		_rules[3].setSurvive(rule.getSurvive());
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
	
	public void setRule(RuleParameter rp) {
		
		StandardRule rule = new StandardRule(new StandardSearch());
		
		rule.setBorn(rp.getBorn());
		rule.setSurvive(rp.getSurvive());
		
		_game.setRule(rule);
	}
	
	public String[] getFieldTypes() {
		return _typeName;
	}
	
	public String[] getSearcchType(int indexOfFieldType) {
		return _searchName[indexOfFieldType];
	}
	
	public RuleParameter[] getRules() {
		return _rules;
	}
}
