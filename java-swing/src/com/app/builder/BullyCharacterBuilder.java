package com.app.builder;

public class BullyCharacterBuilder extends CharacterBuilder  {

	@Override
	void assignStrength() {
		System.out.println("****"+scores[2]);
		this.charModel.setStrength(scores[2]);
	}

	@Override
	void assignDexterity() {
		this.charModel.setDexterity(scores[0]);		
	}

	@Override
	void assignConstitution() {
		this.charModel.setConstitution(scores[1]);		
	}
	
}
