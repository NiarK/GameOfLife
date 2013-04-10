package gameoflife;

import java.awt.Point;
import model.Field;
import model.StandardRule;

/**
 *
 * @author pierre
 */
public class GameOfLife {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Field field = new Field(new Point(30,25));
		
		StandardRule rule = new StandardRule();
		rule.ruleStandard();
		
		rule.randomlyFill(field);
		
		for(int i = 0; i < 50; ++i) {
			
			rule.update(field);
			System.out.println(field.toString());
			
			Thread.sleep(500);
		}
		
	}
}
