package Map;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.PublicParameter;

import Items.Item;

public class Wall implements Json.Serializable{

    Vector2 position, size;
    Texture wall;
    Rectangle bounds;

    public  Wall (){
        this(new Vector2(0,0));
    }

    public Wall(Vector2 position, Vector2 size){
        this.position = position;
        this.size = size;
        bounds = new Rectangle(position.x, position.y, size.x , size.y );
        wall = new Texture(Gdx.files.internal("android/assets//map/wall.png"));
    }

    public Wall(Vector2 position) {
        this(position, new Vector2(PublicParameter.mapPixelSize,PublicParameter.mapPixelSize));
    }

    public void update(){
        bounds.set(position.x, position.y, size.x , size.y);

    }

    public void draw(SpriteBatch batch){

        batch.draw(wall, position.x, position.y, size.x, size.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
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
