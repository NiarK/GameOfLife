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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author pierre
 */
public class Window extends JFrame implements ActionListener {
	
	private JButton _btn_Pause;
	private JButton _btn_Play;
	
	private Controller _controller;

	public Window() {
		
		_btn_Pause = new JButton("Pause");
		_btn_Pause.addActionListener(this);
		
		_btn_Play = new JButton("Play");
		_btn_Play.addActionListener(this);
		
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
		player.add(new JButton("Next Step"));
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
		
	}
	

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == _btn_Pause) {
			_controller.pause();
		}
		
		else if(e.getSource() == _btn_Play) {
			_controller.play();
		}
		
	}
}
