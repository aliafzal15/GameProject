package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Item model for WeaponDecotrator Freezing
 * @author chao wang
 * @version 3.0
 */
public class Freezing extends WeaponDecotrator {

    public Freezing(WeaponSpecialEnchantment decoratedWeapon) {
        super(decoratedWeapon);
    }
    /**
     * get Enchantment
     */
    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
    	Stack<WeaponEnchantement> tmp = super.getEnchantment();
    	tmp.push(WeaponEnchantement.FREEZING);
		return tmp;  
	}
}
