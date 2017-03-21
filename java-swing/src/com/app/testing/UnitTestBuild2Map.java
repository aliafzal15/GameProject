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
public class UnitTestBuild2Map {
	

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
	 * Tests the setMapItems Function of StartGameController
	 * This Test is to verify if the map is loaded with all items
	 * 
	 */
	
	@Test
	public void testSetMapItem() {
		
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
		
		
		assertEquals("Shield",tempController.runTimeMapItems.get(0).itemType);
		assertEquals("1",tempController.runTimeMapItems.get(0).itemBonus);
		
	}
	

	/**
	 * Tests the setHostileFriendlyChars Function of StartGameController
	 * This Test is to verify if the hostile character is zombie when game char is fighter
	 * 
	 */
	
	@Test
	public void testSetHostileFriendlyChars() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		objZombie=new CharacterModel("Zombie");
		
		StartGameController tempController= new StartGameController();
		
		tempController.gamecharacter=objFighter;
		
		map.mapCharacters=new ArrayList();
		
		map.mapCharacters.add(objZombie);
		map.mapCharacters.add(objFighter);
	
		tempController.hostileCharacters=new ArrayList();
		
		tempController.friendlyCharacters=new ArrayList();
		
		tempController.setHostileFriendlyChars(map.mapCharacters, tempController.gamecharacter);
		
		
		assertEquals("Zombie",tempController.hostileCharacters.get(0).getCharType());
	
		
		
	}
	
	/**
	 * Tests the setHostileFriendlyChars Function of StartGameController
	 * This Test is to verify if the hostile character is fighter when game char is zombie
	 * 
	 */
	
	@Test
	public void testFighterHostileChar() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		objZombie=new CharacterModel("Zombie");
		
		StartGameController tempController= new StartGameController();
		
		tempController.gamecharacter=objZombie;
		
		map.mapCharacters=new ArrayList();
		
		map.mapCharacters.add(objZombie);
		map.mapCharacters.add(objFighter);
	
		tempController.hostileCharacters=new ArrayList();
		
		tempController.friendlyCharacters=new ArrayList();
		
		tempController.setHostileFriendlyChars(map.mapCharacters, tempController.gamecharacter);
		
		
		assertEquals("Fighter",tempController.hostileCharacters.get(0).getCharType());
	
		
		
	}
	
	/**
	 * Tests the setHostileFriendlyChars Function of StartGameController
	 * This Test is to verify if the friendly character is zombie when game char is zombie
	 * 
	 */
	
	@Test
	public void testZombieFriendlyChar() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		objZombie=new CharacterModel("Zombie");
		
		StartGameController tempController= new StartGameController();
		
		tempController.gamecharacter=objZombie;
		
		map.mapCharacters=new ArrayList();
		
		map.mapCharacters.add(objZombie);
		map.mapCharacters.add(objFighter);
	
		tempController.hostileCharacters=new ArrayList();
		
		tempController.friendlyCharacters=new ArrayList();
		
		tempController.setHostileFriendlyChars(map.mapCharacters, tempController.gamecharacter);
		
		
		assertEquals("Zombie",tempController.friendlyCharacters.get(0).getCharType());		
		
	}
	
	/**
	 * Tests the setHostileFriendlyChars Function of StartGameController
	 * This Test is to verify if the friendly character is fighter when game char is fighter
	 * 
	 */
	
	@Test
	public void testFighterFriendlyChar() {
		
		objFighter=new CharacterModel(fighterType);
		
		objFighter.setArrayWorn();	
		
		objZombie=new CharacterModel("Zombie");
		
		StartGameController tempController= new StartGameController();
		
		tempController.gamecharacter=objFighter;
		
		map.mapCharacters=new ArrayList();
		
		map.mapCharacters.add(objZombie);
		map.mapCharacters.add(objFighter);
	
		tempController.hostileCharacters=new ArrayList();
		
		tempController.friendlyCharacters=new ArrayList();
		
		tempController.setHostileFriendlyChars(map.mapCharacters, tempController.gamecharacter);
		
		
		assertEquals("Fighter",tempController.friendlyCharacters.get(0).getCharType());
	
		
		
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

