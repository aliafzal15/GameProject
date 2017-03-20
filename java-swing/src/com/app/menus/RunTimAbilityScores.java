package com.app.menus;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.app.controller.RunTimeInventroyController;
import com.app.models.CharacterModel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunTimAbilityScores {

	public JFrame frameAbilityScores;
	public JLabel lblAbilityScore;
	public  JLabel lblStrVal;
	public JLabel lblDexVal;
	public JLabel lblConsVal;
	public JButton btnItemInven;

	private CharacterModel gameChar;
	
	public RunTimAbilityScores(CharacterModel gamePlyr) {
		
		this.gameChar=gamePlyr;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frameAbilityScores = new JFrame();
		frameAbilityScores.setBounds(100, 100, 324, 210);
		frameAbilityScores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameAbilityScores.getContentPane().setLayout(null);
		
		frameAbilityScores.setTitle("Player's Ability Scores");
		
		lblAbilityScore = new JLabel("Player's Ability Scores");
		lblAbilityScore.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAbilityScore.setBounds(88, 23, 131, 24);
		frameAbilityScores.getContentPane().add(lblAbilityScore);
		
		JLabel lblStrength = new JLabel("Strength:");
		lblStrength.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblStrength.setBounds(21, 67, 80, 24);
		frameAbilityScores.getContentPane().add(lblStrength);
		
		JLabel lblDexterity = new JLabel("Dexterity: ");
		lblDexterity.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblDexterity.setBounds(21, 108, 90, 24);
		frameAbilityScores.getContentPane().add(lblDexterity);
		
		JLabel lblConstitution = new JLabel("Constitution: ");
		lblConstitution.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblConstitution.setBounds(21, 143, 102, 24);
		frameAbilityScores.getContentPane().add(lblConstitution);
		
		lblStrVal = new JLabel("New label");
		lblStrVal.setBounds(99, 73, 46, 14);
		frameAbilityScores.getContentPane().add(lblStrVal);
		
		lblDexVal = new JLabel("New label");
		lblDexVal.setBounds(110, 114, 46, 14);
		frameAbilityScores.getContentPane().add(lblDexVal);
		
		lblConsVal = new JLabel("New label");
		lblConsVal.setBounds(120, 149, 46, 14);
		frameAbilityScores.getContentPane().add(lblConsVal);
		
		btnItemInven = new JButton("Item Inventory");
		btnItemInven.setBounds(178, 90, 120, 23);
		frameAbilityScores.getContentPane().add(btnItemInven);
		
		btnItemInven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameAbilityScores.dispose();
				
				RunTimeIneventory invenMenu=new RunTimeIneventory();
				new RunTimeInventroyController(gameChar,invenMenu);
				//new InventoryViewDriver(gameChar);
				//gameChar.getInventory();
				
				
			}
		});
		
		
		
		
		
		frameAbilityScores.setVisible(true);
	}

}
