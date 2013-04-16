/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author pierre
 */
public class Window extends JFrame implements ActionListener, WindowListener, MouseListener, MouseMotionListener {
	
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
		
		
		JPanel player = new JPanel();
		GridLayout playerLayout = new GridLayout(1, 3);
		player.setLayout(playerLayout);
		player.add(_btn_Play);
		player.add(_btn_Pause);
		player.add(_btn_Next);
		player.add(_btn_RandomlyFill);
		player.add(_btn_Empty);
		//player.add(Box.createHorizontalGlue());
		
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(_field, BorderLayout.CENTER);
		main.add(player, BorderLayout.SOUTH);
		main.setBackground(Color.GRAY);
		
		
		this.setContentPane(main);
		this.setVisible(true);
		
		
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
			Point oldIndicator = (Point)_field.getIndicator().clone();

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
	
	
	
}
