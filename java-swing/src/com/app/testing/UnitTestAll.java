package com.app.testing;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.controller.CharacterEditorItemsController;
import com.app.mapValidation.MapFinalValidation;
import com.app.menus.CharacterEditorMainMenu;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.models.MapModel;
import com.app.utilities.FileStorage;



/**
 * This class is for Unit Testing of Important Units in the Game
 * 
 * @author AliAfzal
 *
 */
public class UnitTestAll {
	

	/**
	 * This is type of the fighter of data type String
	 */
	private String fighterType;
	
	/**
	 * This is type of the Zombie of data type String
	 */
	private String zombieType;
	
	/**
	 * This is object of the fighter of data type CharacterModel
	 */
	private CharacterModel objFighter;
	
	
	/**
	 * This is object of the Zombie of data type CharacterModel
	 */
	private CharacterModel objZombie;
	
	/**
	 * This is a character object of data type CharacterModel
	 */
	private CharacterModel objCharacter;
	
	/**
	 * This is temporary item object of data type ItemsModel
	 */
	private ItemsModel tempItem;
	
	/**
	 * This is Items ArrayList of data Type ItemsModel
	 */
	private ArrayList<ItemsModel> items;
	

	/**
	 * This is Characters ArrayList of data Type CharacterModel
	 */
	private ArrayList<CharacterModel> characters;
	

	/**
	 * This is Main Menu object of Character Editor to disable GUI during test execution
	 */
	private CharacterEditorMainMenu mainMenu;
	
	/**
	 * This is MapModel Object
	 */
	private MapModel map;
	

/**
 * This method initializes all the objects that need to be used in the test methods
 * 
 *
 */	
	@Before
	public void initializeTest() {
		System.out.println("@BeforeClass - oneTimeSetUp.");
		fighterType="Fighter";
		zombieType="Zombie";
		objCharacter=new CharacterModel("Fighter");
		characters=new ArrayList();
		characters.add(objCharacter);
		tempItem=new ItemsModel();
		items=new ArrayList();
		mainMenu=new CharacterEditorMainMenu();
		mainMenu.setVisible(false);
		map=new MapModel();
		
		
	}
	

	
	
	/**
	 * Tests the CharacterModel parameterized constructor:- CharacterModel(String charType)
	 * This Test is to verify if the character with the given character type is created
	 * 
	 */
	@Test
	public void testParameterizedConstructor() {
		
		
		objFighter=new CharacterModel(fighterType);
		objZombie=new CharacterModel(zombieType);
		
		assertEquals("Fighter",objFighter.getCharType());
		assertEquals("Zombie",objZombie.getCharType());
		
	}
	
	/**
	 * Tests the CharacterModel setWornItem and CharacterItemsEditorController checkIfInItemWornList:- 
	 * This Test is to verify that character can not wear more than one item of same type
	 * 
	 */	
	@Test
	public void testsetWornItem_checkIfItemInWornList() {
		
		ArrayList<ItemsModel> items=new ArrayList();
		
		CharacterEditorItemsController editrItemCntrl=new CharacterEditorItemsController();
		
		
		ItemsModel tempItemAdd=new ItemsModel();
		tempItem.itemType="Helmet";
		tempItem.itemBonus="2";
		
		tempItemAdd.itemType="Armor";
		tempItemAdd.itemBonus="3";
		
		String comboVal=tempItemAdd.itemType+":"+tempItemAdd.itemBonus;
		
		items.add(tempItem);
		objCharacter.setWornItem(tempItem);

		
		if(editrItemCntrl.checkIfItemInWornList(items,comboVal)==true){
			items.add(tempItemAdd);
			objCharacter.setWornItem(tempItemAdd);
			assertEquals(2,objCharacter.getWornItems().size());
			
		};
		
			
		
		
		if(editrItemCntrl.checkIfItemInWornList(items,comboVal)==true){
			items.add(tempItemAdd);
			objCharacter.setWornItem(tempItemAdd);
			
			
		};	
		
		assertEquals(2,objCharacter.getWornItems().size());
	}
	
