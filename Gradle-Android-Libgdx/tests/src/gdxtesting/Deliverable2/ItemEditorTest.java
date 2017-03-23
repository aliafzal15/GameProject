
package gdxtesting.Deliverable2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import gdxtesting.GdxTestRunner;

import com.chaowang.ddgame.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.ItemModel.Item;

/**
 * the class is Junit test for Item
 * @author chao wang
 * @version 1.0
 */
 
@RunWith(GdxTestRunner.class)
public class ItemEditorTest {
	Item item;

	/**
	 * Initialize item with certain type, value
	 */
	@Before public void createItemSet(){
		 item = new Item(Item.ItemType.HELMET, "unitTest", 1, EnchantedAbility.ARMORCLASS);
	}

	/**
	 * Test if item can switch to its next item
	 */
	@Test
	public void testItemNextItem() {
		item.nextItem();
		assertEquals(item.getItemType(), Item.ItemType.ARMOR);
	}
	/**
	 * Test if item ability can switch to its previous ability
	 */
	@Test
	public void testItemPrevAbility() {
		item.previousAbility();
		assertEquals(item.getEnchantedAbility(), EnchantedAbility.INTELLIGENCE);
	}
}
