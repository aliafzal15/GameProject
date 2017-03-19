package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.CampaignModel.Campaign;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.DialogueSystem.Dialogue;
import com.chaowang.ddgame.DialogueSystem.DialogueNode;
import com.chaowang.ddgame.GameController.DialogueController;
import com.chaowang.ddgame.GameController.PlayerController;
import com.chaowang.ddgame.GameUI.DialogueBox;
import com.chaowang.ddgame.MapModel.Wall;
import com.chaowang.ddgame.PlayModel.Player;
import com.chaowang.ddgame.MapModel.Map;

import com.chaowang.ddgame.GameUI.OptionBox;

import java.util.Iterator;
import java.util.Set;

/**
 * View for Game Selection
 * @author chao wang
 * @version 2.0
 */
public class GameScreen implements Screen{

    private Game game;
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera cam;
    // followed from video
//    private int uiScale = 2;
    private Stage uiStage;
    private Table root;
    private OptionBox optionBox;
    private DialogueBox dialogBox, messageDialog;
    private Dialogue dialogue;
    private DialogueController dialogueController;
    private PlayerController playerController;
//    private Fade fade;

    private Player player;
    private Map mapModel;
    private Campaign campaign;
    private Set<Vector2> vectorKeySet ;
    private Iterator<Vector2> keySetIterator ;
    private boolean isHitObject;
    private static int count=0;

    GameScreen(Game game, Character character,Map map, Campaign camp){
        this.game = game;
        this.player = new Player(new Vector2(1,1), character);
        this.mapModel = map;
        this.campaign = new Campaign(camp);
        batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("terrain/terrain"+mapModel.getSize() + "x" + mapModel.getSize() + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(this.map);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        fade = new Fade(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Color.BLACK, Fade.FadeType.FADE_IN, 3000);

        initUI();

        playerController = new PlayerController(player, this);
        dialogueController = new DialogueController(dialogBox, optionBox, messageDialog);

        dialogue = new Dialogue();
        DialogueNode node1 = new DialogueNode("Hello Adventurer!\n Welcome to town.", 0);
        DialogueNode node2 = new DialogueNode("How can I help you?", 1);
;

        node1.makeLinear(node2.getID());
        node2.addChoice("Trade", 2);
        node2.addChoice("Leave", 3);
        dialogue.addNode(node1);
        dialogue.addNode(node2);

        dialogueController.startDialogue(dialogue);
//        while (dialogueController.getAnswerIndex() != -1){
//            System.out.println(dialogueController.getAnswerIndex());
//        }


    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(uiStage);
        Gdx.input.setInputProcessor(playerController);
        Gdx.input.setInputProcessor(dialogueController);

        mapModel.adjustLevel(player.getCharacter().getLevel());

        if(mapModel.getEntryDoor().y - player.getBound().getHeight() > 0 ){
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y - player.getBound().getHeight()));
        } else {
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y +  mapModel.getEntryDoor().getHeight()));
        }
        //stage.addActor(fade);