	/**
	 * Tests the CharacterModel setBagItem :- 
	 * This Test is to verify that character can not wear more than 10 items
	 * 
	 */	
	@Test
	public void testSetBagItem() {
		
		
		
		
		ItemsModel tempItemAdd1=new ItemsModel();
		tempItem.itemType="Helmet";
		tempItem.itemBonus="2";	
		items.add(tempItemAdd1);

		ItemsModel tempItemAdd2=new ItemsModel();
		tempItem.itemType="Armor";
		tempItem.itemBonus="2";
		items.add(tempItemAdd2);
		
		ItemsModel tempItemAdd3=new ItemsModel();
		tempItem.itemType="Shield";
		tempItem.itemBonus="2";
		items.add(tempItemAdd3);
		
		ItemsModel tempItemAdd4=new ItemsModel();
		tempItem.itemType="Sword";
		tempItem.itemBonus="2";
		items.add(tempItemAdd4);
		
		ItemsModel tempItemAdd5=new ItemsModel();
		tempItem.itemType="Belt";
		tempItem.itemBonus="2";
		items.add(tempItemAdd5);
		
		ItemsModel tempItemAdd6=new ItemsModel();
		tempItem.itemType="Boots";
		tempItem.itemBonus="2";
		items.add(tempItemAdd6);
		
		
		ItemsModel tempItemAdd7=new ItemsModel();
		tempItem.itemType="Boots";
		tempItem.itemBonus="2";
		items.add(tempItemAdd7);
		
		
		ItemsModel tempItemAdd8=new ItemsModel();
		tempItem.itemType="Sword";
		tempItem.itemBonus="2";
		items.add(tempItemAdd8);
		
		ItemsModel tempItemAdd9=new ItemsModel();
		tempItem.itemType="Belt";
		tempItem.itemBonus="2";
		items.add(tempItemAdd9);
		
		ItemsModel tempItemAdd10=new ItemsModel();
		tempItem.itemType="Boots";
		tempItem.itemBonus="2";
		items.add(tempItemAdd10);
		
		
		objCharacter.setBagItemList(items);
		
		assertEquals(10,objCharacter.getBagItems().size());
		
		
		ItemsModel tempItemAdd11=new ItemsModel();
		tempItem.itemType="Sword";
		tempItem.itemBonus="2";
		
		
		objCharacter.setBagItem(tempItemAdd11);
			
		assertEquals(10,objCharacter.getBagItems().size());
		
		
	}
	
	/**
	 *  
	 * This Test is to verify that character abilities are enchanted by wearing items
	 * 
	 */	
		
	@Test
	public void testSetEnchanementValues() {
		
				
		ItemsModel tempItemAdd=new ItemsModel();
		tempItemAdd.itemType="Armor";
		tempItemAdd.itemBonus="3";
		
		items.add(tempItemAdd);
		
		characters.get(0).setWornItem(tempItemAdd);
		characters.get(0).setArmorClass(Integer.parseInt(tempItemAdd.itemBonus));
		
		CharacterEditorItemsController tempCharEditr=new CharacterEditorItemsController(characters,0,characters.get(0).getCharType(),mainMenu);
		
		tempCharEditr.setFrameVisbile(false);
		
		tempCharEditr.setEnchanementValues(characters.get(0).getCharType(), tempItemAdd.itemBonus, "Add");
		
		assertEquals(3,characters.get(0).getArmorClass());
		
		tempCharEditr.addWornToBag(0, characters.get(0).getCharType());
		
		assertEquals(0,characters.get(0).getArmorClass());
				
	}
	
		
	/**
	 *  
	 * This Test is to verify that character is initially created with Level 1
	 * 
	 */			
	@Test
	public void testStrengthNotNull() {
						
		assertEquals(1,objCharacter.getCharLevel());		
	}
	
		
	/**
	 *  
	 * This Test is to verify that character is saved in file/read from file
	 * @throws IOException if not saved in file/read from file
	 */			
	@Test
	public void testCharacterSaved() throws IOException {
		FileStorage itemStrg=new FileStorage();	
		itemStrg.SaveCharInFile(characters);
		characters=itemStrg.readCharacterInFile();
		assertEquals("Fighter",characters.get(0).getCharType());		
	}	
	
	
	/**
	 *  
	 * This Test is to verify that item is saved in file/read from file
	 * @throws IOException if not saved in file/read from file
	 */			
	@Test
	public void testItemSaved() throws IOException {
		FileStorage itemStrg=new FileStorage();	
		
		ItemsModel tempItemAdd1=new ItemsModel();
		tempItem.itemType="Helmet";
		tempItem.itemBonus="2";	
		items.add(tempItem);
		
		itemStrg.SaveItemInFile(items);
		items=itemStrg.ReadItemInFile();
		assertEquals("Helmet",items.get(0).itemType);		
	}	
	
