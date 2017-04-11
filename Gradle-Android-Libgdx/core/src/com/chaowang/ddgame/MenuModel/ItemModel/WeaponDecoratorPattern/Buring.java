package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Item model for WeaponDecotrator Buring
 * @author chao wang
 * @version 3.0
 */
public class Buring extends WeaponDecotrator {

    public Buring(WeaponSpecialEnchantment decoratedWeapon) {
        super(decoratedWeapon);
    }
    /**
     * get Enchantment
     */
    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
    	Stack<WeaponEnchantement> tmp = super.getEnchantment();
    	tmp.push(WeaponEnchantement.BURNING);
		return tmp;
    }
}
