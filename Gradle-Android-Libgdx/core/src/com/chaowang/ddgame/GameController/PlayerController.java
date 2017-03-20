package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.chaowang.ddgame.ItemModel.Item;
import com.chaowang.ddgame.PlayModel.Player;
import com.chaowang.ddgame.View.GameScreen;

/**
 * controller for player
 * @author chao wang
 * @version 2.0
 */
public class PlayerController extends InputAdapter{

    private Player player;
    private GameScreen gameScreen;
    private Rectangle mapBound;
    private float stateTime;
    private String movement;

    /**
     * constructor
     * @param p
     * @param screen
     */
    public PlayerController(Player p, GameScreen screen){
        this.player = p;
        this.gameScreen = screen;
        MapProperties prop = gameScreen.getMap().getProperties();
        mapBound = new Rectangle(0, 0, prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class), prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));
        stateTime = 0f;
    }

    /**
     * use keyboard to control the players' movement
     */
    public void keyDown(){

        if(stateTime < 4 ){
            stateTime += Gdx.graphics.getDeltaTime() ;
        }else{
            stateTime = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W ) && player.getPosition().y + player.getCurrentFrame().getRegionHeight()< mapBound.getHeight() ){
            player.move(0,1);
            movement = "up";
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(12 + (stateTime *4) % 4));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S ) && player.getPosition().y > mapBound.getY() ){
            player.move(0,-1);
            movement = "down";
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(0 + (stateTime *4) % 4));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A ) && player.getPosition().x > mapBound.getX()){
            player.move(-1,0);
            movement = "left";
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(4 + (stateTime *4) % 4));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D ) && player.getPosition().x + player.getCurrentFrame().getRegionWidth() < mapBound.getWidth()){
            player.move(1,0);
            movement = "right";
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(8 + (stateTime *4) % 4));
        }


    }
    /**
     * adjust the location of player when moving
     */
    public void reAdjust(float span){
        if(span <= 0 ){
            span = 0.8f;
        }
        if(movement == "up"){
        	player.move(0,-1 * span);
        }
        else if(movement == "down"){
        	player.move(0,1 * span);
        }
        else if(movement == "left"){
        	player.move(1 * span,0);
        }
        else if(movement == "right"){
        	player.move(-1 * span,0);
        }

    }
    /**
     * pick items by players themself
     * @param item different kinds of items
     */
    public void pickupItem(Item item){
        player.getCharacter().addToBackpack(item);

    }

}
//    @Override
//    public boolean keyDown(int keycode) {
//        if(stateTime < 4 ){
//            stateTime += Gdx.graphics.getDeltaTime();
//        }else{
//            stateTime = 0;
//        }
//
//        if(keycode == Input.Keys.UP && player.getPosition().y < mapBound.getHeight()){
//            player.move(0,1);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(12 + stateTime));
//        }
//        if(keycode == Input.Keys.DOWN && player.getPosition().y > mapBound.getY() ){
//            player.move(0,-1);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(0 + stateTime));
//        }
//        if(keycode == Input.Keys.LEFT && player.getPosition().x > mapBound.getX()){
//            player.move(-1,0);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(4 + stateTime));
//        }
//        if(keycode == Input.Keys.RIGHT && player.getPosition().x < mapBound.getWidth()){
//            player.move(1,0);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(8 + stateTime));
//        }
//        return false;
//    }
