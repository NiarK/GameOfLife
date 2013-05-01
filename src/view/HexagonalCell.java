/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author pierre
 */
public class HexagonalCell extends Cell {

	private boolean _pair;
	
	public HexagonalCell() {
		super();
		_pair = true;
	}

	@Override
	public void draw(Graphics g, Point position, int size) {
		
		if(_pair) {
			//_position.x = 
		}
		
		super.draw(g, position, size); //To change body of generated methods, choose Tools | Templates.
	}
	
	
}
