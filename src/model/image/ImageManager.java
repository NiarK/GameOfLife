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
 * Ce singleton fournit un gestionnaire d'image.
 * Il suffit de lui fournir le chemin d'une image et il l'a chargera qu'une seul fois.
 * Si l'image à déjà été chargée, il ne refera pas un accès fichier pour la recharger, il retournera directement l'instance de l'image demandé.
 */
public class ImageManager {
	
	private static ImageManager _instance;
	
	private HashMap<String, BufferedImage> _images;

	/**
	 * Construit le gestionnaire.
	 */
	private ImageManager() {
		_images = new HashMap<>();
	}
	
	/**
	 * Retourne l'instance du gestionnaire.
	 * Si l'instance n'existe pas, il l'a créé.
	 * @return L'instance du gestionnaire.
	 */
	public static ImageManager getInstance() {
		
		if(_instance == null) {
			_instance = new ImageManager();
		}
		
		return _instance;
	}
	
	/**
	 * Charge l'image à partir d'un chemin.
	 * Si l'image existe dèjà, aucun accès fichier n'est réalisé.
	 * @param filename Le chemin vers l'image.
	 * @return Un objet BufferedImage représentant l'image demandé.
	 */
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
