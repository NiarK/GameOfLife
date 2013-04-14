/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
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
public class Field extends JPanel implements Observer {

	private HashMap<Point, Cell> _cells;
	private Point _size;
	private int _cellSize;
	private Point _offset;
	
	public Field() {
		super();
		
		_cells = new HashMap<>();
		
		_cellSize = 15;
		
		_offset = new Point(0,0);
	}

	public void paintComponent(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.DARK_GRAY);
		g.drawRect(_offset.x, _offset.y, _cellSize * _size.x, _cellSize * _size.y);
		
		g.setColor(Color.red);
		
		for(Entry<Point, Cell> entry : _cells.entrySet()) {
			Point coord = entry.getKey();
			g.fillOval(_cellSize * coord.x + _offset.x, _cellSize * coord.y + _offset.y, _cellSize, _cellSize);
		}
	}

	@Override
	public void update(Observable o, Object o1) {
		
		if(o instanceof GameExecution)
		{
			GameExecution game = (GameExecution)o;
			
			_cells = game.getCells();
			_size = game.getSize();
			
			_offset.x = ( this.getSize().width - (_size.x * _cellSize) ) / 2;
			_offset.y = ( this.getSize().height - (_size.y * _cellSize) ) / 2;
			
			this.repaint();
		}
	}
}
