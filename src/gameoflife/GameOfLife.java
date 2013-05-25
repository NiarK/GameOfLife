package gameoflife;

import java.awt.Window;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe principale qui lancele programme.
 * @author pierre
 */
public class GameOfLife {

	/**
	 * @param args Les arguments passés en ligne de commande.
	 */
	
	public static void main(String[] args) throws InterruptedException {
		
		Locale.setDefault ( java.util.Locale.ENGLISH ) ;
 
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
