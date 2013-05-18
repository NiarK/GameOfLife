/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import view.FieldDrawManager;

/**
 * Permet la gestion des images dans tout le programme
 * 
 */
public class ImageManager {
	
	private static ImageManager _instance;
	
	private HashMap<String, BufferedImage> _images;

	public ImageManager() {
		_images = new HashMap<>();
	}
	
	public static ImageManager getInstance() {
		
		if(_instance == null) {
			_instance = new ImageManager();
		}
		
		return _instance;
	}
	
	public BufferedImage get(String filename) {
		
		BufferedImage img = null;
		
		if(_images.containsKey(filename)) {
			img = _images.get(filename);
		}
		else {
			try {
				img = ImageIO.read(new File(filename));
				_images.put(filename, img);
			} catch (IOException ex) {
				Logger.getLogger(FieldDrawManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		return img;
	}
	
}
