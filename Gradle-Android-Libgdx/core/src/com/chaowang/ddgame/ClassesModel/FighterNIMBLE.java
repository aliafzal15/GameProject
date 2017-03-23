package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.CharacterModel.Abilities;
import com.chaowang.ddgame.ClassesModel.DDgameFighter.FighterType;

public class FighterNIMBLE extends DDgameFighter {

	@Override
	public int[] abilityImportance() {
		return new int[]{Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() };
	}

	@Override
	public int index() {
		return 1;
	}

	@Override
	public FighterType classType() {
		return FighterType.NIMBLE;

	}
	
}
