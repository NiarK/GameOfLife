/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashSet;
import java.util.TreeSet;

/**
 *
 * @author pierre
 */
public class RuleParameter {
	
	private TreeSet<Integer> _born;
	private TreeSet<Integer> _survive;
	private String _search;
	private boolean _torus;
	private String _name;

	public RuleParameter() {
		this("");
		/*_born = new TreeSet<>();
		_survive = new TreeSet<>();
		_name = null;*/
	}
	
	public RuleParameter(String name) {
		_born = new TreeSet<>();
		_survive = new TreeSet<>();
		_torus = false;
		_search = null;
		
		if( name == null ) {
			name = "";
		}
		_name = name;
	}
	
	/*public RuleParameter(RuleParameter rule) {
		this._born = new TreeSet<>(rule._born);
		this._survive = new TreeSet<>(rule._survive);
		this._name = new String(rule._name);
	}*/
	
	public void copy(RuleParameter rule) {
		this._born = new TreeSet<>(rule._born);
		this._survive = new TreeSet<>(rule._survive);
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

	public String getScientificName() {
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
	
	public void removeName() {
		this.setName(null);
	}
	
	public String getName() {
		
		if( ! _name.equals("") ) {
			return _name;
		}
		
		return this.getScientificName();
	}

	public void setName(String name) {
		if(name == null) {
			name = "";
		}
		this._name = name;
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

	public String getSearch() {
		return _search;
	}

	public void setSearch(String name) {
		this._search = name;
	}

	public boolean isTorus() {
		return _torus;
	}

	public void setTorus(boolean torus) {
		this._torus = torus;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}


	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone(); //To change body of generated methods, choose Tools | Templates.
	}
	
	
	
}
