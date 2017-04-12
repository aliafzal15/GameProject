package com.chaowang.ddgame.MenuModel.ClassesModel;


/**
 * Model for Fighter builder director
 * fighter class has no constructor since it depends on Figther builder subclass to set Attributes
 * @author chao wang
 * @version 3.0
 */
public class FighterBuilderDirector {

    private FighterBuilder builder;
    /**
     * set builder
     * @param newFighterBuilder
     */
    public void setBuilder(FighterBuilder newFighterBuilder){
        builder = newFighterBuilder;
    }
    /**
     * construct Fighter
     */
    public void constructFighter(){
        builder.createNewFighter();
        builder.buildAbilityImportance();
        builder.buildFighterType();
    }
    /**
     * get Fighter 
     * @return
     */
    public com.chaowang.ddgame.MenuModel.ClassesModel.Fighter getFighter(){
        return  builder.getFighter();
    }
}
