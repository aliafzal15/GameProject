package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


class TableScreen implements Screen {
    Game game;

    Stage stage;
    Table table;
    SpriteBatch batch;
    TextButton buybutton;

    public TableScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();

        table = new Table();
        table.setBounds(Gdx.graphics.getWidth() / 48, 0, Gdx.graphics.getWidth() - ( Gdx.graphics.getWidth() / 24), Gdx.graphics.getHeight());

        table.debug();

        buybutton = new TextButton("Buy?", MainMenu.buttonStyle);

        table.add(new Label("Title", MainMenu.style)).expandX();
        table.row();
        table.add(new Label("Buy", MainMenu.style)).expandX();
        table.row();
        table.add(new Label("Item", MainMenu.style));
        table.add(new Label("Item", MainMenu.style));
        table.add(buybutton).size(100, 50);



        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);
        stage.act();

        batch.begin();
        stage.draw();
        stage.setDebugAll(true);
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
