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
public class Field extends JPanel implements Observer/*, Runnable */{

	private HashMap<Point, Cell> _cells;
	private Point _size;
	private int _cellSize;
	private Point _offset;
	private Point _indicator;
	//private boolean _exec;
	
	//private view.Cell c;
	
	public Field() {
		super();
		
		/*CellImageParameter p = new CellImageParameter("cell.png", new Point(10,10), 5,5,5);
		c = new view.Cell(new CellState(), p);
		*/
		_cells = new HashMap<>();
		
		_cellSize = 15;
		
		_offset = new Point(0,0);
		
		_size = new Point(0,0);
		
		_indicator = null;
		
		//_exec = true;
		
	}

	public synchronized void paintComponent(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if( _indicator != null ) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(_cellSize * _indicator.x + _offset.x, _cellSize * _indicator.y + _offset.y, _cellSize, _cellSize);
		}
		
		g.setColor(Color.DARK_GRAY);
		g.drawRect(_offset.x, _offset.y, _cellSize * _size.x, _cellSize * _size.y);
		
		g.setColor(Color.red);
		
		for(Entry<Point, Cell> entry : _cells.entrySet()) {
			Point coord = entry.getKey();
			g.fillOval(_cellSize * coord.x + _offset.x, _cellSize * coord.y + _offset.y, _cellSize, _cellSize);
		}
		
		//g.drawImage(c.getNextImage(), 0, 0, this);
	}

	@Override
	public synchronized void update(Observable o, Object o1) {
		
		if(o instanceof GameExecution)
		{
			GameExecution game = (GameExecution)o;
			
			_cells = game.getCells();
			_size = game.getSize();
			
			//_offset.x = ( this.getSize().width - (_size.x * _cellSize) ) / 2;
			//_offset.y = ( this.getSize().height - (_size.y * _cellSize) ) / 2;
			
			this.repaint();
		}
	}
	
	public Point cellCoordinate(Point coord) {
		Point cell = new Point();
		cell.x = ( coord.x - _offset.x - 1 ) / _cellSize;
		cell.y = ( coord.y - _offset.y - 1 ) / _cellSize;
		return cell;
	}
	
	public void setIndicatorPosition(Point coord) {
		coord = this.cellCoordinate(coord);
		if( isInsideTheField(coord) ) {
			if( ! coord.equals(_indicator) ) {
				_indicator = coord;
				
				this.repaint();
			}
		}
		else {
			_indicator = null;
		}
	}
	
	public boolean isInsideTheField(Point coord) {
		return coord.x >= 0 && coord.x < _size.x &&
			   coord.y >= 0 && coord.y < _size.y;
	}

	public void moveField(Point movement) {
		_offset.x += movement.x;
		_offset.y += movement.y;
		
		this.repaint();
	}
	
	public Point getIndicator() {
		return _indicator;
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
