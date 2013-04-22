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

	public Simulator(Runnable task, int period) throws InterruptedException {
		super(task);
		
		//this._timer = new Timer();
		this._task = task;
		this._period = period;
		_exec = false;
		_run = true;
		
		//this._timer.schedule(_task, _period, _period);
		//_timer.wait();
	}

	@Override
	public void run() {
		
		while(_run) {
			if(_exec){
				this._task.run();
			}
			try {
				Thread.sleep(_period);
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
	}

	public int getPeriod() {
		return _period;
	}

	public void setPeriod(int period) {
		this._period = period;
	}
	
	
	
}
