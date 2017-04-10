package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;


/**
 * Created by Chao on 09/04/2017.
 */

public class Slaying extends WeaponDecotrator {

    public Slaying(WeaponSpecialEnchantment decoratedWeapon) {
        super(decoratedWeapon);
    }

    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
    	Stack<WeaponEnchantement> tmp = super.getEnchantment();
    	tmp.push(WeaponEnchantement.SLAYING);
		return tmp;   
	}
}
