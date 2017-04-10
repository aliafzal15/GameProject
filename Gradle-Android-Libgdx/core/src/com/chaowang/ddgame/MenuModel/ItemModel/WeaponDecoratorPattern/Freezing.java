package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;



/**
 * Created by Chao on 09/04/2017.
 */

public class Freezing extends WeaponDecotrator {

    public Freezing(WeaponSpecialEnchantment decoratedWeapon) {
        super(decoratedWeapon);
    }

    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
    	Stack<WeaponEnchantement> tmp = super.getEnchantment();
    	tmp.push(WeaponEnchantement.FREEZING);
		return tmp;  
	}
}
