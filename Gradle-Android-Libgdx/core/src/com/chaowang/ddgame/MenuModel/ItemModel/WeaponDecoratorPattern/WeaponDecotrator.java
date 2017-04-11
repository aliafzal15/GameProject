package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Item model for Weapon Decotrator
 * @author chao wang
 * @version 3.0
 */
public class WeaponDecotrator extends WeaponSpecialEnchantment{

    WeaponSpecialEnchantment decoratedWeapon;
    /**
     * constructor
     * @param decoratedWeapon
     */
    public WeaponDecotrator(WeaponSpecialEnchantment decoratedWeapon) {
        this.decoratedWeapon = decoratedWeapon;
    }
    /**
     * get Enchantment
     */
    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
        return decoratedWeapon.getEnchantment();
    }


}
