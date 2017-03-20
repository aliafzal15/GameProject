package com.app.observer;


import java.util.Observable;
import java.util.Observer;

import com.app.controller.RunTimeInventroyController;
import com.app.menus.RunTimAbilityScores;
import com.app.menus.RunTimeIneventory;
import com.app.models.CharacterModel;

public class ItemInventoryView implements Observer{
	
	private RunTimeIneventory runTimeInven;
	private CharacterModel gameChar;

	
	public ItemInventoryView(CharacterModel gamePlyr){
		
		this.gameChar=gamePlyr;
		runTimeInven=new RunTimeIneventory();		
		
	}

	@Override
	public void update(Observable character, Object x) {		
		runTimeInven.initialize();	
		new RunTimeInventroyController(gameChar,runTimeInven);
	}

}
