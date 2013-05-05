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
public class LargeTriangleSearch implements Search{
    private boolean torus;

    public LargeTriangleSearch(boolean torus) {
        this.torus = torus;
    }

    public LargeTriangleSearch() {
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
            
            // Voisin deux à droite
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
            
            // Voisin deux à gauche
            if(neighbor.x == 0)
                neighbor.x = width - 1;
            else
                neighbor.x -= 1;
            hs.add(neighbor);
            
            // Voisin au dessus
            neighbor = (Point)place.clone();
            if(neighbor.y == 0)
                neighbor.y = height - 1;
            else
                neighbor.y -= 1;
            hs.add(neighbor);
            
            // Voisin au dessus à gauche
            if(neighbor.x == 0)
                neighbor.x = width - 1;
            else
                neighbor.x -= 1;
            hs.add(neighbor);
            
            // Voisin au dessus à droite
            if(neighbor.x == width - 1)
                neighbor.x = 1;
            else if(neighbor.x == width - 2)
                neighbor.x = 0;
            else
                neighbor.x += 2;
            hs.add(neighbor);
            
            // Voisin au dessous
            neighbor = (Point)place.clone();
            if(neighbor.y == height - 1)
                neighbor.y = 0;
            else
                neighbor.y += 1;
            hs.add(neighbor);
            
            // Voisin au dessous à gauche
            if(neighbor.x == 0)
                neighbor.x = width - 1;
            else
                neighbor.x -= 1;
            hs.add(neighbor);
            
            // Voisin au dessous à droite
            if(neighbor.x == width - 1)
                neighbor.x = 1;
            else if(neighbor.x == width - 2)
                neighbor.x = 0;
            else
                neighbor.x += 2;
            hs.add(neighbor);
            
            neighbor = (Point)place.clone();
            if(neighbor.x%2 == neighbor.y%2){
                //voisin dessus droite droite
                if(neighbor.y == 0)
                    neighbor.y = height - 1;
                else
                    neighbor.y -= 1;
                if(neighbor.x == width - 1)
                    neighbor.x = 1;
                else if(neighbor.x == width - 2)
                    neighbor.x = 0;
                else
                    neighbor.x += 2;
                hs.add(neighbor);
                neighbor = (Point)place.clone();
                //voisin dessus gauche gauche
                if(neighbor.y == 0)
                    neighbor.y = height - 1;
                else
                    neighbor.y -= 1;
                if(neighbor.x == 0)
                    neighbor.x = width - 2;
                else if(neighbor.x == 1)
                    neighbor.x = width - 1;
                else
                    neighbor.x -= 2;
                hs.add(neighbor);
            }
            else{
                //voisin dessous droite droite
                if(neighbor.y == height - 1)
                    neighbor.y = 0;
                else
                    neighbor.y += 1;
                if(neighbor.x == width - 1)
                    neighbor.x = 1;
                else if(neighbor.x == width - 2)
                    neighbor.x = 0;
                else
                    neighbor.x += 2;
                hs.add(neighbor);
                neighbor = (Point)place.clone();
                //voisin dessous gauche gauche
                if(neighbor.y == height - 1)
                    neighbor.y = 0;
                else
                    neighbor.y += 1;
                if(neighbor.x == 0)
                    neighbor.x = width - 2;
                else if(neighbor.x == 1)
                    neighbor.x = width - 1;
                else
                    neighbor.x -= 2;
                hs.add(neighbor);
            }
        }
        else{
            // Voisin à droite.
            Point neighbor = (Point)place.clone();
            if(neighbor.x < width - 1){
                neighbor.x += 1;
                hs.add(neighbor);
            }
            
            // Voisin deux à droite
            if(neighbor.x < width - 1){
                neighbor.x += 1;
                hs.add(neighbor);
            }
            
            // Voisin à gauche.
            neighbor = (Point)place.clone();
            if(neighbor.x > 0){
                neighbor.x -= 1;
                hs.add(neighbor);
            }
            
            // Voisin deux à gauche
            if(neighbor.x > 0){
                neighbor.x -= 1;
                hs.add(neighbor);
            }
            
            // Voisin au dessus
            neighbor = (Point)place.clone();
            if(neighbor.y > 0){
                neighbor.y -= 1;
                hs.add(neighbor);
            }
            
            // Voisin au dessus à gauche
            if(neighbor.x > 0){
                neighbor.x -= 1;
                hs.add(neighbor);
            }
            
            // Voisin au dessus à droite
            if(neighbor.x < width - 2){
                neighbor.x += 2;
                hs.add(neighbor);
            }
            
            // Voisin au dessous
            neighbor = (Point)place.clone();
            if(neighbor.y < height - 1){
                neighbor.y += 1;
                hs.add(neighbor);
            }
            
            // Voisin au dessous à gauche
            if(neighbor.x > 0){
                neighbor.x -= 1;
                hs.add(neighbor);
            }
            
            // Voisin au dessous à droite
            if(neighbor.x < width - 2){
                neighbor.x += 2;
                hs.add(neighbor);
            }
            
            neighbor = (Point)place.clone();
            if(neighbor.x%2 == neighbor.y%2){
                //voisin dessus droite droite
                if(neighbor.y > 0){
                    neighbor.y -= 1;
                    if(neighbor.x < width - 2){
                        neighbor.x += 2;
                        hs.add(neighbor);
                    }
                }
                neighbor = (Point)place.clone();
                //voisin dessus gauche gauche
                if(neighbor.y > 0){
                    neighbor.y -= 1;
                    if(neighbor.x > 1){
                        neighbor.x -= 2;
                        hs.add(neighbor);
                    }
                }
            }
            else{
                //voisin dessous droite droite
                if(neighbor.y < height - 1){
                    neighbor.y += 1;
                    if(neighbor.x < width - 2){
                        neighbor.x += 2;
                        hs.add(neighbor);
                    }
                }
                neighbor = (Point)place.clone();
                //voisin dessous gauche gauche
                if(neighbor.y == height - 1){
                    neighbor.y += 1;
                    if(neighbor.x > 1){
                        neighbor.x -= 2;
                        hs.add(neighbor);
                    }
                }
            }
        }
        return hs;
    }
    
    
	@Override
	public int getNeighborMaximumNumber() {
		return 12;
	}
}
