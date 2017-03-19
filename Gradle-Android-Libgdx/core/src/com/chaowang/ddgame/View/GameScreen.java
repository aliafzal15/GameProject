package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.CampaignModel.Campaign;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.Controller.PlayerController;
import com.chaowang.ddgame.MapModel.Wall;
import com.chaowang.ddgame.PlayModel.Player;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.util.Fade;

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
    private PlayerController playerController;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera cam;
    // followed from video
//    private int uiScale = 2;
//    private Stage uiStage;
//    private Table root;
//    private DialogBox dialogBox;
    private Fade fade;

    private Stage stage;
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
        stage = new Stage(new ScreenViewport());
        fade = new Fade(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Color.BLACK, Fade.FadeType.FADE_IN, 3000);

//        initUI();
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    @Override
    public void show() {
        playerController = new PlayerController(player, this);
        Gdx.input.setInputProcessor(playerController);
        Gdx.input.setInputProcessor(stage);
        
        mapModel.adjustLevel(player.getCharacter().getLevel());

        if(mapModel.getEntryDoor().y - player.getBound().getHeight() > 0 ){
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y - player.getBound().getHeight()));
        } else {
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y +  mapModel.getEntryDoor().getHeight()));
        }
        stage.addActor(fade);

    }

    @Override
    public void render(float delta) {
        isHitObject = false;

        renderer.setView(cam);
        renderer.render();

        cam.position.set(player.getPosition().x + (player.getCurrentFrame().getRegionWidth() / 2), player.getPosition().y + player.getCurrentFrame().getRegionHeight() / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        //stage.act();
//        uiStage.act(delta);

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
                playerController.pickupItem(mapModel.getItemLocationList().get(cur));
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
            	System.out.println("Is a friend ");
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


//        stage.draw();

        if(player.getBound().overlaps(mapModel.getExitDoor()) ){
            if(player.getPosition().y + player.getBound().getHeight() <= mapModel.getExitDoor().y + 1f ){
//            	System.out.println("player " + (player.getPosition().y + player.getBound().getHeight()));
//            	System.out.println(mapModel.getExitDoor().y);
                fade.startFade();
                game.setScreen(new MainMenuScreen(game));

//                if(campaign.getMapPack().size == count+1 ){
//    				stage.addAction(Actions.sequence(Actions.fadeOut(2), Actions.run(new Runnable() {
//    					@Override
//    					public void run() {
//    	                    game.setScreen(new MainMenuScreen(game));
//    					}
//    				})));
                } else {
                    player.getCharacter().promoteUp();
                    fade.startFade();
//                    stage.addAction(Actions.sequence(Actions.fadeOut(2), Actions.run(new Runnable() {
//    					@Override
//    					public void run() {
//    	                    //campaign.getMapPack().removeIndex(0);
//    						count++;
//    						System.out.println(count);
//    	                    game.setScreen(new GameScreen(game, player.getCharacter(), campaign.getMapPack().get(count), campaign));
//    					}
//    				})));
                }
            } else{
                playerController.reAdjust();
                isHitObject = true;
            }

        if(player.getBound().overlaps(mapModel.getEntryDoor()) ){
            playerController.reAdjust();
            isHitObject = true;
        }

        if(isHitObject == false){
            playerController.keyDown();
        }
        batch.end();

        //stage.draw();
        
//        uiStage.draw();

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

    
//    private void initUI(){
//        uiStage = new Stage(new ScreenViewport());
//        uiStage.getViewport().update(Gdx.graphics.getWidth() / uiScale, Gdx.graphics.getHeight() / uiScale);
//
//        root = new Table();
//        root.setFillParent(true);
//        uiStage.addActor(root);
//
//        dialogBox = new DialogBox(MainMenuScreen.skin);
//        dialogBox.animateText("Hello advanturer  \nMay I offer you a fresh beverage");
//
//        root.add(dialogBox).expand().align(Align.bottom).pad(8f);
//
//    }


}
