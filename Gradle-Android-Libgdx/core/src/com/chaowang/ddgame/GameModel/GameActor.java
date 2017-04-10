package com.chaowang.ddgame.GameModel;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.GameModel.StrategyPattern.Strategy;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.PublicParameter;

/**
 * Created by Chao on 06/04/2017.
 */

public class GameActor {

    Vector2 position;
    Rectangle bound;
    Character character;
    Strategy strategy;

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
}
