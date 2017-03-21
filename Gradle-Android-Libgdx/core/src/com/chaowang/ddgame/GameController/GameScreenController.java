package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.PlayModel.Player;
import com.chaowang.ddgame.View.GameScreen;

import java.util.Iterator;

/**
 * Created by Chao on 20/03/2017.
 */

public class GameScreenController {

    private GameScreen view;
    private Map mapModel;
    private Player player;
    private Vector3 touch;
    private Vector2 pointer;

    public GameScreenController(GameScreen screen, Map map ,Player role){
        this.view = screen;
        this.mapModel = map;
        this.player = role;
        player.getCharacter().addObserver(view);
        Iterator<Vector2> keySetIterator = mapModel.getEnemyLocationList().keySet().iterator();
        while(keySetIterator.hasNext()) {
            mapModel.getEnemyLocationList().get(keySetIterator.next()).addObserver(view);
        }
        keySetIterator = mapModel.getFriendLocationList().keySet().iterator();
        while(keySetIterator.hasNext()) {
            mapModel.getFriendLocationList().get(keySetIterator.next()).addObserver(view);
        }
        touch = new Vector3();
    }

    public void onClickListen(){

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE )){
            view.getAbilityLabel().setVisible(false);
        }

        if(Gdx.input.isTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            view.getCam().unproject(touch);
//            System.out.println("Screen coordinates translated to world coordinates: "
//                    + "X: " + touch.x + " Y: " + touch.y);
//            System.out.println("player x:" + player.getPosition().x + " player y: "+ player.getPosition().y);
            if(player.getBound().contains(touch.x,touch.y)){
                player.getCharacter().previewAllAttribute();
            }

            Iterator<Vector2> keySetIterator = mapModel.getEnemyLocationList().keySet().iterator();
            while(keySetIterator.hasNext()) {
                pointer = keySetIterator.next();
                if(mapModel.getEnemyLocationList().get(pointer).getBound().contains(touch.x, touch.y)){
                    mapModel.getEnemyLocationList().get(pointer).previewAllAttribute();
                }
            }
            keySetIterator = mapModel.getFriendLocationList().keySet().iterator();
            while(keySetIterator.hasNext()) {
                pointer = keySetIterator.next();
                if(mapModel.getFriendLocationList().get(pointer).getBound().contains(touch.x, touch.y)){
                    mapModel.getFriendLocationList().get(pointer).previewAllAttribute();
                }
            }
        }


    }



}
