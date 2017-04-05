package com.chaowang.ddgame.MenuModel.ClassesModel;

/**
 * Created by Chao on 25/03/2017.
 */

public abstract class FighterBuilder {

    protected Fighter fighterProduct;

    public Fighter getFighter(){
        return fighterProduct;
    }

    public void createNewFighter(){
        fighterProduct = new Fighter();
    }

    abstract void buildFighterType();
    abstract void buildAbilityImportance ();

}