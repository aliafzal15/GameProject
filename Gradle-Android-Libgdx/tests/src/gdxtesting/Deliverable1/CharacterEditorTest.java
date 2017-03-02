package gdxtesting.Deliverable1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.Gdx;
import gdxtesting.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.Gdx;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.ItemModel.Item;

import gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class CharacterEditorTest {
    Character character;
    Item item1, item2;

    @Before public void createCharacter(){
        character = new Character();
        item1 = new Item(Item.ItemType.ARMOR, "lowLevel", 1, EnchantedAbility.ARMORCLASS);
        item2 = new Item(Item.ItemType.ARMOR, "highLevel", 2, EnchantedAbility.ARMORCLASS);


    }

    @Test
    public void characterConstructorExist() {
        assertEquals(character.getBackpack().size(), 0);
    }

    @Test
    public void characterWearItem() {
        int armorClassBefore = character.getArmorClass();
        character.loadEquipment(item1);
        assertEquals(character.getArmorClass()-armorClassBefore, 1);
    }

    @Test
    public void characterWearMoreItem() {
        character.loadEquipment(item1);
        character.loadEquipment(item2);
        assertEquals(character.getEquipment().size(),1);
    }

}
