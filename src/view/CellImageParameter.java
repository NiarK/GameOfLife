/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Point;

/**
 *
 * @author pierre
 */
class CellImageParameter {
	
	int _birth;
	int _life;
	int _death;
	String _filename;
	Point _size;
	
	public CellImageParameter(String filename, Point sizeAnimation, int birthImageNumber, int lifeImageNumber, int deathImageNumber) {
		_birth = birthImageNumber;
		_life = lifeImageNumber;
		_death = deathImageNumber;
		_filename = filename;
		_size = sizeAnimation;
	}

	public int getBirthImageNumber() {
		return _birth;
	}

	public void setBirthImageNumber(int birth) {
		this._birth = birth;
	}

	public int getLifeImageNumber() {
		return _life;
	}

	public void setLifeImageNumber(int life) {
		this._life = life;
	}

	public int getDeathImageNumber() {
		return _death;
	}

	public void setDeathImageNumber(int death) {
		this._death = death;
	}

	public String getFilename() {
		return _filename;
	}

	public void setFilename(String filename) {
		this._filename = filename;
	}

	public Point getSize() {
		return _size;
	}

	public void setSize(Point size) {
		this._size = size;
	}
	
	
}
