package model.gameoflife;

/**
 * Etat d'une cellule.
 * @author pierre
 */
public class CellState {
	
	private boolean _isLiving;
	
	/**
	 * Constructeur par défaut.
	 */
	public CellState(){
		this._isLiving = true;
	}
	
	/**
	 * Constructeur permettant de spécifier si la cellule est en vie.
	 * @param live True si vivante, false sinon.
	 */
	public CellState(boolean live){
		this._isLiving = live;
	}

	/**
	 * Verifie si la cellule est en vie.
	 * @return True si la cellule est en vie, false sinon.
	 */
	public boolean isAlive() {
		return _isLiving;
	}

	/**
	 * Change l'état de la cellule par vivante ou morte.
	 * @param live True si la cellule est en vie, false sinon.
	 */
	public void live(boolean live) {
		this._isLiving = live;
	}
	
	
}
