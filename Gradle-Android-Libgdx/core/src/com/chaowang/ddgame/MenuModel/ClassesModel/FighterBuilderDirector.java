package com.chaowang.ddgame.MenuModel.ClassesModel;

/**
 * Created by Chao on 25/03/2017.
 */

public class FighterBuilderDirector {

    private FighterBuilder builder;

    public void setBuilder(FighterBuilder newFighterBuilder){
        builder = newFighterBuilder;
    }

    public void constructFighter(){
        builder.createNewFighter();
        builder.buildAbilityImportance();
        builder.buildFighterType();
    }

    public com.chaowang.ddgame.MenuModel.ClassesModel.Fighter getFighter(){
        return  builder.getFighter();
    }
}
