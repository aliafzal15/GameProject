package com.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.app.menus.CharacterEditorItemsInventory;
import com.app.menus.CharacterEditorMainMenu;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.utilities.FileStorage;


/**
 * 
 *This Class holds all the logic for actions available on the Main Character Editor Menu.
 *Create Fighter Button:- Creates a new Character with type Fighter. 
 *Create Zombie Button:- Creates a new character with type Zombie
 *Item Inventory Button:- Takes to the character item inventory menu.
 *Edit Button :- Enables the user to edit the character abilities by modifying the level.
 *@author Ali Afzal
 * 
 *
 */
public class CharacterEditorMenuController {
	
private CharacterEditorMainMenu charEditr;
private CharacterModel charObj;
private CharacterModel charTempObj;
private ArrayList <CharacterModel> characterList;
private FileStorage objFSO;
public String isFighter;
public boolean bolIsEmpty;



/**
 * Hold all the logic for implemented ActionListeners.
 * Initializes the Main ArrayList for holding the saved character details. Reads from File.
 * Saves the character in the file when action performed on the create fighter button
 *
 */
	public CharacterEditorMenuController(){
		
	
		charEditr=new CharacterEditorMainMenu();
		charEditr=charEditr.initialize(new JFrame());
		characterList=new ArrayList();
	    //charTempObj=new CharacterModel();
		
		this.isFighter="N";
		getCharactersFromFile();
		
		System.out.println("<<<<<<size of file Tempitems "+characterList.size());
		updateLabels(bolIsEmpty);
		
		 charEditr.btnCreateFighter.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
			 if (toBeAddedInFile(characterList,"Fighter")){ ///|| (bolIsEmpty==true)){	
				 charObj=new CharacterModel("Fighter");
				 
				
				 
					
				 characterList.add(charObj);
				 
				 setIsFighter();
						
		
				 charEditr.lblfStrengthVal.setText(Integer.toString(charObj.getStrength()));
				 charEditr.lblfDexterityVal.setText(Integer.toString(charObj.getDexterity()));
				 charEditr.lblfConstitutionVal.setText(Integer.toString(charObj.getConstitution()));
				 charEditr.lblfLevelVal.setText(Integer.toString(charObj.getCharLevel()));
				 charEditr.lblfStrModVal.setText(Integer.toString(charObj.getStrMod()));
				 charEditr.lblfDexModVal.setText(Integer.toString(charObj.getDexMod()));
				 charEditr.lblfConstModVal.setText(Integer.toString(charObj.getConstMod()));
				 charEditr.lblfHitPointsVal.setText(Integer.toString(charObj.getHitPoints()));
				 charEditr.lblfAttackBonusVal.setText(Integer.toString(charObj.getAttackBonus()));
				 charEditr.lblfDamageBonusVal.setText(Integer.toString(charObj.getDamageBonus()));
				
				  try {
					  objFSO=new FileStorage();
					  objFSO.SaveCharInFile(characterList);
					  
				  } catch (IOException e1) {
					JOptionPane.showMessageDialog (null, "Character Could not be Saved due to Error", "Record Not Saved", 
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				  }
				  objFSO=null;
				
			} //if tobeadded
          }
        });
	
		 charEditr.btnCreateZombie.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				if (toBeAddedInFile(characterList,"Zombie")){ //&& (isFighter.equals("Y"))){	
				 charObj=new CharacterModel("Zombie");
				 
				
				
					
				 characterList.add(charObj);
						
		
				 charEditr.lblzStrengthVal.setText(Integer.toString(charObj.getStrength()));
				 charEditr.lblzDexterityVal.setText(Integer.toString(charObj.getDexterity()));
				 charEditr.lblzConstitutionVal.setText(Integer.toString(charObj.getConstitution()));
				 charEditr.lblzLevelVal.setText(Integer.toString(charObj.getCharLevel()));
				 charEditr.lblzStrModVal.setText(Integer.toString(charObj.getStrMod()));
				 charEditr.lblzDexModVal.setText(Integer.toString(charObj.getDexMod()));
				 charEditr.lblzConstVal.setText(Integer.toString(charObj.getConstMod()));
				 charEditr.lblzHitPointsVal.setText(Integer.toString(charObj.getHitPoints()));
				 charEditr.lblzAttackBonusVal.setText(Integer.toString(charObj.getAttackBonus()));
				 charEditr.lblzDamageBonusVal.setText(Integer.toString(charObj.getDamageBonus()));
				 System.out.println("*****size of file Tempitems "+characterList.size());
				
				  try {
					  objFSO=new FileStorage();
					  objFSO.SaveCharInFile(characterList);
					  
				  } catch (IOException e1) {
					JOptionPane.showMessageDialog (null, "Character Could not be Saved due to Error", "Record Not Saved", 
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				  }
				  objFSO=null;
				  
				  
			} //if tobeadded
          }
        });	
		 
		 charEditr.btnResetdelete.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					try {
						  objFSO=new FileStorage();
						  objFSO.deleteCharacter();
						  setLabelsZero();
						  resetAll();
						  
						  
						  
					  } catch (IOException e1) {
						JOptionPane.showMessageDialog (null, "Could not be Reset","Error",  
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					  }
					  objFSO=null;
					
					
				}
	        });	
		 
		 charEditr.fItemInventory.addActionListener(new ActionListener() {
			 
			 	
				@Override
	            public void actionPerformed(ActionEvent e) {
					
				 if(characterList.size()>0)	{
					for (int i=0;i<characterList.size();i++){
						
						 String type=(characterList.get(i).getCharType());
						 	if(type.equals("Fighter")){
						 		
						 		new CharacterEditorItemsController(characterList,i,"Fighter",charEditr);
						 		
						 	}//if int							  	
						
					}//for
					
				 }//if
				 
			
					
					
				}
	        });
		 
		 charEditr.zItemInventroy.addActionListener(new ActionListener() {
			 
			 	
				@Override
	            public void actionPerformed(ActionEvent e) {
					
				 if(characterList.size()>0)	{
					for (int i=0;i<characterList.size();i++){
						
						 String type=(characterList.get(i).getCharType());
						 	if(type.equals("Zombie")){
						 		
						 		new CharacterEditorItemsController(characterList,i,"Zombie",charEditr);
						 		
						 	}//if int
						 	
							/* else{
								 JOptionPane.showMessageDialog (null, "First Create Character","Error", 
											JOptionPane.ERROR_MESSAGE);
								 
							 }
						 	*/
						
					}//for
					
				 }//if
				 
			
					
					
				}
	        });
		 
		 charEditr.btnEditZombie.addActionListener(new ActionListener() {
			 
			 	
				@Override
	            public void actionPerformed(ActionEvent e) {
					
				 if(characterList.size()>0)	{
					for (int i=0;i<characterList.size();i++){
						
						 String type=(characterList.get(i).getCharType());
						 	if(type.equals("Zombie")){
						 		
						 		new CharacterEditorEditMenuController(characterList,i,type,charEditr);
						 		
						 	}//if int
						
					}//for
					
				 }//if
				 				
				}
	        });
		 
		 charEditr.btnEditFighter.addActionListener(new ActionListener() {
			 
			 	
				@Override
	            public void actionPerformed(ActionEvent e) {
					
				 if(characterList.size()>0)	{
					for (int i=0;i<characterList.size();i++){
						
						 String type=(characterList.get(i).getCharType());
						 	if(type.equals("Fighter")){
						 		
						 		new CharacterEditorEditMenuController(characterList,i,type,charEditr);
						 		
						 	}//if int
						
					}//for
					
				 }//if
				 				
				}
	        });
		 
		 
		
		
	}
	
	/**
	 *
	 * Checks if the Character already created or not.
	 * 
	 * @param charsList
	 * 			Characters ArrayList; holding all characters.
	 * 
	 * @param  charType
	 * 			Type of the character to be created.
	 * 
	 * @return Boolean
	 * 			tobeAdded; True means Character to be created,
	 * 					  False means Character already created
	 *        
	 */
