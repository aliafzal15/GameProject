package com.chaowang.ddgame.GameModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.PublicParameter;
/**
 * the model class for Player
 * @author chao wang
 * @version 2.0
 */
public class Player extends Actor{
    private Vector2 position;
    private Rectangle bound;
    private Character character;

    private static final int COL = 4;
    private static final int ROW = 4;
    private Animation animation;
    private Texture playerTexture;
    private TextureRegion[]  frames;
    private TextureRegion currentFrame;
    
    /**
     * constructor
     * @param position  position of  player
     * @param character different types of character
     */
    public Player(Vector2 position, Character character){
        this.position = position;
        this.character = character;
        playerTexture = new Texture(Gdx.files.internal("arshes.png"));
        TextureRegion[][] tmp = TextureRegion.split(playerTexture,playerTexture.getWidth() / COL ,playerTexture.getHeight() / ROW);
        frames = new TextureRegion[COL * ROW];
        int index = 0;
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++){
                frames[index++]= tmp[i][j];
            }
        }
        animation = new Animation(1, frames);
        currentFrame = (TextureRegion) animation.getKeyFrame(0);
        bound = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }
    /**
     * get bound of player
     * @return
     */
    public Rectangle getBound() {
        return bound;
    }
    /**
     * change of location
     * @param dx
     * @param dy
     */
    public void move (float dx, float dy ){
        position.x += dx * 2f;
        position.y += dy * 2f;
        bound.set(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }
    /**
     * get current frame
     * @return
     */
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }
    /**
     * set current frame
     * @param currentFrame
     */
    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }
    /**
     * get animation
     * @return
     */
    public Animation getAnimation() {
        return animation;
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
        bound.set(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
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
     * get player texture
     * @return
     */
    public Texture getPlayerTexture() {
        return playerTexture;
    }
    /**
     * set player texture
     * @param playerTexture
     */
    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }
}