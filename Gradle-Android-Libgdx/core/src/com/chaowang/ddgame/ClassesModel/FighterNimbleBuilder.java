package com.chaowang.ddgame.ClassesModel;

import com.chaowang.ddgame.CharacterModel.Abilities;

/**
 * Created by Chao on 25/03/2017.
 */

public class FighterNimbleBuilder extends FighterBuilder {


    @Override
    void buildAbilityImportance() {
        fighterProduct.setFighterType(Fighter.FighterType.NIMBLE);

    }

    @Override
    void buildFighterType() {
        fighterProduct.setAbilityImportance( new int[]{Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                        Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                        Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex()});
    }

}
