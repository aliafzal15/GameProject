package com.chaowang.ddgame.MenuModel.ItemModel;

import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.Buring;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.Freezing;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.Frightening;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.Pacifying;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.PlainWeapon;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.Slaying;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.WeaponSpecialEnchantment;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * Model for game weapon
 * @author chao wang
 * @version 3.0
 */
public class WeaponModel {

    private WeaponType weaponType;
    private WeaponSpecialEnchantment weaponEnchantment;
    /**
     * constructor
     */
    public WeaponModel(){
        this(WeaponType.MELEE);
    }
    /**
     * constructor
     */
    public WeaponModel(WeaponType weaponType) {
        this.weaponType = weaponType;
        weaponEnchantment = new PlainWeapon();
    }
    /**
     * get the type of weapon
     * @return
     */
    public WeaponType getWeaponType() {
        return weaponType;
    }
    /**
     * set the type of weapon
     * @param weaponType
     */
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    /**
     * get Weapon Enchantment value
     * @return
     */
    public Stack<WeaponSpecialEnchantment.WeaponEnchantement> getWeaponEnchantment() {
        return weaponEnchantment.getEnchantment();
    }
    /**
     * get Weapon EnhantmentEqu status
     * @return
     */
    public boolean[] getWeaponEnhantmentEqu(){
        boolean [] equivalentBoolArr = new boolean[WeaponSpecialEnchantment.WeaponEnchantement.values().length];
        Iterator<WeaponSpecialEnchantment.WeaponEnchantement> iter = weaponEnchantment.getEnchantment().iterator();

        while (iter.hasNext()){
            equivalentBoolArr[iter.next().getIndex()] = true;
        }
        return equivalentBoolArr;
    }
    /**
     * add Weapon Enchantment
     * @param arr
     */
    public void addWeaponEnchantment(boolean[] arr){
        weaponEnchantment = new PlainWeapon();
        if(arr[0] ){
            weaponEnchantment = new Buring(weaponEnchantment);
        }
        if(arr[1] ){
            weaponEnchantment = new Freezing(weaponEnchantment);
        }
        if(arr[2] ){
            weaponEnchantment = new Frightening(weaponEnchantment);
        }
        if(arr[3] ){
            weaponEnchantment = new Pacifying(weaponEnchantment);
        }
        if(arr[4] ){
            weaponEnchantment = new Slaying(weaponEnchantment);
        }
    }
    /**
     * convert to type String
     */
    @Override
    public String toString() {
        return this.weaponType.toString()+" " + Arrays.toString(this.getWeaponEnchantment().toArray());
    }

    //-------------------------------below is Weapon type enum--------------------------------------

    public enum WeaponType {

        MELEE(0, PublicParameter.MELEE_WEAPON_ATTACK_CELL), RANGE(1, PublicParameter.RANGE_WEAPON_ATTACK_CELL);
        int index;
        int attackRange;
        /**
         * constructor
         * @param value the specific EnchantedAbility
         */
        private WeaponType(int value, int range) {
            this.index = value;
            this.attackRange = range;
        }
        /**
         * get the index of the specific EnchantedAbility
         * @return the index of the specific EnchantedAbility
         */
        public int getIndex() {
            return index;
        }

        /**
         * get the index of the specific EnchantedAbility
         * @return the index of the specific EnchantedAbility
         */
        public static int getAttackRange(WeaponType type){
            return type.attackRange;
        }

        /**
         * get the specific EnchantedAbility
         * @param index  the index of the specific EnchantedAbility
         * @return the specific EnchantedAbility
         */
        public static WeaponType getWeaponType (int index) {
            switch (index) {
                case 0:
                    return MELEE;
                case 1:
                    return RANGE;
            }
            return MELEE;
        }

    }

}
