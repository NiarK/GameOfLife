/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife.search;

import java.awt.Point;
import java.util.HashSet;
import model.gameoflife.Search;

/**
 * Prend les cellules adjacentes (sous forme de +)
 * @author Quentin
 */
public class CrossSearch implements Search{

    @Override
    public HashSet<Point> getNeighbor(int width, int height, Point place) {
        HashSet<Point> hs = new HashSet<>();
        
        Point neighbor = (Point)place.clone();

        // Voisin en haut.
        if(neighbor.y > 0) {
            neighbor.y -= 1;
            hs.add(neighbor);
            neighbor.y += 1;
        }

        // Voisin à droite.
        if(neighbor.x < width - 1) {
            neighbor.x += 1;
            hs.add(neighbor);
            neighbor.x -= 1;
        }

        // Voisin en bas.
        if(neighbor.y < height - 1) {
            neighbor.y += 1;
            hs.add(neighbor);
            neighbor.y -= 1;
        }

        // Voisin à gauche.
        if(neighbor.x > 0) {
            neighbor.x -= 1;
            hs.add(neighbor);
            neighbor.x += 1;
        }

        
        return hs;
    }
    
}
