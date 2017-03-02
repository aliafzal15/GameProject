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

import gdxtesting.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class CharacterEditorTest {
    Character character;

    @Before public void createCharacter(){
        character = new Character();
    }

    @Test
    public void characterConstructorExist() {
        assertEquals(character.getLevel(), 1);
    }

}
