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
	 */
	public StandardRule(Search search){
		this._born = new HashSet<>();
		this._survive = new HashSet<>();
		
		this._search = search;
	}

	/**
	 * Initialise les règles pour le jeu de la vie standard
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
	 * Initialise les règles pour le jeu de la vie : HighLife
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
	 * Initialise les règles pour le jeu de la vie
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
	 * Initialise les règles pour le jeu de la vie
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
	public Field randomlyFill(Field field) {

		Point size = field.getSize();
		HashMap<Point, Cell> cells = new HashMap();//field.getCells();

		Random random = new Random();

		for(int y = 0; y < size.y; ++y) {
			for(int x = 0; x < size.x; ++x) {

				Point coord = new Point(x,y);

				if( random.nextBoolean() && random.nextBoolean() ){

					Cell c = new Cell(coord);

					cells.put(coord, c);

				}

			}
		}

		field.setCells(cells);


		return updateEmergingPlaces(field);
	}

	@Override
	public Field update(Field field) {

		this.updateEmergingPlaces(field);

		HashMap<Point, Cell> cells = field.getCells();
		HashMap<Point, Integer> emergingPlaces = field.getEmergingPlaces();

		for(Map.Entry<Point, Cell> entry : cells.entrySet()) {
			Integer neighborNumber = this.getNeighborNumber(field, entry.getKey());

			if( ! this._survive.contains(neighborNumber) ) {
				Cell cell = entry.getValue();
				cell.setNextState(new CellState(false));
			}
		}

		for(Map.Entry<Point, Integer> entry : emergingPlaces.entrySet()) {

			Integer neighborNumber = entry.getValue();

			if(this._born.contains(neighborNumber)) {
				Point coord = entry.getKey();
				Cell c = new Cell(coord);
				cells.put(coord, c);
			}
		}

		this.updateCells(cells);
		//field.setCells(cells);
		
		return field;
	}

	@Override
	public void empty(Field _field) {
		_field.setCells(new HashMap<Point, Cell>());
		_field.setEmergingPlaces(new HashMap<Point, Integer>());
	}

	
	
	/**
	 * Récupère le nombre de voisin d'une case.
	 * @param field Le terrain dans lequel se situe la case.
	 * @param place La case en question.
	 */
	private int getNeighborNumber(Field field, Point place) {

		HashMap<Point, Cell> cells = field.getCells();
		Point neighbor = (Point)place.clone();
		int cpt = 0;

		HashSet<Point> neighbors = _search.getNeighbor(field.getSize().x,field.getSize().y, place);
		
		for(Point n : neighbors) {
			if(cells.containsKey(n)) {
				++cpt;
			}
		}
		
		/*// Voisin en haut à gauche.
		neighbor.x -= 1;
		neighbor.y -= 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}

		// Voisin en haut.
		neighbor.x += 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}

		// Voisin en haut à droite.
		neighbor.x += 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}

		// Voisin à droite.
		neighbor.y += 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}

		// Voisin en bas à droite.
		neighbor.y += 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}

		// Voisin en bas.
		neighbor.x -= 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}

		// Voisin en bas à gauche.
		neighbor.x -= 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}

		// Voisin à gauche.
		neighbor.y -= 1;
		if(cells.containsKey(neighbor)) {
			++cpt;
		}*/

		return cpt;
	}

	/**
	 * Mets à jour les cases où les cellules vont naitre.
	 * @param field Le terrain à mettre à jour.
	 * @return Le terrain mis à jour.
	 */
	private Field updateEmergingPlaces(Field field){

		HashMap<Point, Cell>	cells			= field.getCells();
		HashMap<Point, Integer> emergingPlaces	= field.getEmergingPlaces(); //new HashMap<>();//
		emergingPlaces.clear();

		for(Map.Entry<Point, Cell> entry : cells.entrySet()) {

			updateEmergingPlace(entry.getKey(), field);

		}

		return field;
	}

	/**
	 * Met à jour les voisins émergeants d'un endroit.
	 * @param neighbor L'endroit en question.
	 * @param field Le terrain dans lequel évolue les cellules.
	 */
	public void updateEmergingPlace(Point place, Field field) {
		
		Point size = field.getSize();
		place = (Point)place.clone();

		HashSet<Point> neighbors = _search.getNeighbor(field.getSize().x, field.getSize().y, place);
		
		for(Point n : neighbors) {
			this.incrementEmergingNeighbor(n, field);
		}
		
		/*// gestion du voisin au dessus à gauche
		place.x -= 1;
		place.y -= 1;

		if(place.x >= 0 && place.y >= 0) {

			incrementEmergingNeighbor(place, field);
		}

		// Gestion du voisin au dessus
		place.x += 1;

		if(place.y >= 0) {

			incrementEmergingNeighbor(place, field);
		}

		// Voisin au dessus à droite
		place.x += 1;

		if(place.x < size.x && place.y >= 0) {

			incrementEmergingNeighbor(place, field);
		}

		// Voisin à droite
		place.y += 1;

		if(place.x < size.x) {

			incrementEmergingNeighbor(place, field);
		}

		// Voisin en dessous à droite
		place.y += 1;

		if(place.x < size.x && place.y < size.y) {

			incrementEmergingNeighbor(place, field);
		}

		// Voisin en dessous
		place.x -= 1;

		if(place.y < size.y) {

			incrementEmergingNeighbor(place, field);
		}

		// Voisin en dessous à gauche
		place.x -= 1;

		if(place.x >= 0 && place.y < size.y) {

			incrementEmergingNeighbor(place, field);
		}

		// Voisin à gauche
		place.y -= 1;

		if(place.x >= 0) {

			incrementEmergingNeighbor(place, field);
		}*/
	}
	
	/**
	 * Mets à jour l'état de toutes les cellules.
	 * @param cells Cellules a mettre à jour.
	 */
	private void updateCells(HashMap<Point, Cell> cells) {

		Iterator<Map.Entry<Point, Cell>> it = cells.entrySet().iterator();

		while(it.hasNext()) {

			Cell cell = it.next().getValue();

			cell.update();

			if( ! cell.getState().isAlive() ) {

				it.remove();
			}
		}
	}

	private void incrementEmergingNeighbor(Point neighbor, Field field) {

		if( ! field.getCells().containsKey(neighbor) ){

			HashMap<Point, Integer> emergingPlaces = field.getEmergingPlaces();

			Integer n = emergingPlaces.get(neighbor);

			if(n == null) {
				n = new Integer(0);
			}

			n += 1;

			emergingPlaces.put((Point)neighbor.clone(), n);
		
		}
		//System.out.println(field);
	}

	public HashSet<Integer> getBorn() {
		return _born;
	}

	public void setBorn(HashSet<Integer> born) {
		this._born = born;
	}

	public HashSet<Integer> getSurvive() {
		return _survive;
	}

	public void setSurvive(HashSet<Integer> survive) {
		this._survive = survive;
	}
}
