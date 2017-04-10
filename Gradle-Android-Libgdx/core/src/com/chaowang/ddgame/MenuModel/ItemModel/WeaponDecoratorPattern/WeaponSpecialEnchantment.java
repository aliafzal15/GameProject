package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Created by Chao on 09/04/2017.
 */

public abstract class WeaponSpecialEnchantment {

    //Stack<WeaponEnchantement> enchantmentStack;

    public abstract Stack<WeaponEnchantement> getEnchantment();

    public enum WeaponEnchantement {

        BURNING(0), FREEZING(1), FRIGHTENING(2), PACIFYING(3), SLAYING(4);
        int index;
        /**
         * constructor
         * @param value the specific EnchantedAbility
         */
        private WeaponEnchantement(int value) {
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
        public static WeaponEnchantement getEnchantment (int index) {
            switch (index) {
                case 0:
                    return BURNING;
                case 1:
                    return FREEZING;
                case 2:
                    return FRIGHTENING;
                case 3:
                    return PACIFYING;
                case 4:
                    return SLAYING;
            }
            return BURNING;
        }
    }

}
