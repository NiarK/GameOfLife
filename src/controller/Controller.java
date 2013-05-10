/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.gameoflife.GameExecution;
import model.gameoflife.Search;
import model.gameoflife.StandardRule;
import model.gameoflife.search.PlusSearch;
import model.gameoflife.search.CrossSearch;
import model.gameoflife.search.HexagoneSearch;
import model.gameoflife.search.LargeTriangleSearch;
import model.gameoflife.search.SquareSearch;
import model.gameoflife.search.TriangleSearch;
import model.simulator.Simulator;
import view.Window;

/**
 *
 * @author pierre
 */
public final class Controller {

	public static final int SPEED_VERY_FAST = 10;
	public static final int SPEED_FAST = 50;
	public static final int SPEED_MEDIUM = 200;
	public static final int SPEED_LOW = 500;
	public static final int SPEED_VERY_LOW = 1000;
	//private int _neighborMaximumNumber;
	private GameExecution _game;
	private Simulator _simulator;
	private RuleParameter[] _rules;
	private String[] _searchName;
	private String[] _typeName;

	public Controller() {

		_typeName = new String[3];
		_typeName[0] = "Square";
		_typeName[1] = "Hexagone";
		_typeName[2] = "Triangle";

		this.initRuleParameter();

		StandardRule rule = StandardRule.gameOfLifeRule();
		//StandardRule rule = StandardRule.highLifeRule();
		//StandardRule rule = StandardRule.seedsRule();
		//StandardRule rule = StandardRule.B1S12Rule();

		_game = new GameExecution(new Point(50, 50), rule);

		try {
			_simulator = new Simulator(_game, 500);
			_simulator.start();
		}
		catch (InterruptedException ex) {
			Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void initRuleParameter() {

		String standardSearch = "Square";

		_searchName = new String[6];
		_searchName[0] = standardSearch;
		_searchName[1] = "Plus";
		_searchName[2] = "Cross";
		_searchName[3] = "Hexagone";
		_searchName[4] = "Triangle";
		_searchName[5] = "Large triangle";

		_rules = new RuleParameter[5];

		StandardRule rule = StandardRule.gameOfLifeRule();
		_rules[0] = new RuleParameter();
		_rules[0].setName("Game of life");
		_rules[0].setBorn(rule.getBorn());
		_rules[0].setSurvive(rule.getSurvive());
		_rules[0].setSearch(standardSearch);

		rule = StandardRule.hexagonalGameOfLifeRule();
		_rules[1] = new RuleParameter();
		_rules[1].setName("Game of life (Hexagone)");
		_rules[1].setBorn(rule.getBorn());
		_rules[1].setSurvive(rule.getSurvive());
		_rules[1].setSearch(standardSearch);

		rule = StandardRule.highLifeRule();
		_rules[2] = new RuleParameter();
		_rules[2].setName("High life");
		_rules[2].setBorn(rule.getBorn());
		_rules[2].setSurvive(rule.getSurvive());
		_rules[2].setSearch(standardSearch);

		rule = StandardRule.seedsRule();
		_rules[3] = new RuleParameter();
		_rules[3].setName("Seeds");
		_rules[3].setBorn(rule.getBorn());
		_rules[3].setSurvive(rule.getSurvive());
		_rules[3].setSearch(standardSearch);

		rule = StandardRule.B1S12Rule();
		_rules[4] = new RuleParameter();
		_rules[4].setBorn(rule.getBorn());
		_rules[4].setSurvive(rule.getSurvive());
		_rules[4].setSearch(standardSearch);

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

	public int save(final String name) {
		/*Thread t = new Thread(new Runnable() {
		
		@Override
		public void run() {*/
		return _game.save(name);
		/*}
		});
		
		t.start();*/
	}

	public int load(final String name) {
		/*Thread t = new Thread(new Runnable() {
		
		@Override
		public void run() {*/
		return _game.load(name);
		/*}
		});
		
		t.start();*/
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

		Search s = this.getSearch(rp);

		//_neighborMaximumNumber = s.getNeighborMaximumNumber();

		final StandardRule rule = new StandardRule(s);

		rule.setBorn(rp.getBorn());
		rule.setSurvive(rp.getSurvive());

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.setRule(rule);
			}
		});

		t.start();
	}

	public String[] getFieldTypes() {
		return _typeName;
	}

	public String[] getSearchType() {
		return _searchName;
		//return _search.keySet().toArray(new String[_search.keySet().size()]);
	}

	private Search getSearch(RuleParameter rp) {

		if (rp.getSearch().equals(_searchName[0])) {
			return new SquareSearch(rp.isTorus());
		}
		else if (rp.getSearch().equals(_searchName[1])) {
			return new PlusSearch(rp.isTorus());
		}
		else if (rp.getSearch().equals(_searchName[2])) {
			return new CrossSearch(rp.isTorus());
		}
		else if (rp.getSearch().equals(_searchName[3])) {
			return new HexagoneSearch(rp.isTorus());
		}
		else if (rp.getSearch().equals(_searchName[4])) {
			return new TriangleSearch(rp.isTorus());
		}
		else if (rp.getSearch().equals(_searchName[5])) {
			return new LargeTriangleSearch(rp.isTorus());
		}

		return new SquareSearch(rp.isTorus());
	}

	public RuleParameter[] getRules() {
		return _rules;
	}

	public int getNeighborMaximumNumber(String searchName) {
		RuleParameter rp = new RuleParameter();
		rp.setSearch(searchName);
		return this.getSearch(rp).getNeighborMaximumNumber();
	}

	public ArrayList patternList() {
		return _game.patternList();
	}
	
	public void setPattern(HashSet<Point> _pattern) {
		_game.setPattern(_pattern);
	}
}
