/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

import java.awt.Point;
import java.util.HashSet;

/**
 *
 * @author Quentin
 */
public interface Search {
    /**
     * Récupère le nombre de voisin d'une case.
     * @param field Le terrain dans lequel se situe la case.
     * @param place La case en question.
	 * @return hashset de point qui contient les voisins
     */
    public HashSet<Point> getNeighbor(int width, int height, Point place);
	
	/**
	 * Récupère le nombre maximum de voisin fournit par la recherche.
	 * @return Nombre de voisins maximum.
	 */
	public int getNeighborMaximumNumber();
    
	/**
	 * Test si le terrain est un torus ou non
	 * @return boolean True si le terrain est un torus, false sinon.
	 */
	public boolean isTorus();
}
