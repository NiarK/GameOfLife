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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Permet d'appliquer les règles standard du jeu de la vie.
 * @author pierre
 */
public class StandardRule implements Rule {

	private HashSet<Integer> _born;
	private HashSet<Integer> _survive;
	
	/**
	 * Constructeur par défaut
	 */
	public StandardRule(){
		this._born = new HashSet<>();
		this._survive = new HashSet<>();
	}

	/**
	 * Initialise les règles pour le jeu de la vie standard
	 * Born : 3
	 * Survive : 2 et 3
	 */
	public void ruleStandard() {
		this._born.clear();
		this._born.add(3);
		
		this._survive.clear();
		this._survive.add(2);
		this._survive.add(3);
	}
	
	/**
	 * Initialise les règles pour le jeu de la vie : HighLife
	 * Born : 3 et 6
	 * Survive : 2 et 3
	 */
	public void ruleHighLife() {
		this._born.clear();
		this._born.add(3);
		this._born.add(6);
		
		this._survive.clear();
		this._survive.add(2);
		this._survive.add(3);
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
	public synchronized Field update(Field field) {

		//this.getNeighborNumber(field.getCells(), new Point(2,2));

		HashMap<Point, Cell> cells = field.getCells();
		HashMap<Point, Integer> emergingPlaces = field.getEmergingPlaces();

		for(Map.Entry<Point, Cell> entry : cells.entrySet()) {
			Integer neighborNumber = this.getNeighborNumber(cells, entry.getKey());

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
		this.updateEmergingPlaces(field);
		field.setCells(cells);
		
		return field;
	}

	/**
	 * Récupère le nombre de voisin d'une case.
	 * @param field Le terrain dans lequel se situe la case.
	 * @param place La case en question.
	 */
	private int getNeighborNumber(HashMap<Point, Cell> cells, Point place) {

		//HashMap<Point, Cell> cells = field.getCells();
		Point neighbor = (Point)place.clone();
		int cpt = 0;

		// Voisin en haut à gauche.
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
		}

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

		Point size = field.getSize();

		Point neighbor;

		for(Map.Entry<Point, Cell> entry : cells.entrySet()) {

			neighbor = (Point)entry.getKey().clone();

			// gestion du voisin au dessus à gauche
			neighbor.x -= 1;
			neighbor.y -= 1;

			if(neighbor.x >= 0 && neighbor.y >= 0) {

				incrementEmergingNeighbor(neighbor, field);
			}

			// Gestion du voisin au dessus
			neighbor.x += 1;

			if(neighbor.y >= 0) {

				incrementEmergingNeighbor(neighbor, field);
			}

			// Voisin au dessus à droite
			neighbor.x += 1;

			if(neighbor.x < size.x && neighbor.y >= 0) {

				incrementEmergingNeighbor(neighbor, field);
			}

			// Voisin à droite
			neighbor.y += 1;

			if(neighbor.x < size.x) {

				incrementEmergingNeighbor(neighbor, field);
			}

			// Voisin en dessous à droite
			neighbor.y += 1;

			if(neighbor.x < size.x && neighbor.y < size.y) {

				incrementEmergingNeighbor(neighbor, field);
			}

			// Voisin en dessous
			neighbor.x -= 1;

			if(neighbor.y < size.y) {

				incrementEmergingNeighbor(neighbor, field);
			}

			// Voisin en dessous à gauche
			neighbor.x -= 1;

			if(neighbor.x >= 0 && neighbor.y < size.y) {

				incrementEmergingNeighbor(neighbor, field);
			}

			// Voisin à gauche
			neighbor.y -= 1;

			if(neighbor.x >= 0) {

				incrementEmergingNeighbor(neighbor, field);
			}

		}
		//System.out.println(emergingPlaces);

		//field.setEmergingPlaces(emergingPlaces);

		return field;
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

			if( ! cell.getState().isLiving() ) {

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
