package model.gameoflife;

import java.awt.Point;
import java.util.HashMap;

/**
 * Permet d'appliquer les règles.
 * @author pierre
 */
public interface Rule {

	/**
	 * Remplie le terrain de façon aléatoire.
	 * @param field Le terrain à initialiser.
	 */
	public abstract void randomlyFill(Field field);
	
	/**
	 * Mets à jour les cases où les cellules vont naitre.
	 * @param field Le terrain à mettre à jour.
	 */
	public abstract void updateEmergingPlaces(HashMap<Point, Cell> clusterCells, Field field);
	
	public abstract void calculEmergingCells(HashMap<Point, Integer> clusterPlaces, Field field);
	
	public abstract void calculNextCellsGeneration(HashMap<Point, Cell> clusterCells, Field field);
	
	/**
	 * Mets à jour l'état de toutes les cellules.
	 * @param cells Cellules a mettre à jour.
	 */
	public abstract void updateCellsState(HashMap<Point, Cell> cells, Field field);
	
	/**
	 * Vide le terrain
	 * @param field Le terrain en question
	 */
	public void empty(Field field);
	
	public Search getSearch();
}
