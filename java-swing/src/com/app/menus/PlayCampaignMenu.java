package com.app.menus;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PlayCampaignMenu {

	public JFrame framePlayCamp;
	public JLabel lblSelectCampaign;
	public JComboBox comboPlayCamp;
	public JButton btnLoadCamp;
	public JButton btnStartGame;



	public PlayCampaignMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePlayCamp = new JFrame();
		framePlayCamp.setBounds(100, 100, 450, 300);
		framePlayCamp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePlayCamp.getContentPane().setLayout(null);
		
		framePlayCamp.setTitle("Game Play");
		
		lblSelectCampaign = new JLabel("Select Campaign");
		lblSelectCampaign.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblSelectCampaign.setBounds(10, 94, 125, 22);
		framePlayCamp.getContentPane().add(lblSelectCampaign);
		
		comboPlayCamp = new JComboBox();
		comboPlayCamp.setBounds(10, 127, 125, 20);
		framePlayCamp.getContentPane().add(comboPlayCamp);
		
		btnLoadCamp = new JButton("Load");
		btnLoadCamp.setBounds(10, 156, 125, 23);
		framePlayCamp.getContentPane().add(btnLoadCamp);
		
		btnStartGame = new JButton("Start Game");
		btnStartGame.setBounds(10, 190, 125, 23);
		framePlayCamp.getContentPane().add(btnStartGame);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(new ImageIcon("images/Campaign.jpg").getImage().getScaledInstance(framePlayCamp.getSize().width,
				(int) ((int) framePlayCamp.getSize().height), java.awt.Image.SCALE_SMOOTH)));
		
	

		lblNewLabel.setBounds(0, 0, 434, 261);
		framePlayCamp.getContentPane().add(lblNewLabel);
		
		
		framePlayCamp.setVisible(true);
	}

}
