package gdxtesting.Deliverable2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import gdxtesting.GdxTestRunner;

import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.MenuController.MapController;
import com.chaowang.ddgame.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.ItemModel.Item;
import com.chaowang.ddgame.MapModel.EntryDoor;
import com.chaowang.ddgame.MapModel.ExitDoor;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.util.MazeSolver;

import java.util.Arrays;

/**
 * the class is Junit test for Map
 * @author chao wang
 * @version 1.0
 */
 
@RunWith(GdxTestRunner.class)
public class MapEditorTest {
    Map map;
    Item item;
    Character character;
    int[][] mapMatrix;
    MapController controller;

    /**
     * initilize map, its character, items, controller
     */
    @Before public void createMapSet(){
        map = new Map(1,4,"name",new EntryDoor(new Vector2(0,0)), new ExitDoor(new Vector2(2,2)));
		item = new Item(Item.ItemType.ARMOR, "unitTest", 1, EnchantedAbility.ARMORCLASS);
        character = new Character();
        mapMatrix = new int[map.getSize()][map.getSize()];
        buildLocationMatrix();
        map.setLocationMatrix(mapMatrix);
        controller = new MapController(map,item,character);
    }

    /**
     * test constructor if size 
     */
    @Test
    public void testMapConstructor() {
        assertEquals(map.getSize(), 4);
    }

    /**
     * validator 1, may only have 1 entry door, exit door.
     */
    @Test
    public void tesMapValidatorDoors(){
        int[] doorArr = new int[2];
        doorArr[0] = map.validateEntryDoor();
        doorArr[1]=map.validateExitDoor();
        int[] expectedResult = {1,1};
        assertEquals(Arrays.toString(doorArr) , Arrays.toString(expectedResult) );
    }
    /**
     * validator 2, check if entry and exit are too close
     */
    @Test
    public void testMapValidatorDistance(){
        MazeSolver maze = new MazeSolver(map.getLocationMatrix());
        assertFalse("Entry door and exit door are not attached",maze.isInSurroundings());
    }
    /**
     * validator 3, check if there is a path to exit from entry
     */
    @Test
    public void testMapValidatorPath(){
        MazeSolver maze = new MazeSolver(map.getLocationMatrix());
        assertFalse("Cannot find way to exit",maze.solveMaze());
    }
    /**
     * validator 4, exit door cannot put at bottom matrix
     */
    @Test
    public void testMapValidatorExitDoor(){
        map.setExitDoor(new ExitDoor(new Vector2(3,3)) );
        buildLocationMatrix();
        map.setLocationMatrix(mapMatrix);
        int[] doorArr = new int[2];
        doorArr[0] = map.validateEntryDoor();
        doorArr[1]=map.validateExitDoor();
        int[] expectedResult = {1,-1};
        assertEquals(Arrays.toString(doorArr) , Arrays.toString(expectedResult) );
    }
    /**
     * test if we can add character to map
     */
    @Test
    public void testaddCharacterOnMap(){
    	controller.setMatrixPointer(-2);
    	controller.addItemCharctOnSpot(1, 1);
        assertEquals(map.getFriendLocationList().size(), 1);
    }
    /**
     * test if we add character and item to the same spot, character will be removed from map
     */
    @Test
    public void testItemCharacterPileUp(){
    	controller.setMatrixPointer(-2);
    	controller.addItemCharctOnSpot(1, 1);
    	controller.setMatrixPointer(-1);
    	controller.addItemCharctOnSpot(1, 1);
        assertEquals(map.getFriendLocationList().size(), 0);

    }
    
    /**
     * internal use to build location matrix, instead of read from view
     */
    private void buildLocationMatrix() {
        // create external matrix for map object, wall is 1, entrance is 2, exit is 3
        for (int i =0 ; i < map.getSize(); i++){
            for(int j = 0; j < map.getSize(); j++){
                if(i == map.getEntryDoor().y && j == map.getEntryDoor().x){
                    mapMatrix[i][j] = 2;
                }
                else if (i == map.getExitDoor().y && j == map.getExitDoor().x){
                    mapMatrix[i][j] = 3;
                }
                else if (i == 1 ){
                    mapMatrix[i][j] = 1;  //block the maze at middle, cannot go to exit
                }
                else{
                    mapMatrix[i][j] = 0;
                }
            }
        }
    }


}
