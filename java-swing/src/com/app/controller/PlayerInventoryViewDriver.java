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
public class PlayerInventoryViewDriver {
	
	public CharacterModel charInvenViewDriver;
	public ItemInventoryView viewInventory;
	public RunTimeGameController runTimeCont;
	
	/**
	 * This is the parameterized constructor
	 * 
	 * @param newChar
	 * 			Type CharacterModel
	 *
	 */
	public PlayerInventoryViewDriver(CharacterModel newChar,RunTimeGameController runTimeCtrl){
		
		runTimeCont=runTimeCtrl;
		charInvenViewDriver=newChar;
		viewInventory=new ItemInventoryView(newChar,runTimeCont);	
		charInvenViewDriver.addObserver(viewInventory);
		
	}
	

}
