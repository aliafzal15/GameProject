package com.chaowang.ddgame.MenuModel.ClassesModel;

import com.chaowang.ddgame.MenuModel.CharacterModel.Abilities;

/**
 * Bully Fighter builder
 * fighter class has no constructor since it depends on Figther builder subclass to set Attributes
 * @author chao wang
 * @version 3.0
 */
public class FighterBullyBuilder extends FighterBuilder{
	/**
	 * build Fighter Type for Bully Fighter
	 */
    @Override
    void buildFighterType() {
        fighterProduct.setFighterType(Fighter.FighterType.BULLY);
    }
    /**
     * build the ability importance  for Bully Fighter
     */
    @Override
    void buildAbilityImportance() {
        fighterProduct.setAbilityImportance( new int[]{Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() });
    }
}
