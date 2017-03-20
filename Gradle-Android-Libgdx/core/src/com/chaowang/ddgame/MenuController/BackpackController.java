package com.chaowang.ddgame.MenuController;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.View.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.View.BackpackEditorScreen;

import com.chaowang.ddgame.ItemModel.Item;
import com.chaowang.ddgame.CharacterModel.Character;
/**
 * controller for backpack
 * @author chao wang
 * @version 1.0
 */
public class BackpackController {

    private Character character;
    private BackpackEditorScreen view;
    /**
     * constructor
     * @param view
     * @param model
     */
    public BackpackController(BackpackEditorScreen view, Character model){
        character = model;
        this.view = view;
    }
    /**
     * backpack matrix
     */
    public void buildBackpackMatrix() {
        // check if backpack has higher level item need to remove.
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
                view.backpackTable.add(tempButton).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT).space(15);
            }
            view.backpackTable.row();
        }
    }
    /**
     * inventory matrix
     */
    public void buildInventoryMatrix() {
        for (int i = 0; i < PublicParameter.ITEM_INVENTORY_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_INVENTORY_COLUMN; j++) {
                if ((i * PublicParameter.ITEM_INVENTORY_ROW) + j < MainMenuScreen.itemInventory.getItemPack().size) {
                    view.inventoryMatrix[(i * PublicParameter.ITEM_INVENTORY_ROW) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(MainMenuScreen.itemInventory.getItemPack().get(i * PublicParameter.ITEM_INVENTORY_ROW + j).getTexture())));
                } else {
                    view.inventoryMatrix[(i * PublicParameter.ITEM_INVENTORY_ROW) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
                ImageButton tempButton = view.inventoryMatrix[(i * PublicParameter.ITEM_INVENTORY_ROW) + j];
                view.inventoryTable.add(tempButton).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT).space(15);
            }
            view.inventoryTable.row();
        }
    }
    /**
     * back pack listener
     */
    public void addBackpackMatrixListener() {
        for (int i = 0; i < character.getBackpack().size() ; i++){
            view.backpackMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    character.getBackpack().remove(getButton());
                    view.backpackTable.clearChildren();
                    buildBackpackMatrix();
                    addBackpackMatrixListener();
                    return true;
                }

                @Override
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
     * inventory matrix listener
     */
    public void addInventoryMatrixListener() {
        for (int i = 0; i < MainMenuScreen.itemInventory.getItemPack().size ; i++){
            view.inventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(MainMenuScreen.itemInventory.getItemPack().get(getButton()).getLevel() <= (1 + character.getLevel()) / 2) {
                        if(character.getBackpack().size() < PublicParameter.ITEM_BACKPACK_COLUMN * PublicParameter.ITEM_BACKPACK_ROW){
                            Item item = new Item(MainMenuScreen.itemInventory.getItemPack().get(getButton()).getItemType(),
                                    MainMenuScreen.itemInventory.getItemPack().get(getButton()).getName(),
                                    MainMenuScreen.itemInventory.getItemPack().get(getButton()).getLevel(),
                                    MainMenuScreen.itemInventory.getItemPack().get(getButton()).getEnchantedAbility());
                            character.getBackpack().add(item);
                            view.backpackTable.clearChildren();
                            buildBackpackMatrix();
                            addBackpackMatrixListener();
                        }
                        else{
                            new Dialog("Error", MainMenuScreen.skin, "dialog") {
                            }.text("Backpack is full").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
                        }
                    }
                    else{
                        new Dialog("Error", MainMenuScreen.skin, "dialog") {
                        }.text("Item level not same as character level").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
                    }
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.inventoryItemInfoLabel.setText(MainMenuScreen.itemInventory.getItemPack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.inventoryItemInfoLabel.setText("");
                }
            });
        }
    }

}
