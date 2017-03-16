package com.chaowang.ddgame.ItemModel;

/**
 * enchanted ability for items
 * @author chao wang
 * @version 1.0
 */

public enum EnchantedAbility {

    STRENGTH(0), DEXTERITY(1), CONSTITUTION(2), WISDOM(3), INTELLIGENCE(4), CHARISMA(5), ARMORCLASS(6), ATTACKBONUS(7), DAMAGEBONUS(8);
    int index;
    /**
     * constructor
     * @param value the specific EnchantedAbility
     */
    private EnchantedAbility(int value) {
        this.index = value;
    }
    /**
     * get the index of the specific EnchantedAbility
     * @return the index of the specific EnchantedAbility
     */
    public int getIndex() {
        return index;
    }
    /**
     * get the specific EnchantedAbility
     * @param index  the index of the specific EnchantedAbility
     * @return the specific EnchantedAbility
     */
    public EnchantedAbility getAbility (int index) {
        switch (index) {
            case 0:
                return STRENGTH;
            case 1:
                return DEXTERITY;
            case 2:
                return CONSTITUTION;
            case 3:
                return WISDOM;
            case 4:
                return INTELLIGENCE;
            case 5:
                return CHARISMA;
            case 6:
                return ARMORCLASS;
            case 7:
                return ATTACKBONUS;
            case 8:
                return DAMAGEBONUS;
        }
        return WISDOM;
    }
}
