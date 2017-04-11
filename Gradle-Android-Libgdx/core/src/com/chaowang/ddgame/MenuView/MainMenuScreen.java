package com.chaowang.ddgame.MenuView;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CampaignModel.CampaignInventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import com.chaowang.ddgame.GameView.GameSelectionScreen;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuModel.ItemModel.ItemInventory;
import com.chaowang.ddgame.MenuModel.CharacterModel.CharacterInventory;
import com.chaowang.ddgame.MenuModel.MapModel.Map;
import com.chaowang.ddgame.MenuModel.MapModel.MapInventory;
import com.chaowang.ddgame.util.IntVector2Pair;

/**
 * view for main menu
 * @author chao wang
 * @version 1.0
 */
public class MainMenuScreen implements Screen{

    private Stage stage;

    public static Label.LabelStyle style;
    public static TextArea logArea;
    public static BitmapFont font;
    public static ItemInventory itemInventory;
    public static CharacterInventory characterInventory;
    public static MapInventory mapInventory;
    public static CampaignInventory campaignInventory;
    TextureAtlas buttonAtlas;
    private Texture backgroundTexture;
    public static TextButton.TextButtonStyle buttonStyle;
    private TextButton playButton, continueButton, characterButton, mapButton, itemButton, campaignButton;
    public static Skin skin;

    private SpriteBatch batch;
    private Game game;

    // for load game;
    private Player player;
    private Map mapModel;
    private Campaign campaign;
    private HashMap<Vector2, NPC> npcList;
    private LinkedList<IntVector2Pair> playOrderList;
    private boolean isUserPlay;
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

        //log label
        logArea = new TextArea("",skin);
        logArea.setColor(new Color(0, 1, 0, 0.4f));
        logArea.setDisabled(true);

        continueButton = new TextButton("Continue", buttonStyle);
        continueButton.setWidth(Gdx.graphics.getWidth() / 3 );
        continueButton.setHeight(Gdx.graphics.getHeight() / 9);
        continueButton.setPosition((Gdx.graphics.getWidth()  * 3 / 4  ) - continueButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 2 ) + (2 * continueButton.getHeight()));

        stage.addActor(continueButton);

        continueButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                try {
                    readFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                game.setScreen(new GameScreen(game, player, mapModel, campaign, npcList, playOrderList, isUserPlay));
                return true;
            }
        });
        
        
        playButton = new TextButton("New Game", buttonStyle);
        playButton.setWidth(Gdx.graphics.getWidth() / 3 );
        playButton.setHeight(Gdx.graphics.getHeight() / 9);
        playButton.setPosition((Gdx.graphics.getWidth()  * 3 / 4  ) - playButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 2 ) + (0.8f * playButton.getHeight()));

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
        characterButton.setPosition((Gdx.graphics.getWidth() * 3 / 4 ) - characterButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 2 ) - 0.4f *characterButton.getHeight());

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
        itemButton.setPosition((Gdx.graphics.getWidth() * 3 / 4 ) - itemButton.getWidth() / 2 , (Gdx.graphics.getHeight() / 2 ) -  1.6f * itemButton.getHeight());

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
        mapButton.setPosition((Gdx.graphics.getWidth() * 3 / 4  ) - mapButton.getWidth() / 2 , (Gdx.graphics.getHeight() /2 - mapButton.getHeight() * 2.8f ) );

        stage.addActor(mapButton);

        mapButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //stage.clear();
				stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
					@Override
					public void run() {
		                game.setScreen(new MapEditorScreen(game));
		                }
				})));

                return true;
            }
        });
        
        campaignButton = new TextButton("Campaign Editor", buttonStyle);
        campaignButton.setWidth(Gdx.graphics.getWidth() / 3 );
        campaignButton.setHeight(Gdx.graphics.getHeight() / 9);
        campaignButton.setPosition((Gdx.graphics.getWidth() * 3 / 4 ) - campaignButton.getWidth() / 2 , (Gdx.graphics.getHeight() /2 - mapButton.getHeight() * 4 )  );

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

    /**
     * read files
     * @throws IOException
     */
    public  void readFile() throws IOException {
        File file = new File("data" + File.separator + "savedGame.json");
        file.createNewFile(); // if file already exists will do nothing

        Scanner scanner = new Scanner(file);
        Json json = new Json();
        String context;
        context = scanner.nextLine();
        player = json.fromJson(Player.class, context);
        context = scanner.nextLine();
        mapModel = json.fromJson(Map.class, context);
        context = scanner.nextLine();
        campaign = json.fromJson(Campaign.class, context);

        context = scanner.nextLine();        
        HashMap<String, NPC> tmpMap;
        tmpMap = json.fromJson(HashMap.class, Vector2.class, context);
        npcList = ConverHMkeyStrToVec2(tmpMap);
        
        context = scanner.nextLine();
        playOrderList = json.fromJson(LinkedList.class, context);
        context = scanner.nextLine();
        isUserPlay = json.fromJson(Boolean.class, context);
        scanner.close();
        file.exists();
    }
    
    
	public HashMap<Vector2, NPC> ConverHMkeyStrToVec2(HashMap<String, NPC> tmpMap) {
		HashMap<Vector2, NPC> hmapReturn = new HashMap<Vector2, NPC>();
        String tmpStr;
        Iterator<String> it = tmpMap.keySet().iterator();
        while(it.hasNext()){
            Vector2 tmpVec = new Vector2();
        	tmpStr = it.next();
        	tmpVec.x = Float.parseFloat(tmpStr.substring(tmpStr.indexOf("(")+1, tmpStr.indexOf(",")));
        	tmpVec.y = Float.parseFloat(tmpStr.substring(tmpStr.indexOf(",")+1, tmpStr.indexOf(")")));
        	hmapReturn.put(tmpVec,tmpMap.get(tmpStr) );
        }
		return hmapReturn;
	}
}
