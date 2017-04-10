package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Created by Chao on 09/04/2017.
 */

public class WeaponDecotrator extends WeaponSpecialEnchantment{

    WeaponSpecialEnchantment decoratedWeapon;

    public WeaponDecotrator(WeaponSpecialEnchantment decoratedWeapon) {
        this.decoratedWeapon = decoratedWeapon;
    }

    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
        return decoratedWeapon.getEnchantment();
    }


}
