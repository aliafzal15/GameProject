package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.chaowang.ddgame.CampaignModel.Campaign;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.Controller.PlayerController;
import com.chaowang.ddgame.PlayModel.Actor;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.PublicParameter;

import java.nio.channels.FileChannel;

public class GameScreen implements Screen{

    private Game game;
    private SpriteBatch batch;
    private PlayerController playerController;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera cam;

    private Actor actor;
    private Map mapModel;
    private Campaign campaign;

    GameScreen(Game game, Character character,Map map, Campaign camp){
        this.game = game;
        this.actor = new Actor(new Vector2(1,1), character);
        this.mapModel = map;
        this.campaign = camp;
        batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("terrain/terrain"+mapModel.getSize() + "x" + mapModel.getSize() + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(this.map);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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



    }

    @Override
    public void render(float delta) {

        renderer.setView(cam);
        renderer.render();

        cam.position.set(actor.getPosition().x + (actor.getCurrentFrame().getRegionWidth() / 2), actor.getPosition().y + actor.getCurrentFrame().getRegionHeight() / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();

        batch.begin();
        batch.draw(actor.getCurrentFrame(), actor.getPosition().x, actor.getPosition().y );

        mapModel.getEntryDoor().draw(batch);
        mapModel.getExitDoor().draw(batch);


        playerController.keyDown();

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
