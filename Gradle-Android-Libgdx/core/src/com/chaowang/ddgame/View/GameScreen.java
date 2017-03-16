package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.CampaignModel.Campaign;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.Controller.PlayerController;
import com.chaowang.ddgame.MapModel.Tree;
import com.chaowang.ddgame.MapModel.Wall;
import com.chaowang.ddgame.PlayModel.Actor;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.util.DialogBox;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Set;

public class GameScreen implements Screen{

    private Game game;
    private SpriteBatch batch;
    private PlayerController playerController;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera cam;
    // followed from video
    private int uiScale = 2;
    private Stage uiStage;
    private Table root;
    private DialogBox dialogBox;

    private Actor actor;
    private Map mapModel;
    private Campaign campaign;
    private Set<Vector2> vectorKeySet ;
    private Iterator<Vector2> keySetIterator ;
    private boolean isHitObject;

    GameScreen(Game game, Character character,Map map, Campaign camp){
        this.game = game;
        this.actor = new Actor(new Vector2(1,1), character);
        this.mapModel = map;
        this.campaign = new Campaign(camp);
        batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("terrain/terrain"+mapModel.getSize() + "x" + mapModel.getSize() + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(this.map);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        initUI();
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    @Override
    public void show() {
        playerController = new PlayerController(actor, this);
        Gdx.input.setInputProcessor(playerController);

        mapModel.adjustLevel(actor.getCharacter().getLevel());

        if(mapModel.getEntryDoor().y - actor.getBound().getHeight() > 0 ){
            actor.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - actor.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y - actor.getBound().getHeight()));
        } else {
            actor.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - actor.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y +  mapModel.getEntryDoor().getHeight()));
        }

    }

    @Override
    public void render(float delta) {
        isHitObject = false;

        renderer.setView(cam);
        renderer.render();

        cam.position.set(actor.getPosition().x + (actor.getCurrentFrame().getRegionWidth() / 2), actor.getPosition().y + actor.getCurrentFrame().getRegionHeight() / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        uiStage.act(delta);

		  if(Gdx.input.isTouched()){
			  System.out.println("Application clicked");
		  }
		  //System.out.println("mouse x : "+ Gdx.input.getX() + "mouse y : "+ Gdx.input.getY());
		    
		  
        batch.begin();
        batch.draw(actor.getCurrentFrame(), actor.getPosition().x, actor.getPosition().y );

        mapModel.getEntryDoor().draw(batch);
        mapModel.getExitDoor().draw(batch);

        //draw walls on screen
//        for(Wall cur : mapModel.getWallLocationList() ){
//            cur.draw(batch);
//            if(actor.getBound().overlaps(cur)){
//                playerController.reAdjust();
//                isHitObject = true;
//            }
//        }

        // draw items on screen
        vectorKeySet = mapModel.getItemLocationList().keySet();
        keySetIterator = vectorKeySet.iterator();

        while(keySetIterator.hasNext()){
            Vector2 cur = keySetIterator.next();
            mapModel.getItemLocationList().get(cur).draw(batch, cur);
            if(actor.getBound().overlaps(mapModel.getItemLocationList().get(cur)) ){
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
            if(actor.getBound().overlaps(mapModel.getFriendLocationList().get(cur)) ){
            	System.out.println("Is a friend ");
            }
        }
        
        // draw enemy on screen
        vectorKeySet = mapModel.getEnemyLocationList().keySet();
        keySetIterator = vectorKeySet.iterator();

        while(keySetIterator.hasNext()){
            Vector2 cur = keySetIterator.next();
            mapModel.getEnemyLocationList().get(cur).draw(batch, cur, false);
            if(actor.getBound().overlaps(mapModel.getEnemyLocationList().get(cur)) ){
            	System.out.println("Is a enemy ");
            }
        }



        if(actor.getBound().overlaps(mapModel.getExitDoor()) ){
            if(actor.getPosition().y + actor.getBound().getHeight() <= mapModel.getExitDoor().y + 1f ){
                if(campaign.getMapPack().size == 1 ){
                    game.setScreen(new MainMenuScreen(game));
                } else {
                    campaign.getMapPack().removeIndex(0);
                    actor.getCharacter().promoteUp();
                    game.setScreen(new GameScreen(game, actor.getCharacter(), campaign.getMapPack().get(0), campaign));
                }
            } else{
                playerController.reAdjust();
                isHitObject = true;
            }

        }

        if(actor.getBound().overlaps(mapModel.getEntryDoor()) ){
            playerController.reAdjust();
            isHitObject = true;
        }

        if(isHitObject == false){
            playerController.keyDown();
        }
        batch.end();

        uiStage.draw();
//        try {
//            Thread.sleep(100);
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
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
        uiStage.getViewport().update(Gdx.graphics.getWidth() / uiScale, Gdx.graphics.getHeight() / uiScale);

        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);

        dialogBox = new DialogBox(MainMenuScreen.skin);
        dialogBox.animateText("Hello advanturer  \nMay I offer you a fresh beverage");

        root.add(dialogBox).expand().align(Align.bottom).pad(8f);

    }


}
