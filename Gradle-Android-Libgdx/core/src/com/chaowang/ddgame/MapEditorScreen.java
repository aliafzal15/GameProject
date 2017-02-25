package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Items.Item;
import Character.Character;
import Map.Map;
import util.MazeSolver;

public class MapEditorScreen implements Screen{

	private Game game;
	private Stage stage;
	private SpriteBatch batch;
	private TextButton backwardButton, saveButton, confirmButton;
	private Texture backgroundTexture, imageTexture;
	private TextField nameField, sizeField, levelField;

	private ImageButton[] mapMatrix, elementList;
	private Table mapTable, elementTable, inputTable;
	private SelectBox<String> itemSelectBox, friendlySelectBox, hostileSelectBox, mapSelectBox;

	private Map map;
	private int matrixPointer = 0;
    private Item itemCarrier;
    private Character characterCarrier;

	public MapEditorScreen (Game game) {
		this.game = game;
	}

	@Override
	public void show() {

		stage = new Stage(new ScreenViewport());
		backgroundTexture = new Texture(Gdx.files.internal("android/assets/EditorBackground.jpg"));
		batch = new SpriteBatch();

		map = new Map();
		matrixPointer = 0;

		backwardButton = new TextButton("<--", MainMenu.buttonStyle);
		backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
		backwardButton.setHeight(Gdx.graphics.getHeight() / 15);

		backwardButton.setPosition(Gdx.graphics.getWidth() * 7 / 10 , (Gdx.graphics.getHeight() * 28 / 30 ) );
		backwardButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MainMenu(game));
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
		inputTable.add(new Label("size (2 - 9)", MainMenu.style));
		sizeField = new TextField("", MainMenu.skin);
		inputTable.add(sizeField);
		inputTable.row();
		inputTable.add(new Label("level (1 - 9)", MainMenu.style));
		levelField = new TextField("", MainMenu.skin);
		inputTable.add(levelField);
		inputTable.row();
		inputTable.add(new Label("name", MainMenu.style));
		nameField = new TextField("", MainMenu.skin);
		inputTable.add(nameField);
		stage.addActor(inputTable);

		confirmButton = new TextButton("CONFIRM", MainMenu.buttonStyle);
		confirmButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (sizeField.getText().matches("^[1-9]$|^0[1-9]$|^1[0-1]$") &&
						levelField.getText().matches("^[1-9]$") &&
						( ! nameField.getText().equals(""))) {
					mapMatrix = new ImageButton[Integer.parseInt(sizeField.getText()) * Integer.parseInt(sizeField.getText())];
					map.setSize(Integer.parseInt(sizeField.getText()));
					map.setLevel(Integer.parseInt(levelField.getText()));
					map.setName(nameField.getText());
					buildMapMatrix();
					addMapMatrixListener();
					sizeField.setDisabled(true);
					levelField.setDisabled(true);
					nameField.setDisabled(true);
					confirmButton.setTouchable(Touchable.disabled);
				}
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
		buildElementList();
		addElementListListener();

		stage.addActor(elementTable);

