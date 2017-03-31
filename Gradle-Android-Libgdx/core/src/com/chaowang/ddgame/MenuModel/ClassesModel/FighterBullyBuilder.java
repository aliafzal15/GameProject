package com.chaowang.ddgame.MenuModel.ClassesModel;

import com.chaowang.ddgame.MenuModel.CharacterModel.Abilities;

/**
 * Created by Chao on 25/03/2017.
 */

public class FighterBullyBuilder extends FighterBuilder{

    @Override
    void buildFighterType() {
        fighterProduct.setFighterType(Fighter.FighterType.BULLY);
    }

    @Override
    void buildAbilityImportance() {
        fighterProduct.setAbilityImportance( new int[]{Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() });
    }
}
