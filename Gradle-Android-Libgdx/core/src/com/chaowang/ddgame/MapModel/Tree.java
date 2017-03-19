package com.chaowang.ddgame.MapModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * the class for Tree
 * @author chao wang
 * @version 2.0
 */
public class Tree {

    Vector2 position, size;
    Texture tree;
    Rectangle bounds;
    /**
     * constructor
     * @param position
     * @param size
     */
    public Tree(Vector2 position, Vector2 size){
        this.position = position;
        this.size = size;
        bounds = new Rectangle(position.x, position.y, size.x , size.y );
        tree = new Texture(Gdx.files.internal("map/wall1.png"));
    }
    /**
     * update the location
     */
    public void update(){
        bounds.set(position.x, position.y, size.x , size.y);

    }
    /**
     * put the Tree image on the program
     * @param batch
     */
    public void draw(SpriteBatch batch){

        batch.draw(tree, position.x, position.y, size.x, size.y * 1.5f);
    }
    /**
     * get the tree's position
     * @return
     */
    public Vector2 getPosition() {
        return position;
    }
    /**
     * set the tree's position
     * @param position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    /**
     * get the size of tree
     * @return
     */
    public Vector2 getSize() {
        return size;
    }
    /**
     * set the size of tree
     * @param size
     */
    public void setSize(Vector2 size) {
        this.size = size;
    }
    /**
     * get tree's information
     * @return
     */
    public Rectangle getBounds() {
        return bounds;
    }
    /**
     * set tree's information
     * @param bounds
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
