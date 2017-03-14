package com.chaowang.ddgame.PlayModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.PublicParameter;

public class Actor {
    private Vector2 position;
    private Rectangle bound;
    private Character character;

    private static final int COL = 4;
    private static final int ROW = 4;
    private Animation animation;
    private Texture playerTexture;
    private TextureRegion[]  frames;
    private TextureRegion currentFrame;


    public Actor(Vector2 position, Character character){
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

    public Rectangle getBound() {
        return bound;
    }

    public void move (float dx, float dy ){
        position.x += dx * 2f;
        position.y += dy * 2f;
        bound.set(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public Animation getAnimation() {
        return animation;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setBound(Rectangle bound) {
        this.bound = bound;
    }
}