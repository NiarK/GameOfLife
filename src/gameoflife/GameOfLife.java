package gameoflife;

import java.awt.Point;
import java.sql.Time;
import java.util.Timer;
import model.gameoflife.Field;
import model.gameoflife.GameExecution;
import model.simulator.Simulator;
import model.gameoflife.StandardRule;

/**
 *
 * @author pierre
 */
public class GameOfLife {

	/**
	 * @param args the command line arguments
	 */
	
	public static void main(String[] args) throws InterruptedException {
		
		Field field = new Field(new Point(20,10));
		
		StandardRule rule = new StandardRule();
		rule.ruleStandard();
		
		rule.randomlyFill(field);
		
		Simulator s = new Simulator(new GameExecution(field, rule), 500);
		
		s.start();
		
		Thread.sleep(10000);
		
		s.pause();
		
		Thread.sleep(5000);
		
		s.play();
		
		Thread.sleep(10000);
		
		//s.pause();
		s.terminate();
		
		/*for(int i = 0; i < 50; ++i) {
			
			rule.update(field);
			System.out.println(field.toString());
			
			Thread.sleep(500);
		}*/
		
	}
}
