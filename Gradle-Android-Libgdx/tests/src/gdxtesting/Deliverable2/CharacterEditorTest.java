package gdxtesting.Deliverable2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import gdxtesting.GdxTestRunner;

import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.MenuController.EquipmentController;
import com.chaowang.ddgame.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.ItemModel.Item;

/**
 * the class is Junit test for Character
 * @author chao wang
 * @version 1.0
 */
 
@RunWith(GdxTestRunner.class)
public class CharacterEditorTest {
    Character character;
    Item item1, item2;
    EquipmentController controller;

    /**
     * prepare character, his items, and controller for load equipment
     */
    @Before public void createCharacterSet(){
        character = new Character();
        item1 = new Item(Item.ItemType.ARMOR, "lowLevel", 1, EnchantedAbility.ARMORCLASS);
        item2 = new Item(Item.ItemType.ARMOR, "highLevel", 2, EnchantedAbility.ARMORCLASS);
        controller = new EquipmentController(character);
    }

    /**
     * test backpack initilization
     */
    @Test
    public void testCharacterInitPack() {
        assertEquals(character.getBackpack().size(), 0);
    }

    /**
     * test character wear equipment will effect ability
     */
    @Test
    public void testCharacterWearItem() {
        int armorClassBefore = character.getArmorClass();
        character.loadEquipment(item1);
        assertEquals(character.getArmorClass()-armorClassBefore, 1);
    }
    
    /**
     * test character wear, unwear equipment will not effect ability
     */
    @Test
    public void testCharacterUnwearItem() {
        int armorClassBefore = character.getArmorClass();
        character.loadEquipment(item1);
        character.removeEquipment(Item.ItemType.ARMOR);
        assertEquals(character.getArmorClass()-armorClassBefore, 0);
    }
    /**
     * test character cannot wear more than 1 type of equipment
     */
    @Test
    public void testCharacterWearMoreItem() {
        character.loadEquipment(item1);
        character.loadEquipment(item2);
        assertEquals(character.getEquipment().size(),1);
    }
    /**
     * test character equip wil reduce the number in backpack
     */
    @Test
    public void testCharacterAddFromPack() {
        character.getBackpack().add(item1);
        character.getBackpack().add(item2);
        controller.loadEquipment(1);
        assertEquals(character.getBackpack().size(),1);
    }
}
