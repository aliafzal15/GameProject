package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.CharacterModel.Abilities;
import com.chaowang.ddgame.ClassesModel.DDgameFighter.FighterType;

public class FighterTANK extends DDgameFighter {
	@Override
	public int[] abilityImportance() {
		return new int[]{Abilities.AbilityType.CONSTITUTION.getIndex(), Abilities.AbilityType.DEXTERITY.getIndex(),
                Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() };
	}

	@Override
	public int index() {
		return 2;
	}

	@Override
	public FighterType classType() {
		return FighterType.TANK;

	}
}
