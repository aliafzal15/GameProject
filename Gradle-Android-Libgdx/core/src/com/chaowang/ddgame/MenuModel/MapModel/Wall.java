package com.chaowang.ddgame.MenuModel.MapModel;


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
public class Wall extends Rectangle implements Json.Serializable{

    private Texture wall;
    private Texture mapTexture;
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
        this.x = position.x;
        this.y = position.y;
        this.width = position.x;
        this.height = position.y;
        wall = new Texture(Gdx.files.internal("map/wall.png"));
        mapTexture = new Texture(Gdx.files.internal("map/wall5.png"));
    }
    /**
     * constructor
     * @param position wall's position
     */
    public Wall(Vector2 position) {
        super(position.x, position.y,PublicParameter.MAP_PIXEL_SIZE * 3 / 4, PublicParameter.MAP_PIXEL_SIZE * 3 / 4);
    }
//    /**
//     * update the wall's information
//     */
//    public void update(){
//        bounds.set(position.x, position.y, size.x , size.y);
//    }
    /**
     * put the wall image on the program
     * @param batch
     */
    public void draw(SpriteBatch batch){
        batch.draw(mapTexture, this.x, this.y, this.width, this.height * 1.5f);
    }
    /**
     * get the wall's position
     * @return wall's position
     */
    public Vector2 getPosition() {
        return new Vector2(this.x,this.y);
    }
//    /**
//     * set the wall's position
//     * @param position wall's position
//     */
//    public void setPosition(Vector2 position) {
//        this.x = position.x ;
//        this.y = position.y;
//    }
//    /**
//     * get the size of door
//     * @return size of door
//     */
//    public Vector2 getSize() {
//        return size;
//    }
//    /**
//     * set the size of door
//     * @param size size of door
//     */
//    public void setSize(Vector2 size) {
//        this.size = size;
//    }
//    /**
//     * get wall's information
//     * @return wall's information
//     */
//    public Rectangle getBounds() {
//        return bounds;
//    }
//    /**
//     * set wall's information
//     * @param bounds wall's information
//     */
//    public void setBounds(Rectangle bounds) {
//        this.bounds = bounds;
//    }
    /**
     * write files for wall's information
     */
    @Override
    public void write(Json json) {
        json.writeValue("position", new Vector2(x, y), Vector2.class);
    }
    /**
     * read files for wall's information
     */
    @Override
    public void read(Json json, JsonValue jsonData) {
        String positionStr = jsonData.child.toString();
        positionStr = positionStr.substring(positionStr.indexOf("{")-1);
        Vector2 tmp = json.fromJson(Vector2.class, positionStr);
        this.x = tmp.x ;
        this.y = tmp.y;
        mapTexture = new Texture(Gdx.files.internal("map/wall1.gif"));
    }
}
