package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Item model for WeaponSpecialEnchantment PlainWeapon
 * @author chao wang
 * @version 3.0
 */
public class PlainWeapon extends  WeaponSpecialEnchantment {

    /**
     * get Enchantment
     */
    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
        return new Stack<WeaponEnchantement>();
    }

}
