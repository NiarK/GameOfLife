/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife.search;

import model.gameoflife.Search;
import java.awt.Point;
import java.util.HashSet;

/**
 * Takes the cells directly adjacent and diagonally
 * | | | | | |
 * | |X|X|X| |
 * | |X|O|X| |
 * | |X|X|X| |
 * | | | | | |
 * @author Quentin
 */
public class SquareSearch implements Search {

	private boolean torus;

	public SquareSearch(boolean torus) {
		this.torus = torus;
	}

	public SquareSearch() {
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
			// Neighbor at the top left.
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

			// Neighbor at the top right.
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

			// Neighbor at the bottom right.
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

			// Neighbor at the bottom left.
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

			// Neighbor at the top.
			neighbor = (Point) place.clone();
			if (neighbor.y == 0) {
				neighbor.y = height - 1;
			}
			else {
				neighbor.y -= 1;
			}
			hs.add(neighbor);

			// Neighbor at the right.
			neighbor = (Point) place.clone();
			if (neighbor.x == width - 1) {
				neighbor.x = 0;
			}
			else {
				neighbor.x += 1;
			}
			hs.add(neighbor);

			// Neighbor at the bottom.
			neighbor = (Point) place.clone();
			if (neighbor.y == height - 1) {
				neighbor.y = 0;
			}
			else {
				neighbor.y += 1;
			}
			hs.add(neighbor);

			// Neighbor at the left.
			neighbor = (Point) place.clone();
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

			// Neighbor at the top.
			if (neighbor.y > 0) {
				neighbor.y -= 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Neighbor at the right.
			if (neighbor.x < width - 1) {
				neighbor.x += 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Neighbor at the bottom.
			if (neighbor.y < height - 1) {
				neighbor.y += 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Neighbor at the left.
			if (neighbor.x > 0) {
				neighbor.x -= 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Neighbor at the top left.
			if (neighbor.y > 0 && neighbor.x > 0) {
				neighbor.y -= 1;
				neighbor.x -= 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Neighbor at the top right.
			if (neighbor.y > 0 && neighbor.x < width - 1) {
				neighbor.y -= 1;
				neighbor.x += 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Neighbor at the bottom right.
			if (neighbor.y < height - 1 && neighbor.x < width - 1) {
				neighbor.y += 1;
				neighbor.x += 1;
				hs.add(neighbor);
			}

			neighbor = (Point) place.clone();
			// Neighbor at the bottom left.
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
		return 8;
	}
}
