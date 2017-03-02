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
import com.chaowang.ddgame.MapModel.Map;

import gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class MapEditorTest {
    Map map;

    @Before public void createMap(){
        map = new Map(1,2,"name");
    }

    @Test
    public void characterConstructorExist() {
        assertEquals(map.getSize(), 2);
    }

}
