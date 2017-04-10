package com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern;

import java.util.Stack;

/**
 * Created by Chao on 09/04/2017.
 */

public class PlainWeapon extends  WeaponSpecialEnchantment {


    @Override
    public Stack<WeaponEnchantement> getEnchantment() {
        return new Stack<WeaponEnchantement>();
    }

}
