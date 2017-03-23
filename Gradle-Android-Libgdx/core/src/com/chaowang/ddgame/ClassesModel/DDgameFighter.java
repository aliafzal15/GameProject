package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.ClassesModel.Class.ClassType;

/**
 * abstract class for my different ifghter type
 */
public abstract class DDgameFighter implements Fighters{

	   public enum FighterType{BULLY,NIMBLE, TANK};

	/**
	 * return the fighter enum
	 * @return
	 */
	public Class dndClass(){
		   return new DDgameClass();
	   }

	/**
	 * return bility of importance
	 * @return
	 */
	public abstract int[] abilityImportance();

	/**
	 * return index
	 * @return
	 */
	public abstract int index();

	/**
	 * different fighter type from enum
	 * @return
	 */
	public abstract FighterType classType();
		
}
