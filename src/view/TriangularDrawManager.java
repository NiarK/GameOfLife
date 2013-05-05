/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Map;
import model.gameoflife.Cell;

/**
 *
 * @author Pierre
 */
public class TriangularDrawManager extends FieldDrawManager {
	public TriangularDrawManager(Point fieldSize) {
		super(fieldSize);
	}

	TriangularDrawManager(FieldDrawManager fdm) {
		super(fdm);
	}

	@Override
	protected synchronized void drawBorder(Graphics g) {
		g.drawRect(
			_offset.x - (int) (_cellSize * _zoom)/2,
			_offset.y,
			_fieldSize.x * (int) (_cellSize * _zoom) + (int) (_cellSize * _zoom),
			_fieldSize.y * (int) (_cellSize * _zoom));
	}

	@Override
	protected synchronized void drawIndicator(Graphics g) {
		
		Point position = new Point(
				(int) (_cellSize * _zoom) * _indicator.x + _offset.x,
				(int) (_cellSize * _zoom) * _indicator.y + _offset.y
			);
		g.drawPolygon(this.getTriangle(position, ((_indicator.x + _indicator.y) % 2) == 0 ));
		
	}

	@Override
	protected synchronized void drawCells(Graphics g, Point p1, Point p2) {
		
		for (Map.Entry<Point, Cell> entry : _cells.entrySet()) {
			Point coord = entry.getKey();
			if (coord.x >= p1.x
				&& coord.y >= p1.y
				&& coord.x <= p2.x
				&& coord.y <= p2.y) {
				
				Point position = new Point(_position);
				position.x = (int) (_cellSize * _zoom) * coord.x + _offset.x;
				position.y = (int) (_cellSize * _zoom) * coord.y + _offset.y ;//- (int)(coord.y * _zoom);
				
				
				
				g.fillPolygon(this.getTriangle(position, ((coord.x + coord. y) % 2) == 0 ));

			}
		}
	}
	
	private Polygon getTriangle(Point position, boolean pair) {
		
			int[] xPoints = new int[3];
			xPoints[0] = position.x - (int)((_cellSize-1) * _zoom /2);
			xPoints[1] = position.x + (int)(_cellSize * _zoom /2);
			xPoints[2] = position.x + (int)((_cellSize-1) * _zoom *3/2);
				
			int[] yPoints = new int[3];
			
			if(pair) {
				yPoints[0] = position.y;
				yPoints[1] = position.y + (int)((_cellSize-1) * _zoom);
				yPoints[2] = position.y;
			}
			else {
				yPoints[0] = position.y + (int)((_cellSize-1) * _zoom);
				yPoints[1] = position.y;
				yPoints[2] = position.y + (int)((_cellSize-1) * _zoom);
			}
			
			
			return new Polygon(xPoints, yPoints, 3);
	}
}
