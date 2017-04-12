package com.chaowang.ddgame.MenuModel.MapModel;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.PublicParameter;
/**
 * class for door
 * @author chao wang
 * @version 1.0
 */
public class Door extends Rectangle{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4467100315917848877L;
	
//	Vector2 position, size;
//    Rectangle bounds;
    /**
     * constructor
     */
    public  Door (){
        this(new Vector2(0,0));
    }
    /**
     * constructor
     * @param position the position of the door 
     * @param size the size of the door
     */
    public Door(Vector2 position, Vector2 size){
        this.x = position.x;
        this.y = position.y;
        this.width = position.x;
        this.height = position.y;
    }
    /**
     * constructor
     * @param position the position of the door 
     */
    public Door(Vector2 position) {
        //this(position, new Vector2(PublicParameter.MAP_PIXEL_SIZE * 3 / 4,PublicParameter.MAP_PIXEL_SIZE * 3 / 4));
    	super(position.x, position.y,PublicParameter.MAP_PIXEL_SIZE * 3 / 4, PublicParameter.MAP_PIXEL_SIZE * 3 / 4);
    }
//    /**
//     * update the door
//     */
//    public void update(){
//        bounds.set(position.x, position.y, size.x , size.y);
//
//    }
//    /**
//     * get the position of the door
//     * @return the position
//     */
//    public Vector2 getPosition() {
//        return position;
//    }
//    /**
//     * set the position of the door
//     * @param position the position of the door
//     */
//    public void setPosition(Vector2 position) {
//        this.position = position;
//    }
//    /**
//     * get the size of the door
//     * @return the door size
//     */
//    public Vector2 getSize() {
//        return size;
//    }
//    /**
//     * set the size of the door
//     * @param size the door size
//     */
//    public void setSize(Vector2 size) {
//        this.size = size;
//    }
//    /**
//     * get the door
//     * @return the door's information
//     */
//    public Rectangle getBounds() {
//        return bounds;
//    }
//    /**
//     * set the door
//     * @param bounds the door's information
//     */
//    public void setBounds(Rectangle bounds) {
//        this.bounds = bounds;
//    }

}
