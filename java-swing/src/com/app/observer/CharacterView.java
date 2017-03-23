package com.app.observer;

import java.util.Observable;
import java.util.Observer;

import com.app.controller.RunTimeGameController;
import com.app.menus.RunTimAbilityScores;
import com.app.models.CharacterModel;


/**
 * This class is the view class for observer pattern ability score view implementation
 * 
 * @author Ali Afzal
 *
 */
public class CharacterView implements Observer{
	
	private RunTimAbilityScores abilityScoreMenu;
	private RunTimeGameController runTimeGameControl;
	
	
	/**
	 * This class is the Parameterized Constructor for the class
	 */
	public CharacterView(CharacterModel mainPlyr, RunTimeGameController tempRunCntrlr){
		
		runTimeGameControl=tempRunCntrlr;
		abilityScoreMenu=new RunTimAbilityScores(mainPlyr,runTimeGameControl);
		
	}

	
	
	/**
	 * This class is the implemented update function
	 */
	@Override
	public void update(Observable character, Object x) {
		
		if(runTimeGameControl.patternController==false){	
			abilityScoreMenu.initialize();	
			abilityScoreMenu.lblStrVal.setText(Integer.toString(((CharacterModel) character).abilityScores.get(0)));
			abilityScoreMenu.lblDexVal.setText(Integer.toString(((CharacterModel) character).abilityScores.get(1)));
			abilityScoreMenu.lblConsVal.setText(Integer.toString(((CharacterModel) character).abilityScores.get(2)));	
		}
	}

}
