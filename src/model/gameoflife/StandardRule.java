/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import model.gameoflife.search.SquareSearch;

/**
 * Permet d'appliquer les règles standard du jeu de la vie.
 * @author pierre
 */
public class StandardRule implements Rule {

	private HashSet<Integer> _born;
	private HashSet<Integer> _survive;
	
	private Search _search;
	
	/**
	 * Constructeur par défaut
	 * @param search L'objet en charge de la recherche des voisins.
	 */
	public StandardRule(Search search){
		this._born = new HashSet<>();
		this._survive = new HashSet<>();
		
		this._search = search;
	}

	/**
	 * Initialise les règles pour le jeu de la vie de Conway.
	 * Born : 3
	 * Survive : 2 et 3
	 */
	public static StandardRule gameOfLifeRule() {
		StandardRule rule = new StandardRule(new SquareSearch());
		
		HashSet b = new HashSet();
		b.add(3);
		
		HashSet s = new HashSet();
		s.add(2);
		s.add(3);
		
		rule.setBorn(b);
		rule.setSurvive(s);
		
		return rule;
	}
	
	/**
	 * Initialise les règles pour le jeu de la vie standard d'un terrain hexagonal.
	 * Born : 2
	 * Survive : 3 et 4
	 */
	public static StandardRule hexagonalGameOfLifeRule() {
		StandardRule rule = new StandardRule(new SquareSearch());
		
		HashSet b = new HashSet();
		b.add(2);
		
		HashSet s = new HashSet();
		s.add(3);
		s.add(4);
		
		rule.setBorn(b);
		rule.setSurvive(s);
		
		return rule;
	}
	
	/**
	 * Initialise les règles pour le jeu de la vie : HighLife.
	 * Born : 3 et 6
	 * Survive : 2 et 3
	 */
	public static StandardRule highLifeRule() {
		
		StandardRule rule = new StandardRule(new SquareSearch());
		
		HashSet b = new HashSet();
		b.add(3);
		b.add(6);
		
		HashSet s = new HashSet();
		s.add(2);
		s.add(3);
		
		rule.setBorn(b);
		rule.setSurvive(s);
		
		return rule;
	}
	
	/**
	 * Initialise les règles pour le jeu de la vie.
	 * Born : 1
	 * Survive : 1 et 2
	 */
	public static StandardRule B1S12Rule() {
		
		StandardRule rule = new StandardRule(new SquareSearch());
		
		HashSet b = new HashSet();
		b.add(1);
		
		HashSet s = new HashSet();
		s.add(1);
		s.add(2);
		
		rule.setBorn(b);
		rule.setSurvive(s);
		
		return rule;
	}
	
	/**
	 * Initialise les règles pour le jeu de la vie : Seeds
	 * Born : 2
	 * Survive : non
	 */
	public static StandardRule seedsRule() {
		
		StandardRule rule = new StandardRule(new SquareSearch());
		
		HashSet b = new HashSet();
		b.add(2);
		
		HashSet s = new HashSet();
		
		rule.setBorn(b);
		rule.setSurvive(s);
		
		return rule;
	}
	
	@Override
	public void randomlyFill(Field field) {

		Point size = field.getSize();
		//HashMap<Point, Cell> cells = new HashMap();//field.getCells();
		field.clearCells();
		
		Random random = new Random();

		for(int y = 0; y < size.y; ++y) {
			for(int x = 0; x < size.x; ++x) {

				Point coord = new Point(x,y);

				if( random.nextBoolean() && random.nextBoolean() ){

					//Cell c = new Cell(coord);

					//cells.put(coord, c);

					field.addCell(coord);
				}

			}
		}

		//field.setCells(cells);


//		updateEmergingPlaces(field);
	}
	
	@Override
	public void updateEmergingPlaces(HashMap<Point, Cell> clusterCells, Field field){

		//HashMap<Point, Cell>	cells			= field.getCells();
		/*HashMap<Point, Integer> emergingPlaces	= *///field.getEmergingPlaces().clear(); //new HashMap<>();//
		//emergingPlaces.clear();

		for(Map.Entry<Point, Cell> entry : clusterCells.entrySet()) {

			if(entry.getValue().getState().isAlive()) {
				
				updateEmergingPlace(entry.getKey(), field);
			}
		}
	}
	
	@Override
	public void calculEmergingCells(HashMap<Point, Integer> clusterPlaces, Field field) {
		
		//HashMap<Point, Cell> cells = field.getCells();
		//HashMap<Point, Integer> emergingPlaces = field.getEmergingPlaces();
		
		for(Map.Entry<Point, Integer> entry : clusterPlaces.entrySet()) {

			Integer neighborNumber = entry.getValue();

			if(this._born.contains(neighborNumber)) {
				Point coord = entry.getKey();
				//Cell c = Cell.getEmergingCell(coord);
				
				//synchronized(field) {
					//cells.put(coord, c);
					field.addEmergingCell(coord);
				//}
			}
		}
	}
	
