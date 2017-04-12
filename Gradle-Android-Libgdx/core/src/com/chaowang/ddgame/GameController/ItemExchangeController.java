package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.GameView.GameItemExchangeScreen;

/**
 * controller for equipment
 * @author chao wang
 * @version 1.0
 */
public class ItemExchangeController {

    private GameItemExchangeScreen view;
    private Player player;
    private Character character;
    /**
     * constructor
     * @param view
     * @param model
     */
    public ItemExchangeController(GameItemExchangeScreen view, Player gamePlayer, Character model){
        this.view = view;
        player = gamePlayer;
        character = model;
    }

    /**
     * constructor
     * @param model
     */
    public ItemExchangeController(Character model){
        character = model;
    }


    /**
     * 
     * build matrix structure for backpack
     */
    public void buildPlayerBackpackMatrix() {
        for (int i = 0; i < player.getCharacter().getBackpack().size(); i++) {
            if (player.getCharacter().getBackpack().get(i).getLevel() > (int) Math.ceil(  player.getCharacter().getLevel() / 4.0 )) {
                player.getCharacter().getBackpack().remove(i);
            }
        }

        for (int i = 0; i < PublicParameter.ITEM_BACKPACK_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_BACKPACK_COLUMN; j++) {
                if ((i * PublicParameter.ITEM_BACKPACK_COLUMN) + j < player.getCharacter().getBackpack().size()) {
                    view.playerBackpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getBackpack().get(i * PublicParameter.ITEM_BACKPACK_COLUMN + j).getTexture())));
                } else {
                    view.playerBackpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
                ImageButton tempButton = view.playerBackpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j];
                view.playerBackpackTable.add(tempButton).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT).space(25);
            }
            view.playerBackpackTable.row();
        }
    }

    /**
     *
     * build matrix structure for backpack
     */
    public void buildNpcBackpackMatrix() {
        for (int i = 0; i < character.getBackpack().size(); i++) {
            if (character.getBackpack().get(i).getLevel() > (int) Math.ceil(  character.getLevel() / 4.0 )) {
                character.getBackpack().remove(i);
            }
        }

        for (int i = 0; i < PublicParameter.ITEM_BACKPACK_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_BACKPACK_COLUMN; j++) {
                if ((i * PublicParameter.ITEM_BACKPACK_COLUMN) + j < character.getBackpack().size()) {
                    view.NPCbackpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getBackpack().get(i * PublicParameter.ITEM_BACKPACK_COLUMN + j).getTexture())));
                } else {
                    view.NPCbackpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
                ImageButton tempButton = view.NPCbackpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j];
                view.NPCbackpackTable.add(tempButton).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT).space(25);
            }
            view.NPCbackpackTable.row();
        }
    }

    /**
     * listen any changes from backpack matrix
     */
    public void addPlayerBackpackMatrixListener() {
        for (int i = 0; i < player.getCharacter().getBackpack().size() ; i++) {
            view.playerBackpackMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (character.getBackpack().size() != 0 && !character.isDead()) {
                        exchangeItem(getButton());
                        view.playerBackpackTable.clearChildren();
                        buildPlayerBackpackMatrix();
                        addPlayerBackpackMatrixListener();
                        view.NPCbackpackTable.clearChildren();
                        buildNpcBackpackMatrix();
                        addNpcBackpackMatrixListener();
                    }
                    if(character.isDead()){
                        player.getCharacter().getBackpack().remove(getButton());
                        view.playerBackpackTable.clearChildren();
                        buildPlayerBackpackMatrix();
                        addPlayerBackpackMatrixListener();
                    }
                    return true;
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.backpackItemInfoLabel.setText(player.getCharacter().getBackpack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.backpackItemInfoLabel.setText("");
                }
            });
        }
    }
    /**
     * exchange Item
     * @param index
     */
    public void exchangeItem(int index ) {
        character.getBackpack().add(player.getCharacter().getBackpack().get(index));
        int randomNum =  (int)(Math.random() * ( character.getBackpack().size() -1 ));
        System.out.println("random number is "+ randomNum);
        Item tmp = character.getBackpack().get(randomNum);
        player.getCharacter().getBackpack().add(tmp);
        // remove item for both
        character.getBackpack().remove(randomNum);
        player.getCharacter().getBackpack().remove(index);
    }

    /**
     * listen any changes from backpack matrix
     */
    public void addNpcBackpackMatrixListener() {
        for (int i = 0; i < character.getBackpack().size() ; i++) {
            view.NPCbackpackMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (character.isDead()) {
                        if(player.getCharacter().backPackisFull()){
                            player.getCharacter().getBackpack().remove(0);
                        }
                        player.getCharacter().getBackpack().add(character.getBackpack().remove(getButton()));
                        view.playerBackpackTable.clearChildren();
                        buildPlayerBackpackMatrix();
                        addPlayerBackpackMatrixListener();
                        view.NPCbackpackTable.clearChildren();
                        buildNpcBackpackMatrix();
                        addNpcBackpackMatrixListener();
                    }
                    return true;
                }
                /**
                 * enter map
                 */
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.NPCItemInfoLabel.setText(character.getBackpack().get(getButton()).toString());
                }
                /**
                 * exit map
                 */
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.NPCItemInfoLabel.setText("");
                }
            });
        }
    }

}
