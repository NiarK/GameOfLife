package gameoflife;

import java.awt.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author pierre
 */
public class GameOfLife {

	/**
	 * @param args the command line arguments
	 */
	
	public static void main(String[] args) throws InterruptedException {
		
		Window win = new view.Window();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(view.Window.class.getName()).log(Level.SEVERE, null, ex);
		}
		SwingUtilities.updateComponentTreeUI(win);
		win.setVisible(true);
		
	}
}
