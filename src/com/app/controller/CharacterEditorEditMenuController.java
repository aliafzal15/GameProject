package com.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.app.menus.CharacterEditorEditMenu;
import com.app.menus.CharacterEditorMainMenu;
import com.app.models.CharacterModel;
import com.app.utilities.FileStorage;


/**
 * This class edits the level of the character and hencen the ability scores.
 */
public class CharacterEditorEditMenuController {
	
	private int charLevel;
	private CharacterEditorEditMenu editFrame;
	private int objIndx;
	private int hitPnts;
	private int attackBonus;
	private int newCharLevel;
	private String plrType;
	private FileStorage objFSO;
	
	
	/**
	 * This Constructor holds the action listeners for the Edit Menu to change the level
	 */
	public CharacterEditorEditMenuController(final ArrayList <CharacterModel> characterLists,final int objIndex,String plyrType,final CharacterEditorMainMenu mainFrame){
		editFrame= new CharacterEditorEditMenu();
		this.charLevel=characterLists.get(objIndex).getCharLevel();
		this.hitPnts=characterLists.get(objIndex).getHitPoints();
		this.attackBonus=0;
		this.plrType=plyrType;
		this.objIndx=objIndex;
		
		
			editFrame.btnSaveLevel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					newCharLevel=(int) editFrame.comboLevel.getSelectedItem();
					characterLists.get(objIndx).setCharLevel(newCharLevel);
					characterLists.get(objIndx).calculateHitPoints();
					hitPnts=characterLists.get(objIndx).getHitPoints();
					attackBonus=(newCharLevel-charLevel);
					characterLists.get(objIndx).setAttackBonus(attackBonus);
					
					
					if(plrType.equals("Zombie")){
					mainFrame.lblzHitPointsVal.setText(Integer.toString(hitPnts));
					mainFrame.lblzAttackBonusVal.setText(Integer.toString(attackBonus));
					}
					
					else if(plrType.equals("Fighter")){
						mainFrame.lblfHitPointsVal.setText(Integer.toString(hitPnts));
						mainFrame.lblfAttackBonusVal.setText(Integer.toString(attackBonus));
						}
					
					 try {
						  objFSO=new FileStorage();
						  objFSO.SaveCharInFile(characterLists);
						  objFSO=null;
						  
					  } catch (IOException e1) {
					
						e1.printStackTrace();
					  }	 
					 

									
				}
     });	
						
   }

}
