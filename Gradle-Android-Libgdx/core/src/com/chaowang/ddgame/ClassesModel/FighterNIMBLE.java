package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.CharacterModel.Abilities;
import com.chaowang.ddgame.ClassesModel.DDgameFighter.FighterType;

/**
 * Nimble class for fighter
 */
public class FighterNIMBLE extends DDgameFighter {

	/**
	 * measure the importance attirubtes
	 * @return
	 */
	@Override
	public int[] abilityImportance() {
		return new int[]{Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() };
	}

	/**
	 * index of this fighter
	 * @return
	 */
	@Override
	public int index() {
		return 1;
	}

	/**
	 * fighter type
	 * @return
	 */
	@Override
	public FighterType classType() {
		return FighterType.NIMBLE;

	}
	
}
