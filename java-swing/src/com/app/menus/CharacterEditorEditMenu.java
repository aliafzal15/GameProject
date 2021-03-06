package com.app.menus;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;

/**
 *
 * This Class initiates the creation of GUI elements in the Character Edit Menu 
 * @author Ali Afzal
 */
public class CharacterEditorEditMenu {
	
	public JLabel lblLevel;
	public JComboBox comboLevel;
	public JButton btnSaveLevel;
	public JFrame frame;
	public JButton btnMakeBully;
	public JButton btnMakeNimble;
	public JButton btnMakeTank;
	
	
	
	/**
	 * Initializes the GUI of Character Edit Menu
	 * 
	 */
	public CharacterEditorEditMenu() {
		initialize();
		initializeComboLevel();
	}
	/**
	 * Initialize the contents of the frame.
	 * All GUI objects are created and aligned on the window in this method.
	 */	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 447, 183);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblLevel = new JLabel("Level :");
		lblLevel.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblLevel.setBounds(34, 52, 57, 14);
		frame.getContentPane().add(lblLevel);
		
		comboLevel = new JComboBox();
		comboLevel.setBounds(90, 50, 46, 20);
		frame.getContentPane().add(comboLevel);
		
		btnSaveLevel = new JButton("Save");
		btnSaveLevel.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnSaveLevel.setBounds(160, 48, 89, 23);
		frame.getContentPane().add(btnSaveLevel);
		
		btnMakeBully = new JButton("Make Bully");
		btnMakeBully.setBounds(10, 102, 117, 23);
		frame.getContentPane().add(btnMakeBully);
		
		btnMakeNimble = new JButton("Make Nimble");
		btnMakeNimble.setBounds(148, 102, 123, 23);
		frame.getContentPane().add(btnMakeNimble);
		
		btnMakeTank = new JButton("Make Tank");
		btnMakeTank.setBounds(292, 102, 117, 23);
		frame.getContentPane().add(btnMakeTank);
		
		frame.setTitle("Character Editor");
		
		frame.setVisible(true);
	}
	
	
	/**
	 * Initializes the Combo Value of the level combo box.
	 * 1-100 levels displayed in the combo.
	 */	
	private void initializeComboLevel(){
		int i=1;
		
		do{
			
			comboLevel.addItem(i);
			i++;
			
		}while(i<101);
	}

}
