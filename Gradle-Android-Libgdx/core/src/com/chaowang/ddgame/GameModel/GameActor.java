package com.chaowang.ddgame.GameModel;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.GameModel.StrategyPattern.Strategy;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.PublicParameter;

/**
 * GameActor Transition
 * @author chao wang
 * @version 3.0
 */
public class GameActor implements Json.Serializable {

    Vector2 position;
    Rectangle bound;
    Character character;
    Strategy strategy;

    /**
     * constructor
     */
    public GameActor(){
        this.position = new Vector2();
        this.character = new Character();
        bound = new Rectangle(position.x, position.y, PublicParameter.MAP_PIXEL_SIZE  / 2, PublicParameter.MAP_PIXEL_SIZE  / 2);
    }

    
    /**
     * constructor
     * @param position  position of  player
     * @param character different types of character
     */
    public GameActor(Vector2 position, Character character){
        this.position = position;
        this.character = character;
        bound = new Rectangle(position.x, position.y, PublicParameter.MAP_PIXEL_SIZE  / 2, PublicParameter.MAP_PIXEL_SIZE  / 2);
    }

    /**
     * get player's position
     * @return
     */
    public Vector2 getPosition() {
        return position;
    }
    /**
     * set player's position
     * @param position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
        bound.set(position.x, position.y, PublicParameter.MAP_PIXEL_SIZE  / 2, PublicParameter.MAP_PIXEL_SIZE  / 2);
    }
    /**
     * get bound of player
     * @return
     */
    public Rectangle getBound() {
        return bound;
    }

    /**
     * set bound
     * @param bound
     */
    public void setBound(Rectangle bound) {
        this.bound = bound;
    }
    /**
     * get current character
     * @return
     */
    public Character getCharacter() {
        return character;
    }
    /**
     * set character
     * @param character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * set Strategy
     * @param strategy
     */
    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    /**
     * get Strategy
     * @@return  strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * execute SetupCamera Stategy
     */
    public void executeSetupCameraStategy(){
        this.strategy.setupCamera();
    }
    /**
     * render Interaction
     */
    public void renderInteraction() {
        this.strategy.renderInteraction();
    }
    /**
     * update Dialogue Stage
     * @param delta
     */
    public void updateDialogueStage(float delta){
        this.strategy.updateDialogueStage(delta);
    }
    /**
     * write file
     */
    @Override
    public void write(Json json) {
        json.writeValue("Position", position);
        json.writeValue("Bound", bound);
        json.writeValue("Character", character);
    }
    /**
     * read file
     */
    @Override
    public void read(Json json, JsonValue jsonData) {
        String context;
        context = jsonData.child.toString();
        if(context.contains("{")){
            context = context.substring(context.indexOf("{")-1);
            position = json.fromJson(Vector2.class, context);
            context = jsonData.child.next.toString();
            context = context.substring(context.indexOf("{")-1);
            bound = json.fromJson(Rectangle.class, context);
            context = jsonData.child.next.next.toString();
            context = context.substring(context.indexOf("{")-1);
            character = json.fromJson(Character.class, context);
        } else{
            context = jsonData.child.next.toString();
            context = context.substring(context.indexOf("{")-1);
            position = json.fromJson(Vector2.class, context);
            context = jsonData.child.next.next.toString();
            context = context.substring(context.indexOf("{")-1);
            bound = json.fromJson(Rectangle.class, context);
            context = jsonData.child.next.next.next.toString();
            context = context.substring(context.indexOf("{")-1);
            character = json.fromJson(Character.class, context);
        }

    }
}
