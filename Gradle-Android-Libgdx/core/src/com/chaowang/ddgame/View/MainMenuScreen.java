package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.chaowang.ddgame.CampaignModel.CampaignInventory;

import java.io.IOException;

import com.chaowang.ddgame.ItemModel.ItemInventory;
import com.chaowang.ddgame.CharacterModel.CharacterInventory;
import com.chaowang.ddgame.MapModel.MapInventory;
/**
 * view for main menu
 * @author chao wang
 * @version 1.0
 */
public class MainMenuScreen implements Screen{

    private Stage stage;

    public static Label.LabelStyle style;
    public static BitmapFont font;
    public static ItemInventory itemInventory;
    public static CharacterInventory characterInventory;
    public static MapInventory mapInventory;
    public static CampaignInventory campaignInventory;
    TextureAtlas buttonAtlas;
    private Texture backgroundTexture;
    public static TextButton.TextButtonStyle buttonStyle;
    private TextButton playButton, characterButton, mapButton, itemButton, campaignButton;
    public static Skin skin;

    private SpriteBatch batch;
    private Game game;
    /**
     * constructor
     * @param game
     */
    public MainMenuScreen(Game game) {
        this.game = game;
    }
    /**
     * show main menu on screen
     */
    @Override
    public void show() {

        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("Style/default.fnt"),false);
        font.getData().markupEnabled = true;
        style = new Label.LabelStyle(font, Color.WHITE);

        itemInventory = new ItemInventory();
        characterInventory = new CharacterInventory();
        mapInventory = new MapInventory();
        campaignInventory = new CampaignInventory();
        try {
            characterInventory.readFile();
            itemInventory.readFile();
            mapInventory.readFile();
            campaignInventory.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        backgroundTexture =  new Texture(Gdx.files.internal("dnd.png"));

        skin = new Skin(Gdx.files.internal("Style/uiskin.json"));
        buttonAtlas = new TextureAtlas("buttons/button.pack");
        skin.addRegions(buttonAtlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("buttonOn");
        buttonStyle.over = skin.getDrawable("buttonOff");
        buttonStyle.down = skin.getDrawable("buttonOff");
        buttonStyle.font = font;

        playButton = new TextButton("PLAY", buttonStyle);
        playButton.setWidth(Gdx.graphics.getWidth() / 6 );
        playButton.setHeight(Gdx.graphics.getHeight() / 9);
        playButton.setPosition((Gdx.graphics.getWidth()  * 3 / 4  ) - playButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 2 ) + (2* playButton.getHeight()));

        stage.addActor(playButton);

        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                game.setScreen(new GameSelectionScreen(game));
                return true;
            }
        });

        characterButton = new TextButton("Character Editor", buttonStyle);
        characterButton.setWidth(Gdx.graphics.getWidth() / 3 );
        characterButton.setHeight(Gdx.graphics.getHeight() / 9);
        characterButton.setPosition((Gdx.graphics.getWidth() * 3 / 4 ) - characterButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 2 ) + characterButton.getHeight() / 2);

        stage.addActor(characterButton);

        characterButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                game.setScreen(new CharacterEditorScreen(game));
                return true;
            }
        });
        
        itemButton = new TextButton("Item Editor", buttonStyle);
        itemButton.setWidth(Gdx.graphics.getWidth() / 3 );
        itemButton.setHeight(Gdx.graphics.getHeight() / 9);
        itemButton.setPosition((Gdx.graphics.getWidth() * 3 / 4 ) - itemButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 2 ) -  itemButton.getHeight());

        stage.addActor(itemButton);

        itemButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                game.setScreen(new ItemEditorScreen(game));
                return true;
            }
        });
        
        mapButton = new TextButton("Map Editor", buttonStyle);
        mapButton.setWidth(Gdx.graphics.getWidth() / 3 );
        mapButton.setHeight(Gdx.graphics.getHeight() / 9);
        mapButton.setPosition((Gdx.graphics.getWidth() * 3 / 4  ) - mapButton.getWidth() / 2 , (Gdx.graphics.getHeight() /3 ) - mapButton.getHeight());

        stage.addActor(mapButton);

        mapButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                game.setScreen(new MapEditorScreen(game));
                return true;
            }
        });
        
        campaignButton = new TextButton("Campaign Editor", buttonStyle);
        campaignButton.setWidth(Gdx.graphics.getWidth() / 3 );
        campaignButton.setHeight(Gdx.graphics.getHeight() / 9);
        campaignButton.setPosition((Gdx.graphics.getWidth() * 3 / 4 ) - campaignButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 6 ) - campaignButton.getHeight());

        stage.addActor(campaignButton);

        campaignButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                game.setScreen(new CampaignEditorScreen(game));
                return true;
            }
        });

        batch = new SpriteBatch();
    }
    /**
     * put image on background
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        batch.begin();

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
