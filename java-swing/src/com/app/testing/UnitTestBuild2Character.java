package com.app.testing;



import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.builder.BullyCharacterBuilder;
import com.app.builder.CharacterBuilder;
import com.app.builder.Explorer;
import com.app.builder.NimbleCharacterBuilder;
import com.app.builder.TankCharacterBuilder;
import com.app.controller.CharacterEditorItemsController;
import com.app.controller.RunTimeGameController;
import com.app.controller.StartGameController;
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
public class UnitTestBuild2Character {
	

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
	 * This is CharacterBuilder Object
	 */	
	private CharacterBuilder tankBuilder;
	
	/**
	 * This is CharacterBuilder Object
	 */
	private CharacterBuilder nimbleBuilder;
	
	/**
	 * This is CharacterBuilder Object
	 */
	private CharacterBuilder bullyBuilder;
	
	/**
	 * This is Explorer Object
	 */
	private Explorer explorer;

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
		CharacterBuilder tankBuilder=new TankCharacterBuilder();
		Explorer explorer=new Explorer();

		
		
	}
	

	/**
	 * Tests the getMapItem Function in RunTimeGameController
	 * This Test is to verify if the character can loot chest
	 * 
	 */
	
	@Test
	public void testLootMapItem() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		StartGameController tempController= new StartGameController();
		RunTimeGameController gameController= new RunTimeGameController();
		
		tempController.gamecharacter=objFighter;
		
		tempItem.itemType="Shield";
		tempItem.itemBonus="1";
		
		items.add(tempItem);
	
		map.setMapItemsList(items);
		
		tempController.runTimeMapItems=map.getMapItems();
		
		gameController.tempStartController=tempController;
		
		tempController.gamecharacter.setBagItem(gameController.getMapItem("Shield"));
		
		assertEquals("Shield",tempController.gamecharacter.getBagItems().get(0).itemType);
		assertEquals("1",tempController.gamecharacter.getBagItems().get(0).itemBonus);
		
	}
	

	
	/**
	 * Tests the getMapItem Function in RunTimeGameController
	 * This Test is to verify if the character can loot Armor
	 * 
	 */
	
	@Test
	public void testLootArmorItem() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		StartGameController tempController= new StartGameController();
		RunTimeGameController gameController= new RunTimeGameController();
		
		tempController.gamecharacter=objFighter;
		
		tempItem.itemType="Armor";
		tempItem.itemBonus="1";
		
		items.add(tempItem);
	
		map.setMapItemsList(items);
		
		tempController.runTimeMapItems=map.getMapItems();
		
		gameController.tempStartController=tempController;
		
		tempController.gamecharacter.setBagItem(gameController.getMapItem("Armor"));
		
		assertEquals("Armor",tempController.gamecharacter.getBagItems().get(0).itemType);
		assertEquals("1",tempController.gamecharacter.getBagItems().get(0).itemBonus);
		
	}

	
	/**
	 * Tests the getMapItem Function in RunTimeGameController
	 * This Test is to verify if the character can loot Helmet
	 * 
	 */
	
	@Test
	public void testLootHelmetItem() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		StartGameController tempController= new StartGameController();
		RunTimeGameController gameController= new RunTimeGameController();
		
		tempController.gamecharacter=objFighter;
		
		tempItem.itemType="Helmet";
		tempItem.itemBonus="1";
		
		items.add(tempItem);
	
		map.setMapItemsList(items);
		
		tempController.runTimeMapItems=map.getMapItems();
		
		gameController.tempStartController=tempController;
		
		tempController.gamecharacter.setBagItem(gameController.getMapItem("Helmet"));
		
		assertEquals("Helmet",tempController.gamecharacter.getBagItems().get(0).itemType);
		assertEquals("1",tempController.gamecharacter.getBagItems().get(0).itemBonus);
		
	}
	
	
	/**
	 * Tests the getMapItem Function in RunTimeGameController
	 * This Test is to verify if the character can loot Weapon
	 * 
	 */
	
	@Test
	public void testLootWeaponItem() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		StartGameController tempController= new StartGameController();
		RunTimeGameController gameController= new RunTimeGameController();
		
		tempController.gamecharacter=objFighter;
		
		tempItem.itemType="Weapon";
		tempItem.itemBonus="1";
		
		items.add(tempItem);
	
		map.setMapItemsList(items);
		
		tempController.runTimeMapItems=map.getMapItems();
		
		gameController.tempStartController=tempController;
		
		tempController.gamecharacter.setBagItem(gameController.getMapItem("Weapon"));
		
		assertEquals("Weapon",tempController.gamecharacter.getBagItems().get(0).itemType);
		assertEquals("1",tempController.gamecharacter.getBagItems().get(0).itemBonus);
		
	}
	
	

	/**
	 * Tests the getMapItem Function in RunTimeGameController
	 * This Test is to verify if the character can loot Boots
	 * 
	 */
	@Test
	public void testLootBootsItem() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		StartGameController tempController= new StartGameController();
		RunTimeGameController gameController= new RunTimeGameController();
		
		tempController.gamecharacter=objFighter;
		
		tempItem.itemType="Boots";
		tempItem.itemBonus="1";
		
		items.add(tempItem);
	
		map.setMapItemsList(items);
		
		tempController.runTimeMapItems=map.getMapItems();
		
		gameController.tempStartController=tempController;
		
		tempController.gamecharacter.setBagItem(gameController.getMapItem("Boots"));
		
		assertEquals("Boots",tempController.gamecharacter.getBagItems().get(0).itemType);
		assertEquals("1",tempController.gamecharacter.getBagItems().get(0).itemBonus);
		
	}
	
	/**
	 * Tests the getMapItem Function in RunTimeGameController
	 * This Test is to verify if the character can loot Ring
	 * 
	 */
	@Test
	public void testLootRingItem() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		StartGameController tempController= new StartGameController();
		RunTimeGameController gameController= new RunTimeGameController();
		
		tempController.gamecharacter=objFighter;
		
		tempItem.itemType="Ring";
		tempItem.itemBonus="1";
		
		items.add(tempItem);
	
		map.setMapItemsList(items);
		
		tempController.runTimeMapItems=map.getMapItems();
		
		gameController.tempStartController=tempController;
		
		tempController.gamecharacter.setBagItem(gameController.getMapItem("Ring"));
		
		assertEquals("Ring",tempController.gamecharacter.getBagItems().get(0).itemType);
		assertEquals("1",tempController.gamecharacter.getBagItems().get(0).itemBonus);
		
	}
	
	/**
	 * Tests the getMapItem Function in RunTimeGameController
	 * This Test is to verify if the character can loot Belt
	 * 
	 */
	@Test
	public void testLootBeltItem() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		StartGameController tempController= new StartGameController();
		RunTimeGameController gameController= new RunTimeGameController();
		
		tempController.gamecharacter=objFighter;
		
		tempItem.itemType="Belt";
		tempItem.itemBonus="1";
		
		items.add(tempItem);
	
		map.setMapItemsList(items);
		
		tempController.runTimeMapItems=map.getMapItems();
		
		gameController.tempStartController=tempController;
		
		tempController.gamecharacter.setBagItem(gameController.getMapItem("Belt"));
		
		assertEquals("Belt",tempController.gamecharacter.getBagItems().get(0).itemType);
		assertEquals("1",tempController.gamecharacter.getBagItems().get(0).itemBonus);
		
	}
	
	
	/**
	 * Tests the Builder Pattern Functions
	 * This Test is to verify if the character with Bully characteristics
	 * 
	 */
	@Test
	public void testBuilderPatternBully() {
		
		objFighter=new CharacterModel(fighterType);
			
		bullyBuilder=new BullyCharacterBuilder();
		explorer=new Explorer();
		explorer.setBuilder(bullyBuilder);
		explorer.buildTypeCharacter(objFighter);
		
		assertTrue(objFighter.getStrength()> objFighter.getConstitution());
		assertTrue(objFighter.getConstitution()>objFighter.getDexterity());
		
	}
	
	/**
	 * Tests the Builder Pattern Functions
	 * This Test is to verify if the character with Tank characteristics
	 * 
	 */
	@Test
	public void testBuilderPatternTank() {
		
		objFighter=new CharacterModel(fighterType);
			
		tankBuilder=new TankCharacterBuilder();
		explorer=new Explorer();
		explorer.setBuilder(tankBuilder);
		explorer.buildTypeCharacter(objFighter);
		
		assertTrue(objFighter.getConstitution()> objFighter.getDexterity());
		assertTrue(objFighter.getStrength()< objFighter.getDexterity());
		
	}
	
	/**
	 * Tests the Builder Pattern Functions
	 * This Test is to verify if the character with Tank characteristics
	 * 
	 */
	@Test
	public void testBuilderPatternNimble() {
		
		objFighter=new CharacterModel(fighterType);
			
		nimbleBuilder=new NimbleCharacterBuilder();
		explorer=new Explorer();
		explorer.setBuilder(nimbleBuilder);
		explorer.buildTypeCharacter(objFighter);
		
		assertTrue(objFighter.getConstitution()< objFighter.getDexterity());
		assertTrue(objFighter.getStrength()< objFighter.getConstitution());
		
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
		explorer=null;
		tankBuilder=null;
		bullyBuilder=null;
		nimbleBuilder=null;
		

	}
	
	

}
