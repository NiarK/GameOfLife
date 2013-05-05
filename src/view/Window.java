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
import java.io.File;
import java.io.FileFilter;
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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.image.ImageManager;

public final class Window extends JFrame implements ActionListener, ChangeListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener, KeyListener {

	private RuleParameter _currentRuleParameter;
	private JButton _btn_Pause;
	private JButton _btn_Play;
	private JButton _btn_Next;
	private JButton _btn_RandomlyFill;
	private JButton _btn_Empty;
	/*private JButton _btn_Save;
	private JButton _btn_Load;*/
	private JButton _btn_RuleParameter;
	private JComboBox _cbb_Speed;
	//private JComboBox	_cbb_Rule;
	private JTextField _txt_Column;
	private JTextField _txt_Row;
	private JSlider _sli_Column;
	private JSlider _sli_Row;
	private JLabel _lbl_Rule;
	private JLabel _lbl_Search;
	private JMenuBar _menu_bar;
	private JMenu _menu_file;
	private JMenu _menu_action;
	private JMenu _menu_parameters;
	private JMenu _menu_zoom;
	private JMenuItem _item_save;
	private JMenuItem _item_load;
	private JMenuItem _item_play;
	private JMenuItem _item_next;
	private JMenuItem _item_random;
	private JMenuItem _item_empty;
	private JMenuItem _item_speed_p;
	private JMenuItem _item_speed_m;
	private JMenuItem _item_size;
	private JMenuItem _item_parameters;
	private JMenuItem _item_plus;
	private JMenuItem _item_moins;
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

