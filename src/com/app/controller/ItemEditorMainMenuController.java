package com.app.controller;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.app.menus.*;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Ali Afzal
 * 
 * This Class holds the logic for item creation/editing
 * Calls another API ItemEditorCreateMenu where ActionListeners
 * have been implemented for the Button and Combos on the Item Editor window.
 *
 */
public class ItemEditorMainMenuController {
	
	//ItemEditorMainMenu mainMenuView;
	
	
	/**
   	 * Calls another API ItemEditorCreateMenu
   	 *
   	 *           
   	 */
	public ItemEditorMainMenuController(final ItemEditorMainMenu mainMenuFrame){
		
		
		mainMenuFrame.createNewItem.addActionListener(new ActionListener() {
          
			@Override
            public void actionPerformed(ActionEvent e) {
				new ItemEditorCreateMenu(mainMenuFrame);
               
            }
        });
		
		
		
	}
	
}