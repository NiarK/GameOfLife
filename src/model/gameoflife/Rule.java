package model.gameoflife;

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
	
	/**
	 * Vide le terreain
	 * @param field Le terrain en question
	 */
	public void empty(Field field);
}