//        stage.draw();
        uiStage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(2)));
    }

    @Override
    public void render(float delta) {
        isHitObject = false;

        renderer.setView(cam);
        renderer.render();

        cam.position.set(player.getPosition().x + (player.getCurrentFrame().getRegionWidth() / 2), player.getPosition().y + player.getCurrentFrame().getRegionHeight() / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        dialogueController.update(delta);
        uiStage.act(delta);

//		  if(Gdx.input.isTouched()){
//			  System.out.println("Application clicked");
//		  }
        //System.out.println("mouse x : "+ Gdx.input.getX() + "mouse y : "+ Gdx.input.getY());


        batch.begin();
        batch.draw(player.getCurrentFrame(), player.getPosition().x, player.getPosition().y );

        mapModel.getEntryDoor().draw(batch);
        mapModel.getExitDoor().draw(batch);

        //draw walls on screen
        for(Wall cur : mapModel.getWallLocationList() ){
            cur.draw(batch);
            if(player.getBound().overlaps(cur)){
                System.out.println("hit wall");
                playerController.reAdjust();
                isHitObject = true;
            }
        }

        // draw items on screen
        vectorKeySet = mapModel.getItemLocationList().keySet();
        keySetIterator = vectorKeySet.iterator();

        while(keySetIterator.hasNext()){
            Vector2 cur = keySetIterator.next();
            mapModel.getItemLocationList().get(cur).draw(batch, cur);
            if(player.getBound().overlaps(mapModel.getItemLocationList().get(cur)) ){
                System.out.println("hit item");
                playerController.pickupItem(mapModel.getItemLocationList().get(cur));
                dialogueController.animateText("Item " + mapModel.getItemLocationList().get(cur).toString()+" found!");
                keySetIterator.remove();
            }
        }

        // draw NPC on screen
        vectorKeySet = mapModel.getFriendLocationList().keySet();
        keySetIterator = vectorKeySet.iterator();

        while(keySetIterator.hasNext()){
            Vector2 cur = keySetIterator.next();
            mapModel.getFriendLocationList().get(cur).draw(batch, cur, true);
            if(player.getBound().overlaps(mapModel.getFriendLocationList().get(cur)) ){
                dialogueController.startDialogue(dialogue);
//                while (dialogueController.getAnswerIndex() != -1){
//                    System.out.println(dialogueController.getAnswerIndex());
//                }
            }
        }

        // draw enemy on screen
        vectorKeySet = mapModel.getEnemyLocationList().keySet();
        keySetIterator = vectorKeySet.iterator();

        while(keySetIterator.hasNext()){
            Vector2 cur = keySetIterator.next();
            mapModel.getEnemyLocationList().get(cur).draw(batch, cur, false);
            if(player.getBound().overlaps(mapModel.getEnemyLocationList().get(cur)) ){
                System.out.println("Is a enemy ");
            }
        }

        if(player.getBound().overlaps(mapModel.getExitDoor()) ) {
            if (player.getPosition().y + player.getBound().getHeight() <= mapModel.getExitDoor().y + 1f) {
                if (campaign.getMapPack().size == count + 1) {
                    player.setPosition(new Vector2(-1000,-1000));
                    uiStage.addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new MainMenuScreen(game));
                        }
                    })));
                } else {
                    player.getCharacter().promoteUp();
                    count++;
                    player.setPosition(new Vector2(-1000,-1000));
                    uiStage.addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            //campaign.getMapPack().removeIndex(0);
                            System.out.println(count);
                            game.setScreen(new GameScreen(game, player.getCharacter(), campaign.getMapPack().get(count), campaign));
                        }
                    })));
                }
            } else {
                playerController.reAdjust();
                isHitObject = true;
            }
        }


        if(player.getBound().overlaps(mapModel.getEntryDoor()) ){
            System.out.println("hit entry door");
            playerController.reAdjust();
            isHitObject = true;
        }

        if(! isHitObject ){
            playerController.keyDown();
        }
        batch.end();


        uiStage.draw();

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


    private void initUI(){
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().update(Gdx.graphics.getWidth() , Gdx.graphics.getHeight() );
        //uiStage.setDebugAll(true);

        root = new Table();
        root.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight() / 2 );
//        root.setFillParent(true);
        uiStage.addActor(root);

        messageDialog = new DialogueBox(MainMenuScreen.skin);
        messageDialog.setVisible(false);

        dialogBox = new DialogueBox(MainMenuScreen.skin);
        dialogBox.setVisible(false);

        optionBox = new OptionBox(MainMenuScreen.skin);
        optionBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(messageDialog).expand().align(Align.right).space(8f).row();
        dialogTable.add(dialogBox).expand().align(Align.bottomLeft).space(8f);
        dialogTable.add(optionBox).width(Gdx.graphics.getWidth() / 5).align(Align.bottomRight).space(8f);

        root.add(dialogTable).expand().align(Align.bottom);
    }


}
