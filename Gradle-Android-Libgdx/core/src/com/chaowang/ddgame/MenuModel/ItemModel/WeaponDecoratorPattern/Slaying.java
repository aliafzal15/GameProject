package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;


/**
 * Item model for WeaponDecotrator Slaying
 * @author chao wang
 * @version 3.0
 */
public class Slaying extends WeaponDecotrator {

    public Slaying(WeaponSpecialEnchantment decoratedWeapon) {
        super(decoratedWeapon);
    }
    /**
     * get Enchantment
     */
    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
    	Stack<WeaponEnchantement> tmp = super.getEnchantment();
    	tmp.push(WeaponEnchantement.SLAYING);
		return tmp;   
	}
}
