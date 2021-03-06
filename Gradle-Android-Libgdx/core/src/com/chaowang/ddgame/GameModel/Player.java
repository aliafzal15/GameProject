package com.chaowang.ddgame.GameModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;

/**
 * the model class for Player
 * @author chao wang
 * @version 2.0
 */
public class Player extends GameActor implements Json.Serializable{


    private static final int COL = 4;
    private static final int ROW = 4;
    private Animation animation;
    private Texture playerTexture;
    private TextureRegion[]  frames;
    private TextureRegion currentFrame;
    // for strategy

    /**
     * constructor
     * @param position  position of  player
     * @param character different types of character
     */
    public Player(){
    	super();
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
     * constructor
     * @param position  position of  player
     * @param character different types of character
     */
    public Player(Vector2 position, Character character){
        super(position,character);
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
     * set player's position
     * @param position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
        bound.set(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
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
    /**
     * change the Facing Direction in screen
     * @param target
     */
    public void changeFacingDirection(Vector2 target){
        if(target.x > position.x && target.y > position.y){
            if( (target.x - position.x) < (target.y - position.y)){
                setCurrentFrame((TextureRegion)animation.getKeyFrame(12));
            }else{
                setCurrentFrame((TextureRegion)animation.getKeyFrame(8));
            }
        } else if( target.x < position.x && target.y > position.y){
            if( (position.x - target.x) < (target.y - position.y)){
                setCurrentFrame((TextureRegion)animation.getKeyFrame(12));
            }else{
                setCurrentFrame((TextureRegion)animation.getKeyFrame(4));
            }
        } else if(target.x < position.x && target.y < position.y){
            if( (position.x - target.x) < (position.y - target.y)){
                setCurrentFrame((TextureRegion)animation.getKeyFrame(0));
            }else{
                setCurrentFrame((TextureRegion)animation.getKeyFrame(4));
            }
        } else if (target.x > position.x && target.y < position.y){
            if( (target.x - position.x) < (position.y - target.y)){
                setCurrentFrame((TextureRegion)animation.getKeyFrame(0));
            }else{
                setCurrentFrame((TextureRegion)animation.getKeyFrame(8));
            }
        }
    }
    /**
     * write file
     */
    @Override
    public void write(Json json) {
        super.write(json);
    }
    /**
     * read file
     */
    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
    }
}