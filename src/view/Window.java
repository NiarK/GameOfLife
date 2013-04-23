/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.IconUIResource;

/**
 *
 * @author pierre
 */
public class Window extends JFrame implements ActionListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	
	private JButton _btn_Pause;
	private JButton _btn_Play;
	private JButton _btn_Next;
	private JButton _btn_RandomlyFill;
	private JButton _btn_Empty;
	
	Point _mousePosition;
	
	private Field _field;
	
	private Controller _controller;

	public Window() {
		
		_mousePosition = new Point();
		
		_btn_Pause = new JButton("Pause");
		_btn_Pause.addActionListener(this);
		
		_btn_Play = new JButton("Play");
		_btn_Play.addActionListener(this);
		
		_btn_Next = new JButton("Next Step");
		_btn_Next.addActionListener(this);
		
		_btn_RandomlyFill = new JButton("Randomly Fill");
		_btn_RandomlyFill.addActionListener(this);
		
		_btn_Empty = new JButton("Empty");
		_btn_Empty.addActionListener(this);
	
		
		this.setTitle("Conway's game of life");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		_field = new Field();
		_field.setBackground(Color.black);
		_field.addMouseListener(this);
		_field.addMouseMotionListener(this);
		_field.addMouseWheelListener(this);
		_field.addComponentListener(this);
		
		
		JPanel player = new JPanel();
		player.setLayout(new GridLayout(1, 3));
		player.add(_btn_Play);
		player.add(_btn_Pause);
		player.add(_btn_Next);
		//player.add(Box.createHorizontalGlue());
		
		JPanel fieldAction = new JPanel();
		fieldAction.setLayout(new GridLayout(1, 2));
		fieldAction.add(_btn_RandomlyFill);
		fieldAction.add(_btn_Empty);
		
		JPanel sizeLabel = new JPanel();
		sizeLabel.setLayout(new GridLayout(1, 4));
		sizeLabel.add(new JLabel("Field size : "));
		sizeLabel.add(new JLabel("50"));
		sizeLabel.add(new JLabel(" x "));
		sizeLabel.add(new JLabel("50"));
		
		JPanel sizeColumn = new JPanel();
		sizeColumn.setLayout(new GridLayout(1,2));
		sizeColumn.add(new JLabel("Column : "));
		sizeColumn.add(new JSlider(JSlider.HORIZONTAL, 1, 1000, 50));
		
		JPanel sizeRow = new JPanel();
		sizeRow.setLayout(new GridLayout(1,2));
		sizeRow.add(new JLabel("Row : "));
		sizeRow.add(new JSlider(JSlider.HORIZONTAL, 1, 1000, 50));
		
		JPanel speed = new JPanel();
		speed.setLayout(new GridLayout(1,2));
		speed.add(new JLabel("Speed : "));
		speed.add(new JComboBox());
		
		JPanel type = new JPanel();
		type.setLayout(new GridLayout(1,2));
		type.add(new JLabel("Type : "));
		type.add(new JComboBox());
		
		JPanel search = new JPanel();
		search.setLayout(new GridLayout(1,2));
		search.add(new JLabel("Search : "));
		search.add(new JComboBox());
		
		JPanel rule = new JPanel();
		rule.setLayout(new GridLayout(1,3));
		rule.add(new JLabel("Rule : "));
		rule.add(new JComboBox());
		Image icon = null;
		try {
			icon = ImageIO.read(new File("src/resources/param.png"));
		} catch (IOException ex) {
			Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
		}
		rule.add(new JButton("ok", new ImageIcon(icon)));
		
		JPanel option = new JPanel();
		option.setLayout(new GridLayout(13, 1, 10, 20));
		option.add(Box.createVerticalGlue());
		option.add(player);
		option.add(fieldAction);
		option.add(sizeLabel);
		option.add(sizeColumn);
		option.add(sizeRow);
		option.add(speed);
		option.add(type);
		option.add(search);
		option.add(rule);
		
		JTabbedPane panel = new JTabbedPane();
		panel.addTab("Otpion", option);
		panel.addTab("Patterns", null);
		
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(_field, BorderLayout.CENTER);
		main.add(panel, BorderLayout.WEST);
		main.setBackground(Color.GRAY);
		
		
		this.setContentPane(main);
		this.setVisible(true);
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			//Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
		}*/
		//SwingUtilities.updateComponentTreeUI(this);
		
		
		//game.addObserver(field);
		_controller = new Controller();
		_controller.addObserverToGame(_field);
		_controller.empty();

		this.addWindowListener(this);

	}
	

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == _btn_Pause) {
			_controller.pause();
		}
		
		else if(e.getSource() == _btn_Play) {
			_controller.play();
		}
		
		else if(e.getSource() == _btn_Next) {
			_controller.next();
		}
		
		else if(e.getSource() == _btn_RandomlyFill) {
			_controller.randomlyFill();
		}
		
		else if(e.getSource() == _btn_Empty) {
			_controller.empty();
		}
		
	}

	@Override
	public void windowClosing(WindowEvent we) {
		if(we.getSource() == this)
		{
			_controller.stop();
//			_field.terminate();
		}
	}

	@Override
	public void windowOpened(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowClosed(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowIconified(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowDeiconified(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowActivated(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowDeactivated(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mousePressed(MouseEvent me) {
		
		if(me.getModifiers() == MouseEvent.BUTTON1_MASK) {
			Point indicator = _field.getIndicator();

			if( indicator != null ) {
				_controller.toggleCell(indicator);
			}
		}
		else if (me.getModifiers() == MouseEvent.BUTTON3_MASK) {
			_mousePosition = me.getPoint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseExited(MouseEvent me) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		
		if(me.getModifiers() == MouseEvent.BUTTON1_MASK) {
			
			Point oldIndicator = _field.getIndicator();
			
			if( oldIndicator != null ) {
				oldIndicator = (Point)oldIndicator.clone();
			}

			mouseMoved(me);

			Point indicator = _field.getIndicator();

			if( indicator != null && ! indicator.equals(oldIndicator)) {
				_controller.toggleCell(indicator);
			}
		}
		else if (me.getModifiers() == MouseEvent.BUTTON3_MASK) {
			
			Point diff = me.getPoint();
			diff.x -= _mousePosition.x;
			diff.y -= _mousePosition.y;
			
			_mousePosition = me.getPoint();
			
			_field.moveField(diff);
		}
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		
		Point position = me.getPoint();
		
		_field.setIndicatorPosition(position);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		
		int unit = mwe.getWheelRotation();
		
		_field.zoom(unit);
	}

	@Override
	public void componentResized(ComponentEvent ce) {
		
		if( ce.getSource() == _field ){
			_field.resize();
		}
		
	}

	@Override
	public void componentMoved(ComponentEvent ce) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void componentShown(ComponentEvent ce) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void componentHidden(ComponentEvent ce) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
