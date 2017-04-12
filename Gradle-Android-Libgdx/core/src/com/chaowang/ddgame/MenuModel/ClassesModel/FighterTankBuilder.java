package com.chaowang.ddgame.MenuModel.ClassesModel;

import com.chaowang.ddgame.MenuModel.CharacterModel.Abilities;

/**
 * Tank Fighter builder
 * fighter class has no constructor since it depends on Figther builder subclass to set Attributes
 * @author chao wang
 * @version 3.0
 */
public class FighterTankBuilder extends FighterBuilder{
	/**
	 * build Fighter Type for Tank Fighter
	 */
    @Override
    void buildFighterType() {
        fighterProduct.setFighterType(Fighter.FighterType.TANK);
    }
    /**
     * build the ability importance for Tank Fighter
     */
    @Override
    void buildAbilityImportance() {
        fighterProduct.setAbilityImportance(new int[]{Abilities.AbilityType.CONSTITUTION.getIndex(), Abilities.AbilityType.DEXTERITY.getIndex(),
                Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() });
    }
}
