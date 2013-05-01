package view;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author pierre
 */
public class Cell {
	
	public Cell() {
	}
	
	public void draw(Graphics g, Point position, int size) {
		g.fillOval(position.x, position.y, size, size);
	}
	
}