		_menu_bar = new JMenuBar();
		_menu_file = new JMenu("File");
		_menu_file.setMnemonic(KeyEvent.VK_F);
		_menu_action = new JMenu("Action");
		_menu_action.setMnemonic(KeyEvent.VK_A);
		_menu_parameters = new JMenu("Parameters");
		_menu_parameters.setMnemonic(KeyEvent.VK_M);
		_menu_zoom = new JMenu("Zoom");
		_menu_zoom.setMnemonic(KeyEvent.VK_Z);
		_item_save = new JMenuItem("Save");
		_item_save.addActionListener(this);
		_item_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		_item_load = new JMenuItem("Load");
		_item_load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		_item_load.addActionListener(this);
		_item_play = new JMenuItem("Play/Pause");
		_item_play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK));
		_item_play.addActionListener(this);
		_item_next = new JMenuItem("Next Step");
		_item_next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_MASK));
		_item_next.addActionListener(this);
		_item_random = new JMenuItem("Random");
		_item_random.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
		_item_random.addActionListener(this);
		_item_empty = new JMenuItem("Stop");
		_item_empty.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_MASK));
		_item_empty.addActionListener(this);
		_item_speed_p = new JMenuItem("Speed +");
		_item_speed_p.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, KeyEvent.CTRL_MASK));
		_item_speed_p.addActionListener(this);
		_item_speed_m = new JMenuItem("Speed -");
		_item_speed_m.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, KeyEvent.CTRL_MASK));
		_item_speed_m.addActionListener(this);
		_item_size = new JMenuItem("Size");
		_item_size.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
		_item_size.addActionListener(this);
		_item_parameters = new JMenuItem("Parameters");
		_item_parameters.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		_item_parameters.addActionListener(this);
		_item_plus = new JMenuItem("Zoom +");
		_item_plus.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_MASK));
		_item_plus.addActionListener(this);
		_item_moins = new JMenuItem("Zoom -");
		_item_moins.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_MASK));
		_item_moins.addActionListener(this);

		_menu_bar.add(_menu_file);
		_menu_bar.add(_menu_action);
		_menu_bar.add(_menu_parameters);
		_menu_bar.add(_menu_zoom);
		_menu_file.add(_item_save);
		_menu_file.add(_item_load);
		_menu_action.add(_item_play);
		_menu_action.add(_item_next);
		_menu_action.add(_item_random);
		_menu_action.add(_item_empty);
		_menu_parameters.add(_item_speed_p);
		_menu_parameters.add(_item_speed_m);
		_menu_parameters.add(_item_size);
		_menu_parameters.add(_item_parameters);
		_menu_zoom.add(_item_plus);
		_menu_zoom.add(_item_moins);

		setJMenuBar(_menu_bar);

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

		/*_btn_Save = new JButton();
		_btn_Save.addActionListener(this);
		_btn_Save.addKeyListener(this);
		_btn_Save.setIcon(new ImageIcon(manager.get("src/resources/save.png")));

		_btn_Load = new JButton();
		_btn_Load.addActionListener(this);
		_btn_Load.addKeyListener(this);
		_btn_Load.setIcon(new ImageIcon(manager.get("src/resources/open.png")));*/


		_btn_RuleParameter = new JButton();
		_btn_RuleParameter.addActionListener(this);
		_btn_RuleParameter.addKeyListener(this);
		_btn_RuleParameter.setIcon(new ImageIcon(manager.get("src/resources/param.png")));

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

		/*JPanel xmlAction = new JPanel();
		xmlAction.setLayout(new BoxLayout(xmlAction, BoxLayout.LINE_AXIS));
		xmlAction.add(Box.createHorizontalGlue());
		xmlAction.add(_btn_Save);
		xmlAction.add(Box.createHorizontalGlue());
		xmlAction.add(_btn_Load);
		xmlAction.add(Box.createHorizontalGlue());*/

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
		ruleBtn.add(_btn_RuleParameter);

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
		/*option.add(Box.createVerticalGlue());
		option.add(xmlAction);*/
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
		this.addKeyListener(this);
		this.setFocusable(true);
		this.addWindowListener(this);

	}

	public String[] getSpeeds() {
		Integer[] int_Speeds = Controller.getSpeeds();
		String[] str_Speeds = new String[int_Speeds.length];

		for (int i = 0; i < str_Speeds.length; ++i) {
			str_Speeds[i] = Integer.toString(int_Speeds[i]) + " ms";
		}

		return str_Speeds;
	}

	public void updateBtnPlay() {
		if (_controller.isPlayed()) {
			_btn_Play.setIcon(new ImageIcon(ImageManager.getInstance().get("src/resources/pause.png")));
		} else {
			_btn_Play.setIcon(new ImageIcon(ImageManager.getInstance().get("src/resources/play.png")));
		}
	}

	public void updateRuleLabel() {

		String textRule = "Rule : ";

		if (!_currentRuleParameter.getName().equals(_currentRuleParameter.getScientificName())) {
			textRule += _currentRuleParameter.getName() + " - ";
		}

		textRule += _currentRuleParameter.getScientificName();

		_lbl_Rule.setText(textRule);

		String textSearch = "Search : " + _currentRuleParameter.getSearch();

		if (_currentRuleParameter.isTorus()) {
			textSearch += " - Torus";
		}

		_lbl_Search.setText(textSearch);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == _btn_Pause) {
			_controller.pause();
		} 
		else if (e.getSource() == _btn_Play || e.getSource() == _item_play) {
			if (_controller.isPlayed()) {
				_controller.pause();
			} else {
				_controller.play();
			}
		} 
		else if (/*e.getSource() == _btn_Save || */e.getSource() == _item_save) {
			boolean test = false;
			JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(new File("Cellule.cells"));
			do {
				if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile().getPath();
					File toto = new File(path);
					if (!toto.exists()) {
						_controller.save(path);
					} else if (JOptionPane.showConfirmDialog(null, "Ce fichier existe déjà, l'écraser?", "Confirmer l'écrasement", JOptionPane.OK_CANCEL_OPTION) == 0) {
						_controller.save(path);
					} else {
						test = true;
					}

				} else {
					test = false;
				}
			} while (test);
		} 
		else if (/*e.getSource() == _btn_Load || */e.getSource() == _item_load) {
			JFileChooser fc = new JFileChooser();
			boolean test;
			do {
				test = false;
				if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile().getAbsolutePath().endsWith(".cells")) {
						_controller.load(fc.getSelectedFile().getAbsolutePath());
					} else {
						JOptionPane.showMessageDialog(null, "Vous devez choisir un fichier .cells");
						test = true;
					}
				}
			} while (test);
		} 
		else if (e.getSource() == _btn_Next || e.getSource() == _item_next) {
			_controller.next();
		} 
		else if (e.getSource() == _btn_RandomlyFill || e.getSource() == _item_random) {
			_controller.randomlyFill();
		} 
		else if (e.getSource() == _btn_Empty || e.getSource() == _item_empty) {
			_controller.empty();
		} 
		else if (e.getSource() == _txt_Column) {

			try {
				int val = Integer.parseInt(_txt_Column.getText());
				_sli_Column.setValue(val);
			} catch (NumberFormatException ex) {
				Logger.getLogger(Window.class.getName()).log(Level.INFO, null, ex);
				_txt_Column.setText(Integer.toString(_sli_Column.getValue()));
			}

		} else if (e.getSource() == _txt_Row) {

			try {
				int val = Integer.parseInt(_txt_Row.getText());
				_sli_Row.setValue(val);
			} catch (NumberFormatException ex) {
				Logger.getLogger(Window.class.getName()).log(Level.INFO, null, ex);
				_txt_Row.setText(Integer.toString(_sli_Row.getValue()));
			}

		} else if (e.getSource() == _cbb_Speed) {

			_controller.setSpeed(Controller.getSpeeds()[_cbb_Speed.getSelectedIndex()]);
		} /*else if(e.getSource() == _cbb_Rule) {
		 try {
		 _controller.setRule(_controller.getRulesName()[_cbb_Rule.getSelectedIndex()]);
		 } catch (BadRuleNameException ex) {
		 Logger.getLogger(Window.class.getName()).log(Level.INFO, null, ex);
		 }
		 }*/ 
		else if (e.getSource() == _btn_RuleParameter || e.getSource() == _item_parameters) {
			RuleParameterDialog dialog = new RuleParameterDialog(this, _controller, _currentRuleParameter);
			RuleParameter rp = dialog.showDialog();

			if (rp != null) {
				_currentRuleParameter = rp;
				_controller.setRule(_currentRuleParameter);
				this.updateRuleLabel();
				_field.setNeighbors(_controller.getNeighborMaximumNumber(rp.getSearch()));
			}
		} 
		else if (e.getSource() == _item_plus) {
			int unit = -1;

			_field.zoom(unit);
		} 
		else if (e.getSource() == _item_moins) {
			int unit = 1;

			_field.zoom(unit);
		}

		this.updateBtnPlay();
	}

	@Override
	public void windowClosing(WindowEvent we) {
		if (we.getSource() == this) {
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

		if (me.getModifiers() == MouseEvent.BUTTON1_MASK) {
			Point indicator = _field.getIndicator();

			if (indicator != null) {
				_controller.toggleCell(indicator);
			}
		} else if (me.getModifiers() == MouseEvent.BUTTON3_MASK) {
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

		if (me.getModifiers() == MouseEvent.BUTTON1_MASK) {

			Point oldIndicator = _field.getIndicator();

			if (oldIndicator != null) {
				oldIndicator = (Point) oldIndicator.clone();
			}

			mouseMoved(me);

			Point indicator = _field.getIndicator();

			if (indicator != null && !indicator.equals(oldIndicator)) {
				_controller.toggleCell(indicator);
			}
		} else if (me.getModifiers() == MouseEvent.BUTTON3_MASK) {

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

		if (ce.getSource() == _field) {
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
		if (ce.getSource() == _sli_Column) {
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
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("Code touche pressée : " + e.getKeyCode() + " - caractère touche pressée : " + e.getKeyChar());
		if (e.getKeyCode() == 40) {
			_field.moveField(new Point(0, -10));
		} else if (e.getKeyCode() == 38) {
			_field.moveField(new Point(0, 10));
		} else if (e.getKeyCode() == 37) {
			_field.moveField(new Point(10, 0));
		} else if (e.getKeyCode() == 39) {
			_field.moveField(new Point(-10, 0));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
