package com.chaowang.ddgame.MapModel;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.PublicParameter;

/**
 * the class for wall
 * @author chao wang
 * @version 1.0
 */
public class Wall implements Json.Serializable{

    Vector2 position, size;
    Texture wall;
    Rectangle bounds;
    /**
     * constructor
     */
    public  Wall (){
        this(new Vector2(0,0));
    }
    /**
     * constructor
     * @param position wall's position
     * @param size wall's size
     */
    public Wall(Vector2 position, Vector2 size){
        this.position = position;
        this.size = size;
        bounds = new Rectangle(position.x, position.y, size.x , size.y );
        wall = new Texture(Gdx.files.internal("map/wall.png"));
    }
    /**
     * constructor
     * @param position wall's position
     */
    public Wall(Vector2 position) {
        this(position, new Vector2(PublicParameter.mapPixelSize,PublicParameter.mapPixelSize));
    }
    /**
     * update the wall's information
     */
    public void update(){
        bounds.set(position.x, position.y, size.x , size.y);

    }
    /**
     * put the wall image on the program
     * @param batch
     */
    public void draw(SpriteBatch batch){

        batch.draw(wall, position.x, position.y, size.x, size.y);
    }
    /**
     * get the wall's position
     * @return wall's position
     */
    public Vector2 getPosition() {
        return position;
    }
    /**
     * set the wall's position
     * @param position wall's position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    /**
     * get the size of door
     * @return size of door
     */
    public Vector2 getSize() {
        return size;
    }
    /**
     * set the size of door
     * @param size size of door
     */
    public void setSize(Vector2 size) {
        this.size = size;
    }
    /**
     * get wall's information
     * @return wall's information
     */
    public Rectangle getBounds() {
        return bounds;
    }
    /**
     * set wall's information
     * @param bounds wall's information
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
    /**
     * write files for wall's information
     */
    @Override
    public void write(Json json) {
        json.writeValue("position", position, Vector2.class);
    }
    /**
     * read files for wall's information
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
