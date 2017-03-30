package com.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.app.builder.BullyCharacterBuilder;
import com.app.builder.CharacterBuilder;
import com.app.builder.Explorer;
import com.app.builder.NimbleCharacterBuilder;
import com.app.builder.TankCharacterBuilder;
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
	public CharacterEditorEditMenuController(final ArrayList <CharacterModel> characterLists,final int objIndex,final String plyrType,final CharacterEditorMainMenu mainFrame){
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
					attackBonus=characterLists.get(objIndx).getCharLevel();
					characterLists.get(objIndx).setAttackBonus(attackBonus);
					
					
					if(plrType.equals("Zombie")){
					mainFrame.lblzHitPointsVal.setText(Integer.toString(hitPnts));
					mainFrame.lblzAttackBonusVal.setText(Integer.toString(attackBonus));
					mainFrame.lblzLevelVal.setText(Integer.toString(characterLists.get(objIndx).getCharLevel()));
					}
					
					else if(plrType.equals("Fighter")){
						mainFrame.lblfHitPointsVal.setText(Integer.toString(hitPnts));
						mainFrame.lblfAttackBonusVal.setText(Integer.toString(attackBonus));
						mainFrame.lblfLevelVal.setText(Integer.toString(characterLists.get(objIndx).getCharLevel()));
						}
					
					 try {
						  objFSO=new FileStorage();
						  objFSO.SaveCharInFile(characterLists);
						  objFSO=null;
						  editFrame.frame.dispose();
						  
					  } catch (IOException e1) {
					
						e1.printStackTrace();
					  }	 
					 
					 editFrame.frame.dispose();	
									
				}
     });
			
			editFrame.btnMakeBully.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						CharacterBuilder bullyBuilder=new BullyCharacterBuilder();
						Explorer explr=new Explorer();
						explr.setBuilder(bullyBuilder);
						explr.buildTypeCharacter(characterLists.get(objIndx));
						characterLists.get(objIndx).setFightingType("Bully");
						characterLists.get(objIndx).generateModifiers();
						characterLists.get(objIndx).calculateHitPoints();
						renderAbilities(mainFrame,plyrType,characterLists.get(objIndx));
						
						 try {
							  objFSO=new FileStorage();
							  objFSO.SaveCharInFile(characterLists);
							  objFSO=null;
							  editFrame.frame.dispose();
							  
						  } catch (IOException e1) {
						
							e1.printStackTrace();
						  }	 
						 
						editFrame.frame.dispose();
				}
     });	
			
			editFrame.btnMakeNimble.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						CharacterBuilder nimbleBuilder=new NimbleCharacterBuilder();
						Explorer explr=new Explorer();
						explr.setBuilder(nimbleBuilder);
						explr.buildTypeCharacter(characterLists.get(objIndx));	
						characterLists.get(objIndx).setFightingType("Nimble");
						characterLists.get(objIndx).generateModifiers();
						characterLists.get(objIndx).calculateHitPoints();
						renderAbilities(mainFrame,plyrType,characterLists.get(objIndx));
						
						 try {
							  objFSO=new FileStorage();
							  objFSO.SaveCharInFile(characterLists);
							  objFSO=null;
							  editFrame.frame.dispose();
							  
						  } catch (IOException e1) {
						
							e1.printStackTrace();
						  }	 
						 
						editFrame.frame.dispose();
				}
     });	
	
			editFrame.btnMakeTank.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						CharacterBuilder tankBuilder=new TankCharacterBuilder();
						Explorer explr=new Explorer();
						explr.setBuilder(tankBuilder);
						explr.buildTypeCharacter(characterLists.get(objIndx));
						characterLists.get(objIndx).setFightingType("Tank");
						characterLists.get(objIndx).generateModifiers();
						characterLists.get(objIndx).calculateHitPoints();
						renderAbilities(mainFrame,plyrType,characterLists.get(objIndx));
						
						 try {
							  objFSO=new FileStorage();
							  objFSO.SaveCharInFile(characterLists);
							  objFSO=null;
							  editFrame.frame.dispose();
							  
						  } catch (IOException e1) {
						
							e1.printStackTrace();
						  }	 
						 
						editFrame.frame.dispose();
				}
     });	
						
   }
	
public void renderAbilities(CharacterEditorMainMenu mainFrame,String plyrType,CharacterModel objCharacter){
	
	if(plrType.equals("Fighter")){
		mainFrame.lblfStrengthVal.setText(Integer.toString(objCharacter.getStrength()));
		mainFrame.lblfDexterityVal.setText(Integer.toString(objCharacter.getDexterity()));
		mainFrame.lblfConstitutionVal.setText(Integer.toString(objCharacter.getConstitution()));
		mainFrame.lblfStrModVal.setText(Integer.toString(objCharacter.getStrMod()));
		mainFrame.lblfDexModVal.setText(Integer.toString(objCharacter.getDexMod()));
		mainFrame.lblfConstModVal.setText(Integer.toString(objCharacter.getConstMod()));
		mainFrame.lblfHitPointsVal.setText(Integer.toString(objCharacter.getConstMod()));
		}
		
		else if(plrType.equals("Zombie")){
			mainFrame.lblzStrengthVal.setText(Integer.toString(objCharacter.getStrength()));
			mainFrame.lblzDexterityVal.setText(Integer.toString(objCharacter.getDexterity()));
			mainFrame.lblzConstitutionVal.setText(Integer.toString(objCharacter.getConstitution()));
			mainFrame.lblzStrModVal.setText(Integer.toString(objCharacter.getStrMod()));
			mainFrame.lblzDexModVal.setText(Integer.toString(objCharacter.getDexMod()));
			mainFrame.lblzConstVal.setText(Integer.toString(objCharacter.getConstMod()));
			mainFrame.lblzHitPointsVal.setText(Integer.toString(objCharacter.getConstMod()));
			}
	}
	

}