		saveButton = new TextButton("SAVE", MainMenu.buttonStyle);
		saveButton.setWidth(Gdx.graphics.getWidth() / 9);
		saveButton.setHeight(Gdx.graphics.getHeight() / 12);
		saveButton.setPosition((Gdx.graphics.getWidth() * 8 / 10) - saveButton.getWidth() / 2, (Gdx.graphics.getHeight() / 15));
		saveButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				int entryCount = map.validateEntryDoor();
				int exitCount = map.validateExitDoor();
				if (Integer.parseInt(sizeField.getText()) == map.getSize() && entryCount > 0 && exitCount > 0 ) {
					if(entryCount == 1 && exitCount == 1){
						MazeSolver solver = new MazeSolver();
						if(solver.solveMaze(map.getLocationMatrix())){
							map.addWall();
							MainMenu.mapInventory.addToInventory(map);
							MainMenu.mapInventory.saveToFile();
							sizeField.setText("");
							sizeField.setDisabled(false);
							levelField.setText("");
							levelField.setDisabled(false);
							nameField.setText("");
							nameField.setDisabled(false);
							confirmButton.setTouchable(Touchable.enabled);
							mapTable.clearChildren();
						}
						else{
							new Dialog("Error", MainMenu.skin, "dialog") {
							}.text("No path to exit").button("OK", true).key(Keys.ENTER, true)
									.show(stage);
						}
					}
					else{
						new Dialog("Error", MainMenu.skin, "dialog") {
						}.text("Map entry door exit door more than 1").button("OK", true).key(Keys.ENTER, true)
								.show(stage);
					}
				}
				else{
					new Dialog("Error", MainMenu.skin, "dialog") {
					}.text("Map does not have entry door or exit door").button("OK", true).key(Keys.ENTER, true)
							.show(stage);
				}
				return true;
			}


		});
		stage.addActor(saveButton);
	}

	private void buildMapMatrix() {
		int size = map.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
 				switch (map.getLocationMatrix()[i][j]){
                    case -3:
                        mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/enemy.png")))));
                        break;
                    case -2:
                        mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/friend.png")))));
                        break;
                    case -1:
                        mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/chest.png")))));
                        break;
                    case 1:
						mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/wall.png")))));
						break;
					case 2:
						mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/entryDoor.png")))));
						break;
					case 3:
						mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/exitDoor.png")))));
						break;
					default:
						mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/pave.png")))));
						break;
				}
				mapTable.add(mapMatrix[(i * size) + j]).fill();
			}
			mapTable.row();
		}
	}

	private void buildElementList() {

		for (int i = 0 ; i < elementList.length ; i++){
			switch (i){
				case 1:
					elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/wall.png")))));
					break;
				case 2:
					elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/entryDoor.png")))));
					break;
				case 3:
					elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/exitDoor.png")))));
					break;
				default:
					elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/map/pave.png")))));
					break;
			}
			elementTable.add(elementList[i]).fill().expandX();
		}
		elementTable.row();
		elementTable.add(new Label("Item", MainMenu.style));
		itemSelectBox = new SelectBox<String>(MainMenu.skin);
		itemSelectBox.setItems(MainMenu.itemInventory.getItemPackInfo());
		elementTable.add(itemSelectBox).colspan(3);
		elementTable.row();
		elementTable.add(new Label("Friendly ",MainMenu.style));
		friendlySelectBox = new SelectBox<String>(MainMenu.skin);
		friendlySelectBox.setItems(MainMenu.characterInventory.getCharacterListInfo());
		elementTable.add(friendlySelectBox).colspan(3);
		elementTable.row();
		elementTable.add(new Label("Hostile ", MainMenu.style));
		hostileSelectBox = new SelectBox<String>(MainMenu.skin);
		hostileSelectBox.setItems(MainMenu.characterInventory.getCharacterListInfo());
		elementTable.add(hostileSelectBox).colspan(3);
		elementTable.row();
		elementTable.add(new Label("Maps ", MainMenu.style));
		mapSelectBox = new SelectBox<String>(MainMenu.skin);
		mapSelectBox.setItems(MainMenu.characterInventory.getCharacterListInfo());
		elementTable.add(mapSelectBox).colspan(3);
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

	private void addElementListListener(){
		for (int i = 0; i < elementList.length; i++){
			elementList[i].addListener(new ClickListener(i){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					matrixPointer = getButton();
					return true;
				}
			});
		}

		itemSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String text = itemSelectBox.getSelected();
                int level = Integer.parseInt(text.substring(text.lastIndexOf('-')+1));
                System.out.println("item level is "+ level);
                if(level == map.getLevel()){
                    int index = Integer.parseInt(text.substring(0, text.indexOf('-')));
                    matrixPointer = -1;
                    itemCarrier = MainMenu.itemInventory.getItemPack().get(index);
                }
                else{
                	new Dialog("Error", MainMenu.skin, "dialog") {
                	}.text("Item level not same as map level").button("OK", true).key(Keys.ENTER, true)
                	    .show(stage);
                }
			}
		});

        friendlySelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = friendlySelectBox.getSelected();
                int level = Integer.parseInt(text.substring(text.lastIndexOf('-')+1));
                if(level == map.getLevel()) {
                    int index = Integer.parseInt(text.substring(0, text.indexOf('-')));
                    System.out.println("friend level is "+ level);
                    matrixPointer = -2;
                    characterCarrier = MainMenu.characterInventory.getChatacterPack().get(index);
                }
                else{
                	new Dialog("Error", MainMenu.skin, "dialog") {
                	}.text("Character level not same as map level").button("OK", true).key(Keys.ENTER, true)
                	    .show(stage);
                }
            }
        });

        hostileSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = hostileSelectBox.getSelected();
                int level = Integer.parseInt(text.substring(text.lastIndexOf('-')+1));
                if(level == map.getLevel()) {
                    int index = Integer.parseInt(text.substring(0, text.indexOf('-')));
                    matrixPointer = -3;
                    characterCarrier = MainMenu.characterInventory.getChatacterPack().get(index);
                }
                else{
                	new Dialog("Error", MainMenu.skin, "dialog") {
                	}.text("Character level not same as map level").button("OK", true).key(Keys.ENTER, true)
                	    .show(stage);
                }
            }
        });
	}

	private void addMapMatrixListener() {
		for (int i = 0; i < mapMatrix.length; i++) {
			mapMatrix[i].addListener(new ClickListener(i) {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    int i = getButton() / map.getSize();
                    int j = getButton() % map.getSize();
                    switch (map.getLocationMatrix()[i][j]){
                        case -3:
                            map.removeEnemyLocationList(i,j);
                            break;
                        case -2:
                            map.removeFriendLocationList(i,j);
                            break;
                        case -1:
                            map.removeItemLocationList(i,j);
                            break;
                    }
					map.getLocationMatrix()[i][j] = matrixPointer;
                    switch (map.getLocationMatrix()[i][j]){
                        case -3:
                            map.addEnemyLocationList(i,j,characterCarrier);
                            break;
                        case -2:
                            map.addFriendLocationList(i,j,characterCarrier);
                            break;
                        case -1:
                            map.addItemLocationList(i,j, itemCarrier);
                            break;
                    }
					mapTable.clearChildren();
					buildMapMatrix();
					addMapMatrixListener();
					return true;
				}
			});
		}
	}

}
