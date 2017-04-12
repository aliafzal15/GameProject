package gdxtesting.Deliverable3;

/**
 * Created by AliAfzal on 4/12/2017.
 */

import com.chaowang.ddgame.MenuModel.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.WeaponSpecialEnchantment;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import gdxtesting.GdxTestRunner;
import java.util.Stack;

@RunWith(GdxTestRunner.class)
public class WeaponModelTest {

    Item item;
    WeaponModel testWeaponModel1;
    WeaponModel testWeaponModel2;

    /**
     * Initialize item with certain type, value
     */
    @Before public void createItemSet(){

        testWeaponModel1=new WeaponModel();
        testWeaponModel1.setWeaponType(WeaponModel.WeaponType.MELEE);

        testWeaponModel2=new WeaponModel();
        testWeaponModel2.setWeaponType(WeaponModel.WeaponType.RANGE);

        item = new Item(Item.ItemType.WEAPON, "unitTest", 1, EnchantedAbility.ATTACKBONUS,testWeaponModel1);


    }

    /**
     * Verifies if the plain item of Weapon Type is set to Melee
     */
    @Test
    public void testItemMelee() {
        assertEquals(item.getWeaponModel().getWeaponType(), WeaponModel.WeaponType.MELEE);
    }

    /**
     * Verifies if the plain item of Weapon Type is set to Ranged
     */
    @Test
    public void testItemRanged() {
        item = new Item(Item.ItemType.WEAPON, "unitTest", 1, EnchantedAbility.ATTACKBONUS,testWeaponModel2);
        assertEquals(item.getWeaponModel().getWeaponType(), WeaponModel.WeaponType.RANGE);
    }


    /**
     * Verifies if the plain item of Weapon Type Melee Attack Range is set correctly
     */
    @Test
    public void testItemMeleeAttackRange() {
        assertEquals(WeaponModel.WeaponType.getAttackRange(item.getWeaponType()), 1);
    }

    /**
     * Verifies if the plain item of Weapon Type Ranged Attack Range is set correctly
     */
    @Test
    public void testItemRangedAttackRange() {
        item = new Item(Item.ItemType.WEAPON, "unitTest", 1, EnchantedAbility.ATTACKBONUS,testWeaponModel2);
        assertEquals(WeaponModel.WeaponType.getAttackRange(item.getWeaponType()), 2);
    }



    /**
     * Verifies if the Burning enchantment is added
     */
    @Test
    public void testBurningEnchantment() {
        boolean [] arr=new boolean[]{true, false, false, false ,false};
        item.addWeaponEnchantment(arr);
        Stack<WeaponSpecialEnchantment.WeaponEnchantement> stackEnchatments= item.getWeaponEnchantment();
        int index=stackEnchatments.get(0).getIndex();
        assertEquals(index, 0);
    }


    /**
     * Verifies if the Freezing enchantment is added to item
     */
    @Test
    public void testFreezingEnchantment() {
        boolean [] arr=new boolean[]{false,true,false,false,false};
        item.addWeaponEnchantment(arr);
        Stack<WeaponSpecialEnchantment.WeaponEnchantement> stackEnchatments= item.getWeaponEnchantment();
        int index=stackEnchatments.get(0).getIndex();
        assertEquals(index, 1);
    }


    /**
     * Verifies if the Frightening enchantment is added to item
     */
    @Test
    public void testFrighteningEnchantment() {
        boolean [] arr=new boolean[]{false,false,true,false,false};
        item.addWeaponEnchantment(arr);
        Stack<WeaponSpecialEnchantment.WeaponEnchantement> stackEnchatments= item.getWeaponEnchantment();
        int index=stackEnchatments.get(0).getIndex();
        assertEquals(index, 2);
    }


    /**
     * Verifies if the Pacifying enchantment is added to item
     */
    @Test
    public void testPacifyingEnchantment() {
        boolean [] arr=new boolean[]{false,false,false,true,false};
        item.addWeaponEnchantment(arr);
        Stack<WeaponSpecialEnchantment.WeaponEnchantement> stackEnchatments= item.getWeaponEnchantment();
        int index=stackEnchatments.get(0).getIndex();
        assertEquals(index, 3);
    }

    /**
     * Verifies if the Slaying enchantment is added to item
     */
    @Test
    public void testSlayingEnchantment() {
        boolean [] arr=new boolean[]{false,false,false,false,true};
        item.addWeaponEnchantment(arr);
        Stack<WeaponSpecialEnchantment.WeaponEnchantement> stackEnchatments= item.getWeaponEnchantment();
        int index=stackEnchatments.get(0).getIndex();
        assertEquals(index, 4);
    }

    /**
     * Verifies if All enchantments are stacked
     */
    @Test
    public void testAllEnchantment() {
        boolean [] arr=new boolean[]{true,true,true,true,true};
        item.addWeaponEnchantment(arr);
        Stack<WeaponSpecialEnchantment.WeaponEnchantement> stackEnchatments= item.getWeaponEnchantment();
        int lengthofenchantmentstack=stackEnchatments.size();
        assertEquals(lengthofenchantmentstack, 5);
    }

}
