package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Character.Character;
import Items.Item;

class BackpackEditorScreen implements Screen {

    private Game game;
    private Stage stage;
    private SpriteBatch batch;
    private TextButton backwardButton, saveButton;
    private Texture backgroundTexture;

    private ImageButton[] backpackMatrix, inventoryMatrix;
    private Table backpackTable, inventoryTable;
    private Character character;
    private Label inventoryItemInfoLabel, backpackItemInfoLabel;


    public BackpackEditorScreen(Game game, Character character) {
        this.game = game;
        this.character = character;
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("android/assets/EditorBackground.jpg"));
        batch = new SpriteBatch();

        backwardButton = new TextButton("<--", MainMenu.buttonStyle);
        backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
        backwardButton.setHeight(Gdx.graphics.getHeight() / 10);
        backwardButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
        backwardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CharacterEditorScreen(game, character));
                return true;
            }
        });
        stage.addActor(backwardButton);

        inventoryItemInfoLabel = new Label("", MainMenu.style);
        inventoryItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 2), (Gdx.graphics.getHeight() / 8));
        stage.addActor(inventoryItemInfoLabel);

        backpackItemInfoLabel = new Label("", MainMenu.style);
        backpackItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 4), (Gdx.graphics.getHeight() / 8));
        stage.addActor(backpackItemInfoLabel);

        inventoryTable = new Table();

        inventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 4 / 5);
        inventoryTable.setPosition(Gdx.graphics.getWidth() / 2 - 10 , Gdx.graphics.getHeight() * 1 / 8);

        inventoryMatrix = new ImageButton[PublicParameter.itemInventoryRow * PublicParameter.itemInventoryColumn ];
        buildInventoryMatrix();
        addInventoryMatrixListener();

        stage.addActor(inventoryTable);


        backpackTable = new Table();
        backpackTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/backpackBackground.png")))));
        backpackTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 1 / 3);
        backpackTable.setPosition( 0 , Gdx.graphics.getHeight() * 1 / 3);

        backpackMatrix = new ImageButton[PublicParameter.itemBackpackRow * PublicParameter.itemBackpackColumn];
        buildBackpackMatrix();
        addBackpackMatrixListener();

        stage.addActor(backpackTable);

    }



    private void buildBackpackMatrix() {
        for (int i = 0; i < PublicParameter.itemBackpackRow; i++) {
            for (int j = 0; j < PublicParameter.itemBackpackColumn; j++) {
                if ((i * PublicParameter.itemBackpackColumn) + j < character.getBackpack().size()) {
                    backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getBackpack().get(i * PublicParameter.itemBackpackColumn + j).getTexture())));
                } else {
                    backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
                }
                ImageButton tempButton = backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j];
                backpackTable.add(tempButton).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight).space(15);
            }
            backpackTable.row();
        }
    }

    private void buildInventoryMatrix() {
        for (int i = 0; i < PublicParameter.itemInventoryRow; i++) {
            for (int j = 0; j < PublicParameter.itemInventoryColumn; j++) {
                if ((i * PublicParameter.itemInventoryRow) + j < MainMenu.itemInventory.getItemPack().size) {
                    inventoryMatrix[(i * PublicParameter.itemInventoryRow) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(MainMenu.itemInventory.getItemPack().get(i * PublicParameter.itemInventoryRow + j).getTexture())));
                } else {
                    inventoryMatrix[(i * PublicParameter.itemInventoryRow) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
                }
                ImageButton tempButton = inventoryMatrix[(i * PublicParameter.itemInventoryRow) + j];
                inventoryTable.add(tempButton).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight).space(15);
            }
            inventoryTable.row();
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);
        stage.act();

        batch.begin();

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.draw();

        //stage.setDebugAll(true);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


    private void addBackpackMatrixListener() {
        for (int i = 0; i < character.getBackpack().size() ; i++){
            backpackMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    character.getBackpack().remove(getButton());
                    backpackTable.clearChildren();
                    buildBackpackMatrix();
                    addBackpackMatrixListener();
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    backpackItemInfoLabel.setText(character.getBackpack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    backpackItemInfoLabel.setText("");
                }
            });
        }
    }


    private void addInventoryMatrixListener() {
        for (int i = 0; i < MainMenu.itemInventory.getItemPack().size ; i++){
            inventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(MainMenu.itemInventory.getItemPack().get(getButton()).getLevel() == character.getLevel()) {
                        if(character.getBackpack().size() < PublicParameter.itemBackpackColumn * PublicParameter.itemBackpackRow){
                            Item item = new Item(MainMenu.itemInventory.getItemPack().get(getButton()).getItemType(),
                                    MainMenu.itemInventory.getItemPack().get(getButton()).getName(),
                                    MainMenu.itemInventory.getItemPack().get(getButton()).getLevel(),
                                    MainMenu.itemInventory.getItemPack().get(getButton()).getEnchantedAbility());
                            character.getBackpack().add(item);
                            backpackTable.clearChildren();
                            buildBackpackMatrix();
                            addBackpackMatrixListener();
                        }
                        else{
                            new Dialog("Error", MainMenu.skin, "dialog") {
                            }.text("Backpack is full").button("OK", true).key(Input.Keys.ENTER, true).show(stage);
                        }
                    }
                    else{
                        new Dialog("Error", MainMenu.skin, "dialog") {
                        }.text("Item level not same as character level").button("OK", true).key(Input.Keys.ENTER, true).show(stage);
                    }
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    inventoryItemInfoLabel.setText(MainMenu.itemInventory.getItemPack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    inventoryItemInfoLabel.setText("");
                }
            });
        }
    }

}
