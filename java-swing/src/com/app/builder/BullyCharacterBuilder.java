package com.app.builder;

/**
 * This class is for Bully Character Builder in the Game
 * 
 * @author AliAfzal
 */

public class BullyCharacterBuilder extends CharacterBuilder  {

	
	/**
	 * This Functions sets the strength of the Bully Character
	 *
	 */
	@Override
	void assignStrength() {
		System.out.println("****"+scores[2]);
		this.charModel.setStrength(scores[2]);
	}

	/**
	 * This Functions sets the dexterity of the Bully Character
	 *
	 */
	@Override
	void assignDexterity() {
		this.charModel.setDexterity(scores[0]);		
	}

	/**
	 * This Functions sets the constitution of the Bully Character
	 *
	 */
	@Override
	void assignConstitution() {
		this.charModel.setConstitution(scores[1]);		
	}
	
}
