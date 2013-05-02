/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import controller.RuleParameter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.image.ImageManager;

/**
 *
 * @author pierre
 */
public final class Window extends JFrame implements ActionListener, ChangeListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener, KeyListener {
	
	private RuleParameter _currentRuleParameter;
	
	private JButton		_btn_Pause;
	private JButton		_btn_Play;
	private JButton		_btn_Next;
	private JButton		_btn_RandomlyFill;
	private JButton		_btn_Empty;
	private JButton		_btn_Save;
	private JButton		_btn_Download;
	private JButton		_btn_ruleParameter;
	
	private JComboBox	_cbb_Speed;
	//private JComboBox	_cbb_Rule;
	
	private JTextField	_txt_Column;
	private JTextField	_txt_Row;
	private JSlider		_sli_Column;
	private JSlider		_sli_Row;
	private JLabel		_lbl_Rule;
	private JLabel		_lbl_Search;
	
	private JMenuBar	_menuBar;
	private JMenu		_menuFile;
	private JMenu		_menuAction;
	private JMenu		_menuParameters;
	private JMenu		_menuZoom;
	private JMenuItem	_itemSave;
	private JMenuItem	_itemLoad;
	private JMenuItem	_itemPlay;
	private JMenuItem	_itemNext;
	private JMenuItem	_itemRandom;
	private JMenuItem	_itemEmpty;
	private JMenuItem	_itemSpeedP;
	private JMenuItem	_itemSpeedM;
	private JMenuItem	_itemSize;
	private JMenuItem	_itemParameters;
	private JMenuItem	_itemPlus;
	private JMenuItem	_itemMoins;
	
	Point _mousePosition;
	private Field _field;
	private Controller _controller;

