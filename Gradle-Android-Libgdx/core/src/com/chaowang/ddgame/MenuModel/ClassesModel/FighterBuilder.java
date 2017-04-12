package com.chaowang.ddgame.MenuModel.ClassesModel;

/**
 * Fighter builder
 * fighter class has no constructor since it depends on Figther builder subclass to set Attributes
 * @author chao wang
 * @version 3.0
 */
public abstract class FighterBuilder {

    protected Fighter fighterProduct;
    /**
     * get Fighter
     * @return
     */
    public Fighter getFighter(){
        return fighterProduct;
    }
    /**
     * create Fighter
     */
    public void createNewFighter(){
        fighterProduct = new Fighter();
    }
    /**
     * abstract class for buildFighterType class
     */
    abstract void buildFighterType();
    /**
     * abstract class for buildAbilityImportance class
     */
    abstract void buildAbilityImportance ();

}
