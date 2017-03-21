package com.app.controller;

import com.app.menus.RunTimeIneventory;
import com.app.models.CharacterModel;
import com.app.observer.CharacterView;
import com.app.observer.ItemInventoryView;


/**
 * This class is the driver class for the observer pattern
 * 
 * @author Ali Afzal
 *
 */
public class AbilityViewDriver {
	
	public CharacterModel charViewDriver;
	public CharacterView  viewCharacter;
	public ItemInventoryView viewInventory;
	
	
	/**
	 * This is the parameterized constructor
	 * 
	 * @param newChar
	 * 			Type CharacterModel
	 *
	 */
	public AbilityViewDriver(CharacterModel newChar){
		
		charViewDriver=newChar;
		viewCharacter=new CharacterView(newChar);
		//viewInventory=new ItemInventoryView(newChar);	
		charViewDriver.addObserver(viewCharacter);

	}
	

}
