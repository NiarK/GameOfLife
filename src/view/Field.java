/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.gameoflife.GameExecution;

/**
 *
 * @author pierre
 */
public final class Field extends JPanel implements Observer/*, Runnable */ {

	/*private HashMap<Point, Cell> _cells;
	private Point _size;
	private int _cellSize;
	private Point _position;
	private Point _offset;
	private Point _indicator;
	private double _zoom;
	private Point _oldComponentSize;*/
	
	private FieldDrawManager _drawer;
	
	public static double ZOOM_UNIT = 0.9;
	

	//private view.Cell c;
	public Field(int neighbors) {
		super();

		/*CellImageParameter p = new CellImageParameter("cell.png", new Point(10,10), 5,5,5);
		 c = new view.Cell(new CellState(), p);
		 */
		/*_cells = new HashMap<>();

		_cellSize = 15;

		_position = new Point(this.getWidth() / 2, this.getHeight() / 2);

		_offset = new Point(0, 0);

		_size = new Point(1, 1);

		_indicator = null;

		_zoom = 1;

		_oldComponentSize = new Point(this.getWidth(), this.getHeight());*/

		_drawer = new FieldDrawManager(new Point(this.getWidth(), this.getHeight()));
		_drawer.setCellSize(10);
		/*_drawer.setCellSize(15);
		_drawer.setComponentSize(new Point(this.getWidth(), this.getHeight()));
		_drawer.setFieldSize(_size);
		_drawer.setIndicator(_indicator);
		_drawer.setOffset(_offset);
		_drawer.setPosition(_position);
		_drawer.setZoom(_zoom);*/
		
		
		//this.setNeighbors(neighbors);
		//_exec = true;

	}

	@Override
	public synchronized void paintComponent(Graphics g) {

		_drawer.draw(g);
		
		/*g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		//TODO: Mettre ce calcul autre part
		_offset.x = _position.x - (int) (_size.x * _cellSize * _zoom) / 2;
		_offset.y = _position.y - (int) (_size.y * _cellSize * _zoom) / 2;

		if (_indicator != null) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(
					(int) (_cellSize * _zoom) * _indicator.x + _offset.x,
					(int) (_cellSize * _zoom) * _indicator.y + _offset.y,
					/*(int) ((_cellSize * _indicator.x + _offset.x) * _zoom),
					 (int) ((_cellSize * _indicator.y + _offset.y) * _zoom),*
					(int) (_cellSize * _zoom),
					(int) (_cellSize * _zoom));
		}

		g.setColor(Color.DARK_GRAY);
		g.drawRect(
				_offset.x,
				_offset.y,
				_size.x * (int) (_cellSize * _zoom),
				_size.y * (int) (_cellSize * _zoom));

		g.setColor(Color.red);

		Point p1 = new Point();
		Point p2 = new Point();

		p1.x = (int) (-_offset.x / _zoom) / _cellSize;
		p1.y = (int) (-_offset.y / _zoom) / _cellSize;
		p2.x = (int) ((this.getWidth() - _offset.x) / (int)(_cellSize * _zoom));
		p2.y = (int) ((this.getHeight() - _offset.y) / (int)(_cellSize * _zoom));
		
		for (Entry<Point, Cell> entry : _cells.entrySet()) {
			Point coord = entry.getKey();
			if (coord.x >= p1.x
				&& coord.y >= p1.y
				&& coord.x <= p2.x
				&& coord.y <= p2.y) {
				
				Point position = new Point(_position);
				position.x = (int) (_cellSize * _zoom) * coord.x + _offset.x;
				position.y = (int) (_cellSize * _zoom) * coord.y + _offset.y;
				
				int size = (int) (_cellSize * _zoom);
				_cell.draw(g, position, size);
				/*g.fillOval(
						(int) (_cellSize * _zoom) * coord.x + _offset.x,
						(int) (_cellSize * _zoom) * coord.y + _offset.y,
						/*(int) ((_cellSize * coord.x + _offset.x) * _zoom), 
						 (int) ((_cellSize * coord.y + _offset.y) * _zoom), *
						(int) (_cellSize * _zoom),
						(int) (_cellSize * _zoom));*

			}
		}*/
	}

	@Override
	public synchronized void update(Observable o, Object o1) {

		if (o instanceof GameExecution) {
			GameExecution game = (GameExecution) o;
			
			_drawer.setCells(game.getCells());
			_drawer.setFieldSize(game.getFieldSize());
			/*_cells = game.getCells();
			_size = game.getFieldSize();*/

			this.repaint();
		}
	}

	public synchronized void setNeighbors(int n) {
		System.out.println(n);
		if ( n == 6 ) {
			_drawer = new HexagonalDrawManager(_drawer);
		}
		else if ( n == 3 || n == 12) {
			_drawer = new TriangularDrawManager(_drawer);
		}
		else {
			_drawer = new FieldDrawManager(_drawer);
		}
	}
	
	/**
	 * Retourne la position de la cellule a partir d'une position dans le composant.
	 * @param coord La position dans le composant.
	 * @return La coordonnée de la céllule
	 */
	/*public Point cellCoordinate(Point coord) {
		Point cell = new Point();
		cell.x = (coord.x - _offset.x - 1) / (int) (_cellSize * _zoom);
		cell.y = (coord.y - _offset.y - 1) / (int) (_cellSize * _zoom);
		return cell;
	}*/

	public void setIndicatorPosition(Point coord) {
		if(_drawer.setIndicatorPosition(coord)){
			this.repaint();
		}
		/*coord = this.cellCoordinate(coord);
		if (isInsideTheField(coord)) {
			if (!coord.equals(_indicator)) {
				_indicator = coord;

				this.repaint();
			}
		} else {
			_indicator = null;
		}*/
	}

	/*public boolean isInsideTheField(Point coord) {
		return coord.x >= 0 && coord.x < _size.x
			&& coord.y >= 0 && coord.y < _size.y;
	}*/

	public void moveField(Point movement) {
		/*_offset.x += movement.x;
		 _offset.y += movement.y;*/
		_drawer.moveField(movement);
		/*_position.x += movement.x;
		_position.y += movement.y;
		_offset.x = _position.x - (int) (_size.x * _cellSize * _zoom) / 2;
		_offset.y = _position.y - (int) (_size.y * _cellSize * _zoom) / 2;*/

		this.repaint();
	}

	public void zoom(int unit) {
		
		_drawer.zoom(unit);
		
		/*_zoom *= Math.pow(Field.ZOOM_UNIT, unit);
		//System.out.println(_zoom);

		if (_zoom > 1) {
			_zoom = 1;
		} else if (_zoom < 0.2) {
			_zoom = 0.2;
		}

		_drawer.setZoom(_zoom);*/
		
		this.repaint();
	}

	public Point getIndicator() {
		return _drawer.getIndicator();//_indicator;
	}

	public void resize() {
		
		_drawer.setComponentSize(new Point(this.getWidth(),this.getHeight()));
		_drawer.resize();
		/*Point size = new Point();
		size.x = this.getWidth() - _oldComponentSize.x;
		size.y = this.getHeight() - _oldComponentSize.y;

		_position.x += size.x / 2;
		_position.y += size.y / 2;

		_oldComponentSize.x = this.getWidth();
		_oldComponentSize.y = this.getHeight();*/
	}
	
	public void setPattern(HashSet<Point> _pattern) {
		_drawer.setPattern(_pattern);
	}
}
