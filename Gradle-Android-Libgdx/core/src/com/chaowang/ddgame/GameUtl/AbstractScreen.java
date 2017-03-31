package com.chaowang.ddgame.GameUtl;


import com.badlogic.gdx.Screen;
import com.chaowang.ddgame.DDGame;

/**
 * @author hydrozoa
 */
public abstract class AbstractScreen implements Screen {

    private DDGame app;

    public AbstractScreen(DDGame app) {
        this.app = app;
    }

    @Override
    public abstract void dispose();

    @Override
    public abstract void hide();

    @Override
    public abstract void pause();

    public abstract void update(float delta);

    @Override
    public abstract void render(float delta);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void resume();

    @Override
    public abstract void show();

    public DDGame getApp() {
        return app;
    }

}
