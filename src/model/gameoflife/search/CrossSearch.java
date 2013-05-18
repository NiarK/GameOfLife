/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife.search;

import model.gameoflife.Search;
import java.awt.Point;
import java.util.HashSet;

/**
 * Prend les cellules en diagonale directe (sous forme de X)
 * 
 * | | | | | |
 * | |X| |X| |
 * | | |O| | |
 * | |X| |X| |
 * | | | | | |
 * @author Quentin
 */
public class CrossSearch implements Search {

	private boolean torus;

	public CrossSearch(boolean torus) {
		this.torus = torus;
	}

	public CrossSearch() {
		this.torus = false;
	}

	public boolean isTorus() {
		return torus;
	}

	public void setTorus(boolean torus) {
		this.torus = torus;
	}

	@Override
	public HashSet<Point> getNeighbor(int width, int height, Point place) {
		HashSet<Point> hs = new HashSet<>();

		if (torus) {
			// Voisin en haut à gauche.
			Point neighbor = (Point) place.clone();
			if (neighbor.y == 0) {
				neighbor.y = height - 1;
			}
			else {
				neighbor.y -= 1;
			}
			if (neighbor.x == 0) {
				neighbor.x = width - 1;
			}
			else {
				neighbor.x -= 1;
			}
			hs.add(neighbor);

			// Voisin en haut à droite.
			neighbor = (Point) place.clone();
			if (neighbor.y == 0) {
				neighbor.y = height - 1;
			}
			else {
				neighbor.y -= 1;
			}
			if (neighbor.x == width - 1) {
				neighbor.x = 0;
			}
			else {
				neighbor.x += 1;
			}
			hs.add(neighbor);

			// Voisin en bas à droite.
			neighbor = (Point) place.clone();
			if (neighbor.y == height - 1) {
				neighbor.y = 0;
			}
			else {
				neighbor.y += 1;
			}
			if (neighbor.x == width - 1) {
				neighbor.x = 0;
			}
			else {
				neighbor.x += 1;
			}
			hs.add(neighbor);

			// Voisin en bas à gauche.
			neighbor = (Point) place.clone();
			if (neighbor.y == height - 1) {
				neighbor.y = 0;
			}
			else {
				neighbor.y += 1;
			}
			if (neighbor.x == 0) {
				neighbor.x = width - 1;
			}
			else {
				neighbor.x -= 1;
			}
			hs.add(neighbor);
		}
		else {
			Point neighbor = (Point) place.clone();

			// Voisin en haut à gauche.
			if (neighbor.y > 0 && neighbor.x > 0) {
				neighbor.y -= 1;
				neighbor.x -= 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Voisin en haut à droite.
			if (neighbor.y > 0 && neighbor.x < width - 1) {
				neighbor.y -= 1;
				neighbor.x += 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Voisin en bas à droite.
			if (neighbor.y < height - 1 && neighbor.x < width - 1) {
				neighbor.y += 1;
				neighbor.x += 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Voisin en bas à gauche.
			if (neighbor.x > 0 && neighbor.y < height - 1) {
				neighbor.y += 1;
				neighbor.x -= 1;
				hs.add(neighbor);
			}
		}

		return hs;
	}

	@Override
	public int getNeighborMaximumNumber() {
		return 4;
	}
}
