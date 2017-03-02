package gdxtesting.Deliverable1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.MapModel.EntryDoor;
import com.chaowang.ddgame.MapModel.ExitDoor;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.util.MazeSolver;

import java.util.Arrays;

import gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class MapEditorTest {
    Map map;
    int[][] mapMatrix;


    @Before public void createMap(){
        map = new Map(1,3,"name",new EntryDoor(new Vector2(0,0)), new ExitDoor(new Vector2(2,2)));
        mapMatrix = new int[map.getSize()][map.getSize()];
        buildLocationMatrix();
        map.setLocationMatrix(mapMatrix);
    }


    @Test
    public void mapConstructorExist() {
        assertEquals(map.getSize(), 3);
    }

    @Test
    public void mapValidateDoors(){
        int[] doorArr = new int[2];
        doorArr[0] = map.validateEntryDoor();
        doorArr[1]=map.validateExitDoor();
        int[] expectedResult = {1,1};
        assertEquals(Arrays.toString(doorArr) , Arrays.toString(expectedResult) );
    }

    @Test
    public void validateDoorConnected(){
        MazeSolver maze = new MazeSolver(map.getLocationMatrix());
        assertFalse("Entry door and exit door are not attached",maze.isInSurroundings());
    }

    @Test
    public void validateMazeSolvable(){
        MazeSolver maze = new MazeSolver(map.getLocationMatrix());
        assertFalse("Cannot find way to exit",maze.solveMaze());
    }



    private void buildLocationMatrix() {
        // create external matrix for map object, wall is 1, entrance is 2, exit is 3
        for (int i =0 ; i < map.getSize(); i++){
            for(int j = 0; j < map.getSize(); j++){
                if(i == map.getEntryDoor().getPosition().y && j == map.getEntryDoor().getPosition().x){
                    mapMatrix[i][j] = 2;
                }
                else if (i == map.getExitDoor().getPosition().y && j == map.getExitDoor().getPosition().x){
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
