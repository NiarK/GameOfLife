/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author pierre
 */
public class RuleParameter {
	
	private TreeSet<Integer> _born;
	private TreeSet<Integer> _survive;
	private String _name;

	public RuleParameter() {
		_born = new TreeSet<>();
		_survive = new TreeSet<>();
		_name = null;
	}
	
	public void addBorn(Integer b) {
		_born.add(b);
	}
	
	public void removeBorn(Integer b) {
		_born.remove(b);
	}
	
	public void addSurvive(Integer s) {
		_survive.add(s);
	}
	
	public void removeSurvive(Integer s) {
		_survive.remove(s);
	}

	public void removeName() {
		this.setName(null);
	}
	
	public String getName() {
		
		if( _name != null ) {
			return _name;
		}
		
		String str = "B";
		
		for(Integer b : _born) {
			str += b.toString();
		}
		
		str += "S";
		
		for(Integer s : _survive) {
			str += s.toString();
		}
		
		return str;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public void setBorn(HashSet<Integer> born) {
		 _born = new TreeSet(born);
	}

	public void setSurvive(HashSet<Integer> survive) {
		 _survive = new TreeSet(survive);
	}
	
	public HashSet<Integer> getBorn() {
		return new HashSet(_born);
	}

	public HashSet<Integer> getSurvive() {
		return new HashSet(_survive);
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
}
