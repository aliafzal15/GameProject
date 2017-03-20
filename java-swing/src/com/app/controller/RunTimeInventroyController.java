package com.app.controller;



import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.app.menus.CharacterEditorItemsInventory;
import com.app.menus.CharacterEditorMainMenu;
import com.app.menus.RunTimAbilityScores;
import com.app.menus.RunTimeIneventory;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.utilities.FileStorage;




/**
 * 
 *This Class holds all the logic for actions available on the Character Inventory Menu.
 *@author Ali Afzal
 * 
 *
 */
public class RunTimeInventroyController {
	
private CharacterModel runTimeCharacter;
private RunTimeIneventory runTimeInvenWindow;
private int armorClassEnchanment;
private int strengthClassEnchanment;
private int attackBonusClassEnchanment;
private int removeIndex;
private int removeBagIndex;
	
	/**
	 * 
	 *This Constructor holds all the action listeners for Item Inventory Menu
	 *
	 *@param characterLists
	 *			ArrayList of all characters in the file and their properties
	 *@param objIndex
	 *			index in the ArrayList which holds the character properties 
	 *@param plyrType
	 *			Type of the character
	 *@param mainMenu
	 *			CharacterEditorMainMenu object to manipulate GUI elements
	 */
	public RunTimeInventroyController(final CharacterModel gameChar,RunTimeIneventory invenMenu){
		
		
		this.runTimeCharacter=gameChar;
		this.runTimeInvenWindow=invenMenu;
		
		populateWornItemsCombo();
		populateBagItemsCombo();

		
		this.armorClassEnchanment=initializeEnchanementClass("ArmorClass");
		this.strengthClassEnchanment=initializeEnchanementClass("StrengthClass");
		this.attackBonusClassEnchanment=initializeEnchanementClass("AttackBonusClass");
		
		
		
		
		
		runTimeInvenWindow.btnAddWornToBag.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				addWornToBag();							
				render();
				runTimeInvenWindow.frame.dispose();
			}
        });	
		
		runTimeInvenWindow.btnAddBagToWorn.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
			if(checkIfBagItemInWorn()==false){
				
				String temp=(String) runTimeInvenWindow.comboBagPack.getSelectedItem();
				
				String[] item=temp.split(":");
				
				ItemsModel objItem=new ItemsModel();
				objItem.itemType=item[0];
				objItem.itemBonus=item[1];
				
				
				
				if(runTimeCharacter.setWornItem(objItem)){
					
					setEnchanementValues(item[0],item[1],"Add");		
													
				}
				
				runTimeCharacter.getWornItems();
				
				runTimeCharacter.getBagItems().remove(removeBagIndex);
				
				
				
				//11 feb itemInv.comboItemsWorn.addItem(objItem.itemType+":"+objItem.itemBonus);
				render();
				
				objItem=null;
				
				runTimeInvenWindow.frame.dispose();
									
				
			}
		 }
						
        });	
		
				
	}
	
	
	
	
	
	
	
/**
 * Default Constructor
 */	
public RunTimeInventroyController() {
		// TODO Auto-generated constructor stub
	}





/**
 * This Method Sets all Items Combo
 */
private void setAllItemsCombo(){
		
	
}

	



/**
 * 
 *This Method populates Worn Items Combo
 *@param objInx
 *			index of the character object in characterLists ArrayList
 *				
 */
public void populateWornItemsCombo() {
		
			if(runTimeCharacter.getWornItems().size()>0){
	
					for(int i=0; i <runTimeCharacter.getWornItems().size();i++){
		
					 runTimeInvenWindow.comboItemsWorn.addItem(runTimeCharacter.getWornItems().get(i).itemType+":"+runTimeCharacter.getWornItems().get(i).itemBonus);
		
				}
			}
	}



/**
 * 
 *This Method populates Bag Pack Items Combo
 *@param objInx
 *			index of the character in the characterLists
 *						
 */
public void populateBagItemsCombo() {
	
	if(runTimeCharacter.getBagItems().size()>0){
		
		for(int i=0; i <runTimeCharacter.getBagItems().size();i++){

		 runTimeInvenWindow.comboBagPack.addItem(runTimeCharacter.getBagItems().get(i).itemType+":"+runTimeCharacter.getBagItems().get(i).itemBonus);

	}
}
			

}



