package com.app.builder;

import java.util.ArrayList;

import com.app.models.CharacterModel;


/**
 * This is abstract class is the builder class for builder pattern
 * 
 * @author AliAfzal
 */
public abstract class CharacterBuilder {
	
	protected CharacterModel charModel;
	protected int [] scores;

	
	/**
	 * This Method returns the character
	 * 	
	 * @return Character- of type CharacterModel
	 * 		
	 */
	public CharacterModel getCharacterModel(){
		
		return charModel;
	}
	
	/**
	 * This Method sets the character for the builder pattern and assign the ability scores
	 * 	
	 * @param newModel
	 * 			Type CharacterModel
	 */
	public void createNewCharacter(CharacterModel newModel){
		this.charModel=newModel;
		//this.charModel.generateAbilityScores();
		arrangeScroesAscending();
	}	
	
	
	/**
	 * This Method arranges the ability scores in the ascending order
	 * 	
	 */
	public void arrangeScroesAscending(){
		
		
		scores=new int[3];
		scores[0]=charModel.getStrength();
		scores[1]=charModel.getDexterity();
		scores[2]=charModel.getConstitution();
		
		System.out.println("Strength Score Before Change:"+scores[0]);
		System.out.println("Dexterity Score Before Change:"+scores[1]);
		System.out.println("Constitution Score Before Change:"+scores[2]);
		
		this.scores= doInsertionSort(scores);
		
		System.out.println(scores[0]);
		System.out.println(scores[1]);
		System.out.println(scores[2]);
		
	}
	
	/**
	 * This Method sorts and array in ascending order 
	 * 	@param input Array of type int
	 */
	public static int[] doInsertionSort(int[] input){
        
        int temp;
        for (int i = 1; i < input.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(input[j] < input[j-1]){
                    temp = input[j];
                    input[j] = input[j-1];
                    input[j-1] = temp;
                }
            }
        }
        return input;
    }
	
	
	abstract void assignStrength();
	abstract void assignDexterity();
	abstract void assignConstitution();
	
	
}
