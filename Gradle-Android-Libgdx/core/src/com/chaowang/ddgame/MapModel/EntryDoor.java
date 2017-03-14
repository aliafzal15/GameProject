package com.chaowang.ddgame.MapModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
/**
 * the class for entry door
 * @author chao wang
 * @version 1.0
 */
public class EntryDoor extends Door implements Json.Serializable{

    Texture entryDoor;
    /**
     * constructor
     * @param position the entry door's position
     */
    public EntryDoor(Vector2 position){
        super(position);
        entryDoor = new Texture(Gdx.files.internal("map/entry1.png"));
    }
    /**
     * constructor
     * @param position the entry door's position
     * @param size the entry door's size
     */
    public EntryDoor(Vector2 position, Vector2 size){
        super(position, size);
        entryDoor = new Texture(Gdx.files.internal("map/entry1.png"));
    }
    /**
     * constructor
     */
    public EntryDoor(){
        super();
        entryDoor = new Texture(Gdx.files.internal("map/entry1.png"));
    }
    /**
     * put the entry door on the background image
     * @param batch
     */
    public void draw(SpriteBatch batch){

        batch.draw(entryDoor, position.x, position.y, size.x, size.y * 1.5f);
    }
    /**
     * write the files
     */
    @Override
    public void write(Json json) {
        json.writeValue("position", position, Vector2.class);
    }
    /**
     * read files
     */
    @Override
    public void read(Json json, JsonValue jsonData) {
        String positionStr = jsonData.child.toString();
        positionStr = positionStr.substring(positionStr.indexOf("{")-1);
        position = json.fromJson(Vector2.class, positionStr);
        bounds.setX(position.x);
        bounds.setY(position.y);
    }

}
