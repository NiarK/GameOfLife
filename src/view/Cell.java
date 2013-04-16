/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.gameoflife.CellState;
import model.image.ImageManager;

/**
 *
 * @author pierre
 */
public class Cell {
	
	private BufferedImage _img;
	
	private CellImageParameter _param;
	
	int _state;
	int _moment;
	
	public Cell(CellState cell, CellImageParameter param) {
		
		_state = 0;
		_moment = 0;
	
		_param = param;
		
		_img = ImageManager.getInstance().get(param.getFilename());
		
	}
	
	public void dead() {
		_moment = 2;
	}
	
	public BufferedImage getNextImage() {
		
		BufferedImage img = _img.getSubimage(
				_state * _param.getSize().x, 
				_moment * _param.getSize().y, 
				_param.getSize().x, 
				_param.getSize().y
				);
		
		++_state;
		
		if(_moment == 0) {
			
			if(_state >= _param.getBirthImageNumber()) {
				++_moment;
				_state = 0;
			}
		}
		else if(_moment == 1) {
			
			if(_state >= _param.getLifeImageNumber()) {
				_state = 0;
			}
		}
		else if(_state >= _param.getDeathImageNumber()) {
			--_state;
		}
		
		return img;
	}
}
