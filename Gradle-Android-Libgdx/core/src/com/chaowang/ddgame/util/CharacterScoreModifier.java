package com.chaowang.ddgame.util;

import com.chaowang.ddgame.MenuModel.CharacterModel.Abilities;
import com.chaowang.ddgame.MenuView.MainMenuScreen;

/**
 * class for modifying ability
 * @author chao wang
 * @version 1.0
 */
public class CharacterScoreModifier {

    private static int abilityModifier(int ability){
        MainMenuScreen.logArea.appendText("calling ability modifier\n");
        return ability / 2 - 5;
    }

	/**
	 * modifier for hit point
	 * @param constitution
	 * @param level
	 * @return a new changed value for hit point
	 */
    public static int hitPointCalculator(int constitution, int level){
        MainMenuScreen.logArea.appendText("calculate hitpoint\n");
        return  Dice.roll(level, 10) + Math.max(0, level * abilityModifier(constitution));
    }
    /**
     * modifier for armor Class
     * @param dexterity
     * @return a new changed value for armor Class
     */
    public static int armorClassCalculator(int dexterity){
        MainMenuScreen.logArea.appendText("calculate armor class\n");
        return  10 + abilityModifier(dexterity);
    }
    /**
     * modifier for attach Bonus
     * @param strength
     * @param dexterity
     * @param level
     * @return a new changed value for attach Bonus
     */
    public static int attachBonusCalculator(int strength, int dexterity, int level){
        MainMenuScreen.logArea.appendText("calculate attack bonus\n");
        return baseAttackBonus(level)+ abilityModifier(dexterity);
    }
    /**
     * modifier for damage Bonus
     * @param strength
     * @return a new changed value for damage Bonus
     */
    public static int damageBonusCalculator(int strength){
        MainMenuScreen.logArea.appendText("calculate damage bonus\n");
        return Math.max(0, abilityModifier(strength));
    }

    private static int baseAttackBonus(int level){
        return level + Math.max(0, level-5) + Math.max(0, level-10) + Math.max(0, level-15);
    }

}
