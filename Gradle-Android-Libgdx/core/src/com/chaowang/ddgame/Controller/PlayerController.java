package com.chaowang.ddgame.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.chaowang.ddgame.PlayModel.Actor;
import com.chaowang.ddgame.View.GameScreen;


public class PlayerController extends InputAdapter{

    private Actor actor;
    private GameScreen gameScreen;
    private Rectangle mapBound;
    private float stateTime;

    public PlayerController(Actor p, GameScreen screen){
        this.actor = p;
        this.gameScreen = screen;
        MapProperties prop = gameScreen.getMap().getProperties();
        mapBound = new Rectangle(0, 0, prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class), prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));
        stateTime = 0f;
    }


    public void keyDown(){

        if(stateTime < 4 ){
            stateTime += Gdx.graphics.getDeltaTime() ;
        }else{
            stateTime = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W ) && actor.getPosition().y + actor.getCurrentFrame().getRegionHeight()< mapBound.getHeight() ){
            actor.move(0,1);
            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(12 + (stateTime *4) % 4));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S ) && actor.getPosition().y > mapBound.getY() ){
            actor.move(0,-1);
            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(0 + (stateTime *4) % 4));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A ) && actor.getPosition().x > mapBound.getX()){
            actor.move(-1,0);
            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(4 + (stateTime *4) % 4));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D ) && actor.getPosition().x + actor.getCurrentFrame().getRegionWidth() < mapBound.getWidth()){
            actor.move(1,0);
            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(8 + (stateTime *4) % 4));
        }


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
//        if(keycode == Input.Keys.UP && actor.getPosition().y < mapBound.getHeight()){
//            actor.move(0,1);
//            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(12 + stateTime));
//        }
//        if(keycode == Input.Keys.DOWN && actor.getPosition().y > mapBound.getY() ){
//            actor.move(0,-1);
//            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(0 + stateTime));
//        }
//        if(keycode == Input.Keys.LEFT && actor.getPosition().x > mapBound.getX()){
//            actor.move(-1,0);
//            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(4 + stateTime));
//        }
//        if(keycode == Input.Keys.RIGHT && actor.getPosition().x < mapBound.getWidth()){
//            actor.move(1,0);
//            actor.setCurrentFrame((TextureRegion)actor.getAnimation().getKeyFrame(8 + stateTime));
//        }
//        return false;
//    }
