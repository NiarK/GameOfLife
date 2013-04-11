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
	 * @return le terrain initialiser.
	 */
	public abstract Field randomlyFill(Field field);
	
	/**
	 * Mets à jour un terrain.
	 * @param field Le terrain a mettre à jour
	 * @return Le terrain mis à jour.
	 */
	public abstract Field update(Field field);
}