	@Override
	public void calculNextCellsGeneration(HashMap<Point, Cell> clusterCells, Field field) {


		HashMap<Point, Cell> cells = field.getCells();
		
		// Mets à jour l'états suivant de chaque cellules
		for(Map.Entry<Point, Cell> entry : clusterCells.entrySet()) {
			
			Integer neighborNumber = this.getNeighborNumber(field, entry.getKey());

			if( ! this._survive.contains(neighborNumber) ) {
				Cell cell = entry.getValue();
				cell.setNextState(new CellState(false));
			}
		}
	}
	
	@Override
	public void updateCellsState(HashMap<Point, Cell> cells, Field field) {

		Iterator<Map.Entry<Point, Cell>> it = cells.entrySet().iterator();

		while(it.hasNext()) {

			Cell cell = it.next().getValue();

			cell.update();

			if( ! cell.getState().isAlive() ) {
				
				it.remove();
				field.removeCell(cell);
			}
		}
	}
	
	@Override
	public void empty(Field _field) {
		_field.empty();
		/*_field.setCells(new HashMap<Point, Cell>());
		_field.setEmergingPlaces(new HashMap<Point, Integer>());*/
		
	}

	
	
	/**
	 * Récupère le nombre de voisin d'une case.
	 * @param field Le terrain dans lequel se situe la case.
	 * @param place La case en question.
	 */
	private int getNeighborNumber(Field field, Point place) {

		HashMap<Point, Cell> cells = field.getCells();
		//Point neighbor = (Point)place.clone();
		int cpt = 0;

		HashSet<Point> neighbors = _search.getNeighbor(field.getSize().x,field.getSize().y, place);
		
		for(Point n : neighbors) {
			synchronized(cells) {
				if(cells.containsKey(n) && cells.get(n).getState().isAlive()) {
					++cpt;
				}
			}
		}
		

		return cpt;
	}

	/**
	 * Met à jour les voisins d'un endroit .
	 * @param place L'endroit en question.
	 * @param field Le terrain dans lequel évolue les cellules.
	 */
	public void updateEmergingPlace(Point place, Field field) {
		
		HashSet<Point> neighbors = _search.getNeighbor(field.getSize().x, field.getSize().y, place);
		
		for(Point n : neighbors) {
			this.incrementEmergingNeighbor(n, field);
		}
		
	}

	/**
	 * Incrémente le nombre de voisin d'une case.
	 * @param placea case à incrementer.
	 * @param field Le terrain dans lequel évolue les cellules.
	 */
	private synchronized void incrementEmergingNeighbor(Point place, Field field) {

		if( ! field.getCells().containsKey(place) || ! field.getCells().get(place).getState().isAlive() ){

			HashMap<Point, Integer> emergingPlaces = field.getEmergingPlaces();

			Integer n = emergingPlaces.get(place);

			if(n == null) {
				n = new Integer(0);
			}

			n += 1;
			
			field.addEmergingPlace(place, n);
		}
	}

	/**
	 * Récupère la liste des nombres de voisins dont une cellule à besoin pour naitre.
	 * @return Un objet HashSet contenant la liste.
	 */
	public HashSet<Integer> getBorn() {
		return _born;
	}

	/**
	 * Définit la liste des nombres de voisins dont une cellule à besoin pour naitre.
	 * @param born Un objet HashSet contenant la liste.
	 */
	public void setBorn(HashSet<Integer> born) {
		this._born = born;
	}

	/**
	 * Récupère la liste des nombres de voisins dont une cellule à besoin pour survivre.
	 * @return Un objet HashSet contenant la liste.
	 */
	public HashSet<Integer> getSurvive() {
		return _survive;
	}

	/**
	 * Définit la liste des nombres de voisins dont une cellule à besoin pour survivre.
	 * @param survive Un objet HashSet contenant la liste.
	 */
	public void setSurvive(HashSet<Integer> survive) {
		this._survive = survive;
	}

	/**
	 * Récupère l'objet permettant de récupérer les voisins d'une cellule.
	 * @return Un objet Search.
	 */
	public Search getSearch() {
		return _search;
	}

	/**
	 * Définit l'objet permettant de récupérer les voisins d'une cellule.
	 * @param _search Un objet Search.
	 */
	public void setSearch(Search _search) {
		this._search = _search;
	}
}
