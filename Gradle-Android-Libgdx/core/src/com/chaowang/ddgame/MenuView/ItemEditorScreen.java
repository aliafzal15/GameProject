package com.chaowang.ddgame.MenuView;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.chaowang.ddgame.MenuController.ItemController;
import com.chaowang.ddgame.ItemModel.Item;
import com.chaowang.ddgame.PublicParameter;

/**
 * view for item editor 
 * @author chao wang
 * @version 1.0
 */
public class ItemEditorScreen implements Screen {
    private Game game;
	public Stage stage;
	public Texture backgroundTexture;
	public Table editorTable, inventoryTable;
	public TextButton itemLeftButton, itemRightButton, abilityLeftButton, abilityRightButton, itemSaveButton, mainPageButton;
	public Label itemLabel, abilityLabel, itemInfoLabel;
	public TextField nameText, levelText;
	public Image itemImage;
	public ImageButton[] inventoryMatrix;

	private SpriteBatch batch;
	private ItemController itemController;
	private Item item;
	/**
	 * constructor
	 * @param game
	 */
	public ItemEditorScreen(Game game) {
        this.game = game;
	}
    /**
     * show whole item editor view on screen
     */
	@Override
	public void show() {

		stage = new Stage(new ScreenViewport());
		backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
		batch = new SpriteBatch();

		item = new Item();
		itemController = new ItemController(this , this.item);

		mainPageButton = new TextButton("<--", MainMenuScreen.buttonStyle);
		mainPageButton.setWidth(Gdx.graphics.getWidth() / 20 );
		mainPageButton.setHeight(Gdx.graphics.getHeight() / 10);
		mainPageButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
		mainPageButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				stage.clear();
				game.setScreen(new MainMenuScreen(game));
				return true;
			}
		});
		stage.addActor(mainPageButton);

		nameText = new TextField(item.getName(), MainMenuScreen.skin);
		levelText = new TextField("0", MainMenuScreen.skin);
		itemLabel = new Label(item.getItemType().toString(), MainMenuScreen.style);
		abilityLabel = new Label(item.getEnchantedAbility().toString(), MainMenuScreen.style);
		itemImage = new Image(item.getTexture());

		itemLeftButton = new TextButton("<", MainMenuScreen.buttonStyle);
		itemLeftButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				itemController.controlItemLeftButton();
				return true;
			}
		});

		itemRightButton = new TextButton(">", MainMenuScreen.buttonStyle);
		itemRightButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				itemController.controlItemRightButton();
				return true;
			}
		});

		abilityLeftButton = new TextButton("<", MainMenuScreen.buttonStyle);
		abilityLeftButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				itemController.controlAbilityLeftButton();
				return true;
			}
		});

		abilityRightButton = new TextButton(">", MainMenuScreen.buttonStyle);
		abilityRightButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				itemController.controlAbilityRightButton();
				return true;
			}
		});

		editorTable = new Table();
		editorTable.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2);
		editorTable.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() * 1 / 4);

		editorTable.add(new Label("", MainMenuScreen.style)).width(80);
		editorTable.add(itemImage).maxSize(200, 200).center();
		editorTable.row();
		editorTable.add(new Label("Name", MainMenuScreen.style));
		editorTable.add(nameText);
		editorTable.row();
		editorTable.add(new Label("Bonus point", MainMenuScreen.style));
		editorTable.add(levelText);
		editorTable.add(new Label("1 - 5", MainMenuScreen.style));
		editorTable.row();
		editorTable.add(itemLeftButton).size(50, 50);
		editorTable.add(itemLabel).center();
		editorTable.add(itemRightButton).size(50, 50).expandX();
		editorTable.row();
		editorTable.add(abilityLeftButton).size(50, 50);
		editorTable.add(abilityLabel).center();
		editorTable.add(abilityRightButton).size(50, 50).expandX();

		stage.addActor(editorTable);

		itemSaveButton = new TextButton("SAVE", MainMenuScreen.buttonStyle);
		itemSaveButton.setWidth(Gdx.graphics.getWidth() / 9);
		itemSaveButton.setHeight(Gdx.graphics.getHeight() / 9);
		itemSaveButton.setPosition((Gdx.graphics.getWidth() * 1 / 4) - itemSaveButton.getWidth() / 2, (Gdx.graphics.getHeight() / 8));
		itemSaveButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				itemController.controlSaveButton();
				return true;
			}
		});
		
		stage.addActor(itemSaveButton);

		// Right hand side
		itemInfoLabel = new Label("", MainMenuScreen.style);
		itemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 2), (Gdx.graphics.getHeight() / 8));
		stage.addActor(itemInfoLabel);

		inventoryTable = new Table();

		inventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 4 / 5);
		inventoryTable.setPosition(Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() * 1 / 10);

		inventoryMatrix = new ImageButton[PublicParameter.ITEM_INVENTORY_ROW * PublicParameter.ITEM_INVENTORY_COLUMN];
	 	itemController.buildInventoryMatrix();
		itemController.addInventoryMatrixListener();

		stage.addActor(inventoryTable);
	}
	 /**
     * put image on background
     */
	@Override
	public void render(float delta) {
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);
        stage.act();

		batch.enableBlending();
		batch.begin();

		stage.getBatch().begin();
		stage.getBatch().draw(backgroundTexture, 0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		stage.getBatch().end();

        stage.draw();
		//stage.setDebugAll(true);
        batch.end();
	}
    /**
     * update the item editor view
     */
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();

	}


}
