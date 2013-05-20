/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.gameoflife.GameExecution;
import model.gameoflife.Pattern;

/**
 *
 * @author pierre
 */
public final class Field extends JPanel implements Observer/*, Runnable */ {

	
	private FieldDrawManager _drawer;
	
	public static double ZOOM_UNIT = 0.9;
	

	//private view.Cell c;
	public Field(int neighbors) {
		super();

		_drawer = new FieldDrawManager(new Point(this.getWidth(), this.getHeight()));
		_drawer.setCellSize(10);

	}

	@Override
	public synchronized void paintComponent(Graphics g) {

		_drawer.draw(g);
		
	}

	@Override
	public synchronized void update(Observable o, Object o1) {

		if (o instanceof GameExecution) {
			GameExecution game = (GameExecution) o;
			
			_drawer.setCells(game.getCells());
			_drawer.setFieldSize(game.getFieldSize());

			this.repaint();
		}
	}

	public synchronized void setNeighbors(int n, boolean torus) {
		_drawer.setTorus(torus);
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
	

	public void setIndicatorPosition(Point coord) {
		if(_drawer.setIndicatorPosition(coord)){
			this.repaint();
		}
	}


	public void moveField(Point movement) {
		_drawer.moveField(movement);

		this.repaint();
	}

	public void zoom(int unit) {
		
		_drawer.zoom(unit);
		
		
		this.repaint();
	}

	public Point getIndicator() {
		return _drawer.getIndicator();//_indicator;
	}

	public void resize() {
		
		_drawer.setComponentSize(new Point(this.getWidth(),this.getHeight()));
		_drawer.resize();
	}
	
	public void setPattern(Pattern _pattern) {
		_drawer.setPattern(_pattern);
	}
	
	public boolean isPatternDefine() {
		return _drawer.isPatternDefine();
	}
	
	public boolean isInsideTheField(Point coord) {
		return _drawer.isInsideTheField(coord);
	}
	
	public void verticalSymmetry(){
		_drawer.verticalSymmetry();
	}
	
	public void horizontalSymmetry(){
		_drawer.horizontalSymmetry();
	}
	
	public void rotateRight(){
		_drawer.rotateRight();
	}
	
	public void rotateLeft(){
		_drawer.rotateLeft();
	}
}
