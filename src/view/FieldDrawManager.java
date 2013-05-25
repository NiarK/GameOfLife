package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import model.gameoflife.Cell;
import model.gameoflife.Pattern;

/**
 * Cette classe gère le dessin du terrain. Elle est utilisé par la classe view.Field.
 * @author pierre
 */
public class FieldDrawManager {
	
	protected HashMap<Point, Cell> _cells;
	protected Point _fieldSize;
	protected int _cellSize;
	protected Point _position;
	protected Point _offset;
	protected Point _indicator;
	protected double _zoom;
	protected Point _oldComponentSize;
	protected Point _componentSize;
	protected Pattern _pattern;
	protected boolean _torus;
	
	public static double ZOOM_UNIT = 0.9;
	

	/**
	 * Construit le gestionnaire de dessin.
	 */
	public FieldDrawManager() {
		
		_cells = new HashMap<>();

		_cellSize = 1;
		
		_componentSize = new Point(0,0);

		_position = new Point(_componentSize.x / 2, _componentSize.y / 2);

		_offset = new Point(0, 0);

		_fieldSize = new Point(1, 1);

		_indicator = null;

		_zoom = 1;

		_oldComponentSize = new Point(_componentSize.x, _componentSize.y);
		
		_pattern = null;
		
		_torus = false;

	}

	/**
	 * Constructeur par copie.
	 * @param fdm L'objet à copier.
	 */
	FieldDrawManager(FieldDrawManager fdm) {
		_cells = fdm._cells;
		_fieldSize = fdm._fieldSize;
		_cellSize = fdm._cellSize;
		_position = fdm._position;
		_offset = fdm._offset;
		_indicator = fdm._indicator;
		_zoom = fdm._zoom;
		_oldComponentSize = fdm._oldComponentSize;
		_componentSize = fdm._componentSize;
		_pattern = fdm._pattern;
		_torus = fdm._torus;
	}

	/**
	 * dessine les bordure du terrain.
	 * @param g L'objet Graphics permettant de dessiner.
	 */
	protected synchronized void drawBorder(Graphics g) {
		g.drawRect(
			_offset.x,
			_offset.y,
			_fieldSize.x * (int) (_cellSize * _zoom),
			_fieldSize.y * (int) (_cellSize * _zoom));
	}
	
	/**
	 * Dessine l'indicateur sur le terrain.
	 * @param g L'objet Graphics permettant de dessiner.
	 */
	protected synchronized void drawIndicator(Graphics g) {
		g.drawRect(
			(int) (_cellSize * _zoom) * _indicator.x + _offset.x,
			(int) (_cellSize * _zoom) * _indicator.y + _offset.y,
			(int) (_cellSize * _zoom),
			(int) (_cellSize * _zoom));
	}
	
	/**
	 * Dessine le modèle sur le terrain.
	 * @param g L'objet Graphics permettant de dessiner.
	 */
	protected synchronized void drawPattern(Graphics g) {
		Iterator<Point> it = _pattern.getCellsByMiddle().iterator();
		while (it.hasNext()) {
			Point t = it.next();
			Point temp = (Point) t.clone();
			if(isInsideTheField(new Point(_indicator.x + temp.x, _indicator.y + temp.y))){
					this.drawPoint(g, temp);
			}
			else{
				if(this.isTorus()){
					temp = calculateTorus(temp);
					this.drawPoint(g, temp);
				}
			}
		}
	}
	
	/**
	 * Dessine une cellule du modèle.
	 * @param g L'objet Graphics permettant de dessiner. 
	 * @param p Les coordonnées du point à dessiner.
	 */
	protected synchronized void drawPoint(Graphics g, Point p){
		g.fillOval(
				(int) (_cellSize * _zoom) * (_indicator.x + p.x) + _offset.x,
				(int) (_cellSize * _zoom) * (_indicator.y + p.y) + _offset.y,
				(int) (_cellSize * _zoom),
				(int) (_cellSize * _zoom));
	}
	
	/**
	 * Si le terrain est un tore, cette fonction permet de calculer la position d'une cellule du modèle si celle ci déborde du terrain.
	 * @param p Les coordonnées de la cellule.
	 * @return Les nouvelles coordonnées dans le terrain.
	 */
	protected synchronized Point calculateTorus(Point p){
		if((_indicator.x + p.x) >= _fieldSize.x){
			p.x = (_indicator.x + p.x) % _fieldSize.x - _indicator.x;
		}
		else{
			while((_indicator.x + p.x) < 0){
				p.x = p.x + _fieldSize.x;
			}
		}
		if((_indicator.y + p.y) >= _fieldSize.y){
			p.y = (_indicator.y + p.y) % _fieldSize.y - _indicator.y;					
		}
		else{
			while((_indicator.y + p.y) < 0){
				p.y = p.y + _fieldSize.y;
			}
		}
		return p;
	}
	
