package Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class EntryDoor extends Door implements Json.Serializable{

    Texture entryDoor;

    public EntryDoor(Vector2 position){
        super(position);
        entryDoor = new Texture(Gdx.files.internal("android/assets/map/entryDoor.png"));
    }

    public EntryDoor(Vector2 position, Vector2 size){
        super(position, size);
        entryDoor = new Texture(Gdx.files.internal("android/assets/map/entryDoor.png"));
    }

    public EntryDoor(){
        super();
        entryDoor = new Texture(Gdx.files.internal("android/assets/map/entryDoor.png"));
    }

    public void draw(SpriteBatch batch){

        batch.draw(entryDoor, position.x, position.y, size.x, size.y);
    }

    @Override
    public void write(Json json) {
        json.writeValue("position", position, Vector2.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        String positionStr = jsonData.child.toString();
        positionStr = positionStr.substring(positionStr.indexOf("{")-1);
        position = json.fromJson(Vector2.class, positionStr);
        bounds.setX(position.x);
        bounds.setY(position.y);
    }

}
