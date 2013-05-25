/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gameoflife;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe centrale du model du jeu de la vie.
 * Cette classe, implémentant Runnable, permet de gérer l'éxecution du jeu.
 * Elle gère aussi quasiment toutes les interactions entre le controller et le model.
 * @author pierre
 */
public class GameExecution extends Observable implements Runnable, Observer {

	private Field _field;
	private Rule _rule;
	private Pattern _pattern;

	/**
	 * Construit tout le model.
	 * @param size La taille du terrain.
	 * @param rule Les règles à appliquer au terrain.
	 */
	public GameExecution(Point size, Rule rule) {
		this._field = new Field(size);
		this._field.addObserver(this);
		this._rule = rule;
	}

	@Override
	public synchronized void run() {
		try {

			
			UpdatePlaceProcess upp = new UpdatePlaceProcess();
			Thread updatePlaceProcess = new Thread(upp);
			
			EmergingProcess ep = new EmergingProcess();
			Thread emergingProcess = new Thread(ep);

			EvolutionProcess Evp = new EvolutionProcess();
			Thread evolutionProcess = new Thread(Evp);

			Thread updateProcess = new Thread(new UpdateProcess());

			updatePlaceProcess.start();
			evolutionProcess.start();
			
			evolutionProcess.join(0);
			updatePlaceProcess.join(0);
			
			emergingProcess.start();
			emergingProcess.join(0);

			updateProcess.start();
			updateProcess.join(0);

			this.setChanged();
			this.notifyObservers();
		}
		catch (InterruptedException ex) {
			Logger.getLogger(GameExecution.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Récupère le terrain.
	 * @return Le terrain.
	 */
	public Field getField() {
		return _field;
	}
	
	/**
	 * Vide le terrain.
	 */
	public synchronized void empty() {

		_rule.empty(_field);

		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Remplie aléatoirement le terrain.
	 */
	public synchronized void randomlyFill() {

		_rule.randomlyFill(_field);

		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Récupère la liste de toutes les cellules en vie.
	 * @return Un objet HashMap contenant les cellules.
	 */
	public synchronized HashMap<Point, Cell> getCells() {
		return (HashMap<Point, Cell>) this._field.getCells().clone();
	}

	/**
	 * Récupère la taille du terrain.
	 * @return Un objet Point contenant la taille du terrain.
	 */
	public Point getFieldSize() {
		return this._field.getSize();
	}

	/**
	 * Change l'état d'un cellule à une certaines position : une cellule vivante meurt et une cellule morte nait.
	 * @param position La position de la cellule.
	 */
	public synchronized void toggleCell(Point position) {

		if (position.x >= 0
			&& position.x < _field.getSize().x
			&& position.y >= 0
			&& position.y < _field.getSize().y) {
			if (_field.getCells().containsKey(position)) {
				_field.removeCell(_field.getCells().get(position));
			}
			else {
				_field.addCell(position);
			}

			this.setChanged();
			this.notifyObservers();
		}

	}
	
	/**
	 * Fait naitre toutes les cellules si situant dans le modèle à une certaines position.
	 * @param position La position du modèle.
	 */
	public synchronized void putPattern(Point position){

		Iterator<Point> it = _pattern.getCellsByMiddle().iterator();
		while (it.hasNext()) {
			Point temp = it.next();
			Point p = new Point(position.x + temp.x, position.y + temp.y);
			if(_rule.getSearch().isTorus()){
				if(p.x >= _field.getSize().x){
					p.x = p.x % _field.getSize().x;
				}
				else{
					while(p.x < 0){
						p.x = p.x + _field.getSize().x;
					}
				}
				if(p.y >= _field.getSize().y){
					p.y = p.y % _field.getSize().y;					
				}
				else{
					while(p.y < 0){
						p.y = p.y + _field.getSize().y;
					}
				}
				_field.addCell(p);
			}
			else{
				if(p.x >= 0 && p.x < _field.getSize().x && p.y >= 0 && p.y < _field.getSize().y)
					_field.addCell(p);
			}
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * définit la taille du terrain.
	 * @param size La nouvelle taille du terrain.
	 */
	public synchronized void setFieldSize(Point size) {
		_field.setSize(size);

		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Définit la règle à appliquer au terrain.
	 * @param rule L'objet Rule à appliquer au terrain.
	 */
	public synchronized void setRule(Rule rule) {
		_rule = rule;
	}

	/***
	 * Sauvegarde toutes les cellules vivante du terrain dans un fichier XML.
	 * @param filepath Chemin du fichier à sauvegarder.
	 */
	public void save(String filepath) {
		_field.save(filepath);
	}

	/**
	 * charge un terrain depuis un fichier XML.
	 * @param filepath Chemin du fichier à charger.
	 */
	public void load(String filepath) {
		_field.load(filepath);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Crée une liste contenant le nom des modèles présent dans un dossier.
	 * @param repertory Dossier à scanner.
	 * @return ArrayList Liste contenant le nom des modèles trouvés.
	 */
	public ArrayList patternList(String repertory) {
		return _field.patternList(repertory);
	}

	/**
	 * Crée une liste contenant les noms des dossiers du dossier repertory
	 * @param repertory Dossier à scanner.
	 * @return ArrayList Liste contenant les noms des dossiers.
	 */
	public ArrayList patternRepertoryList(String repertory) {
		return _field.patternRepertoryList(repertory);
	}

	/**
	 * récupère le modèle en cours.
	 * @return Un objet Pattern représentant le modèle.
	 */
	public Pattern getPattern() {
		return _pattern;
	}

	/**
	 * Définit le modèle en cours.
	 * @param _pattern Un objet Pattern représentant le modèle.
	 */
	public void setPattern(Pattern _pattern) {
		this._pattern = _pattern;
		
	}
	
	/**
	 * définit le nombre de thread que l'application doit utiiser pour calculer les prochaines générations.
	 * @param n Le nouveau nombre de thread.
	 */
	public synchronized void setThreadNumber(int n) {
		
		_field.setFragmentNumber(n);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}

	/**
	 * récupère le nombre de thread que l'application utiise pour calculer les prochaines générations.
	 * @return Le nombre de thread.
	 */
	public int getThreadNumber() {
		return _field.getFragmentNumber();
	}
	
	/**
	 * Classe interne gérant la création des différents thread de calcule.
	 */
	private abstract class Process implements Runnable {

		@Override
		public void run() {
			
			this.init();
			
			int threadNumber = _field.getFragmentNumber();
			
			Thread[] processes = new Thread[threadNumber];
			
			for(int i = 0; i < threadNumber; ++i) {
				
				final int index = i;
				processes[i] = new Thread(new Runnable() {

					@Override
					public void run() {
						
						exec(index);
					}
				});
				
				processes[i].start();
			}
			for(int i = 0; i < threadNumber; ++i) {
				try {
					processes[i].join();
				} catch (InterruptedException ex) {
					Logger.getLogger(GameExecution.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		/**
		 * Permet d'initialiser certaines choses avant le début du calcul.
		 */
		protected void init(){}
		
		/**
		 * Lance le calcule.
		 * @param fragmentIndex Le numéro du fragment associé au thread.
		 */
		protected abstract void exec(int fragmentIndex);
		
	}
	
	/**
	 * Classe interne gérant la mise à jour des positions où les cellules peuvent potentiellement naitres.
	 */
	private class UpdatePlaceProcess extends Process {

		@Override
		protected void init() {
			super.init();
			
			_field.clearEmergingPlaces();
		}
		
		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Cell> fragment = _field.getCellsFragments(fragmentIndex);
			_rule.updateEmergingPlaces(fragment, _field);
		}
	}
	
	/**
	 * Classe interne gérant la naissance des cellules.
	 */
	private class EmergingProcess extends Process  {


		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Integer> fragment = _field.getEmergingPlacesFragments(fragmentIndex);
			_rule.calculEmergingCells(fragment, _field);
		}

	}

	/**
	 * Classe interne gérant la mort ou la survie des cellules.
	 */
	private class EvolutionProcess extends Process  {

		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Cell> fragment = _field.getCellsFragments(fragmentIndex);
			_rule.calculNextCellsGeneration(fragment, _field);
		}

	}

	/**
	 * Classe interne gérant la mise à jour de l'état des cellules.
	 */
	private class UpdateProcess extends Process  {

		@Override
		protected void exec(int fragmentIndex) {
			HashMap<Point, Cell> fragment = _field.getCellsFragments(fragmentIndex);
			_rule.updateCellsState(fragment, _field);
		}

	}
}
