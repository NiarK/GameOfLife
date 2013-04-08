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
					CellState state = new CellState();
					Cell c = new Cell(coord, state);

					cells.put(coord, c);
					
				}
				
			}
		}
		
		field.setCells(cells);
		
		
		return updateEmergingPlaces(field);
	}

	@Override
	public Field update(Field field) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
				
				incrementNeighbor(neighbor, field);
			}
			
			// Gestion du voisin au dessus
			neighbor.x += 1;
			
			if(neighbor.y >= 0) {
				
				incrementNeighbor(neighbor, field);
			}
			
			// Voisin au dessus à droite
			neighbor.x += 1;
			
			if(neighbor.x < size.x && neighbor.y >= 0) {
				
				incrementNeighbor(neighbor, field);
			}
			
			// Voisin à droite
			neighbor.y += 1;
			
			if(neighbor.x < size.x) {
				
				incrementNeighbor(neighbor, field);
			}
			
			// Voisin en dessous à droite
			neighbor.y += 1;
			
			if(neighbor.x < size.x && neighbor.y < size.y) {
				
				incrementNeighbor(neighbor, field);
			}
			
			// Voisin en dessous
			neighbor.x -= 1;
			
			if(neighbor.y < size.y) {
				
				incrementNeighbor(neighbor, field);
			}
			
			// Voisin en dessous à gauche
			neighbor.x -= 1;
			
			if(neighbor.x >= 0 && neighbor.y < size.y) {
				
				incrementNeighbor(neighbor, field);
			}
			
			// Voisin à gauche
			neighbor.y -= 1;
			
			if(neighbor.x >= 0) {

				incrementNeighbor(neighbor, field);
			}
			
		}
		//System.out.println(emergingPlaces);
		
		//field.setEmergingPlaces(emergingPlaces);
		
		return field;
	}
	
	private void incrementNeighbor(Point neighbor, Field field) {
	
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
	
	//public void updateEmergingPlaces(Field field, Point place);
	
	/*public void updateNighCases(){
		//TODO: a refaire !
		HashMap<Point, Cell> cells = this._field.getCells();
		HashMap<Point, Integer> nighCases = this._field.getEmergingPlaces();
		
		Point size = this._field.getSize();
		
		for(Map.Entry<Point, Cell> entry : cells.entrySet()) {
			
			Point cell = entry.getKey();
			Point nearCell = cell;
			
			boolean ok = true;
			
			if(cell.x - 1 >= 0 ) {
				nearCell = cell;
				nearCell.x = cell.x - 1;
				
				incrementNeighborOnCase(nearCell);
			}
			
			if(cell.x + 1 < size.x ) {
				nearCell = cell;
				nearCell.x = cell.x + 1;
				
				incrementNeighborOnCase(nearCell);
			}
			
			if(cell.y - 1 >= 0 ) {
				nearCell = cell;
				nearCell.y = cell.y - 1;
				
				incrementNeighborOnCase(nearCell);
			}
			
			if(cell.y + 1 < size.y ) {
				nearCell = cell;
				nearCell.y = cell.y + 1;
				
				incrementNeighborOnCase(nearCell);
			}
		}
		
	}
	
	public void incrementNeighborOnCase(Point c) {
		
		HashMap<Point, Integer> nighCases = this._field.getEmergingPlaces();
		
		Integer neighbor = nighCases.get(c);
				
		if(neighbor == null) {
			neighbor = 0;
		}

		neighbor += 1;

		nighCases.put(c, neighbor);
	}*/
}