	/**
	 * Dessine les cellules sur le terrain. Les arguments permettent de limiter le dessin entre le pont p1 et p2.
	 * @param g L'objet Graphics permettant de dessiner.
	 * @param p1 Les premières coordonnées.
	 * @param p2 Les dexièmes coordonnées.
	 */
	protected synchronized void drawCells(Graphics g, Point p1, Point p2) {
		
		for (Map.Entry<Point, Cell> entry : _cells.entrySet()) {
			Point coord = entry.getKey();
			if (coord.x >= p1.x
				&& coord.y >= p1.y
				&& coord.x <= p2.x
				&& coord.y <= p2.y) {
				
				Point position = new Point(_position);
				position.x = (int) (_cellSize * _zoom) * coord.x + _offset.x;
				position.y = (int) (_cellSize * _zoom) * coord.y + _offset.y;
				
				g.fillOval(
						position.x,
						position.y,
						(int) (_cellSize * _zoom),
						(int) (_cellSize * _zoom));
			}
		}
	}
	
	/**
	 * Dessine le terrain.
	 * @param g L'objet Graphics permettant de dessiner.
	 */
	public synchronized void draw(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, _componentSize.x, _componentSize.y);

		//TODO: Mettre ce calcul autre part
		_offset.x = _position.x - (int) (_fieldSize.x * _cellSize * _zoom) / 2;
		_offset.y = _position.y - (int) (_fieldSize.y * _cellSize * _zoom) / 2;


		g.setColor(Color.DARK_GRAY);
		this.drawBorder(g);

		Point p1 = new Point();
		Point p2 = new Point();

		p1.x = (int) (-_offset.x / _zoom) / _cellSize;
		p1.y = (int) (-_offset.y / _zoom) / _cellSize;
		p2.x = (int) ((_componentSize.x - _offset.x) / (int)(_cellSize * _zoom));
		p2.y = (int) ((_componentSize.y - _offset.y) / (int)(_cellSize * _zoom));
		
