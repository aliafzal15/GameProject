package com.app.menus;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.app.controller.ItemEditorMainMenuController;

/**
 * This class is for creating GUI of Main Item Editor Menu
 * @author AliAfzal
 */
public class ItemEditorMainMenu extends JFrame{
	


	private final static int  windowWidth=300;
	private final static int  windowHeight=222;
	private String menuTitle ="Item Editor";
	
	public JButton createNewItem;
	public JButton editItem;
	
	
	/**
	 * ItemEditorMainMenu Constructor
	 * Builds the GUI elements on Main Window
	 */
	public ItemEditorMainMenu(){
		
		
		
		initMainEditorWindow();
		new ItemEditorMainMenuController(this);
		
	}

	/**
	 * This method initializes the GUI of Main Item Editor Menu
	 * 
	 */
private void initMainEditorWindow() {
		
	
		
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(menuTitle);
        this.setPreferredSize(new Dimension(windowWidth,windowHeight));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        createNewItem = new JButton("Create/Edit Items");
        //editItem = new JButton("Edit Item");
  
        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        c.add(createNewItem, new GridBagConstraints());
        //c.add(editItem, new GridBagConstraints());
        this.setVisible(true);
        
		
	}
	
	
	
}