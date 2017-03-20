package com.app.builder;

import com.app.models.CharacterModel;

public class Explorer {

	private CharacterBuilder charBuilder;
	
	public void setBuilder(CharacterBuilder newCharBuilder){
		this.charBuilder=newCharBuilder;
	}
	
	public void buildTypeCharacter(CharacterModel curCharModel){
		
		charBuilder.createNewCharacter(curCharModel);
		charBuilder.assignStrength();
		charBuilder.assignDexterity();
		charBuilder.assignConstitution();
	}
	
	public CharacterModel getCharacter(){
		return charBuilder.getCharacterModel();
	}
}
