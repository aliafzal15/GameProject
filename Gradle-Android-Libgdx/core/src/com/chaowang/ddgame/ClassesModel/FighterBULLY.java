package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.CharacterModel.Abilities;

/**
 * subclass for fighter
 */
public class FighterBULLY extends DDgameFighter{

	/**
	 * return the importance attributes for this type
	 * @return
	 */
	@Override
	public int[] abilityImportance() {
		return new int[]{Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() };
	}

	/**
	 * index of this fighter
	 * @return
	 */
	@Override
	public int index() {
		return 0;
	}

	/**
	 * return enum of this fighter
	 * @return
	 */
	@Override
	public FighterType classType() {
		return FighterType.BULLY;
	}

}
