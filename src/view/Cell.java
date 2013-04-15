/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author pierre
 */
public class Cell {
	
	private BufferedImage _img;
	
	private CellImageParameter _param;
	
	public Cell(String filename) {
		try {
			_img = ImageIO.read(new File(filename));
		} catch (IOException ex) {
			Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		
	}
	
	public BufferedImage getNextImage() {
		return _img;
	}
}