		g.setColor(Color.red);
		this.drawCells(g, p1, p2);
		
		
		if (_indicator != null) {
			if(_pattern == null) {
				g.setColor(Color.LIGHT_GRAY);
				this.drawIndicator(g);
			}
			else {
				g.setColor(new Color(.3f, .4f, .5f, .6f));
				this.drawPattern(g);
			}
			
			g.setColor(new Color(200, 200, 200, 200));
			g.setFont(new Font("Tahoma",Font.PLAIN,11));
			String info = "x:" + _indicator.x + " \t y:" + _indicator.y;
			g.drawString(info, _componentSize.x - info.length() * 6, _componentSize.y - 10);
		}
	}

	
	/**
	 * Retourne la position de la cellule a partir d'une position dans le composant.
	 * @param coord La position dans le composant.
	 * @return La coordonnée de la céllule.
	 */
	public Point cellCoordinate(Point coord) {
		Point cell = new Point();
		if(coord.x < _offset.x){
			cell.x = -1;
		}
		else{
			cell.x = (coord.x - _offset.x) / (int) (_cellSize * _zoom);
		}
		if(coord.y < _offset.y){
			cell.y = -1;
		}
		else{
			cell.y = (coord.y - _offset.y) / (int) (_cellSize * _zoom);
		}
		return cell;
	}

	/**
	 * Change la position de l'indicateur en fonction des coordonées dans le composant.
	 * @param coord Coordonnées dans le composant.
	 * @return True si il faut repaindre le composant, false sinon.
	 */
	public boolean setIndicatorPosition(Point coord) {
		boolean repaint = false;
		if(coord == null){
			_indicator = null;
			repaint = true;
		}
		else{
			coord = this.cellCoordinate(coord);
			if (isInsideTheField(coord)) {
				if (!coord.equals(_indicator)) {
					_indicator = coord;

					repaint = true;
				}
			} else {
				_indicator = null;
			}
		}
		return repaint;
	}

	/**
	 * Permet de savoir si les coordonnées sont dans le terrain.
	 * @param coord Les coordonnées à tester.
	 * @return True ou false.
	 */
	public boolean isInsideTheField(Point coord) {
		return coord.x >= 0 && coord.x < _fieldSize.x
			&& coord.y >= 0 && coord.y < _fieldSize.y;
	}

	/**
	 * Déplace l'affichage du terrain.
	 * @param movement Le mouvement à effectuer.
	 */
	public void moveField(Point movement) {
		_position.x += movement.x;
		_position.y += movement.y;
		_offset.x = _position.x - (int) (_fieldSize.x * _cellSize * _zoom) / 2;
		_offset.y = _position.y - (int) (_fieldSize.y * _cellSize * _zoom) / 2;

	}

	/**
	 * Zoom ou dézoome l'affichage du terrain.
	 * @param unit L'unité de zoom.
	 */
	public void zoom(int unit) {
		_zoom *= Math.pow(Field.ZOOM_UNIT, unit);
		
		if (_zoom > 1) {
			_zoom = 1;
		} else if (_zoom < 0.2) {
			_zoom = 0.2;
		}

	}

	/**
	 * Récupère la position de l'indicateur.
	 * @return La position de l'indicateur.
	 */
	public Point getIndicator() {
		return _indicator;
	}

	/**
	 * Centre l'affichage lorsqu'il y a eu un redimensionnement du composant.
	 */
	public void resize() {
		Point size = new Point();
		size.x = _componentSize.x - _oldComponentSize.x;
		size.y = _componentSize.y - _oldComponentSize.y;

		_position.x += size.x / 2;
		_position.y += size.y / 2;

		_oldComponentSize.x = _componentSize.x;
		_oldComponentSize.y = _componentSize.y;
	}

	/**
	 * Définit les cellules à afficher.
	 * @param cells Un objet HashMap contenant les cellules à afficher.
	 */
	public void setCells(HashMap<Point, Cell> cells) {
		this._cells = cells;
	}


	/**
	 * Définit la taille du terrain.
	 * @param fieldSize La nouvelle taille.
	 */
	public void setFieldSize(Point fieldSize) {
		this._fieldSize = fieldSize;
	}

	/**
	 * Récupère la taille du terrain.
	 * @return La taille du terrain.
	 */
	public int getCellSize() {
		return _cellSize;
	}

	/**
	 * Définit la taille du cellule.
	 * @param cellSize Taille d'une cellule en pixel.
	 */
	public void setCellSize(int cellSize) {
		this._cellSize = cellSize;
	}

	/**
	 * Récupère la position du terrain.
	 * @return La position du terrain en pixel.
	 */
	public Point getPosition() {
		return _position;
	}

	/**
	 * Définit la position du terrain.
	 * @param position La nouvelle position en pixel.
	 */
	public void setPosition(Point position) {
		this._position = position;
	}

	/**
	 * Récupère le niveau de zoom.
	 * @return Le niveau de zoom.
	 */
	public double getZoom() {
		return _zoom;
	}

	/**
	 * Définie le niveau de zoom.
	 * @param zoom Le niveau de zoom.
	 */
	public void setZoom(double zoom) {
		this._zoom = zoom;
	}

	/**
	 * Définit la nouvelle taille du composant à prendre en compte.
	 * @param componentSize La nouvelle taille.
	 */
	public void setComponentSize(Point componentSize) {
		this._componentSize = componentSize;
	}

	/**
	 * Rècupère le modèle qui doit etre prévisualiser.
	 * @return Le modèle.
	 */
	public Pattern getPattern() {
		return _pattern;
	}

 
	/**
	* Définit le modèle qui doit etre prévisualisé.
	* @param _pattern Le nouveau modèle.
	*/
	public void setPattern(Pattern _pattern) {
		this._pattern = _pattern;
		
	}
	
	public void verticalSymmetry(){
		if(_pattern != null){
			_pattern.verticalSymmetry();
		}
	}
	
	public void horizontalSymmetry(){
		if(_pattern != null){
			_pattern.horizontalSymmetry();
		}
	}
	
	public void rotateRight(){
		if(_pattern != null){
			_pattern.rotateRight();
		}
	}
	
	public void rotateLeft(){
		if(_pattern != null){
			_pattern.rotateLeft();
		}
	}

	public boolean isTorus() {
		return _torus;
	}

	public void setTorus(boolean _torus) {
		this._torus = _torus;
	}

	boolean isPatternDefine() {
		return (_pattern != null)?true:false;
	}
	
}
