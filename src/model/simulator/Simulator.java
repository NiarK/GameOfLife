/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.simulator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pierre
 */
public class Simulator extends Thread {
	
	//private Timer _timer;
	private Runnable _task;
	private int _period;
	private boolean _exec;
	private boolean _run;
	
	public static final int STAND_BY_PERIOD = 5;

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
	
	
	
	public void play() {
		_exec = true;
	}
	
	public void pause() {
		_exec = false;
	}
	
	public void next() {
		this.pause();
		_task.run();
	}
	
	public void terminate() {
		_run = false;
		_exec = false;
	}

	public int getPeriod() {
		return _period;
	}

	public void setPeriod(int period) {
		this._period = period;
	}
	
	public boolean isPlayed() {
		return _exec;
	}
	
}
