package com.chaowang.ddgame.GameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chao on 18/03/2017.
 */

public class OptionBox extends Table {

    private int selectorIndex = 0;
    private List<Image> arrows = new ArrayList<Image>();
    private List<Label> options = new ArrayList<Label>();
    private Table uiContainer;

    public OptionBox(Skin skin){
        super(skin);
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gameUI/dialogBox.png")))));
        uiContainer = new Table();
        this.add(uiContainer).expand().align(Align.left).pad(12f);
    }

    public void addOption(String option){
        Label optionLabel = new Label(option, this.getSkin());
        options.add(optionLabel);
        Image selectorLabel  = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gameUI/arrow.png")))));
        selectorLabel.setScaling(Scaling.none);
        arrows.add(selectorLabel);
        selectorLabel.setVisible(false);

        if (selectorLabel == arrows.get(selectorIndex)) {
            selectorLabel.setVisible(true);
        }

        uiContainer.add(selectorLabel).expand().align(Align.left).pad(5f);
        uiContainer.add(optionLabel).expand().align(Align.left).space(5f);
        uiContainer.row();

        calcArrowVisibility();
    }

    private void calcArrowVisibility(){
        for (int i= 0 ; i < arrows.size(); i++){
            if (i == selectorIndex){
                arrows.get(i).setVisible(true);
            } else{
                arrows.get(i).setVisible(false);
            }
        }
    }

    public void moveUp() {
        selectorIndex--;
        if (selectorIndex < 0) {
            selectorIndex = 0;
        }
        calcArrowVisibility();
    }

    public void moveDown(){
        selectorIndex ++;
        if(selectorIndex >= options.size() ){
            selectorIndex = options.size()-1 ;
        }
        calcArrowVisibility();

    }

    public void clearChoices(){
        uiContainer.clearChildren();
        arrows.clear();
        options.clear();
        selectorIndex = 0;
    }

    public int getIndex() {
        return selectorIndex;
    }
}
