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
	public static void main(String[] args) {
		
		Field field = new Field(new Point(10,10));
		
		StandardRule rule = new StandardRule();
		rule.randomlyFill(field);
		
		System.out.println(field.toString());
		
		rule.randomlyFill(field);
		
		System.out.println(field.toString());
		
		rule.update(field);
		
	}
}
