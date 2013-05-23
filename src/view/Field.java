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
 * Cette classe est un conposant graphique permettant d'afficher le terrain du jeu de la vie.
 * Elle peut observer un terrain et ce mettre à jour à chaque changement de celui ci.
 * @author pierre
 */
public final class Field extends JPanel implements Observer/*, Runnable */ {

	
	private FieldDrawManager _drawer;
	
	public static double ZOOM_UNIT = 0.9;
	
	/**
	 * Construit le composant graphique capabe d'afficher un terrain normal (carré, 8 voisins).
	 */
	public Field() {
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

	/**
	 * Définit le nombre de voisins que chaque cellule a.
	 * Cela impacte sur l'affichage (affichage en carré, hexagone...).
	 * @param n Nombre de voisins
	 */
	public synchronized void setNeighbors(int n) {
		
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
	 * Définit si le terrain est un tore. Si oui, lorsque la prévisualisation d'un modèle déborde sur un coté elle apparait de l'autre coté. 
	 * @param torus 
	 */
	public synchronized void setTorus(boolean torus) {
		_drawer.setTorus(torus);
	}
	
	/**
	 * Définit la position de l'indicateur de la souris sur le terrain. 
	 * @param coord Les coordonnées de l'indicateur dans le terrain.
	 */
	public void setIndicatorPosition(Point coord) {
		if(_drawer.setIndicatorPosition(coord)){
			this.repaint();
		}
	}

	/**
	 * Déplace l'affichage du terrain.
	 * @param movement Le mouvement à effectuer.
	 */
	public void moveField(Point movement) {
		_drawer.moveField(movement);

		this.repaint();
	}

	/**
	 * Zoom ou dézoome l'affichage du terrain.
	 * @param unit L'unité de zoom.
	 */
	public void zoom(int unit) {
		
		_drawer.zoom(unit);
		
		
		this.repaint();
	}

	/**
	 * Récupère la position de l'indicateur.
	 * @return La position de l'indicateur.
	 */
	public Point getIndicator() {
		return _drawer.getIndicator();//_indicator;
	}

	/**
	 * 
	 */
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
