package com.chaowang.ddgame.GameModel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import java.util.Queue;

/**
 * NPC Transition
 * @author chao wang
 * @version 3.0
 */
public class NPC extends GameActor implements Json.Serializable{

    private boolean isFriendly;
    
    /**
     * construct
     * @param position
     * @param character
     * @param isFriendly
     */
    public NPC() {
        super();
        this.isFriendly = true;
        if(isFriendly){
            this.bound.width = PublicParameter.MAP_PIXEL_SIZE  / 3;
            this.bound.height = PublicParameter.MAP_PIXEL_SIZE  / 3;
        } else{
            this.bound.width = PublicParameter.MAP_PIXEL_SIZE  / 2;
            this.bound.height = PublicParameter.MAP_PIXEL_SIZE  / 2;
        }
    }
    
    /**
     * construct
     * @param position
     * @param character
     * @param isFriendly
     */
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
    /**
     * decide Friendly or not
     * @return
     */
    public boolean isFriendly() {
        return isFriendly;
    }
    /**
     * set Friendly value
     * @param friendly
     */
    public void setFriendly(boolean friendly) {
    	MainMenuScreen.logArea.appendText(this.character.getName() + " becomes "+ friendly +" friendly\n");
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
    /**
     * write file
     */
    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("IsFriendly", isFriendly);
    }
    /**
     * read file
     */
    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        isFriendly = jsonData.child.next.next.next.next.asBoolean();
    }
    /**
     * convert to String type
     */
    @Override
    public String toString(){
		return this.position.toString()+"|"+this.bound.toString()+"|"+this.character.toString()+"|isFriendly:"+isFriendly;
    	
    }
}