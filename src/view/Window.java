/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author pierre
 */
public class Window extends JFrame implements ActionListener, WindowListener {
	
	private JButton _btn_Pause;
	private JButton _btn_Play;
	private JButton _btn_Next;
	private JButton _btn_RandomlyFill;
	private JButton _btn_Empty;
	
	private Controller _controller;

	public Window() {
		
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
		
		
		Field field = new Field();
		field.setBackground(Color.black);
		
		
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
		main.add(field, BorderLayout.CENTER);
		main.add(player, BorderLayout.SOUTH);
		main.setBackground(Color.GRAY);
		
		
		this.setContentPane(main);
		this.setVisible(true);
		
		
		//game.addObserver(field);
		_controller = new Controller();
		_controller.addObserverToGame(field);

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
	
	
}
