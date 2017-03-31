package com.chaowang.ddgame.MenuModel.MapModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
/**
 * the class for exit door
 * @author chao wang
 * @version 1.0
 */
public class ExitDoor extends Door implements Json.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3064593254115695006L;
	private Texture exitDoor;
    /**
     * constructor
     * @param position the exit door's position
     * @param size the exit door's size
     */
    public ExitDoor(Vector2 position, Vector2 size){
        super(position, size);
        exitDoor = new Texture(Gdx.files.internal("map/exit1.png"));
    }
    /**
     * constructor
     * @param position the exit door's position
     */
    public ExitDoor(Vector2 position) {
        super(position);
        exitDoor = new Texture(Gdx.files.internal("map/exit1.png"));
    }
    /**
     * constructor
     */
    public ExitDoor(){
        super();
        exitDoor = new Texture(Gdx.files.internal("map/exit1.png"));
    }
    /**
     * put the exit door on the background image
     * @param batch
     */
    public void draw(SpriteBatch batch){

        //batch.draw(exitDoor, position.x, position.y, size.x, size.y * 1.5f);
        batch.draw(exitDoor, x, y, this.width, this.height * 1.5f);
    }
    /**
     * write files
     */
    @Override
    public void write(Json json) {
        json.writeValue("position", new Vector2(this.x, this.y), Vector2.class);
    }
    /**
     * read files
     */
    @Override
    public void read(Json json, JsonValue jsonData) {
        String positionStr = jsonData.child.toString();
        positionStr = positionStr.substring(positionStr.indexOf("{")-1);
        Vector2 position = json.fromJson(Vector2.class, positionStr);
        this.x = position.x;
        this.y = position.y;
//        position = json.fromJson(Vector2.class, positionStr);
//        bounds.setX(position.x);
//        bounds.setY(position.y);
    }

}