	/**
	 *  
	 * This Test is to verify that a valid map is stored
	 * @throws IOException if not saved in file/read from file
	 */			
	@Test
	public void testMapValidation() {
	
		map.setEntryPoint(new Point(1,0));
		map.setExitPoint(new Point(1,4));
		map.setMapDescriptionOnSave("");
		map.isEntryDone=true;
		map.isExitDone=true;
		map.isZombiePlaced=true;
		
		MapFinalValidation mapValidator=new MapFinalValidation();
		
		mapValidator.mapValidations(map);
		System.out.println("Map Desc"+map.mapDescriptionOnSave);
		assertEquals("",map.mapDescriptionOnSave);
			
	}	
	
	/**
	 *  
	 * This Test is to verify that a valid map is stored if exit not done
	 */	
	@Test
	public void testMapValidationExitNotDone() {
	
		map.setExitPoint(new Point(1,4));
		map.setMapDescriptionOnSave("");
		map.isExitDone=true;
		map.isZombiePlaced=true;
		
		MapFinalValidation mapValidator=new MapFinalValidation();
		
		mapValidator.mapValidations(map);
		System.out.println("Map Desc"+map.mapDescriptionOnSave);
		assertNotEquals("",map.mapDescriptionOnSave);
			
	}	
	
	/**
	 *  
	 * This Test is to verify that a valid map is stored if no character is placed on map
	 */	
	@Test
	public void testMapValidationWithNoCharacter() {
	
		map.setEntryPoint(new Point(1,0));
		map.setExitPoint(new Point(1,4));
		map.setMapDescriptionOnSave("");
		map.isEntryDone=true;
		map.isExitDone=true;
		
		MapFinalValidation mapValidator=new MapFinalValidation();
		
		mapValidator.mapValidations(map);
		System.out.println("Map Desc"+map.mapDescriptionOnSave);
		assertNotEquals("",map.mapDescriptionOnSave);
			
	}	
	
	/**
	 *  
	 * This Test is to verify that a valid map is stored if entry not done
	 */	
	@Test
	public void testMapValidationEntryNotDone() {
	
		map.setEntryPoint(new Point(1,0));
		map.setMapDescriptionOnSave("");
		map.isEntryDone=true;
		map.isZombiePlaced=true;
		
		MapFinalValidation mapValidator=new MapFinalValidation();
		
		mapValidator.mapValidations(map);
		System.out.println("Map Desc"+map.mapDescriptionOnSave);
		assertNotEquals("",map.mapDescriptionOnSave);
			
	}	
	

/**
 * This method is for destroying all the objects after the test has been executed
 *
 */	
	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
		System.out.println("@AfterClass - oneTimeTearDown");
		objFighter = null;
		objZombie = null;
		objCharacter=null;
		tempItem=null;
		items=null;
		characters=null;
		mainMenu=null;
		map=null;
		

	}
	

}