private boolean toBeAddedInFile(ArrayList <CharacterModel> charsList,String charType){
		
	boolean toBeAdded=true;
	
 if (charsList.size()>0){
	
	for (int i=0;i<charsList.size();i++){
		String tempType =charsList.get(i).getCharType();
		
			if(tempType.equals(charType)){
				JOptionPane.showMessageDialog (null,"Character Could not be Created. Please Try Edit Option.","Error !!!", 
						JOptionPane.ERROR_MESSAGE); 
			    toBeAdded=false;
				
			}
			
	 }//for
	
 }//if
 
 
 else{
	 toBeAdded=true;
 }
		
	return toBeAdded;
}
	
/**
 * Calls to the FileStorage readCharacterInFile() function to read the saved characters.
 *        
 */
private void getCharactersFromFile(){
	
	objFSO=new FileStorage();
	try {
		
		
		    this.characterList=objFSO.readCharacterInFile();
			if(this.characterList.size()>0){
				this.bolIsEmpty=false;
			}
			
			else{
				this.bolIsEmpty=true;
			}
		System.out.println("XXXXXXXXsize of file Tempitems "+characterList.size());
			
		objFSO=null;
		
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	objFSO=null;
	

	//return items;
	
	
}

/**
 * This function sets ability scroes to zero when no character is generated.
 *        
 */
private void setLabelsZero(){
	
	 charEditr.lblfStrengthVal.setText("0");
	 charEditr.lblfDexterityVal.setText("0");
	 charEditr.lblfConstitutionVal.setText("0");
	 charEditr.lblfLevelVal.setText("0");
	 charEditr.lblfStrModVal.setText("0");
	 charEditr.lblfDexModVal.setText("0");
	 charEditr.lblfConstModVal.setText("0");
	 charEditr.lblfHitPointsVal.setText("0");
	 charEditr.lblfAttackBonusVal.setText("0");
	 charEditr.lblfDamageBonusVal.setText("0");
	 charEditr.lblfArmorClassVal.setText("0");
	 charEditr.lblzStrengthVal.setText("0");
	 charEditr.lblzDexterityVal.setText("0");
	 charEditr.lblzConstitutionVal.setText("0");
	 charEditr.lblzLevelVal.setText("0");
	 charEditr.lblzStrModVal.setText("0");
	 charEditr.lblzDexModVal.setText("0");
	 charEditr.lblzConstVal.setText("0");
	 charEditr.lblzHitPointsVal.setText("0");
	 charEditr.lblzAttackBonusVal.setText("0");
	 charEditr.lblzDamageBonusVal.setText("0");
	 charEditr.lblzArmorClassVal.setText("0");
	
}
	


/**
 * This function updates the GUI elements with the new ability scores
 *        
 */
private void updateLabels(boolean bolEmpty){
	
	if (bolEmpty==true){
		
		setLabelsZero();
		
	}
	
	
	else{
		
		for (int i=0;i<characterList.size();i++){
		
		 String type=(characterList.get(i).getCharType());
		 	if(type.equals("Fighter")){
		 		
		 	 setIsFighter();
			 charEditr.lblfStrengthVal.setText(Integer.toString(characterList.get(i).getStrength()));
			 charEditr.lblfStrengthVal.setText(Integer.toString(characterList.get(i).getStrength()));
			 charEditr.lblfDexterityVal.setText(Integer.toString(characterList.get(i).getDexterity()));
			 charEditr.lblfConstitutionVal.setText(Integer.toString(characterList.get(i).getConstitution()));
			 charEditr.lblfLevelVal.setText(Integer.toString(characterList.get(i).getCharLevel()));
			 charEditr.lblfStrModVal.setText(Integer.toString(characterList.get(i).getStrMod()));
			 charEditr.lblfDexModVal.setText(Integer.toString(characterList.get(i).getDexMod()));
			 charEditr.lblfConstModVal.setText(Integer.toString(characterList.get(i).getConstMod()));
			 charEditr.lblfHitPointsVal.setText(Integer.toString(characterList.get(i).getHitPoints()));
			 charEditr.lblfAttackBonusVal.setText(Integer.toString(characterList.get(i).getAttackBonus()));
			 charEditr.lblfDamageBonusVal.setText(Integer.toString(characterList.get(i).getDamageBonus()));
			 charEditr.lblfArmorClassVal.setText(Integer.toString(characterList.get(i).getArmorClass()));
		 	}
		 	
		 	if(type.equals("Zombie")){
		 		
			 	 this.isFighter="Y";
				 charEditr.lblzStrengthVal.setText(Integer.toString(characterList.get(i).getStrength()));
				 charEditr.lblzStrengthVal.setText(Integer.toString(characterList.get(i).getStrength()));
				 charEditr.lblzDexterityVal.setText(Integer.toString(characterList.get(i).getDexterity()));
				 charEditr.lblzConstitutionVal.setText(Integer.toString(characterList.get(i).getConstitution()));
				 charEditr.lblzLevelVal.setText(Integer.toString(characterList.get(i).getCharLevel()));
				 charEditr.lblzStrModVal.setText(Integer.toString(characterList.get(i).getStrMod()));
				 charEditr.lblzDexModVal.setText(Integer.toString(characterList.get(i).getDexMod()));
				 charEditr.lblzConstVal.setText(Integer.toString(characterList.get(i).getConstMod()));
				 charEditr.lblzHitPointsVal.setText(Integer.toString(characterList.get(i).getHitPoints()));
				 charEditr.lblzAttackBonusVal.setText(Integer.toString(characterList.get(i).getAttackBonus()));
				 charEditr.lblzDamageBonusVal.setText(Integer.toString(characterList.get(i).getDamageBonus()));
				 charEditr.lblzArmorClassVal.setText(Integer.toString(characterList.get(i).getArmorClass()));
			 	}
			 	
		 	
		
	}
		
  }

}


	/**
	 * This function resets all characters to zero values, updates the GUI 
	 *        
	 */
private void resetAll(){
	
	characterList=null;
	characterList=new ArrayList();
	this.isFighter="N";
	this.bolIsEmpty=true;
	getCharactersFromFile();
	updateLabels(bolIsEmpty);
}


/**
 * This function sets isFighter variable to "Y"
 *        
 */
private void setIsFighter(){
	this.isFighter="Y";
	
}

}
