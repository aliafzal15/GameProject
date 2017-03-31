package com.chaowang.ddgame.GameView;


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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

/**
 * class for Game Selection
 * @author chao wang
 * @version 2.0
 */
public class GameSelectionScreen implements Screen{

    private Game game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private TextButton gameStartButton, backwardButton;
    private Image characterImage;
    public ImageButton[] characterInventoryMatrix;
    private Label[] campaignInventoryMatrix;
    private Table campaignInventoryTable, characterInventoryTable, campaignTable;
    private Label campaignInfoLabel, characterInfoLabel, campaignLabel;
    private Campaign campaign;
    private Character character;

    public GameSelectionScreen(Game game) {
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

        backwardButton = new TextButton("<--", MainMenuScreen.buttonStyle);
        backwardButton.setWidth(Gdx.graphics.getWidth() / 20);
        backwardButton.setHeight(Gdx.graphics.getHeight() / 10);
        backwardButton.setPosition((Gdx.graphics.getWidth() * 1 / 30), (Gdx.graphics.getHeight() * 37 / 40 ));
        backwardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });
        stage.addActor(backwardButton);

        campaignInfoLabel = new Label("", MainMenuScreen.style);
        campaignInfoLabel.setPosition((Gdx.graphics.getWidth() * 2 / 3), (Gdx.graphics.getHeight() / 4));
        stage.addActor(campaignInfoLabel);

        characterInfoLabel = new Label("", MainMenuScreen.style);
        characterInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 20), (Gdx.graphics.getHeight() / 4));
        stage.addActor(characterInfoLabel);


        characterInventoryTable = new Table();

        characterInventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 4 / 5);
        characterInventoryTable.setPosition(10, Gdx.graphics.getHeight() * 1 / 5);

        characterInventoryMatrix = new ImageButton[PublicParameter.CHARACTER_INVENTORY_ROW * PublicParameter.CHARACTER_INVENTORY_COLUMN];
        buildCharacterInventoryMatrix();
        addCharacterInventoryMatrixListener();

        stage.addActor(characterInventoryTable);

        campaignInventoryTable = new Table();

        campaignInventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 7 / 10);
        campaignInventoryTable.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 1 / 4);
        campaignInventoryTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("scrollPaper.png")))));

        campaignInventoryMatrix = new Label[PublicParameter.CAMPAIGN_INVENTORY_ROW * PublicParameter.CAMPAIGN_INVENTORY_COLUMN];

        buildCampaignInventoryMatrix();
        addCampaignInventoryMatrixListener();

        stage.addActor(campaignInventoryTable);

        //-------------------------------draw the bottom half frame ------------------------------

        characterImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        characterImage.setSize(Gdx.graphics.getWidth() * 1 / 12, Gdx.graphics.getHeight() * 1 / 6);
        characterImage.setPosition((Gdx.graphics.getWidth() * 1 / 4) , (Gdx.graphics.getHeight() / 40));

        stage.addActor(characterImage);

        campaignLabel = new Label("?", MainMenuScreen.style);
        campaignTable = new Table();

        campaignTable.setSize(Gdx.graphics.getWidth() * 1 / 5, Gdx.graphics.getHeight() * 1 / 5);
        campaignTable.setPosition(Gdx.graphics.getWidth() * 5 / 8, Gdx.graphics.getHeight() * 1 / 40);
        campaignTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpackBackground.png")))));
        campaignTable.add(campaignLabel);

        stage.addActor(campaignTable);

        gameStartButton = new TextButton("START", MainMenuScreen.buttonStyle);
        gameStartButton.setWidth(Gdx.graphics.getWidth() / 9);
        gameStartButton.setHeight(Gdx.graphics.getHeight() / 9);
        gameStartButton.setPosition((Gdx.graphics.getWidth() * 1 / 2 - gameStartButton.getWidth() / 2 ) , Gdx.graphics.getHeight() * 1 / 30 );
        gameStartButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(character != null && campaign != null ){
                    game.setScreen(new GameScreen(game, character,campaign.getMapPack().get(0), campaign));
                }
                else{
                    new Dialog("Error", MainMenuScreen.skin, "dialog") {
                    }.text("need character and campaign selected").button("OK", true).key(Input.Keys.ENTER, true)
                            .show(stage);
                }
                return true;
            }
        });
        stage.addActor(gameStartButton);

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
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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



    /**
     * build matrix structure for inventory
     */
    public void buildCharacterInventoryMatrix() {
        for (int i = 0; i < PublicParameter.CHARACTER_INVENTORY_ROW; i++) {
            for (int j = 0; j < PublicParameter.CHARACTER_INVENTORY_COLUMN; j++) {
                if ((i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j < MainMenuScreen.characterInventory.getChatacterPack().size) {
                    characterInventoryMatrix[(i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(MainMenuScreen.characterInventory.getChatacterPack().get(i * PublicParameter.CHARACTER_INVENTORY_COLUMN + j).getTexture())));
                } else {
                    characterInventoryMatrix[(i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
                ImageButton tempButton = characterInventoryMatrix[(i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j];
                characterInventoryTable.add(tempButton).width(PublicParameter.CHARACTER_CELL_WIDTH).height(PublicParameter.CHARACTER_CELL_HEIGHT).space(15);
            }
            characterInventoryTable.row();
        }
    }

    /**
     * listen any change from character inventory
     */
    public void addCharacterInventoryMatrixListener() {
        for (int i = 0; i < MainMenuScreen.characterInventory.getChatacterPack().size; i++) {
            characterInventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    character = new Character(
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getName(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getLevel(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getPromotionPoint(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getRaceType(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getFighterType(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getBaseAttributes(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getAbilityBonusArr(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getBackpack(),
                            MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getEquipment());
                    characterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(character.getTexture())));
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    characterInfoLabel.setText(MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    characterInfoLabel.setText("");
                }
            });
        }
    }

    /**
     * build matrix structure for map inventory
     */
    public void buildCampaignInventoryMatrix() {
        Label tmpLabel;
        for (int i = 0; i < PublicParameter.CAMPAIGN_INVENTORY_ROW; i++) {
            for (int j = 0; j < PublicParameter.CAMPAIGN_INVENTORY_COLUMN; j++) {
                if ((i * PublicParameter.CAMPAIGN_INVENTORY_ROW) + j < MainMenuScreen.campaignInventory.getCampaignPack().size) {
                    campaignInventoryMatrix[(i * PublicParameter.CAMPAIGN_INVENTORY_ROW) + j] = new Label(MainMenuScreen.campaignInventory.getCampaignPack().get(i).toString(), MainMenuScreen.style);
                } else {
                    campaignInventoryMatrix[(i * PublicParameter.CAMPAIGN_INVENTORY_ROW) + j] = new Label("", MainMenuScreen.style);
                }
                campaignInventoryMatrix[(i * PublicParameter.CAMPAIGN_INVENTORY_ROW) + j].setAlignment(Align.center);
                tmpLabel = campaignInventoryMatrix[(i * PublicParameter.CAMPAIGN_INVENTORY_ROW) + j];
                campaignInventoryTable.add(tmpLabel).width(PublicParameter.MAP_CELL_WIDTH).height(PublicParameter.MAP_CELL_HEIGHT).space(18);
            }
            campaignInventoryTable.row();
        }
    }

    /**
     * add listener to listen map inventory matrix
     */
    public void addCampaignInventoryMatrixListener() {
        for (int i = 0; i < MainMenuScreen.campaignInventory.getCampaignPack().size ; i++){
            campaignInventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    campaign = MainMenuScreen.campaignInventory.getCampaignPack().get(getButton());
                    campaignTable.clearChildren();
                    campaignLabel.setText(campaign.toString());
                    campaignTable.add(campaignLabel);
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    campaignInfoLabel.setText(MainMenuScreen.campaignInventory.getCampaignPack().get(getButton()).getMapPack().toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    campaignInfoLabel.setText("");
                }
            });
        }
    }
}