package com.chaowang.ddgame.MenuModel.ClassesModel;

import com.chaowang.ddgame.MenuModel.CharacterModel.Abilities;

/**
 * Nimble Fighter builder
 * fighter class has no constructor since it depends on Figther builder subclass to set Attributes
 * @author chao wang
 * @version 3.0
 */
public class FighterNimbleBuilder extends FighterBuilder {

    /**
     * build the ability importance for Nimble Fighter
     */
    @Override
    void buildAbilityImportance() {
        fighterProduct.setFighterType(Fighter.FighterType.NIMBLE);

    }
	/**
	 * build Fighter Type for Nimble Fighter
	 */
    @Override
    void buildFighterType() {
        fighterProduct.setAbilityImportance( new int[]{Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                        Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                        Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex()});
    }

}
