package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.ItemModel.Item;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.PlayModel.Player;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.View.GameScreen;
import com.chaowang.ddgame.View.MainMenuScreen;

import java.util.Iterator;
import java.util.Map.Entry;

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
            for(Image image : view.getBackpackMatrix()){
                image.setVisible(false);
            }
            for(Image image : view.getEquipmentMatrix()){
                image.setVisible(false);
            }
        }

        if(Gdx.input.isTouched() && Gdx.input.isKeyPressed(Input.Keys.I )){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            view.getCam().unproject(touch);
            if(player.getBound().contains(touch.x,touch.y)){
                player.getCharacter().previewLoadEquipment(-1);
                for (int i = 0; i < view.getBackpackMatrix().length ; i++) {
                    view.getBackpackMatrix()[i].addListener(new ClickListener(i) {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            System.out.println("getbutton" + getButton());
                            if ( getButton() < player.getCharacter().getBackpack().size()  && !player.getCharacter().getEquipment().containsKey(player.getCharacter().getBackpack().get(getButton()).getItemType())) {
                                player.getCharacter().previewLoadEquipment(getButton());
                            }
                            return true;
                        }

                    });
                }

                for (int i = 0; i < view.getEquipmentMatrix().length ; i++){
                    view.getEquipmentMatrix()[i].addListener(new ClickListener(i) {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            if(player.getCharacter().getEquipment().containsKey(Item.ItemType.getItemType(getButton()))){
                                player.getCharacter().previewUnloadEquipment(getButton());
                            }
                            return true;
                        }
                    });
                }
            }

            Iterator<Vector2> keySetIterator = mapModel.getEnemyLocationList().keySet().iterator();
            while(keySetIterator.hasNext()) {
                pointer = keySetIterator.next();
                if(mapModel.getEnemyLocationList().get(pointer).getBound().contains(touch.x, touch.y)){
                    mapModel.getEnemyLocationList().get(pointer).previewLoadEquipment(-1);
                }
            }
            keySetIterator = mapModel.getFriendLocationList().keySet().iterator();
            while(keySetIterator.hasNext()) {
                pointer = keySetIterator.next();
                if(mapModel.getFriendLocationList().get(pointer).getBound().contains(touch.x, touch.y)){
                    mapModel.getFriendLocationList().get(pointer).previewLoadEquipment(-1);
                }
            }
        } else if(Gdx.input.isTouched()){
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

    public boolean isEnemyAllDead(){
    	if(mapModel.getEnemyLocationList().size() == 0){
    		return true;
    	}
    	for (Entry<Vector2, Character> entry: mapModel.getEnemyLocationList().entrySet()){
    		if(! entry.getValue().isDead()){
    			return false;
    		}
    	}
    	return true;
    	
    }
}