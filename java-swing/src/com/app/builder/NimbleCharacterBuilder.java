package com.app.builder;


public class NimbleCharacterBuilder extends CharacterBuilder  {

	@Override
	void assignStrength() {
		this.charModel.setStrength(scores[0]);
	}

	@Override
	void assignDexterity() {
		this.charModel.setDexterity(scores[2]);		
	}

	@Override
	void assignConstitution() {
		this.charModel.setConstitution(scores[1]);		
	}
	
}
