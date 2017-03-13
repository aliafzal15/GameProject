package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.Controller.PlayerController;
import com.chaowang.ddgame.PlayModel.Actor;
import com.chaowang.ddgame.PublicParameter;

public class GameScreen implements Screen{

    private Game game;
    private SpriteBatch batch;
    private Actor actor;
    private PlayerController playerController;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera cam;

    GameScreen(Game game){
        this.game = game;
        actor = new Actor(new Vector2(1,1));
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("terrain/terrain5x5.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
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
