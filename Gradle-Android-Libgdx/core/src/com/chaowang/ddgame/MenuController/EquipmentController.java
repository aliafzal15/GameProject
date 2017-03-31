package com.chaowang.ddgame.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.MenuView.EquipmentEditorScreen;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import java.util.HashMap;
import java.util.Iterator;

import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.ItemModel.Item;
/**
 * controller for equipment
 * @author chao wang
 * @version 1.0
 */
public class EquipmentController {

    private EquipmentEditorScreen view;
    private Character character;
    /**
     * constructor
     * @param view
     * @param model
     */
    public EquipmentController(EquipmentEditorScreen view, Character model){
        this.view = view;
        character = model;
    }
    
    /**
     * constructor
     * @param model
     */
    public EquipmentController(Character model){
        character = model;
    }


    /**
     * 
     * build matrix structure for backpack
     */
    public void buildBackpackMatrix() {
        for (int i = 0; i < character.getBackpack().size(); i++) {
            if (character.getBackpack().get(i).getLevel() > (int) Math.ceil(  character.getLevel() / 4.0 )) {
                character.getBackpack().remove(i);
            }
        }

        for (int i = 0; i < PublicParameter.ITEM_BACKPACK_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_BACKPACK_COLUMN; j++) {
                if ((i * PublicParameter.ITEM_BACKPACK_COLUMN) + j < character.getBackpack().size()) {
                    view.backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getBackpack().get(i * PublicParameter.ITEM_BACKPACK_COLUMN + j).getTexture())));
                } else {
                    view.backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
                ImageButton tempButton = view.backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j];
                view.backpackTable.add(tempButton).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT).space(25);
            }
            view.backpackTable.row();
        }
    }
    /**
     * build matrix structure for equipment
     */
    public void buildEquipmentMatrix() {
        // check if equipment has higher level item need to remove.
        for(Iterator<HashMap.Entry<Item.ItemType, Item>> it = character.getEquipment().entrySet().iterator(); it.hasNext(); ) {
            HashMap.Entry<Item.ItemType, Item> entry = it.next();
            if(entry.getValue().getLevel() > (int) Math.ceil(  character.getLevel() / 4.0 )) {
                character.getBackpack().add(entry.getValue());
                it.remove();
            }
        }

        view.equipmentTable.add(new Label("", MainMenuScreen.style));
        if(character.getEquipment().get(Item.ItemType.HELMET) != null){
            view.equipmentMatrix[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.HELMET).getTexture())));
        }else{
            view.equipmentMatrix[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[0]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        view.equipmentTable.row();

        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        if(character.getEquipment().get(Item.ItemType.ARMOR) != null){
            view.equipmentMatrix[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.ARMOR).getTexture())));
        }else{
            view.equipmentMatrix[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[1]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        view.equipmentTable.row();

        if(character.getEquipment().get(Item.ItemType.WEAPON) != null){
            view.equipmentMatrix[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.WEAPON).getTexture())));
        }else{
            view.equipmentMatrix[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[2]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        if(character.getEquipment().get(Item.ItemType.BELT) != null){
            view.equipmentMatrix[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.BELT).getTexture())));;
        }else{
            view.equipmentMatrix[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[3]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        if(character.getEquipment().get(Item.ItemType.SHIELD) != null){
            view.equipmentMatrix[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.SHIELD).getTexture())));
        }else{
            view.equipmentMatrix[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[4]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        view.equipmentTable.row();

        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        if(character.getEquipment().get(Item.ItemType.BOOTS) != null){
            view.equipmentMatrix[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.BOOTS).getTexture())));
        }else{
            view.equipmentMatrix[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[5]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        if(character.getEquipment().get(Item.ItemType.RING) != null){
            view.equipmentMatrix[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.RING).getTexture())));
        }else{
            view.equipmentMatrix[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[6]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);

    }

    /**
     * listen any changes from backpack matrix
     */
    public void addBackpackMatrixListener() {
        for (int i = 0; i < character.getBackpack().size() ; i++) {
            view.backpackMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (character.getBackpack().get(button).getLevel() <= (int) Math.ceil(  character.getLevel() / 4.0 )
                            && !character.getEquipment().containsKey(character.getBackpack().get(getButton()).getItemType())) {
                        loadEquipment(getButton());
                        view.characterInfoLabel.setText(character.displayAllAtributes());
                        view.equipmentTable.clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        view.backpackTable.clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    else{
                        new Dialog("Error", MainMenuScreen.skin, "dialog") {
                        }.text("Item level to high, or cannot equip").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
                    }
                    return true;
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.backpackItemInfoLabel.setText(character.getBackpack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.backpackItemInfoLabel.setText("");
                }
            });
        }
    }

    /**
     * listen any change from equipment matrix
     */
    public void addEquipmentMatrixListener() {
        for (int i = 0; i < view.equipmentMatrix.length ; i++){
            view.equipmentMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(character.getEquipment().containsKey(Item.ItemType.getItemType(getButton()))){
                        character.getBackpack().add(character.removeEquipment(Item.ItemType.getItemType(getButton())));
                        view.characterInfoLabel.setText(character.displayAllAtributes());
                        view.equipmentTable.clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        view.backpackTable.clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if(character.getEquipment().get(Item.ItemType.getItemType(getButton())) != null){
                        view.equipmentItemInfoLabel.setText(character.getEquipment().get(Item.ItemType.getItemType(getButton())).toString());
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.equipmentItemInfoLabel.setText("");
                }
            });
        }
    }

    /**
     * remove backpack item at index, and load it to character equipment
     * @param index index of backpack 1-10
     */
	public void loadEquipment(int index) {
		Item itemtmp = character.getBackpack().remove(index);
		character.loadEquipment(itemtmp);
	}

}
