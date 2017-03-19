package com.chaowang.ddgame.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * class for DialogBox
 * @author chao wang
 * @version 2.0
 */
public class DialogBox extends Table{

    private String targetText = "";
    private float animTimer = 0f;
    private float animationTotalTime = 0f;
    private float TIME_PER_CHARACTER = 0.05f;
    private STATE state = STATE.IDLE;

    private Label textLabel;

    private enum STATE {
        ANIMATING,
        IDLE,
        ;
    }
    /**
     * constructor
     * @param skin
     */
    public DialogBox(Skin skin){
        super(skin);
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpackBackground.png")))));
        //this.setBackground("dialogbox");
        textLabel = new Label("\n", skin);
        this.add(textLabel).expand().align(Align.left).pad(5f);
    }
    /**
     * texture
     * @param text
     */
    public void animateText(String text){
        targetText = text;
        animationTotalTime = text.length()*TIME_PER_CHARACTER;
        state = STATE.ANIMATING;
        animTimer = 0f ;
    }
    /**
     * decide the state is finished or not
     * @return
     */
    public boolean isFinished(){
        if(state == STATE.IDLE) {
            return true;
        } else{
            return false;
        }
    }
    /**
     * set text value
     * @param text
     */
    private void setText(String text){
        if(! text.contains("\n")){
            text += "\n";
        }
        this.textLabel.setText(text);
    }
    /**
     * implement action
     */
    public void act(float delta){
        super.act(delta);
        if(state == STATE.ANIMATING){
            animTimer += delta;
            if(animTimer > animationTotalTime){
                state = STATE.IDLE;
                animTimer = animationTotalTime;
            }
            String actuallyDisplayText = "";
            int chatactersToDisplay = (int)(animTimer/animationTotalTime) *targetText.length();
            for (int i = 0; i < chatactersToDisplay; i++){
                actuallyDisplayText += targetText.charAt(i);
            }
            if(! actuallyDisplayText.equals(textLabel.getText().toString())){
                setText(actuallyDisplayText);
            }
        }
    }

    public float getPrefWidth(){
        return 200f;
    }
}