public boolean checkIfItemInBagRmv(ArrayList<ItemsModel> items){
	
	if(items.size()>0){
		
		String temp=(String) runTimeInvenWindow.comboBagPack.getSelectedItem();
		//this.removeBagIndex= itemInv.comboBagPack.getSelectedIndex();
		
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeBagIndex= i;
			  		return false;
			  	}
			  	
			  		
			  
		  }
				
	}
	return true;
}

/**
 * 
 *This Method adds item to worn from bag list
 *@param objInx
 *			index of the character in the characterLists
 *			
 *				
 */
public void addWornToBag(){
	
	
	
	String tempItem=(String) runTimeInvenWindow.comboItemsWorn.getSelectedItem();
	int comboInx=runTimeInvenWindow.comboItemsWorn.getSelectedIndex();
	
	String [] itmArr=tempItem.split(":");
	
	ItemsModel objItm=new ItemsModel();
	
	objItm.itemType=itmArr[0];
	objItm.itemBonus=itmArr[1];
	

			
			if(runTimeCharacter.setBagItem(objItm)==true){
		
					//11feb itemInv.comboBagPack.addItem(tempItem);
					//11feb itemInv.comboItemsWorn.removeItemAt(comboInx);
					System.out.println("<><><><>Size of WornItems BeforeRemoval<><><><> "+this.runTimeCharacter.getWornItems().size());
						if (checkIfItemInWornRmv(runTimeCharacter.getWornItems())==false){
								setEnchanementValues(runTimeCharacter.getWornItems().get(removeIndex).itemType,runTimeCharacter.getWornItems().get(removeIndex).itemBonus,"Sub");
								runTimeCharacter.getWornItems().remove(removeIndex);
							}							
		}
		
}
	
	
	


/**
 * 
 *This Method renders the Main Character Editor Menu
 *			
 *				
 */
public void render(){
	setAllCombosNull();
	populateWornItemsCombo();
	populateBagItemsCombo();
	updateAblities();
}



/**
 * 
 *This Method sets all combos to Null
 *			
 *				
 */
public void setAllCombosNull(){
	
	this.runTimeInvenWindow.comboBagPack.removeAllItems();
	this.runTimeInvenWindow.comboItemsWorn.removeAllItems();
}

/**
 * 
 *This Method checks if bag item already in worn list
 *@param items
 *		   All items ArrayList				
 *				
 */
public boolean checkIfBagItemInWorn(){
	
if(runTimeCharacter.getBagItems().size()>0){
		
		String temp=(String) runTimeInvenWindow.comboBagPack.getSelectedItem();
		
		  for(int i=0;i<runTimeCharacter.getBagItems().size();i++){
			  		String tempVal=runTimeCharacter.getBagItems().get(i).itemType+":"+runTimeCharacter.getBagItems().get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeBagIndex=i;
			  		//JOptionPane.showMessageDialog (null, "Item Already in the Worn List!!!","Error", 
					//		JOptionPane.ERROR_MESSAGE);
			  		return false;
			  	}			  				  
		  }
				
	}
	return true;
		
}

/**
 * 
 *This Method updates all ability scores
 *			
 *				
 */
public void updateAblities(){
		runTimeCharacter.getAbilityScores();
}

public int getItemEnchanement(String itmType){
		
		for(int i=0;i<runTimeCharacter.getWornItems().size();i++){
			if(runTimeCharacter.getWornItems().get(i).itemType.equals(itmType)){
				return Integer.parseInt(runTimeCharacter.getWornItems().get(i).itemBonus);
			}
		}
	return 0;

}

/**
 * 
 *This Method gets the enchantment class of the item
 *@param Type
 *			Item Type			
 *@return the class to be increased
 */
public String getEnchanementType(String Type){
	
	String classType;
	
	
	 switch(Type) {
     case "Helmet" :
    	 classType= "ArmorClass"; 
        break;
     case "Armor" :
    	 classType= "ArmorClass";
     case "Shield":
    	 classType= "ArmorClass";
        break;
     case "Ring" :
    	 classType= "ArmorClass";
     case "Boots" :
    	 classType= "ArmorClass";
        break;
        
     case "Weapon":
    	 classType= "AttackBonusClass";
    	 break;
    	 
     case "Belt":
    	 classType="StrengthClass";
    	 break;
         
     default :
    	 classType="";
  }
	
	 	return classType;
	
	}

/**
 * 
 *This Method initializes the enchantment class
 *@param Type
 *			Item Type			
 *@return enchm: the enchantment value
 */
