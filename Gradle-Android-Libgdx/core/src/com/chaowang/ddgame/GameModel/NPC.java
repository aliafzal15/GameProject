package com.chaowang.ddgame.GameModel;

import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import java.util.Queue;

/**
 * Created by Chao on 06/04/2017.
 */

public class NPC extends GameActor{

    private boolean isFriendly;

    public NPC(Vector2 position, Character character, boolean isFriendly) {
        super(position, character);
        this.isFriendly = isFriendly;
        if(isFriendly){
            this.bound.width = PublicParameter.MAP_PIXEL_SIZE  / 3;
            this.bound.height = PublicParameter.MAP_PIXEL_SIZE  / 3;
        } else{
            this.bound.width = PublicParameter.MAP_PIXEL_SIZE  / 2;
            this.bound.height = PublicParameter.MAP_PIXEL_SIZE  / 2;
        }
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
    	MainMenuScreen.logArea.appendText(this.character.getName() + "becomes "+ friendly +" friendly");
        isFriendly = friendly;
    }

    /**
     * change of location, and bound
     * @param dx
     * @param dy
     */
    public void move (float dx, float dy ){
        position.x += dx * 2f;
        position.y += dy * 2f;
        if(isFriendly){
            bound.set(position.x, position.y, PublicParameter.MAP_PIXEL_SIZE  / 3, PublicParameter.MAP_PIXEL_SIZE  / 3);
        } else{
            bound.set(position.x, position.y, PublicParameter.MAP_PIXEL_SIZE  / 2, PublicParameter.MAP_PIXEL_SIZE  / 2);
        }
    }
    
    /**
     * set player's position
     * @param position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
        if(isFriendly){
            bound.set(position.x, position.y, PublicParameter.MAP_PIXEL_SIZE  / 3, PublicParameter.MAP_PIXEL_SIZE  / 3);
        } else{
            bound.set(position.x, position.y, PublicParameter.MAP_PIXEL_SIZE  / 2, PublicParameter.MAP_PIXEL_SIZE  / 2);
        }
    }
}