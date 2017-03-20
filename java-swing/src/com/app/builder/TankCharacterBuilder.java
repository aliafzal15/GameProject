package com.app.builder;

public class TankCharacterBuilder extends CharacterBuilder  {

	@Override
	void assignStrength() {
		this.charModel.setStrength(scores[0]);
	}

	@Override
	void assignDexterity() {
		this.charModel.setDexterity(scores[1]);		
	}

	@Override
	void assignConstitution() {
		this.charModel.setConstitution(scores[2]);		
	}
	
}
