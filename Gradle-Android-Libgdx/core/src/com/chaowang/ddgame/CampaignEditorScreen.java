package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Campaign.Campaign;
import Controller.CampaignController;
/**
 * view for campaign editor 
 * @author chao wang
 * @version 1.0
 */
public class CampaignEditorScreen implements Screen {

    private Game game;
    public Stage stage;
    private SpriteBatch batch;
    public TextButton backwardButton, mapSaveButton;
    private Texture backgroundTexture;

    public Label[] campaignMatrix, mapInventoryMatrix, campaignInventoryMatrix;
    public Table campaignTable, mapInventoryTable, campaignInventoryTable, inputTable;
    public Label inventoryMapInfoLabel, inventoryCampaignInfoLabel;
    public TextField nameField;
    private Campaign campaign;
    private CampaignController campaignController;


     /**
     * constructor
     * @param game
     */
    public CampaignEditorScreen(Game game) {
        this.game = game;
    }
    /**
     * show whole campaign view on screen
     */
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
        batch = new SpriteBatch();
        
        campaign = new Campaign();
        campaignController = new CampaignController(this.campaign, this);

        backwardButton = new TextButton("<--", MainMenuScreen.buttonStyle);
        backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
        backwardButton.setHeight(Gdx.graphics.getHeight() / 10);
        backwardButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
        backwardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });
        stage.addActor(backwardButton);

        inventoryMapInfoLabel = new Label("", MainMenuScreen.style);
        inventoryMapInfoLabel.setPosition((Gdx.graphics.getWidth() * 3 / 4), (Gdx.graphics.getHeight() / 4));
        stage.addActor(inventoryMapInfoLabel);

        inventoryCampaignInfoLabel = new Label("", MainMenuScreen.style);
        inventoryCampaignInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 4), (Gdx.graphics.getHeight() / 4));
        stage.addActor(inventoryCampaignInfoLabel);

        mapInventoryTable = new Table();

        mapInventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 7 / 10);
        mapInventoryTable.setPosition(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() * 1 / 4);
        mapInventoryTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("scrollPaper.png")))));
        mapInventoryMatrix = new Label[PublicParameter.mapInventoryRow * PublicParameter.mapInventoryColumn ];
        campaignController.buildMapInventoryMatrix();
        campaignController.addMapInventoryMatrixListener();

        stage.addActor(mapInventoryTable);
        
        campaignInventoryTable = new Table();

        campaignInventoryTable.setSize(Gdx.graphics.getWidth() * 9 / 10 , Gdx.graphics.getHeight() * 1 / 5);
        campaignInventoryTable.setPosition(10 , Gdx.graphics.getHeight() * 1 / 50);
        campaignInventoryTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpackBackground.png")))));

        campaignInventoryMatrix = new Label[PublicParameter.campaignInventorySize];
        campaignController.buildCampaignInventoryMatrix();
        campaignController.addCampaignInventoryMatrixListener();

        stage.addActor(campaignInventoryTable);

        campaignTable = new Table();
        campaignTable.setSize(Gdx.graphics.getWidth() *1 / 2, Gdx.graphics.getHeight() * 2 / 3);
        campaignTable.setPosition(10 , Gdx.graphics.getHeight() * 1 / 5);

        campaignMatrix = new Label[PublicParameter.mapInventoryRow * PublicParameter.mapInventoryColumn];
        campaignController.buildCampaignMatrix();
        campaignController.addCampaignMatrixListener();

        stage.addActor(campaignTable);

        inputTable = new Table();
        inputTable.setSize(Gdx.graphics.getWidth() / 4 , Gdx.graphics.getHeight() * 1 / 6);
        inputTable.setPosition( Gdx.graphics.getWidth() * 1 / 2 , Gdx.graphics.getHeight() * 8 / 10);
        inputTable.add(new Label("name", MainMenuScreen.style));
        nameField = new TextField("", MainMenuScreen.skin);
        inputTable.add(nameField);
        stage.addActor(inputTable);

        mapSaveButton = new TextButton("SAVE", MainMenuScreen.buttonStyle);
        mapSaveButton.setWidth(Gdx.graphics.getWidth() / 9);
        mapSaveButton.setHeight(Gdx.graphics.getHeight() / 9);
        mapSaveButton.setPosition((Gdx.graphics.getWidth() * 1 / 2) + 30, Gdx.graphics.getHeight() * 8 / 10 - 30);
        mapSaveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                campaignController.controlSaveButton();
                return true;
            }
        });
        stage.addActor(mapSaveButton);

    }
    /**
     * put image on background
     */
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
    /**
     * update the campaign editor view
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
