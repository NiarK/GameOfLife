/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife.search;

import model.gameoflife.Search;
import java.awt.Point;
import java.util.HashSet;

/**
 *
 * @author Quentin
 */
public class TriangleSearch implements Search{
    private boolean torus;

    public TriangleSearch(boolean torus) {
        this.torus = torus;
    }

    public TriangleSearch() {
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
        if(torus){
            // Voisin à droite.
            Point neighbor = (Point)place.clone();
            if(neighbor.x == width - 1)
                neighbor.x = 0;
            else
                neighbor.x += 1;
            hs.add(neighbor);
            
            // Voisin à gauche.
            neighbor = (Point)place.clone();
            if(neighbor.x == 0)
                neighbor.x = width - 1;
            else
                neighbor.x -= 1;
            hs.add(neighbor);
            
            neighbor = (Point)place.clone();
            if(neighbor.y%2 == neighbor.x%2){
                if(neighbor.y == 0)
                    neighbor.y = height - 1;
                else
                    neighbor.y -= 1;
                hs.add(neighbor);
            }
            else{
                if(neighbor.y == height - 1)
                    neighbor.y = 0;
                else
                    neighbor.y += 1;
                hs.add(neighbor);
            }
        }
        else{
            Point neighbor = (Point)place.clone();
            // Voisin à droite.
            if(neighbor.x < width - 1) {
                neighbor.x += 1;
                hs.add(neighbor);
            }
            neighbor = (Point)place.clone();
            // Voisin à gauche.
            if(neighbor.x > 0) {
                neighbor.x -= 1;
                hs.add(neighbor);
            }
            
            neighbor = (Point)place.clone();
            if(neighbor.y%2 == neighbor.x%2){
                // Voisin en haut.
                if(neighbor.y > 0) {
                    neighbor.y -= 1;
                    hs.add(neighbor);
                }
            }
            else
            {
                // Voisin en bas.
                if(neighbor.y < height - 1) {
                    neighbor.y += 1;
                    hs.add(neighbor);
                }
            }
        }
        return hs;
    }
    
    
	@Override
	public int getNeighborMaximumNumber() {
		return 4;
	}
}
