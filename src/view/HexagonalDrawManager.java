/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.Map;
import model.gameoflife.Cell;

/**
 *
 * @author pierre
 */
public class HexagonalDrawManager extends FieldDrawManager {

	public HexagonalDrawManager(Point fieldSize) {
		super(fieldSize);
	}

	HexagonalDrawManager(FieldDrawManager fdm) {
		super(fdm);
	}

	@Override
	protected synchronized void drawBorder(Graphics g) {
		g.drawRect(
			_offset.x,
			_offset.y,
			_fieldSize.x * (int) (_cellSize * _zoom) + (int) (_cellSize * _zoom)/2,
			_fieldSize.y * (int) (_cellSize * _zoom));
	}

	@Override
	protected synchronized void drawIndicator(Graphics g) {
		
		int space = 0;
		if(_indicator.y % 2 == 1) {
			space = (int) (_cellSize * _zoom)/2;
		}
		
		g.drawRect(
			(int) (_cellSize * _zoom) * _indicator.x + _offset.x + space,
			(int) (_cellSize * _zoom) * _indicator.y + _offset.y,
			(int) (_cellSize * _zoom),
			(int) (_cellSize * _zoom));
		
		if(_pattern != null){
			this.drawPattern(g);
		}
	}
	
	protected synchronized void drawPattern(Graphics g) {
		Iterator<Point> it = _pattern.iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			int space = 0;
			if((_indicator.y + temp.y) % 2 == 1) {
				space = (int) (_cellSize * _zoom)/2;
			}
			if(isInsideTheField(new Point(_indicator.x + temp.x, _indicator.y + temp.y))){
				g.fillOval(
				(int) (_cellSize * _zoom) * (_indicator.x + temp.x) + _offset.x + space,
				(int) (_cellSize * _zoom) * (_indicator.y + temp.y) + _offset.y,
				/*(int) ((_cellSize * _indicator.x + _offset.x) * _zoom),
				 (int) ((_cellSize * _indicator.y + _offset.y) * _zoom),*/
				(int) (_cellSize * _zoom),
				(int) (_cellSize * _zoom));
			}
		}
	}

	@Override
	public Point cellCoordinate(Point coord) {
		
		
		
		Point cell = new Point();
		cell.y = (coord.y - _offset.y - 1) / (int) (_cellSize * _zoom);
		
		int space = 0;
		if(cell.y % 2 == 1) {
			space = _cellSize/2;
		}
		
		cell.x = (coord.x - _offset.x - 1 - space) / (int) (_cellSize * _zoom);
		return cell;
	}
	
	

	@Override
	protected synchronized void drawCells(Graphics g, Point p1, Point p2) {
		/*point p1 = new Point();
		Point p2 = new Point();

		p1.x = (int) (-_offset.x / _zoom) / _cellSize;
		p1.y = (int) (-_offset.y / _zoom) / _cellSize;
		p2.x = (int) ((_componentSize.x - _offset.x) / (int)(_cellSize * _zoom));
		p2.y = (int) ((_componentSize.y - _offset.y) / (int)(_cellSize * _zoom));*/
		
		for (Map.Entry<Point, Cell> entry : _cells.entrySet()) {
			Point coord = entry.getKey();
			if (coord.x >= p1.x
				&& coord.y >= p1.y
				&& coord.x <= p2.x
				&& coord.y <= p2.y) {
				
				int space = 0;
				if(coord.y % 2 == 1) {
					space = (int) (_cellSize * _zoom)/2;
					//System.out.println(space);
				}
				
				Point position = new Point(_position);
				position.x = (int) (_cellSize * _zoom) * coord.x + _offset.x + space;
				position.y = (int) (_cellSize * _zoom) * coord.y + _offset.y ;//- (int)(coord.y * _zoom);
				
				g.fillOval(
						position.x,
						position.y,
						(int) (_cellSize * _zoom),
						(int) (_cellSize * _zoom));

			}
		}
	}

	
	
	
	
}
