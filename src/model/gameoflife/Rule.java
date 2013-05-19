package model.gameoflife;

import java.awt.Point;
import java.util.HashMap;

/**
 * Cette classe permet d'appliquer les règles à un terrains.
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
	 * @param clusterCells Groupe de cellules sur lequel il faut appliquer la fonction.
	 * @param field Le terrain à mettre à jour.
	 */
	public abstract void updateEmergingPlaces(HashMap<Point, Cell> clusterCells, Field field);
	
	/**
	 * Mets à jour la case si elle doit naitre.
	 * @param clusterPlaces Groupe de positions sur lequel il faut appliquer la fonction.
	 * @param field Le terrain à mettre à jour.
	 */
	public abstract void calculEmergingCells(HashMap<Point, Integer> clusterPlaces, Field field);
	
	/**
	 * Calcule si les cellules doive,t survivre ou mourir et mets à jour leurs états suivants.
	 * @param clusterCells Groupe de cellules sur lequel il faut appliquer la fonction.
	 * @param field Le terrain à mettre à jour.
	 */
	public abstract void calculNextCellsGeneration(HashMap<Point, Cell> clusterCells, Field field);
	
	/**
	 * Mets à jour l'état courant avec l'état suivant de toutes les cellules.
	 * @param clusterCells Groupe de cellules sur lequel il faut appliquer la fonction.
	 * @param field Le terrain à mettre à jour.
	 */
	public abstract void updateCellsState(HashMap<Point, Cell> clusterCells, Field field);
	
	/**
	 * Vide le terrain.
	 * @param field Le terrain en question
	 */
	public void empty(Field field);
	
	/**
	 * Récupère la recherche.
	 * @return search la recherche utilisée
	 */
	public Search getSearch();
}
