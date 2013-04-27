/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import controller.RuleParameter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
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

	private boolean _sendData;
	
	private RuleParameter _customRule;
	private RuleParameter _rule;
	
	private JComboBox _cbb_Rule;
	
	private JCheckBox[] _chb_Borns;
	private JCheckBox[] _chb_Survives;
	
	private JPanel _pnl_Born;
	private JPanel _pnl_Survive;
	
	private JButton _btn_Ok;
	private JButton _btn_Cancel;

	
	public RuleParameterDialog(JFrame parent, Controller controller, RuleParameter rp) {
		super(parent, "Rule parameters", true);
		
		_sendData = false;
		
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		
		//_customRule = new RuleParameter("Custom");
		
		_cbb_Rule = new JComboBox(controller.getRules());
		_cbb_Rule.addItem(new RuleParameter("Custom"));
		_cbb_Rule.addActionListener(this);
		
		_customRule = (RuleParameter)_cbb_Rule.getItemAt(_cbb_Rule.getItemCount() - 1);
		_rule = (RuleParameter)_cbb_Rule.getSelectedItem();
		
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
		validation.add(Box.createRigidArea(new Dimension(10, 0)));
		validation.add(_btn_Cancel);
		validation.add(Box.createRigidArea(new Dimension(10, 0)));
		
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
		
		if(rp != null){
			
			boolean updateCustom = true;
					
			for(int i = 0; i < _cbb_Rule.getItemCount() - 2; ++i) {
				RuleParameter item = (RuleParameter)_cbb_Rule.getItemAt(i);
				if(rp.getName().equals(item.getName())) {
					_cbb_Rule.setSelectedIndex(i);
					updateCustom = false;
					break;
				}
			}
			
			if(updateCustom) {
				_customRule.copy(rp);
				_cbb_Rule.setSelectedIndex(_cbb_Rule.getItemCount() - 1);
			}
		}
		
		_rule = (RuleParameter)_cbb_Rule.getSelectedItem();
	}
	
	public void initBSCheckBox() {
		int nbBorn = 8;
		int nbSurvive = 8;
		
		_pnl_Born.removeAll();
		_chb_Borns = new JCheckBox[nbBorn];
		for(int i = 0; i < nbBorn; ++i) {
			_chb_Borns[i] = new JCheckBox(Integer.toString(i+1));
			_chb_Borns[i].addActionListener(this); 
			_pnl_Born.add(_chb_Borns[i]);
		}
		
		_pnl_Survive.removeAll();
		_chb_Survives = new JCheckBox[nbSurvive];
		for(int i = 0; i < nbSurvive; ++i) {
			_chb_Survives[i] = new JCheckBox(Integer.toString(i+1));
			_chb_Survives[i].addActionListener(this);
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
			
			_rule = (RuleParameter)_cbb_Rule.getSelectedItem();
		}
		
		else if(ae.getSource() == _btn_Ok) {
			_sendData = true;
			this.dispose();
		}
		
		else if(ae.getSource() == _btn_Cancel) {
			_sendData = false;
			this.dispose();
		}
		
		else if( Arrays.asList(_chb_Borns).contains(ae.getSource()) ) {
			this.updateCustomRuleWithSelectedRule();
			
			JCheckBox chb = (JCheckBox)ae.getSource();
			
			if(chb.isSelected()) {
				_customRule.addBorn(Integer.parseInt(chb.getText()));
			}
			else {
				_customRule.removeBorn(Integer.parseInt(chb.getText()));
			}
			
			_cbb_Rule.setSelectedIndex(_cbb_Rule.getItemCount() - 1);
		}
		
		else if( Arrays.asList(_chb_Survives).contains(ae.getSource()) ) {
			this.updateCustomRuleWithSelectedRule();
			
			JCheckBox chb = (JCheckBox)ae.getSource();
			
			if(chb.isSelected()) {
				_customRule.addSurvive(Integer.parseInt(chb.getText()));
			}
			else {
				_customRule.removeSurvive(Integer.parseInt(chb.getText()));
			}
			
			_cbb_Rule.setSelectedIndex(_cbb_Rule.getItemCount() - 1);
		}
	}
	
	public void updateCustomRuleWithSelectedRule() {
		if (_cbb_Rule.getSelectedIndex() + 1 != _cbb_Rule.getItemCount()) {
			_customRule.copy((RuleParameter) _cbb_Rule.getSelectedItem());
		}
	}
	
	public RuleParameter showDialog() {
		
		this.setVisible(true);
		
		return (_sendData)? _rule : null;
	}
}