private int initializeEnchanementClass(String Type){
	
	int enchm=0;
	
	
	 switch(Type) {
	 
     case "ArmorClass" :
    		int enchHelmet=getItemEnchanement("Helmet");
    		int enchArmor=getItemEnchanement("Armor");
    		int enchShield=getItemEnchanement("Shield");
    		int enchRing=getItemEnchanement("Ring");
    		int enchBoots=getItemEnchanement("Boots");
    		
    		int totalArmorModifier=enchHelmet+enchArmor+enchShield+enchRing+enchBoots;
    		enchm=totalArmorModifier; 
    		break;
        
        
     case "AttackBonusClass" :
    	  enchm=runTimeCharacter.getAttackBonus();
    	  break;
    	 
    	 
     case "StrengthClass":
    	 enchm=runTimeCharacter.getStrength();
        break;
     
         
     default :
    	 return enchm;
  }
	
	 	return enchm;
	
	}

/**
 * 
 *This Method sets the enchantment value of the object Armor,Strength or Attack Bonus according to enchantment class
 *@param type
 *			Item type	
 *@param Opr		
 *		 Operation to be performed either add or subtract
 */
public void setEnchanementValues(String type,String bonus,String Opr){
	
	
	switch(Opr){
	
	case "Add":
	
	if(getEnchanementType(type).equals("ArmorClass")){
		
		armorClassEnchanment=armorClassEnchanment+Integer.parseInt(bonus);
		armorClassEnchanment=armorClassEnchanment+10+ runTimeCharacter.getDexMod();
		runTimeCharacter.increaseArmorClass(armorClassEnchanment);	
	}
	
	else if(getEnchanementType(type).equals("StrengthClass")){
		
		strengthClassEnchanment=strengthClassEnchanment+Integer.parseInt(bonus);		
		runTimeCharacter.increaseStrength(strengthClassEnchanment);
		int tempMod=runTimeCharacter.calculateModifier(runTimeCharacter.getStrength());
		runTimeCharacter.setStrMod(tempMod);
		runTimeCharacter.calculateDamageBonus();
		
		
	}
	else if(getEnchanementType(type).equals("AttackBonusClass")){
		
		attackBonusClassEnchanment=attackBonusClassEnchanment+Integer.parseInt(bonus);
		runTimeCharacter.increaseAttackBonus(attackBonusClassEnchanment);
		
		
	}
	
	break;
	
	
	case "Sub":
		
		if(getEnchanementType(type).equals("ArmorClass")){
			
			armorClassEnchanment=armorClassEnchanment-Integer.parseInt(bonus);
			armorClassEnchanment=armorClassEnchanment-10-runTimeCharacter.getDexMod();
			runTimeCharacter.increaseArmorClass(armorClassEnchanment);	
			
		
		}
		
		else if(getEnchanementType(type).equals("StrengthClass")){
			
			strengthClassEnchanment=strengthClassEnchanment-Integer.parseInt(bonus);
			runTimeCharacter.increaseStrength(strengthClassEnchanment);
			int tempMod=runTimeCharacter.calculateModifier(runTimeCharacter.getStrength());
			runTimeCharacter.setStrMod(tempMod);
			runTimeCharacter.calculateDamageBonus();
			
		}
		else if(getEnchanementType(type).equals("AttackBonusClass")){
			
			attackBonusClassEnchanment=attackBonusClassEnchanment-Integer.parseInt(bonus);
			runTimeCharacter.increaseAttackBonus(attackBonusClassEnchanment);
			
		}
		
		break;
		
	
	}
	
}

/**
 * 
 *This Method checks if item in worn list		
 *@param items
 *		   items worn list.
 *@param temp
 *			combo item to be compared
 *@return boolean : False, if found. True, if not found.
 */
public boolean checkIfItemInWornList(ArrayList<ItemsModel> items,String temp){
			
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeIndex=i;
			  		 //JOptionPane.showMessageDialog (null, "Item Already in the Worn List!!!","Error", 
					//			JOptionPane.ERROR_MESSAGE);
			  		return false;
			  	}
			  			  
		  }
				

	return true;
}

/**
 * 
 *This Method checks if item already in worn then remove the older entry and adds new entry
 *@param items
 *			list of items worn in items ArrayList
 *			
 *				
 */
public boolean checkIfItemInWornRmv(ArrayList<ItemsModel> items){
	
	if(items.size()>0){
		
		String temp=(String) runTimeInvenWindow.comboItemsWorn.getSelectedItem();
		
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeIndex=i;			  		
			  		return false;
			  	}		  					  
		  }
				
	}
	return true;
}



}

