package com.app.menus;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RunTimeFriendlyExchangeItems {

	public JFrame frameExchange;
	public JComboBox comboBox;
	public JLabel lblPlyrItems;
	public JButton btnExchange;
	
	
	public RunTimeFriendlyExchangeItems() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frameExchange = new JFrame();
		frameExchange.setBounds(100, 100, 304, 197);
		frameExchange.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameExchange.getContentPane().setLayout(null);
		
		frameExchange.setTitle("Exchange Item With Friendly Character");
		
		
		JLabel lblExchangeItemWith = new JLabel("Exchange Item With Friendly Character");
		lblExchangeItemWith.setFont(new Font("Arial", Font.BOLD, 11));
		lblExchangeItemWith.setBounds(35, 11, 243, 14);
		frameExchange.getContentPane().add(lblExchangeItemWith);
		
		comboBox = new JComboBox();
		comboBox.setBounds(141, 75, 108, 20);
		frameExchange.getContentPane().add(comboBox);
		
		lblPlyrItems = new JLabel("New label");
		lblPlyrItems.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblPlyrItems.setBounds(38, 76, 93, 17);
		frameExchange.getContentPane().add(lblPlyrItems);
		
		btnExchange = new JButton("Exchange");	
		btnExchange.setBounds(141, 105, 108, 23);
		frameExchange.getContentPane().add(btnExchange);
		
		

		
		
		frameExchange.setVisible(true);
	
	}
}
