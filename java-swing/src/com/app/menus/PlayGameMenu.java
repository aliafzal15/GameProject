package com.app.menus;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PlayGameMenu {

	public JFrame framePlayGame;
	public JComboBox comboPlayer;
	public JLabel lblSelectPlayer;
	public JButton btnNext;
	
	


	public PlayGameMenu() {
		

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePlayGame = new JFrame();
		framePlayGame.setBounds(100, 100, 450, 300);
		framePlayGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePlayGame.getContentPane().setLayout(null);
		framePlayGame.setTitle("Game Play");	
		
		comboPlayer = new JComboBox();
		comboPlayer.setBounds(30, 95, 101, 20);	
		framePlayGame.getContentPane().add(comboPlayer);
		
		lblSelectPlayer = new JLabel("Your Player");
		lblSelectPlayer.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblSelectPlayer.setBounds(30, 67, 112, 17);
		framePlayGame.getContentPane().add(lblSelectPlayer);
		
		btnNext = new JButton("Next");
		btnNext.setBounds(30, 126, 101, 22);
		framePlayGame.getContentPane().add(btnNext);
		
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(new ImageIcon("images/PlayGame.jpg").getImage().getScaledInstance(framePlayGame.getSize().width,
				(int) ((int) framePlayGame.getSize().height), java.awt.Image.SCALE_SMOOTH)));
		
	

		lblNewLabel.setBounds(0, 0, 434, 261);
		framePlayGame.getContentPane().add(lblNewLabel);
		
		

		
		framePlayGame.setVisible(true);
	}
}
