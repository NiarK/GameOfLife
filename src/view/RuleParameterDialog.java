/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import controller.RuleParameter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author pierre
 */
public final class RuleParameterDialog extends JDialog implements ActionListener {

	private JComboBox _cbb_Rule;
	
	private JCheckBox[] _chb_Borns;
	private JCheckBox[] _chb_Survives;
	
	private JPanel _pnl_Born;
	private JPanel _pnl_Survive;
	
	private JButton _btn_Ok;
	private JButton _btn_Cancel;

	
	public RuleParameterDialog(JFrame parent, Controller controller, boolean modal) {
		super(parent, "Rule parameters", modal);
		
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		
		_cbb_Rule = new JComboBox(controller.getRules());
		_cbb_Rule.addActionListener(this);
		
		_pnl_Born = new JPanel();
		_pnl_Born.setBorder(BorderFactory.createTitledBorder("Born"));
		
		_pnl_Survive = new JPanel();
		_pnl_Survive.setBorder(BorderFactory.createTitledBorder("Survive"));
		
		_btn_Ok = new JButton("Ok");
		_btn_Ok.addActionListener(this);
		
		_btn_Cancel = new JButton("Cancel");
		_btn_Cancel.addActionListener(this);
		
		JPanel type = new JPanel();
		type.add(new JLabel("Type : "));
		type.add(new JComboBox(controller.getRules()));
		
		JPanel search = new JPanel();
		search.add(new JLabel("Search : "));
		search.add(new JComboBox(controller.getRules()));
		
		JPanel rule = new JPanel();
		rule.add(new JLabel("Rule : "));
		rule.add(_cbb_Rule);
		
		/*born.add(_chb_borns[0]);
		born.add(_chb_borns[1]);
		born.add(_chb_borns[2]);
		born.add(_chb_borns[3]);
		born.add(_chb_borns[4]);
		born.add(_chb_borns[5]);
		born.add(_chb_borns[6]);
		born.add(_chb_borns[7]);*/
		
		/*survive.add(_chb_survives[0]);
		survive.add(_chb_survives[1]);
		survive.add(_chb_survives[2]);
		survive.add(_chb_survives[3]);
		survive.add(_chb_survives[4]);
		survive.add(_chb_survives[5]);
		survive.add(_chb_survives[6]);
		survive.add(_chb_survives[7]);*/
		
		Box validation = Box.createHorizontalBox();
		validation.add(Box.createHorizontalGlue());
		validation.add(_btn_Ok);
		validation.add(Box.createHorizontalGlue());
		validation.add(_btn_Cancel);
		validation.add(Box.createHorizontalGlue());
		
		Box center = Box.createVerticalBox();
		center.add(_pnl_Born);
		center.add(Box.createVerticalStrut(5));
		center.add(_pnl_Survive);
		center.add(Box.createVerticalStrut(5));
		center.add(validation);
		center.add(Box.createVerticalStrut(5));
		
		JPanel panelRule = new JPanel();
		panelRule.setLayout(new BorderLayout());
		panelRule.add(rule, BorderLayout.NORTH);
		panelRule.add(center, BorderLayout.CENTER);
		
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BorderLayout());
		panelSearch.add(search, BorderLayout.NORTH);
		panelSearch.add(panelRule, BorderLayout.CENTER);
		
		JPanel panelType = new JPanel();
		panelType.setLayout(new BorderLayout());
		panelType.add(type, BorderLayout.NORTH);
		panelType.add(panelSearch, BorderLayout.CENTER);
		
		/*JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(top, BorderLayout.NORTH);
		main.add(center, BorderLayout.CENTER);*/
		
		this.setContentPane(panelType);
		
		this.initBSCheckBox();
	}
	
	public void initBSCheckBox() {
		int nbBorn = 8;
		int nbSurvive = 8;
		
		_pnl_Born.removeAll();
		_chb_Borns = new JCheckBox[nbBorn];
		for(int i = 0; i < nbBorn; ++i) {
			_chb_Borns[i] = new JCheckBox(Integer.toString(i+1));
			_pnl_Born.add(_chb_Borns[i]);
		}
		
		_pnl_Survive.removeAll();
		_chb_Survives = new JCheckBox[nbSurvive];
		for(int i = 0; i < nbSurvive; ++i) {
			_chb_Survives[i] = new JCheckBox(Integer.toString(i+1));
			_pnl_Survive.add(_chb_Survives[i]);
		}
		
		this.updateBSCheckBox();
	}
	
	public void updateBSCheckBox() {
		RuleParameter rule = (RuleParameter)_cbb_Rule.getSelectedItem();
		
		HashSet<Integer> borns = rule.getBorn();
		
		for(JCheckBox chb_born : _chb_Borns) {
			if(borns.contains(Integer.parseInt(chb_born.getText()))) {
				chb_born.setSelected(true);
			}
			else {
				chb_born.setSelected(false);
			}
		}
		
		HashSet<Integer> survives = rule.getSurvive();
		
		for(JCheckBox chb_survive : _chb_Survives) {
			if(survives.contains(Integer.parseInt(chb_survive.getText()))) {
				chb_survive.setSelected(true);
			}
			else {
				chb_survive.setSelected(false);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == _cbb_Rule) {
			this.updateBSCheckBox();
		}
		
		else if(ae.getSource() == _btn_Ok) {
			this.dispose();
		}
		
		else if(ae.getSource() == _btn_Cancel) {
			this.dispose();
		}
	}
	
	
}
