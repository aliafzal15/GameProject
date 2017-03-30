package com.app.observer;


import java.util.Observable;
import java.util.Observer;

import com.app.controller.RunTimeGameController;
import com.app.controller.RunTimeInventroyController;
import com.app.menus.RunTimAbilityScores;
import com.app.menus.RunTimeIneventory;
import com.app.models.CharacterModel;


/**
 * This class is the view class for observer pattern inventory view implementation
 * 
 * @author Ali Afzal
 *
 */
public class ItemInventoryView implements Observer{
	
	private RunTimeIneventory runTimeInven;
	private CharacterModel gameChar;
	private RunTimeGameController runTimeGameControl;
	
	
	/**
	 * This class is the Parameterized Constructor for the class
	 */
	public ItemInventoryView(CharacterModel gamePlyr,RunTimeGameController tempRunController){
		
		this.gameChar=gamePlyr;
		runTimeGameControl=tempRunController;
		runTimeInven=new RunTimeIneventory();		
		
	}

	
	/**
	 * This class is the implemented update function
	 */
	@Override
	public void update(Observable character, Object x) {		
		
		if(runTimeGameControl.patternController==true){
			runTimeInven.initialize();	
			new RunTimeInventroyController(gameChar,runTimeInven);
		}
	}

}
