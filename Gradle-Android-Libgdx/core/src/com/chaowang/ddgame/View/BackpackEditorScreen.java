package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.PublicParameter;

import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.MenuController.BackpackController;
/**
 * view for backpack editor 
 * @author chao wang
 * @version 1.0
 */
public class BackpackEditorScreen implements Screen {

    private Game game;
    public Stage stage;
    private SpriteBatch batch;
    public TextButton backwardButton;
    private Texture backgroundTexture;

    public ImageButton[] backpackMatrix, inventoryMatrix;
    public Table backpackTable, inventoryTable;
    private Character character;
    private BackpackController controller;
    public Label inventoryItemInfoLabel, backpackItemInfoLabel;

    /**
     * constructor
     * @param game
     * @param character
     */
    public BackpackEditorScreen(Game game, Character character) {
        this.game = game;
        this.character = character;
    }
    /**
     * show whole back pack editor view on screen
     */
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
        batch = new SpriteBatch();

        controller = new BackpackController(this, this.character);
        backwardButton = new TextButton("<--", MainMenuScreen.buttonStyle);
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

        inventoryItemInfoLabel = new Label("", MainMenuScreen.style);
        inventoryItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 2), (Gdx.graphics.getHeight() / 8));
        stage.addActor(inventoryItemInfoLabel);

        backpackItemInfoLabel = new Label("", MainMenuScreen.style);
        backpackItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 4), (Gdx.graphics.getHeight() / 8));
        stage.addActor(backpackItemInfoLabel);

        inventoryTable = new Table();

        inventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 4 / 5);
        inventoryTable.setPosition(Gdx.graphics.getWidth() / 2 - 10 , Gdx.graphics.getHeight() * 1 / 8);

        inventoryMatrix = new ImageButton[PublicParameter.ITEM_INVENTORY_ROW * PublicParameter.ITEM_INVENTORY_COLUMN ];
        controller.buildInventoryMatrix();
        controller.addInventoryMatrixListener();

        stage.addActor(inventoryTable);


        backpackTable = new Table();
        backpackTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpackBackground.png")))));
        backpackTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 1 / 3);
        backpackTable.setPosition( 0 , Gdx.graphics.getHeight() * 1 / 3);

        backpackMatrix = new ImageButton[PublicParameter.ITEM_BACKPACK_ROW * PublicParameter.ITEM_BACKPACK_COLUMN];
        controller.buildBackpackMatrix();
        controller.addBackpackMatrixListener();

        stage.addActor(backpackTable);

    }

    /**
     * put image on background
     */
    @Override
    public void render(float delta) {

//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    /**
     * update the back pack editor view
     */
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

}
