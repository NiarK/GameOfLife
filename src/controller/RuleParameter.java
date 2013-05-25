/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * Classe permettant de gérer les paramètres pour une règle de jeu.
 * Elle permet de centraliser Les entrées fournit par l'utilisateur pour le paramétrage des règles.
 * Le controller sera ensuite capable de construire une règle en fonction de ses paramètres.
 * @author pierre
 */
public class RuleParameter {
	
	private TreeSet<Integer> _born;
	private TreeSet<Integer> _survive;
	private String _search;
	private boolean _torus;
	private String _name;
	private int _neighbor;

	/**
	 * Constructeur par défaut.
	 */
	public RuleParameter() {
		this("");
	}
	
	/**
	 * Construit des paramètres de règle avec un certains nom.
	 * @param name Le nom de la règle.
	 */
	public RuleParameter(String name) {
		_born = new TreeSet<>();
		_survive = new TreeSet<>();
		_torus = false;
		_search = null;
		
		if( name == null ) {
			name = "";
		}
		_name = name;
		_neighbor = 0;
	}
	
	/**
	 * Constructeur par copie.
	 * @param rule Les paramètres à copier.
	 */
	public void copy(RuleParameter rule) {
		this._born = new TreeSet<>(rule._born);
		this._survive = new TreeSet<>(rule._survive);
	}
	
	/**
	 * Ajoute un nombre de voisin pour lequel une cellule nait.
	 * @param b Le nombre de voisin.
	 */
	public void addBorn(Integer b) {
		_born.add(b);
	}
	
	/**
	 * Supprime un nombre de voisin pour lequel une cellule nait.
	 * @param b Le nombre de voisin.
	 */
	public void removeBorn(Integer b) {
		_born.remove(b);
	}
	
	/**
	 * Vide la liste des voisins pour lesquels une cellule nait.
	 */
	public void emptyBorn() {
		_born.clear();
	}
	
	/**
	 * Ajoute un nombre de voisin pour lequel une cellule survie.
	 * @param s Le nombre de voisin.
	 */
	public void addSurvive(Integer s) {
		_survive.add(s);
	}
	
	/**
	 * Supprime un nombre de voisin pour lequel une cellule survie.
	 * @param s Le nombre de voisin.
	 */
	public void removeSurvive(Integer s) {
		_survive.remove(s);
	}

	/**
	 * Vide la liste des voisins pour lesquels une cellule survie.
	 */
	public void emptySurvive() {
		_survive.clear();
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
	
	public int getNeighbor() {
		return _neighbor;
	}

	public void setNeighbor(int neighbor) {
		this._neighbor = neighbor;
	}
	
	
	
}
