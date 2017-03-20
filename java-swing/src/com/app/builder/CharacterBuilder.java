package com.app.builder;

import java.util.ArrayList;

import com.app.models.CharacterModel;

public abstract class CharacterBuilder {
	
	protected CharacterModel charModel;
	protected int [] scores;

	
	public CharacterModel getCharacterModel(){
		
		return charModel;
	}
	
	public void createNewCharacter(CharacterModel newModel){
		this.charModel=newModel;
		//this.charModel.generateAbilityScores();
		arrangeScroesAscending();
	}	
	
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
