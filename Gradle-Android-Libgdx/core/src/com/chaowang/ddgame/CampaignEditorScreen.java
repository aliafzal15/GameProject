package com.chaowang.ddgame;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Campaign.Campaign;
import Character.Character;
import Items.Item;
import Map.Map;

public class CampaignEditorScreen implements Screen {

    private Game game;
    private Stage stage;
    private SpriteBatch batch;
    private TextButton backwardButton, mapSaveButton;
    private Texture backgroundTexture;

    private Label[] campaignMatrix, mapInventoryMatrix, campaignInventoryMatrix;
    private Table campaignTable, mapInventoryTable, campaignInventoryTable, inputTable;
    private Map map;
    private Array<Map> mapList;
    private Campaign campaign;
    private Label inventoryMapInfoLabel, inventoryCampaignInfoLabel;
    private TextField nameField;


    public CampaignEditorScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("android/assets/EditorBackground.jpg"));
        batch = new SpriteBatch();
        
        mapList = new Array<Map>();
        campaign = new Campaign();

        backwardButton = new TextButton("<--", MainMenu.buttonStyle);
        backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
        backwardButton.setHeight(Gdx.graphics.getHeight() / 10);
        backwardButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
        backwardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game));
                return true;
            }
        });
        stage.addActor(backwardButton);

        inventoryMapInfoLabel = new Label("", MainMenu.style);
        inventoryMapInfoLabel.setPosition((Gdx.graphics.getWidth() * 3 / 4), (Gdx.graphics.getHeight() / 4));
        stage.addActor(inventoryMapInfoLabel);

        inventoryCampaignInfoLabel = new Label("", MainMenu.style);
        inventoryCampaignInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 4), (Gdx.graphics.getHeight() / 4));
        stage.addActor(inventoryCampaignInfoLabel);

        mapInventoryTable = new Table();

        mapInventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 7 / 10);
        mapInventoryTable.setPosition(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() * 1 / 4);
        mapInventoryTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/scrollPaper.png")))));
        mapInventoryMatrix = new Label[PublicParameter.mapInventoryRow * PublicParameter.mapInventoryColumn ];
        buildMapInventoryMatrix();
        addMapInventoryMatrixListener();

        stage.addActor(mapInventoryTable);
        
        campaignInventoryTable = new Table();

        campaignInventoryTable.setSize(Gdx.graphics.getWidth() * 9 / 10 , Gdx.graphics.getHeight() * 1 / 5);
        campaignInventoryTable.setPosition(10 , Gdx.graphics.getHeight() * 1 / 50);
        campaignInventoryTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/backpackBackground.png")))));

        campaignInventoryMatrix = new Label[PublicParameter.campaignInventorySize];
        buildCampaignInventoryMatrix();
        addCampaignInventoryMatrixListener();

        stage.addActor(campaignInventoryTable);

        campaignTable = new Table();
        campaignTable.setSize(Gdx.graphics.getWidth() *1 / 2, Gdx.graphics.getHeight() * 2 / 3);
        campaignTable.setPosition(10 , Gdx.graphics.getHeight() * 1 / 5);

        campaignMatrix = new Label[PublicParameter.mapInventoryRow * PublicParameter.mapInventoryColumn];
        buildCampaignMatrix();
        addCampaignMatrixListener();

        stage.addActor(campaignTable);

        inputTable = new Table();
        inputTable.setSize(Gdx.graphics.getWidth() / 4 , Gdx.graphics.getHeight() * 1 / 6);
        inputTable.setPosition( Gdx.graphics.getWidth() * 1 / 2 , Gdx.graphics.getHeight() * 8 / 10);
        inputTable.add(new Label("name", MainMenu.style));
        nameField = new TextField("", MainMenu.skin);
        inputTable.add(nameField);
        stage.addActor(inputTable);

        mapSaveButton = new TextButton("SAVE", MainMenu.buttonStyle);
        mapSaveButton.setWidth(Gdx.graphics.getWidth() / 9);
        mapSaveButton.setHeight(Gdx.graphics.getHeight() / 9);
        mapSaveButton.setPosition((Gdx.graphics.getWidth() * 1 / 2) + 30, Gdx.graphics.getHeight() * 8 / 10 - 30);
        mapSaveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (! nameField.getText().equals("") && mapList.size > 2 ) {
                    campaign.setName(nameField.getText());
                    campaign.setMapPack(mapList);
                    MainMenu.campaignInventory.addToCampaignPack(campaign);
                    MainMenu.campaignInventory.saveToFile();
                    nameField.setText("");
                    mapList.clear();
                    campaignTable.clearChildren();
                    campaignInventoryTable.clearChildren();
                    buildCampaignInventoryMatrix();
                    addCampaignInventoryMatrixListener();
                }
                else{
                    new Dialog("Error", MainMenu.skin, "dialog") {
                    }.text("Campaign name cannot be empty, map number > 1").button("OK", true).key(Keys.ENTER, true)
                            .show(stage);
                }
                return true;
            }
        });
        stage.addActor(mapSaveButton);

    }



    private void buildCampaignMatrix() {
    	Label tmpLabel;
        for (int i = 0; i < PublicParameter.mapInventoryRow; i++) {
            for (int j = 0; j < PublicParameter.mapInventoryRow; j++) {
                if ((i * PublicParameter.mapInventoryRow) + j < mapList.size) {
                	campaignMatrix[(i * PublicParameter.mapInventoryRow) + j] = new Label(mapList.get((i * PublicParameter.mapInventoryRow) + j).toString()+"--", MainMenu.skin);
                } else {
                	campaignMatrix[(i * PublicParameter.mapInventoryRow) + j] = new Label("", MainMenu.skin);
                }
                campaignMatrix[(i * PublicParameter.mapInventoryRow) + j].setAlignment(Align.center);
                tmpLabel = campaignMatrix[(i * PublicParameter.mapInventoryRow) + j];
                campaignTable.add(tmpLabel).width(PublicParameter.mapCellWidth).height(PublicParameter.mapCellHeight).space(18);
            }
            campaignTable.row();
        }
    }

    private void buildMapInventoryMatrix() {
    	Label tmpLabel;
        BitmapFont font = new BitmapFont(Gdx.files.internal("android/assets/Style/default.fnt"),false);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
        for (int i = 0; i < PublicParameter.mapInventoryRow; i++) {
            for (int j = 0; j < PublicParameter.mapInventoryRow; j++) {
                if ((i * PublicParameter.mapInventoryRow) + j < MainMenu.mapInventory.getMapPack().size) {
                	mapInventoryMatrix[(i * PublicParameter.mapInventoryRow) + j] = new Label(MainMenu.mapInventory.getMapPack().get((i * PublicParameter.mapInventoryRow) + j).toString(), style);
                } else {
                    mapInventoryMatrix[(i * PublicParameter.mapInventoryRow) + j] = new Label("", style);
                }
                mapInventoryMatrix[(i * PublicParameter.mapInventoryRow) + j].setAlignment(Align.center);
                tmpLabel = mapInventoryMatrix[(i * PublicParameter.mapInventoryRow) + j];
                mapInventoryTable.add(tmpLabel).width(PublicParameter.mapCellWidth).height(PublicParameter.mapCellHeight).space(18);
            }
            mapInventoryTable.row();
        }
    }
    
    
    private void buildCampaignInventoryMatrix() {
    	Label tmpLabel;
        for (int i = 0; i < PublicParameter.campaignInventorySize; i++) {
            if (i < MainMenu.campaignInventory.getCampaignPack().size ) {
            	campaignInventoryMatrix[i] = new Label(MainMenu.campaignInventory.getCampaignPack().get(i).toString()+"--", MainMenu.style);
            } else {
                campaignInventoryMatrix[i] = new Label("", MainMenu.style);
            }
            tmpLabel = campaignInventoryMatrix[(i)];
            campaignInventoryTable.add(tmpLabel).width(PublicParameter.mapCellWidth).height(PublicParameter.mapCellHeight).space(18);
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


    private void addCampaignMatrixListener() {
        for (int i = 0; i < mapList.size ; i++){
            campaignMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                	mapList.removeIndex(getButton());
                    campaignTable.clearChildren();
                    buildCampaignMatrix();
                    addCampaignMatrixListener();
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                	inventoryCampaignInfoLabel.setText(mapList.get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                	inventoryCampaignInfoLabel.setText("");
                }
            });
        }
    }


    private void addCampaignInventoryMatrixListener() {
        for (int i = 0; i < MainMenu.campaignInventory.getCampaignPack().size ; i++){
            campaignInventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                	Campaign campaignPointer = MainMenu.campaignInventory.getCampaignPack().removeIndex(getButton());
                    nameField.setText(campaignPointer.getName());
                    mapList.clear();
                    for(Map m : campaignPointer.getMapPack()){
                    	mapList.add(m);
                    }
                	campaignTable.clearChildren();
                    buildCampaignMatrix();
                    addCampaignMatrixListener();
                    MainMenu.campaignInventory.saveToFile();
                    campaignInventoryTable.clearChildren();
                    buildCampaignInventoryMatrix();
                    addCampaignInventoryMatrixListener();
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                	inventoryCampaignInfoLabel.setText(MainMenu.campaignInventory.getCampaignPack().get(getButton()).getMapPack().toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                	inventoryCampaignInfoLabel.setText("");
                }
            });
        }
    }
    
    private void addMapInventoryMatrixListener() {
        for (int i = 0; i < MainMenu.mapInventory.getMapPack().size ; i++){
            mapInventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    map = new Map(MainMenu.mapInventory.getMapPack().get(getButton()).getLevel(),
                            MainMenu.mapInventory.getMapPack().get(getButton()).getSize(),
                            MainMenu.mapInventory.getMapPack().get(getButton()).getName());
                    map.setEntryDoor(MainMenu.mapInventory.getMapPack().get(getButton()).getEntryDoor());
                    map.setExitDoor(MainMenu.mapInventory.getMapPack().get(getButton()).getExitDoor());
                    map.setWallLocationList(MainMenu.mapInventory.getMapPack().get(getButton()).getWallLocationList());
                    map.setItemLocationList(MainMenu.mapInventory.getMapPack().get(getButton()).getItemLocationList());
                    map.setFriendLocationList(MainMenu.mapInventory.getMapPack().get(getButton()).getFriendLocationList());
                    map.setEnemyLocationList(MainMenu.mapInventory.getMapPack().get(getButton()).getEnemyLocationList());
                    mapList.add(map);
                    campaignTable.clearChildren();
                    buildCampaignMatrix();
                    addCampaignMatrixListener();

                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    inventoryMapInfoLabel.setText(MainMenu.mapInventory.getMapPack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                	inventoryMapInfoLabel.setText("");
                }
            });
        }
    }

}
