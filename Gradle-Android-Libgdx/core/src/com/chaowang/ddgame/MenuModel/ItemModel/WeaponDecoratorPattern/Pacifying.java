package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Item model for WeaponDecotrator Pacifying
 * @author chao wang
 * @version 3.0
 */
public class Pacifying extends WeaponDecotrator{

    public Pacifying(WeaponSpecialEnchantment decoratedWeapon) {
        super(decoratedWeapon);
    }

    /**
     * get Enchantment
     */
    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
    	Stack<WeaponEnchantement> tmp = super.getEnchantment();
    	tmp.push(WeaponEnchantement.PACIFYING);
		return tmp; 
    }
}
