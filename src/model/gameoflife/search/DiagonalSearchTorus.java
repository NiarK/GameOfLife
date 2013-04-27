/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife.search;
import java.awt.Point;
import java.util.HashSet;
import model.gameoflife.Search;

/**
 * Prend les cellules en diagonale directe (sous forme de X)
 * @author Quentin
 */
public class DiagonalSearchTorus  implements Search{
    @Override
    public HashSet<Point> getNeighbor(int width, int height, Point place) {
        HashSet<Point> hs = new HashSet<>();
        
        // Voisin en haut à gauche.
        Point neighbor = (Point)place.clone();
        if(neighbor.y == 0)
            neighbor.y = height - 1;
        else
            neighbor.y -= 1;
        if(neighbor.x == 0)
            neighbor.x = width - 1;
        else
            neighbor.x -= 1;
        hs.add(neighbor);

        // Voisin en haut à droite.
        neighbor = (Point)place.clone();
        if(neighbor.y == 0)
            neighbor.y = height - 1;
        else
            neighbor.y -= 1;
        if(neighbor.x == width - 1)
            neighbor.x = 0;
        else
            neighbor.x += 1;
        hs.add(neighbor);

        // Voisin en bas à droite.
        neighbor = (Point)place.clone();
        if(neighbor.y == height - 1)
            neighbor.y = 0;
        else
            neighbor.y += 1;
        if(neighbor.x == width - 1)
            neighbor.x = 0;
        else
            neighbor.x += 1;
        hs.add(neighbor);

        // Voisin en bas à gauche.
        neighbor = (Point)place.clone();
        if(neighbor.y == height - 1)
            neighbor.y = 0;
        else
            neighbor.y += 1;
        if(neighbor.x == 0)
            neighbor.x = width - 1;
        else
            neighbor.x -= 1;
        hs.add(neighbor);

        
        return hs;
    }
}
