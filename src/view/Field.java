/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.gameoflife.Cell;
import model.gameoflife.GameExecution;

/**
 *
 * @author pierre
 */
public class Field extends JPanel implements Observer/*, Runnable */ {

	private HashMap<Point, Cell> _cells;
	private Point _size;
	private int _cellSize;
	private Point _position;
	private Point _offset;
	private Point _indicator;
	private double _zoom;
	private Point _oldComponentSize;
	//private boolean _exec;

	//private view.Cell c;
	public Field() {
		super();

		/*CellImageParameter p = new CellImageParameter("cell.png", new Point(10,10), 5,5,5);
		 c = new view.Cell(new CellState(), p);
		 */
		_cells = new HashMap<>();

		_cellSize = 15;

		_position = new Point(this.getWidth() / 2, this.getHeight() / 2);

		_offset = new Point(0, 0);

		_size = new Point(0, 0);

		_indicator = null;

		_zoom = 1;

		_oldComponentSize = new Point(this.getWidth(), this.getHeight());

		//_exec = true;

	}

	public synchronized void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
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
					 (int) ((_cellSize * _indicator.y + _offset.y) * _zoom),*/
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
		p2.x = (int) ((this.getWidth() - _offset.x) / _zoom) / _cellSize;
		p2.y = (int) ((this.getHeight() - _offset.y) / _zoom) / _cellSize;
		//(_position.x + (int) (_size.x * _cellSize * _zoom)/2 - this.getWidth()) / _cellSize;
		//p2.x = (_position.y + (int) (_size.y * _cellSize * _zoom)/2 - this.getHeight()) / _cellSize;

		synchronized (_cells) {
			for (Entry<Point, Cell> entry : _cells.entrySet()) {
				Point coord = entry.getKey();
				if (coord.x >= p1.x
					&& coord.y >= p1.y
					&& coord.x <= p2.x
					&& coord.y <= p2.y) {
					g.fillOval(
							(int) (_cellSize * _zoom) * coord.x + _offset.x,
							(int) (_cellSize * _zoom) * coord.y + _offset.y,
							/*(int) ((_cellSize * coord.x + _offset.x) * _zoom), 
							 (int) ((_cellSize * coord.y + _offset.y) * _zoom), */
							(int) (_cellSize * _zoom),
							(int) (_cellSize * _zoom));
					
				}
			}
		}
		//g.drawImage(c.getNextImage(), 0, 0, this);
	}

	@Override
	public synchronized void update(Observable o, Object o1) {

		if (o instanceof GameExecution) {
			GameExecution game = (GameExecution) o;
			synchronized (_cells) {
				_cells = game.getCells();
				_size = game.getSize();
			}
			//_offset.x = ( this.getSize().width - (_size.x * _cellSize) ) / 2;
			//_offset.y = ( this.getSize().height - (_size.y * _cellSize) ) / 2;

			this.repaint();
		}
	}

	public Point cellCoordinate(Point coord) {
		Point cell = new Point();
		cell.x = (coord.x - _offset.x - 1) / (int) (_cellSize * _zoom);
		cell.y = (coord.y - _offset.y - 1) / (int) (_cellSize * _zoom);
		return cell;
	}

	public void setIndicatorPosition(Point coord) {
		coord = this.cellCoordinate(coord);
		if (isInsideTheField(coord)) {
			if (!coord.equals(_indicator)) {
				_indicator = coord;

				this.repaint();
			}
		} else {
			_indicator = null;
		}
	}

	public boolean isInsideTheField(Point coord) {
		return coord.x >= 0 && coord.x < _size.x
				&& coord.y >= 0 && coord.y < _size.y;
	}

	public void moveField(Point movement) {
		/*_offset.x += movement.x;
		 _offset.y += movement.y;*/
		_position.x += movement.x;
		_position.y += movement.y;
		_offset.x = _position.x - (int) (_size.x * _cellSize * _zoom) / 2;
		_offset.y = _position.y - (int) (_size.y * _cellSize * _zoom) / 2;

		this.repaint();
	}

	public void zoom(int unit) {
		_zoom += (double) unit / 10;

		if (_zoom > 1) {
			_zoom = 1;
		} else if (_zoom < 0.2) {
			_zoom = 0.2;
		}

		this.repaint();
	}

	public Point getIndicator() {
		return _indicator;
	}

	public void resize() {
		Point size = new Point();
		size.x = this.getWidth() - _oldComponentSize.x;
		size.y = this.getHeight() - _oldComponentSize.y;

		_position.x += size.x / 2;
		_position.y += size.y / 2;

		_oldComponentSize.x = this.getWidth();
		_oldComponentSize.y = this.getHeight();
	}
	/*
	 @Override
	 public synchronized void run() {
		
	 while (_exec) {
	 this.repaint();
	 try {
	 Thread.sleep(200);
	 } catch (InterruptedException ex) {
	 Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
	 }
	 }
		
	 }
	
	 public synchronized void terminate() {
	 _exec = false;
	 }
	 */
}
