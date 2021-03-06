package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * class for driving the game view
 * @author chao wang
 * @version 1.0
 */
public class DDGame extends Game {
	private OrthographicCamera camera;

    Game game;

    /**
     * initialize game
     */
    @Override
	public void create () {
		game = this;
        setScreen(new com.chaowang.ddgame.MenuView.MainMenuScreen(game));


		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h/w);




    }
    /**
     * render
     */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f ,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
	}
	
	@Override
	public void dispose () {
    }
}
