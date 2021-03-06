package com.chaowang.ddgame.GameView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleEntry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.GameController.ItemExchangeController;
import com.chaowang.ddgame.MenuModel.MapModel.Map;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.util.IntVector2Pair;
import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.PublicParameter;

/**
 * View for Exchange Items
 * @author chao wang
 * @version 2.0
 */
public class GameItemExchangeScreen implements Screen{

    private Game game;
    public Stage stage;
    private SpriteBatch batch;
    public TextButton backwardButton;
    private Texture backgroundTexture;

    public ImageButton[] playerBackpackMatrix, NPCbackpackMatrix;
    public Table playerBackpackTable, NPCbackpackTable;
    private Player player;
    private Character NPCcharacter;
    private Vector2 NPCposition;
    private Map mapModel;
    private Campaign campaign;
    private HashMap<Vector2, NPC> npcList;
    private LinkedList<IntVector2Pair> playOrderList;
    private ItemExchangeController controller;
    public Label NPCItemInfoLabel, backpackItemInfoLabel;
    private boolean isUserPlay;

    /**
     * constructor
     * @param game
     */
    public GameItemExchangeScreen (Game game, Player player, Map map, Campaign camp, Vector2 NPCposition, HashMap<Vector2, NPC> npcList, LinkedList<IntVector2Pair> linkedList, boolean isUserPlay) {
        this.game = game;
        this.player = player;
        this.mapModel = map;
        this.campaign = camp;
        this.NPCposition = NPCposition;
        this.npcList = npcList;
        this.playOrderList = linkedList;
        this.NPCcharacter = this.npcList.get(NPCposition).getCharacter();
        this.isUserPlay = isUserPlay;
    }
    
    /**
     * constructor
     * @param game
     */
    public GameItemExchangeScreen (Game game, Player player, Map map, Campaign camp, Vector2 NPCposition, boolean isUserPlay) {
        this.game = game;
        this.player = player;
        this.mapModel = map;
        this.campaign = camp;
        this.NPCposition = NPCposition;
        this.NPCcharacter = this.npcList.get(NPCposition).getCharacter();
        this.isUserPlay = isUserPlay;
    }
    /**
     * show whole equipment editor on screen
     */
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
        batch = new SpriteBatch();

        controller = new ItemExchangeController(this,this.player, this.NPCcharacter);

        backwardButton = new TextButton("<--", MainMenuScreen.buttonStyle);
        backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
        backwardButton.setHeight(Gdx.graphics.getHeight() / 10);
        backwardButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
        backwardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, player, mapModel, campaign, npcList, playOrderList, isUserPlay));
                return true;
            }
        });
        stage.addActor(backwardButton);

        backpackItemInfoLabel = new Label("", MainMenuScreen.style);
        backpackItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 2), (Gdx.graphics.getHeight() / 8));
        stage.addActor(backpackItemInfoLabel);

        NPCItemInfoLabel = new Label("", MainMenuScreen.style);
        NPCItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 20), (Gdx.graphics.getHeight() / 8));
        stage.addActor(NPCItemInfoLabel);

        playerBackpackTable = new Table();
        playerBackpackTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpackBackground.png")))));
        playerBackpackTable.setSize(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() * 1 / 3);
        playerBackpackTable.setPosition(Gdx.graphics.getWidth() * 3 / 7, Gdx.graphics.getHeight() * 1 / 3);

        playerBackpackMatrix = new ImageButton[PublicParameter.ITEM_BACKPACK_ROW * PublicParameter.ITEM_BACKPACK_COLUMN];
        controller.buildPlayerBackpackMatrix();
        controller.addPlayerBackpackMatrixListener();

        stage.addActor(playerBackpackTable);

        NPCbackpackTable = new Table();

        NPCbackpackTable.setSize(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() * 1 / 3);
        NPCbackpackTable.setPosition( Gdx.graphics.getWidth() / 100 , Gdx.graphics.getHeight() * 1 / 3);

        NPCbackpackMatrix = new ImageButton[PublicParameter.ITEM_BACKPACK_ROW * PublicParameter.ITEM_BACKPACK_COLUMN];
        controller.buildNpcBackpackMatrix();
        controller.addNpcBackpackMatrixListener();

        stage.addActor(NPCbackpackTable);

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
    /**
     * resize view
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
    /**
     * dispose
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

}
