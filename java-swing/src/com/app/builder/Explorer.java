package com.app.builder;

import com.app.models.CharacterModel;

/**
 * This class is the Director class for the builder pattern in the Game
 * 
 * @author AliAfzal
 */
public class Explorer {

	private CharacterBuilder charBuilder;
	
	
	/**
	 * This Functions sets the character builder
	 * @param newCharBuilder 
	 * 				Type Character Builder
	 *
	 */
	public void setBuilder(CharacterBuilder newCharBuilder){
		this.charBuilder=newCharBuilder;
	}
	
	/**
	 * This Functions builds the character of specified character
	 * @param curCharModel
	 * 				Type CharacterModel
	 *
	 */
	public void buildTypeCharacter(CharacterModel curCharModel){
		
		charBuilder.createNewCharacter(curCharModel);
		charBuilder.assignStrength();
		charBuilder.assignDexterity();
		charBuilder.assignConstitution();
	}
	
	/**
	 * This Functions returns the character
	 * @return Character
	 * <p>- of Type CharacterModel</p>
	 *
	 */
	public CharacterModel getCharacter(){
		return charBuilder.getCharacterModel();
	}
}
