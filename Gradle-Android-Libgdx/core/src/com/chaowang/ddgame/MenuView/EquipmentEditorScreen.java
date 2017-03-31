package com.chaowang.ddgame.MenuView;

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

import com.chaowang.ddgame.MenuController.EquipmentController;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
/**
 * view for equipment editor 
 * @author chao wang
 * @version 1.0
 */
public class EquipmentEditorScreen implements Screen{

    private Game game;
    public Stage stage;
    private SpriteBatch batch;
    public TextButton backwardButton;
    private Texture backgroundTexture;

    public ImageButton[] backpackMatrix, equipmentMatrix;
    public Table backpackTable, equipmentTable;
    private Character character;
    private EquipmentController controller;
    public Label equipmentItemInfoLabel, backpackItemInfoLabel, characterInfoLabel;

    /**
     * constructor
     * @param game
     * @param character
     */
    public EquipmentEditorScreen (Game game, Character character) {
        this.game = game;
        this.character = character;
    }
    /**
     * show whole equipment editor on screen
     */
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
        batch = new SpriteBatch();

        controller = new EquipmentController(this, this.character);

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

        backpackItemInfoLabel = new Label("", MainMenuScreen.style);
        backpackItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 2), (Gdx.graphics.getHeight() / 8));
        stage.addActor(backpackItemInfoLabel);

        equipmentItemInfoLabel = new Label("", MainMenuScreen.style);
        equipmentItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 20), (Gdx.graphics.getHeight() / 8));
        stage.addActor(equipmentItemInfoLabel);

        characterInfoLabel = new Label(character.displayAllAtributes(), MainMenuScreen.style);
        characterInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 4), (Gdx.graphics.getHeight() / 3));
        stage.addActor(characterInfoLabel);

        backpackTable = new Table();
        backpackTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpackBackground.png")))));
        backpackTable.setSize(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() * 1 / 3);
        backpackTable.setPosition(Gdx.graphics.getWidth() * 3 / 7, Gdx.graphics.getHeight() * 1 / 3);

        backpackMatrix = new ImageButton[PublicParameter.ITEM_BACKPACK_ROW * PublicParameter.ITEM_BACKPACK_COLUMN];
        controller.buildBackpackMatrix();
        controller.addBackpackMatrixListener();

        stage.addActor(backpackTable);

        equipmentTable = new Table();

        equipmentTable.setSize(Gdx.graphics.getWidth() / 8 , Gdx.graphics.getHeight() * 1 / 3);
        equipmentTable.setPosition( Gdx.graphics.getWidth() / 20 , Gdx.graphics.getHeight() * 1 / 3);
        equipmentTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("MaleHumanUnderwear.png")))));

        equipmentMatrix = new ImageButton[PublicParameter.ITEM_TYPE_COUNT];
        controller.buildEquipmentMatrix();
        controller.addEquipmentMatrixListener();

        stage.addActor(equipmentTable);

    }
    /**
     * put image on background
     */
    @Override
    public void render(float delta) {
//
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
