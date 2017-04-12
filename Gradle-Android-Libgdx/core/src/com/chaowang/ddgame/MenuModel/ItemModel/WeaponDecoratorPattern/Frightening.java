package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;


/**
 * Item model for Frightening
 * @author chao wang
 * @version 3.0
 */
public class Frightening extends WeaponDecotrator {

    public Frightening(WeaponSpecialEnchantment decoratedWeapon) {
        super(decoratedWeapon);
    }
    /**
     * get Enchantment
     */
    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
    	Stack<WeaponEnchantement> tmp = super.getEnchantment();
    	tmp.push(WeaponEnchantement.FRIGHTENING);
		return tmp; 
    }
}
