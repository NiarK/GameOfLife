/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Permet d'appliquer les règles standard du jeu de la vie.
 * @author pierre
 */
public class StandardRule implements Rule {

	/**
	 * Constructeur par défaut
	 */
	public StandardRule(){
		
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
		
		//this.getNeighborNumber(field.getCells(), new Point(2,2));
		
		HashMap<Point, Cell> cells = field.getCells();
		
		for(Map.Entry<Point, Cell> entry : cells.entrySet()) {
			int neighborNumber = this.getNeighborNumber(cells, entry.getKey());
			
			//if(neighborNumber )
		}
		
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
	
}
