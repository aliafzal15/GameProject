package com.app.builder;

/**
 * This class is for Nimble Character Builder in the Game
 * 
 * @author AliAfzal
 */
public class NimbleCharacterBuilder extends CharacterBuilder  {

	/**
	 * This Functions sets the strength of the Bully Character
	 *
	 */
	@Override
	void assignStrength() {
		this.charModel.setStrength(scores[0]);
	}

	/**
	 * This Functions sets the dexterity of the Bully Character
	 *
	 */
	@Override
	void assignDexterity() {
		this.charModel.setDexterity(scores[2]);		
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
