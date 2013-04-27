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
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public final class Window extends JFrame implements ActionListener, ChangeListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	
	private RuleParameter _currentRuleParameter;
	
	private JButton		_btn_Pause;
	private JButton		_btn_Play;
	private JButton		_btn_Next;
	private JButton		_btn_RandomlyFill;
	private JButton		_btn_Empty;
	private JButton		_btn_ruleParameter;
	
	private JComboBox	_cbb_Speed;
	//private JComboBox	_cbb_Rule;
	
	private JTextField	_txt_Column;
	private JTextField	_txt_Row;
	private JSlider		_sli_Column;
	private JSlider		_sli_Row;
	private JLabel		_lbl_rule;
	
	Point _mousePosition;
	
	private Field _field;
	
	private Controller _controller;

	public Window() {
		
		ImageManager manager = ImageManager.getInstance();
		
		_field = new Field();
		_field.setBackground(Color.black);
		_field.addMouseListener(this);
		_field.addMouseMotionListener(this);
		_field.addMouseWheelListener(this);
		_field.addComponentListener(this);
		
		_controller = new Controller();
		_controller.addObserverToGame(_field);
		_controller.empty();
		
		_currentRuleParameter = _controller.getRules()[0];
		
		_mousePosition = new Point();
		
		_btn_Pause = new JButton();
		_btn_Pause.addActionListener(this);
		_btn_Pause.setIcon(new ImageIcon(manager.get("src/resources/pause.png")));
		
		_btn_Play = new JButton();
		_btn_Play.addActionListener(this);
		_btn_Play.setIcon(new ImageIcon(manager.get("src/resources/play.png")));
		
		_btn_Next = new JButton();
		_btn_Next.addActionListener(this);
		_btn_Next.setIcon(new ImageIcon(manager.get("src/resources/next.png")));
		
		_btn_RandomlyFill = new JButton();
		_btn_RandomlyFill.addActionListener(this);
		_btn_RandomlyFill.setIcon(new ImageIcon(manager.get("src/resources/random.png")));
		
		_btn_Empty = new JButton();
		_btn_Empty.addActionListener(this);
		_btn_Empty.setIcon(new ImageIcon(manager.get("src/resources/empty.png")));
		
		_btn_ruleParameter = new JButton();
		_btn_ruleParameter.addActionListener(this);
		_btn_ruleParameter.setIcon(new ImageIcon(manager.get("src/resources/param.png")));
		
		_lbl_rule = new JLabel();
		
		_sli_Column = new JSlider(JSlider.HORIZONTAL, 1, 999, 50);
		_sli_Column.addChangeListener(this);
		
		_sli_Row = new JSlider(JSlider.HORIZONTAL, 1, 999, 50);
		_sli_Row.addChangeListener(this);
		
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
		rule.add(_lbl_rule);
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
		option.add(ruleBtn);
		//option.add(Box.createVerticalGlue());
		
		JTabbedPane panel = new JTabbedPane();
		panel.addTab("Otpion", option);
		panel.addTab("Patterns", null);
		
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(_field, BorderLayout.CENTER);
		main.add(panel, BorderLayout.WEST);
		main.setBackground(Color.GRAY);
		
		
		this.setContentPane(main);
		
		//game.addObserver(field);

		this.addWindowListener(this);

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
		_lbl_rule.setText("Rule : " + _currentRuleParameter.getName() + " - " + _currentRuleParameter.getScientificName());
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == _btn_Pause) {
			_controller.pause();
		}
		
		else if(e.getSource() == _btn_Play) {
			if(_controller.isPlayed()) {
				_controller.pause();
			}
			else {
				_controller.play();
			}
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
		
		else if(e.getSource() == _btn_ruleParameter) {
			RuleParameterDialog dialog = new RuleParameterDialog(this, _controller, _currentRuleParameter);
			RuleParameter rp = dialog.showDialog();
			
			if(rp != null) {
				_currentRuleParameter = rp;
				_controller.setRule(_currentRuleParameter);
				this.updateRuleLabel();
			}
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
	
	
	
}
