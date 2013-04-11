/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.gameoflife.Cell;
import model.gameoflife.Field;
import model.gameoflife.StandardRule;

/**
 *
 * @author Quentin
 */
public class WindowGame implements Observer {
    private Field field;
    private JFrame frame;
    private JPanel pane;
    private JPanel jPjeu;
    private JPanel jPbuttons;
    private JPanel jPsize;
    private JPanel[][] jPs;
    private JButton[] buttons;
    private int x;
    private int y;

    public WindowGame(int x, int y) {
        this.x = x;
        this.y = y;
        this.init();
    }

    public WindowGame() {
        this(10,10);
    }
    
    
    
    public void init() {
        String img = "tache-d-encre.jpg";
        ImageIcon image = new ImageIcon(img);
        //Détermine la dimension de la fenêtre
        Dimension d = new Dimension(x*25 + 200,y*25);
        
        //Définition du panel qui englobe tout
        pane = new JPanel(new GridLayout(1,0));
        //Création de la fenêtre
        frame = new JFrame("Le jeu de la vie");
        
        //On crée une taille de panel de la taille voulue
        jPjeu = new JPanel(new GridLayout(x,y));
        //Création du panel des boutons
        jPbuttons = new JPanel(new GridLayout(10,1));
        jPbuttons.setSize(200, y*25);
        
        //On crée chaque panel et on le met dans le panel de jeu
        jPs = new JPanel[x][y];
        for(int i =0; i<x; i++){
            for(int j =0; j<y; j++){
                jPs[i][j] = new JPanel();
                jPs[i][j].setSize(25, 25);
                jPs[i][j].setLayout(null);
                jPjeu.add(jPs[i][j]);
                JLabel lbl = new JLabel(image);
                lbl.setSize(25, 25);
                jPs[i][j].add(lbl);
                
                jPs[i][j].setBackground(Color.black);
            }
        }
        
        pane.add(jPjeu);
        pane.add(jPbuttons);
        frame.getContentPane().add(pane);
        frame.setSize(d);
        frame.setResizable(false);
        frame.setVisible(true);
        
        field = new Field(new Point(10,10));
        //field.addObserver(this);
        StandardRule rule = new StandardRule();
        rule.randomlyFill(field);
    }

    @Override
    public void update(Observable o, Object arg) {
        String img = "tache-d-encre.jpg";
        ImageIcon image = new ImageIcon(img);
        String imgInLife = "images.jpg";
        ImageIcon imageInLife = new ImageIcon(imgInLife);
        HashMap<Point,Cell> hm = field.getCells();
        /*if(o instanceof Field){
            for(int i = 0; i < x; i++){
                for(int j = 0; j < y; j++){
                    jPs[i][j].removeAll();
                    if(hm.containsKey(new Point(i,j))){
                        JLabel lbl = new JLabel(imageInLife);
                        lbl.setSize(25, 25);
                        jPs[i][j].add(lbl);
                    }
                    else{
                        JLabel lbl = new JLabel(image);
                        lbl.setSize(25, 25);
                        jPs[i][j].add(lbl);
                    }
                }
            }
        }*/
    }
}
