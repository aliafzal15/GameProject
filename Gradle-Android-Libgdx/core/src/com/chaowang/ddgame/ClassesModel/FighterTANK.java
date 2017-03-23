package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.CharacterModel.Abilities;
import com.chaowang.ddgame.ClassesModel.DDgameFighter.FighterType;

/**
 * sub class for this fighter
 */
public class FighterTANK extends DDgameFighter {
	/**
	 * this fighter's importance of attributes
	 * @return
	 */
	@Override
	public int[] abilityImportance() {
		return new int[]{Abilities.AbilityType.CONSTITUTION.getIndex(), Abilities.AbilityType.DEXTERITY.getIndex(),
                Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() };
	}

	/**
	 * this fighter is index in array
	 * @return
	 */
	@Override
	public int index() {
		return 2;
	}

	/**
	 * this fighter is type
	 * @return
	 */
	@Override
	public FighterType classType() {
		return FighterType.TANK;

	}
}
