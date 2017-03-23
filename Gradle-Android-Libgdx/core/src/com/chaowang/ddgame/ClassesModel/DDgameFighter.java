package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.ClassesModel.Class.ClassType;

public abstract class DDgameFighter implements Fighters{

	   public enum FighterType{BULLY,NIMBLE, TANK};
	   
	   public Class dndClass(){
		   return new DDgameClass();
	   }
	   
	   public abstract int[] abilityImportance();
	   
	   public abstract int index();
		   
	   public abstract FighterType classType();
		
}
