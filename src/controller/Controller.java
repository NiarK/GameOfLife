/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.gameoflife.GameExecution;
import model.gameoflife.Pattern;
import model.gameoflife.Search;
import model.gameoflife.StandardRule;
import model.gameoflife.search.PlusSearch;
import model.gameoflife.search.CrossSearch;
import model.gameoflife.search.HexagoneSearch;
import model.gameoflife.search.LargeTriangleSearch;
import model.gameoflife.search.RainSearch;
import model.gameoflife.search.SquareSearch;
import model.gameoflife.search.TriangleSearch;
import model.simulator.Simulator;
import view.Window;

/**
 *
 * @author pierre
 */
public final class Controller{

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
	//private String[] _typeName;
	
	/*
	 * Construit le controleur de l'objet Window.
	 */
	public Controller() {

		/*_typeName = new String[3];
		_typeName[0] = "Square";
		_typeName[1] = "Hexagone";
		_typeName[2] = "Triangle";*/

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

	public GameExecution getGame() {
		return _game;
	}
	
	/*
	 * Initialise des objets RuleParameter pour le jeu.
	 */
	public void initRuleParameter() {

		String standardSearch = "Square";

		_searchName = new String[7];
		_searchName[0] = standardSearch;
		_searchName[1] = "Plus";
		_searchName[2] = "Cross";
		_searchName[3] = "Hexagone";
		_searchName[4] = "Triangle";
		_searchName[5] = "Large triangle";
		_searchName[6] = "Rain (from a bug)";

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
	
	/*
	 * Met le jeu en pause.
	 */
	public void pause() {
		_simulator.pause();
	}

	/*
	 * Lance le jeu.
	 */
	public void play() {
		_simulator.play();
	}

	/**
	 * Sauvegarde un terrain.
	 * @param name Chemin du fichier sous lequel enregistré.
	 */
	public void save(final String name) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				 _game.save(name);
			}
		});

		t.start();
	}

	/**
	 * Chargement d'un terrain.
	 * @param filepath Chemin du fichier a charger
	 */
	public void load(final String filepath) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.load(filepath);
			}
		});

		t.start();
	}

	/**
	 * Lance le calcule de la prochaine génération de cellule.
	 */
	public void next() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_simulator.next();
			}
		});

		t.start();
	}

	/**
	 * Termine le jeu.
	 */
	public void stop() {
		_simulator.terminate();
	}

	/**
	 * Remplie aléatoirement le terrain.
	 */
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

	/**
	 * Vide le terrain.
	 */
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

	/**
	 * Change l'état d'une cellule. Elle passe de morte à vivante ou de vivante à morte.
	 * @param position Position de la cellule à changé.
	 */
	public void toggleCell(final Point position) {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.toggleCell(position);
			}
		});

		t.start();
	}

	/**
	 * Test si le jeu est lancé.
	 * @return Vrai i le jeu est lancé, faux sinon.
	 */
	public boolean isPlayed() {
		return _simulator.isPlayed();
	}

	/**
	 * Définie la taille du terrain.
	 * @param size Nouvelle taille du terrain.
	 */
	public void setFieldSize(final Point size) {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.setFieldSize(size);
				
			}
		});

		t.start();
	}

	/**
	 * Définie la vitesse du jeu. Cela correspond au temps entre chaque génération de cellule.
	 * @param speed Vitesse de jeu en millisecond.
	 */
	public void setSpeed(int speed) {
		_simulator.setPeriod(speed);
	}

	/**
	 * Récupère un tableau contenant les diférentes vitesses.
	 * @return un tableau contenant les diférentes vitesses.
	 */
	public static Integer[] getSpeeds() {

		Integer[] speeds = new Integer[5];

		speeds[0] = Controller.SPEED_VERY_LOW;
		speeds[1] = Controller.SPEED_LOW;
		speeds[2] = Controller.SPEED_MEDIUM;
		speeds[3] = Controller.SPEED_FAST;
		speeds[4] = Controller.SPEED_VERY_FAST;

		return speeds;
	}

	/**
	 * Définie la règle du jeu.
	 * @param rp Les paramètres de la règle du jeu à définir.
	 */
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

	/**
	 * Récupère les différents type de terrain possible ("Square", "Hexagone"...).
	 * @return Un tableau de String contenant le nom des type de terrain possible.
	 */
	/*public String[] getFieldTypes() {
		return _typeName;
	}*/

	/**
	 * Récupère les types de recherche ("Hexagonal", "square"...).
	 * @return Un tableau de String contenant le nom des type de recherche possible.
	 */
	public String[] getSearchType() {
		return _searchName;
		//return _search.keySet().toArray(new String[_search.keySet().size()]);
	}

	/**
	 * 
	 * @param rp Récupère un objet Search en fonction de paramètres.
	 * @return Les paramètres qui définisse la recherche.
	 */
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
		else if (rp.getSearch().equals(_searchName[6])) {
			return new RainSearch(rp.isTorus());
		}

		return new SquareSearch(rp.isTorus());
	}

	/**
	 * Récupère les différents paramètres de règles définit.
	 * @return Un tableau de RuleParameter.
	 */
	public RuleParameter[] getRules() {
		return _rules;
	}

	/**
	 * Récupère le nombre de voisin maximum que renverra une recherche.
	 * @param searchName Nom de la recherche.
	 * @return Nombre de voisin maximum.
	 */
	public int getNeighborMaximumNumber(String searchName) {
		RuleParameter rp = new RuleParameter();
		rp.setSearch(searchName);
		return this.getSearch(rp).getNeighborMaximumNumber();
	}

	/**
	 * Retourne la liste des patterns d'un répertoire.
	 * @param repertory Le répertoire a lister.
	 * @return Un ArrayList contenant les différents pattern.
	 */
	public ArrayList patternList(String repertory) {
		return _game.patternList(repertory);
	}

	/**
	 * Retourne la liste des dossiers d'un répertoire.
	 * @param repertory Le répertoire a lister.
	 * @return Un ArrayList contenant les différents dossiers.
	 */
	public ArrayList patternRepertoryList() {
		return _game.patternRepertoryList();
	}
	
	/**
	 * Définie le pattern.
	 * @param _pattern Le pattern à définir.
	 */
	public void setPattern(Pattern _pattern) {
		_game.setPattern(_pattern);
	}

	public void putPattern(final Point position){
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				_game.putPattern(position);
			}
		});

		t.start();
	}
	
	/**
	 * Définit le numéro de thread pour le calcul des générations.
	 * @param n Nombre de thread.
	 */
	public void setThreadNumber(int n) {
		_game.setThreadNumber(n);
	}

	/**
	 * Récupère le numéro de thread pour le calcul des générations.
	 * @return le nombre de thread.
	 */
	public int getThreadNumber() {
		return _game.getThreadNumber();
	}
	
	/**
	 * Crée un objet pattern à partir du chemin passé en paramètre
	 * @param s chemin où aller chercher le pattern
	 * @return Objet Pattern
	 */
	public Pattern loadPattern(String s, Observer o){
		Pattern p = new Pattern();
		p.addObserver(o);
		p.loadPattern(s);
		return p;
	}
}
