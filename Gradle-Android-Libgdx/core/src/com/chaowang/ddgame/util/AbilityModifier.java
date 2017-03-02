package com.chaowang.ddgame.util;
/**
 * class for modifying ability
 * @author chao wang
 * @version 1.0
 */
public class AbilityModifier {
	/**
	 * modifier for hit point
	 * @param constitution
	 * @param level
	 * @return a new changed value for hit point
	 */
    public static int hitPointModifier(int constitution, int level){
            return constitution * 2 + level * 10;
    }
    /**
     * modifier for armor Class
     * @param dexterity
     * @return a new changed value for armor Class
     */
    public static int armorClassModifier(int dexterity){
        return dexterity / 2 ;
    }
    /**
     * modifier for attach Bonus
     * @param strength
     * @param dexterity
     * @param level
     * @return a new changed value for attach Bonus
     */
    public static int attachBonusModifier(int strength, int dexterity, int level){
        return strength / dexterity * 10 + level * 2 ;
    }
    /**
     * modifier for damage Bonus
     * @param strength
     * @return a new changed value for damage Bonus
     */
    public static int damageBonusModifier(int strength){
        return strength / 2;
    }
}
