package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Controller.MapController;
import Map.Map;

public class MapEditorScreen implements Screen{

	private Game game;
	public Stage stage;
	private SpriteBatch batch;
	public TextButton backwardButton, saveButton, confirmButton;
	public Texture backgroundTexture;
	public TextField nameField, sizeField;

	public ImageButton[] mapMatrix, elementList;
	public Table mapTable, elementTable, inputTable;
	public SelectBox<String> itemSelectBox, friendlySelectBox, hostileSelectBox, mapSelectBox;

	private Map map;
	private MapController mapController;


	public MapEditorScreen (Game game) {
		this.game = game;
	}

	@Override
	public void show() {

		stage = new Stage(new ScreenViewport());
		backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
		batch = new SpriteBatch();

		map = new Map();
		mapController = new MapController(this.map, this);

		backwardButton = new TextButton("<--", MainMenuScreen.buttonStyle);
		backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
		backwardButton.setHeight(Gdx.graphics.getHeight() / 15);

		backwardButton.setPosition(Gdx.graphics.getWidth() * 7 / 10 , (Gdx.graphics.getHeight() * 28 / 30 ) );
		backwardButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MainMenuScreen(game));
				return true;
			}
		});
		stage.addActor(backwardButton);

		mapTable = new Table();
		mapTable.setSize(Gdx.graphics.getWidth() * 2 / 3 , Gdx.graphics.getWidth() * 2 / 3);
		mapTable.setPosition(5, 5);

		stage.addActor(mapTable);

		inputTable = new Table();
		inputTable.setSize(Gdx.graphics.getWidth() / 4 , Gdx.graphics.getHeight() * 1 / 6);
		inputTable.setPosition( Gdx.graphics.getWidth() * 7 / 10 , Gdx.graphics.getHeight() * 3 / 4);
		inputTable.add(new Label("size (2 - 11)", MainMenuScreen.style));
		sizeField = new TextField("", MainMenuScreen.skin);
		inputTable.add(sizeField);
		inputTable.row();
		inputTable.add(new Label("name", MainMenuScreen.style));
		nameField = new TextField("", MainMenuScreen.skin);
		inputTable.add(nameField);
		stage.addActor(inputTable);

		confirmButton = new TextButton("CONFIRM", MainMenuScreen.buttonStyle);
		confirmButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				mapController.controlConfirmButton();
				return true;
			}
		});
		confirmButton.setWidth(Gdx.graphics.getWidth() / 9);
		confirmButton.setHeight(Gdx.graphics.getHeight() / 12);
		confirmButton.setPosition( Gdx.graphics.getWidth() * 7 / 10 , Gdx.graphics.getHeight() * 3 / 4 - confirmButton.getHeight() * 3 / 4);
		stage.addActor(confirmButton);

		elementTable = new Table();
		elementTable.setSize(Gdx.graphics.getWidth() / 4 , Gdx.graphics.getHeight() * 1 / 2);
		elementTable.setPosition( Gdx.graphics.getWidth() * 7 / 10 , Gdx.graphics.getHeight() * 1 / 7);

		elementList = new ImageButton[PublicParameter.mapPixelType];
		mapController.buildElementList();
		mapController.addElementListListener();

		stage.addActor(elementTable);

		saveButton = new TextButton("SAVE", MainMenuScreen.buttonStyle);
		saveButton.setWidth(Gdx.graphics.getWidth() / 9);
		saveButton.setHeight(Gdx.graphics.getHeight() / 12);
		saveButton.setPosition((Gdx.graphics.getWidth() * 8 / 10) - saveButton.getWidth() / 2, (Gdx.graphics.getHeight() / 15));
		saveButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				mapController.controlSaveButton();
				return true;
			}


		});
		stage.addActor(saveButton);
	}
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

	@Override
	public void dispose() {
		stage.dispose();
	}

}
