package com.app.menus;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.app.controller.CampaignEditorController;

import java.awt.Font;
import javax.swing.JButton;

/**
 * This is the Campaign editor view class.
 * 
 * @author Ali Afzal
 *
 */
public class CampaignEditorMenu {

	public JFrame frameCamp;
	public JLabel lblCampaignMaps;
	public JComboBox comboMaps;
	public JButton btnAddMap;
	public JButton btnSaveCamp;
	public JLabel lblCampigns;
	public JComboBox comboLoadCamp;
	public JButton btnLoadCamp;
	public JButton btnRmvMap;

	/**
	 * Initializes the main campaign editor window and gives call to the controller constructor.
	 * 
	 * @author Ali Afzal
	 *
	 */
	public CampaignEditorMenu() {
		initialize();
		new CampaignEditorController(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameCamp = new JFrame();
		frameCamp.setBounds(100, 100, 500, 400);
		frameCamp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameCamp.getContentPane().setLayout(null);
		frameCamp.setTitle("Campaign Editor");
		
		comboMaps = new JComboBox();
		comboMaps.setBounds(162, 137, 120, 20);
		frameCamp.getContentPane().add(comboMaps);
		
		JLabel lblCampaignMaps = new JLabel("Campaign Maps");
		lblCampaignMaps.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblCampaignMaps.setBounds(35, 129, 120, 35);
		frameCamp.getContentPane().add(lblCampaignMaps);
		
		btnAddMap = new JButton("Add Map");
		btnAddMap.setBounds(162, 168, 120, 22);
		frameCamp.getContentPane().add(btnAddMap);
		
		
		btnRmvMap = new JButton("Remove Map");
		btnRmvMap.setBounds(292, 136, 111, 23);
		frameCamp.getContentPane().add(btnRmvMap);
		
		btnSaveCamp = new JButton("Save");
		btnSaveCamp.setBounds(162, 201, 120, 23);
		frameCamp.getContentPane().add(btnSaveCamp);
		
		lblCampigns = new JLabel("Campaign");
		lblCampigns.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblCampigns.setBounds(50, 49, 92, 14);
		frameCamp.getContentPane().add(lblCampigns);
		
		comboLoadCamp = new JComboBox();
		comboLoadCamp.setBounds(162, 47, 138, 20);
		frameCamp.getContentPane().add(comboLoadCamp);
		
		btnLoadCamp = new JButton("Load Campaign");
		btnLoadCamp.setBounds(162, 76, 138, 23);
		frameCamp.getContentPane().add(btnLoadCamp);
		
		frameCamp.setVisible(true);
		
		
	}
}
