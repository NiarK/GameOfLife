/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.simulator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe fournit les fonctionnalités pour la gestion d'une tache.
 * Elle execute un objet Runnable dans un thread toute les periodes.
 * Elle fournit aussi la possibilité de mettre en pause au de reprendre l'execution.
 * @author pierre
 */
public class Simulator extends Thread {
	
	private Runnable _task;
	private int _period;
	private boolean _exec;
	private boolean _run;
	
	public static final int STAND_BY_PERIOD = 5;

	/**
	 * Construit un simulateur qui executera une tache toute les periodes.
	 * @param task Un objet Runnable à exectuer
	 * @param period La période entre chaque execution.
	 * @throws InterruptedException 
	 */
	public Simulator(Runnable task, int period) throws InterruptedException {
		super(task);
		
		this._task = task;
		this._period = period;
		_exec = false;
		_run = true;
	}

	@Override
	public void run() {
		
		while(_run) {
			
			Thread sleep = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(_period - Simulator.STAND_BY_PERIOD);
					} catch (InterruptedException ex) {
						Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			});
			
			sleep.start();
			if(_exec){
				this._task.run();
			}
			try {
				Thread.sleep(Simulator.STAND_BY_PERIOD);
				sleep.join();
			} catch (InterruptedException ex) {
				Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
	}
	
	/**
	 * Lance l'execution de la tache.
	 */
	public void play() {
		_exec = true;
	}
	
	/**
	 * Mets en pause l'execution de la tache.
	 */
	public void pause() {
		_exec = false;
	}
	
	/**
	 * Met en pause le simulateur et execute une fois la tache.
	 */
	public void next() {
		this.pause();
		_task.run();
	}
	
	/**
	 * Termine l'execution et ferme le thread.
	 */
	public void terminate() {
		_run = false;
		_exec = false;
	}

	/**
	 * Récupère la periode d'execution.
	 * @return La periode en milliseconde.
	 */
	public int getPeriod() {
		return _period;
	}

	/**
	 * Définit la période d'éxecution.
	 * @param period La période en milliseconde.
	 */
	public void setPeriod(int period) {
		this._period = period;
	}
	
	/**
	 * Test si le simulateur est en marche.
	 * @return True si le simulateur est en marche, false sinon.
	 */
	public boolean isPlayed() {
		return _exec;
	}
	
}
