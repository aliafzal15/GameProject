package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.CharacterModel.Abilities;

public class FighterBULLY extends DDgameFighter{

	@Override
	public int[] abilityImportance() {
		return new int[]{Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() };
	}

	@Override
	public int index() {
		return 0;
	}

	@Override
	public FighterType classType() {
		return FighterType.BULLY;
	}

}