	public Window() {
		ImageManager manager = ImageManager.getInstance();
		_controller = new Controller();
		
		_field = new Field(8);
		_field.setBackground(Color.black);
		_field.addMouseListener(this);
		_field.addMouseMotionListener(this);
		_field.addMouseWheelListener(this);
		_field.addComponentListener(this);
		
		_menuBar = new JMenuBar();
		_menuFile = new JMenu("File");
		_menuAction = new JMenu("Action");
		_menuParameters = new JMenu("Parameters");
		_menuZoom = new JMenu("Zoom");
		_itemSave = new JMenuItem("Save (s)");
		_itemSave.addActionListener(this);
		_itemLoad = new JMenuItem("Load (o)");
		_itemLoad.addActionListener(this);
		_itemPlay = new JMenuItem("Play/Pause (p)");
		_itemPlay.addActionListener(this);
		_itemNext = new JMenuItem("Next Step (space)");
		_itemNext.addActionListener(this);
		_itemRandom = new JMenuItem("Random (r)");
		_itemRandom.addActionListener(this);
		_itemEmpty = new JMenuItem("Stop (suppr)");
		_itemEmpty.addActionListener(this);
		_itemSpeedP = new JMenuItem("Speed + (PgPr)");
		_itemSpeedP.addActionListener(this);
		_itemSpeedM = new JMenuItem("Speed - (PgSv)");
		_itemSpeedM.addActionListener(this);
		_itemSize = new JMenuItem("Size");
		_itemSize.addActionListener(this);
		_itemParameters = new JMenuItem("Parameters (Enter)");
		_itemParameters.addActionListener(this);
		_itemPlus = new JMenuItem("Zoom + (+)");
		_itemPlus.addActionListener(this);
		_itemMoins = new JMenuItem("Zoom - (-)");
		_itemMoins.addActionListener(this);
		
		_menuBar.add(_menuFile);
		_menuBar.add(_menuAction);
		_menuBar.add(_menuParameters);
		_menuBar.add(_menuZoom);
		_menuFile.add(_itemSave);
		_menuFile.add(_itemLoad);
		_menuAction.add(_itemPlay);
		_menuAction.add(_itemNext);
		_menuAction.add(_itemRandom);
		_menuAction.add(_itemEmpty);
		_menuParameters.add(_itemSpeedP);
		_menuParameters.add(_itemSpeedM);
		_menuParameters.add(_itemSize);
		_menuParameters.add(_itemParameters);
		_menuZoom.add(_itemPlus);
		_menuZoom.add(_itemMoins);
		
		setJMenuBar(_menuBar);
		
		//_controller = new Controller();
		_controller.addObserverToGame(_field);
		_controller.empty();
		
		_currentRuleParameter = _controller.getRules()[0];
		
		_mousePosition = new Point();
		
		_btn_Pause = new JButton();
		_btn_Pause.addActionListener(this);
		_btn_Pause.addKeyListener(this);
		_btn_Pause.setIcon(new ImageIcon(manager.get("src/resources/pause.png")));
		
		_btn_Play = new JButton();
		_btn_Play.addActionListener(this);
		_btn_Play.addKeyListener(this);
		_btn_Play.setIcon(new ImageIcon(manager.get("src/resources/play.png")));
		
		_btn_Next = new JButton();
		_btn_Next.addActionListener(this);
		_btn_Next.addKeyListener(this);
		_btn_Next.setIcon(new ImageIcon(manager.get("src/resources/next.png")));
		
		_btn_RandomlyFill = new JButton();
		_btn_RandomlyFill.addActionListener(this);
		_btn_RandomlyFill.addKeyListener(this);
		_btn_RandomlyFill.setIcon(new ImageIcon(manager.get("src/resources/random.png")));
		
		_btn_Empty = new JButton();
		_btn_Empty.addActionListener(this);
		_btn_Empty.addKeyListener(this);
		_btn_Empty.setIcon(new ImageIcon(manager.get("src/resources/empty.png")));
		
		_btn_Save = new JButton();
		_btn_Save.addActionListener(this);
		_btn_Save.addKeyListener(this);
		_btn_Save.setIcon(new ImageIcon(manager.get("src/resources/save.png")));
		
		_btn_Download = new JButton();
		_btn_Download.addActionListener(this);
		_btn_Download.addKeyListener(this);
		_btn_Download.setIcon(new ImageIcon(manager.get("src/resources/open.png")));
		
                
		_btn_ruleParameter = new JButton();
		_btn_ruleParameter.addActionListener(this);
		_btn_ruleParameter.addKeyListener(this);
		_btn_ruleParameter.setIcon(new ImageIcon(manager.get("src/resources/param.png")));
		
		_lbl_Rule = new JLabel();
		_lbl_Search = new JLabel();
		
		_sli_Column = new JSlider(JSlider.HORIZONTAL, 1, 999, 50);
		_sli_Column.addChangeListener(this);
		_sli_Column.addKeyListener(this);
		
		_sli_Row = new JSlider(JSlider.HORIZONTAL, 1, 999, 50);
		_sli_Row.addChangeListener(this);
		_sli_Row.addKeyListener(this);
		
		_txt_Column = new JTextField(3);
		_txt_Column.setText(Integer.toString(_sli_Row.getValue()));
		_txt_Column.addActionListener(this);
		//_txt_Column.setInputVerifier(new InputVerifier);
		
		_txt_Row = new JTextField(3);
		_txt_Row.setText(Integer.toString(_sli_Row.getValue()));
		_txt_Row.addActionListener(this);
		
		_cbb_Speed = new JComboBox(this.getSpeeds());
		_cbb_Speed.setSelectedIndex(1);
		_cbb_Speed.addActionListener(this);
		_cbb_Speed.addKeyListener(this);
		
		/*_cbb_Rule = new JComboBox(_controller.getRules());
		_cbb_Rule.setSelectedIndex(0);
		_cbb_Rule.addActionListener(this);*/
		
		this.setTitle("Conway's game of life");
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel player = new JPanel();
		player.setLayout(new BoxLayout(player, BoxLayout.LINE_AXIS));
		player.add(Box.createHorizontalGlue());
		player.add(_btn_Play);
		player.add(Box.createHorizontalGlue());
		//player.add(_btn_Pause);
		player.add(_btn_Next);
		player.add(Box.createHorizontalGlue());
		
		JPanel fieldAction = new JPanel();
		fieldAction.setLayout(new BoxLayout(fieldAction, BoxLayout.LINE_AXIS));
		fieldAction.add(Box.createHorizontalGlue());
		fieldAction.add(_btn_RandomlyFill);
		fieldAction.add(Box.createHorizontalGlue());
		fieldAction.add(_btn_Empty);
		fieldAction.add(Box.createHorizontalGlue());
                
                JPanel xmlAction = new JPanel();
		xmlAction.setLayout(new BoxLayout(xmlAction, BoxLayout.LINE_AXIS));
		xmlAction.add(Box.createHorizontalGlue());
		xmlAction.add(_btn_Save);
		xmlAction.add(Box.createHorizontalGlue());
		xmlAction.add(_btn_Download);
		xmlAction.add(Box.createHorizontalGlue());
		
		JPanel sizeLabel = new JPanel();
		sizeLabel.setLayout(new FlowLayout());
		sizeLabel.add(new JLabel("Field size : "));
		sizeLabel.add(_txt_Column);
		sizeLabel.add(new JLabel(" x "));
		sizeLabel.add(_txt_Row);
		
		JPanel sizeColumn = new JPanel();
		sizeColumn.setLayout(new FlowLayout());
		sizeColumn.add(new JLabel("Column : "));
		sizeColumn.add(_sli_Column);
		
		JPanel sizeRow = new JPanel();
		sizeRow.setLayout(new FlowLayout());
		sizeRow.add(new JLabel("Row : "));
		sizeRow.add(_sli_Row);
		
		JPanel speed = new JPanel();
		speed.setLayout(new FlowLayout());
		speed.add(new JLabel("Speed : "));
		speed.add(_cbb_Speed);
		
		/*JPanel type = new JPanel();
		type.setLayout(new FlowLayout());
		type.add(new JLabel("Type : "));
		type.add(new JComboBox());
		
		JPanel search = new JPanel();
		search.setLayout(new FlowLayout());
		search.add(new JLabel("Search : "));
		search.add(new JComboBox());*/
		
		JPanel ruleBtn = new JPanel();
		ruleBtn.setLayout(new FlowLayout());
		ruleBtn.add(_btn_ruleParameter);
		
		JPanel rule = new JPanel();
		rule.setLayout(new FlowLayout());
		rule.add(_lbl_Rule);
		
		JPanel search = new JPanel();
		search.setLayout(new FlowLayout());
		search.add(_lbl_Search);
		
		this.updateRuleLabel();
		//rule.add(_cbb_Rule);
		//rule.add(_btn_ruleParameter);
		
		JPanel option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.PAGE_AXIS));
		option.add(Box.createVerticalGlue());
		option.add(player);
		option.add(Box.createVerticalGlue());
		option.add(fieldAction);
		option.add(Box.createVerticalGlue());
		option.add(xmlAction);
		option.add(Box.createVerticalGlue());
		option.add(new JSeparator());
		option.add(speed);
		option.add(new JSeparator());
		option.add(sizeLabel);
		option.add(sizeColumn);
		option.add(sizeRow);
		option.add(Box.createVerticalGlue());
		option.add(new JSeparator());
		/*option.add(type);
		option.add(search);*/
		option.add(rule);
		option.add(search);
		option.add(ruleBtn);
		//option.add(Box.createVerticalGlue());
		
		JTabbedPane panel = new JTabbedPane();
		panel.addTab("Option", option);
		panel.addTab("Patterns", null);
		
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(_field, BorderLayout.CENTER);
		main.add(panel, BorderLayout.WEST);
		main.setBackground(Color.GRAY);
		
		
		this.setContentPane(main);
		
		//game.addObserver(field);
		this.setFocusable(true);
		this.addWindowListener(this);
		this.addKeyListener(this);

	}
	
	public String[] getSpeeds() {
		Integer[] int_Speeds = Controller.getSpeeds();
		String[] str_Speeds = new String[int_Speeds.length];
		
		for(int i = 0; i < str_Speeds.length; ++i) {
			str_Speeds[i] = Integer.toString(int_Speeds[i])  + " ms";
		}
		
		return str_Speeds;
	}
	
	public void updateBtnPlay() {
		if (_controller.isPlayed()) {
			_btn_Play.setIcon(new ImageIcon(ImageManager.getInstance().get("src/resources/pause.png")));
		} 
		else {
			_btn_Play.setIcon(new ImageIcon(ImageManager.getInstance().get("src/resources/play.png")));
		}
	}

	public void updateRuleLabel() {
		
		String textRule = "Rule : ";
		
		if( ! _currentRuleParameter.getName().equals(_currentRuleParameter.getScientificName()) ) {
			textRule += _currentRuleParameter.getName() + " - ";
		}
		
		textRule += _currentRuleParameter.getScientificName();
		
		_lbl_Rule.setText(textRule);
		
		String textSearch = "Search : " + _currentRuleParameter.getSearch();
		
		if( _currentRuleParameter.isTorus() ) {
			textSearch += " - Torus";
		}
		
		_lbl_Search.setText(textSearch);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == _btn_Pause) {
			_controller.pause();
		}
		
		else if(e.getSource() == _btn_Play || e.getSource() == _itemPlay) {
			if(_controller.isPlayed()) {
				_controller.pause();
			}
			else {
				_controller.play();
			}
		}
                
                else if(e.getSource() == _btn_Save || e.getSource() == _itemSave) {
		    JFileChooser fc = new JFileChooser();
		    fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

		    if( fc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
		    {
			String name = JOptionPane.showInputDialog(null, "Nom du fichier :");
			_controller.save(fc.getSelectedFile().getAbsolutePath()+"/"+name);
		    }
		}
                
                else if(e.getSource() == _btn_Download || e.getSource() == _itemLoad) {
                    JFileChooser fc = new JFileChooser();
		    if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
		       // chemin absolu du fichier choisi
		       _controller.load(fc.getSelectedFile().getAbsolutePath());
		    }
		}
                		
		else if(e.getSource() == _btn_Next || e.getSource() == _itemNext) {
			_controller.next();
		}
		
		else if(e.getSource() == _btn_RandomlyFill || e.getSource() == _itemRandom) {
			_controller.randomlyFill();
		}
		
		else if(e.getSource() == _btn_Empty || e.getSource() == _itemEmpty) {
			_controller.empty();
		}
		
		else if(e.getSource() == _txt_Column) {
			
			try {
				int val = Integer.parseInt(_txt_Column.getText());
				_sli_Column.setValue(val);
			}
			catch (NumberFormatException ex) {
				Logger.getLogger(Window.class.getName()).log(Level.INFO, null, ex);
				_txt_Column.setText(Integer.toString(_sli_Column.getValue()));
			}
			
		}
		
		else if(e.getSource() == _txt_Row) {
			
			try {
				int val = Integer.parseInt(_txt_Row.getText());
				_sli_Row.setValue(val);
			}
			catch (NumberFormatException ex) {
				Logger.getLogger(Window.class.getName()).log(Level.INFO, null, ex);
				_txt_Row.setText(Integer.toString(_sli_Row.getValue()));
			}
			
		}
		
		else if(e.getSource() == _cbb_Speed) {
			
			_controller.setSpeed(Controller.getSpeeds()[_cbb_Speed.getSelectedIndex()]);
		}
		
		/*else if(e.getSource() == _cbb_Rule) {
			try {
				_controller.setRule(_controller.getRulesName()[_cbb_Rule.getSelectedIndex()]);
			} catch (BadRuleNameException ex) {
				Logger.getLogger(Window.class.getName()).log(Level.INFO, null, ex);
			}
		}*/
		
		else if(e.getSource() == _btn_ruleParameter || e.getSource() == _itemParameters) {
			RuleParameterDialog dialog = new RuleParameterDialog(this, _controller, _currentRuleParameter);
			RuleParameter rp = dialog.showDialog();
			
			if(rp != null) {
				_currentRuleParameter = rp;
				_controller.setRule(_currentRuleParameter);
				this.updateRuleLabel();
			}
		}
		
		else if(e.getSource() == _itemPlus){
		    int unit = -1;
		
		    _field.zoom(unit);
		}
		
		else if(e.getSource() == _itemMoins){
		    int unit = 1;
		
		    _field.zoom(unit);
		}
		
		this.updateBtnPlay();
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

	@Override
	public void stateChanged(ChangeEvent ce) {
		if(ce.getSource() == _sli_Column) {
			_txt_Column.setText(Integer.toString(_sli_Column.getValue()));
			_controller.setFieldSize(
					new Point(
					_sli_Column.getValue(),
					_sli_Row.getValue()));
		} else if (ce.getSource() == _sli_Row) {
			_txt_Row.setText(Integer.toString(_sli_Row.getValue()));
			_controller.setFieldSize(
					new Point(
					_sli_Column.getValue(),
					_sli_Row.getValue()));
		}
	}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
	System.out.println("Code touche pressée : " + e.getKeyCode() + " - caractère touche pressée : " + e.getKeyChar());
	
	if(e.getKeyCode() == 80){
	    if(_controller.isPlayed()) {
		_controller.pause();
	    }
	    else {
		_controller.play();
	    }
	}
	else if(e.getKeyCode() == 83){
	    JFileChooser fc = new JFileChooser();
	    fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

	    if( fc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
	    {
		String name = JOptionPane.showInputDialog(null, "Nom du fichier :");
		_controller.save(fc.getSelectedFile().getAbsolutePath()+"/"+name);
	    }
	    
	}
	else if(e.getKeyCode() == 79){
	    JFileChooser fc = new JFileChooser();
	    if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
	       // chemin absolu du fichier choisi
	       _controller.load(fc.getSelectedFile().getAbsolutePath());
	    }
	}
	else if(e.getKeyCode() == 32){
	    _controller.next();
	}
	else if(e.getKeyCode() == 82){
	    _controller.randomlyFill();
	}
	else if(e.getKeyCode() == 127){
	    _controller.empty();
	}
	else if(e.getKeyCode() == 82){
	    _controller.randomlyFill();
	}
	else if(e.getKeyCode() == 61){
	    int unit = -1;
	    _field.zoom(unit);
	}
	else if(e.getKeyCode() == 54){
	    int unit = 1;
	    _field.zoom(unit);
	}
	else if(e.getKeyCode() == 40){
	    _field.moveField(new Point(0,-10));
	}
	else if(e.getKeyCode() == 38){
	    _field.moveField(new Point(0,10));
	}
	else if(e.getKeyCode() == 37){
	    _field.moveField(new Point(10,0));
	}
	else if(e.getKeyCode() == 39){
	    _field.moveField(new Point(-10,0));
	}
	else if(e.getKeyCode() == 10){
	    RuleParameterDialog dialog = new RuleParameterDialog(this, _controller, _currentRuleParameter);
	    RuleParameter rp = dialog.showDialog();

	    if(rp != null) {
		    _currentRuleParameter = rp;
		    _controller.setRule(_currentRuleParameter);
		    this.updateRuleLabel();
	    }
	}
	
    }

    @Override
    public void keyReleased(KeyEvent e) {}
	
	
	
}
