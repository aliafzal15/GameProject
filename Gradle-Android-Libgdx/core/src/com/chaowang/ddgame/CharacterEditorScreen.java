package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import Character.Character;
import util.AbilityModifier;
import util.Dice;


public class CharacterEditorScreen implements Screen {

	private Game game;
	private Stage stage;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private Table editorTable, inventoryTable;
	private TextButton raceLeftButton, raceRightButton, characterSaveButton, mainPageButton;
	private Character character;
	private Label classLabel, raceLabel;
	private Label hitpointLabel, attackBonusLabel, damageBonusLaber, armorClassLabel,
			strengthLabel, dexterityLabel, constitutionLabel, wisdomLabel, intellegenceLabel, charismaLabel, characterInfoLabel;
	private TextField nameText, levelText;
	private Image characterImage;


	private ImageButton[] inventoryMatrix;
	private ImageButton diceButton ,backpackButton,equipmentPageButton;

	public CharacterEditorScreen(Game game) {
		this.game = game;
	}

	public CharacterEditorScreen(Game game, Character character) {
		this.game = game;
		this.character = character;
	}

	@Override
	public void show() {

		stage = new Stage(new ScreenViewport());
		backgroundTexture = new Texture(Gdx.files.internal("android/assets/EditorBackground.jpg"));
		batch = new SpriteBatch();

		mainPageButton = new TextButton("<--", MainMenu.buttonStyle);
		mainPageButton.setWidth(Gdx.graphics.getWidth() / 20 );
		mainPageButton.setHeight(Gdx.graphics.getHeight() / 10);
		mainPageButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
		mainPageButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				stage.clear();
				game.setScreen(new MainMenu(game));
				return true;
			}
		});
		stage.addActor(mainPageButton);

		if(character == null){
			character = new Character();
		}
		nameText = new TextField(character.getName(), MainMenu.skin);
		if(character.getLevel() > 0){
			levelText = new TextField(Integer.toString(character.getLevel()), MainMenu.skin);
		}
		else{
			levelText = new TextField("0", MainMenu.skin);
		}
		raceLabel = new Label(character.getRaceType().toString(), MainMenu.style);
		characterImage = new Image(character.getTexture());

		raceLeftButton = new TextButton("<", MainMenu.buttonStyle);
		raceLeftButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				character.previousRace();
				raceLabel.setText(character.getRaceType().toString());
				characterImage.setDrawable(new SpriteDrawable(new Sprite(character.getTexture())));
				return true;
			}
		});

		raceRightButton = new TextButton(">", MainMenu.buttonStyle);
		raceRightButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				character.nextRace();
				raceLabel.setText(character.getRaceType().toString());
				characterImage.setDrawable(new SpriteDrawable(new Sprite(character.getTexture())));
				return true;
			}
		});

		classLabel = new Label("Fighter",MainMenu.style);
		strengthLabel = new Label(Integer.toString(character.getStrength()),MainMenu.style);
		dexterityLabel = new Label(Integer.toString(character.getDexterity()),MainMenu.style);
		constitutionLabel = new Label(Integer.toString(character.getConstitution()),MainMenu.style);
		wisdomLabel = new Label(Integer.toString(character.getWisdom()),MainMenu.style);
		intellegenceLabel = new Label(Integer.toString(character.getIntelligence()),MainMenu.style);
		charismaLabel = new Label(Integer.toString(character.getCharisma()),MainMenu.style);
		hitpointLabel = new Label(Integer.toString(character.getHitPoints()),MainMenu.style);
		attackBonusLabel  = new Label(Integer.toString(character.getAttackBonus()),MainMenu.style);
		damageBonusLaber  = new Label(Integer.toString(character.getDamageBonus()),MainMenu.style);
		armorClassLabel  = new Label(Integer.toString(character.getArmorClass()),MainMenu.style);


		editorTable = new Table();

		editorTable.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() );
		editorTable.setPosition(Gdx.graphics.getWidth() / 10, 2);


		editorTable.add(new Label("", MainMenu.style)).width(80);
		editorTable.add(characterImage).maxSize(250, 250).center();
		editorTable.row();
		editorTable.add(new Label("Name", MainMenu.style));
		editorTable.add(nameText);
		editorTable.row();
		editorTable.add(new Label("Level", MainMenu.style));
		editorTable.add(levelText);
		editorTable.add(new Label("1 - 9", MainMenu.style));
		editorTable.row();
		editorTable.add(raceLeftButton).size(50, 50);
		editorTable.add(raceLabel).center();
		editorTable.add(raceRightButton).size(50, 50).expandX();
		editorTable.row();
		editorTable.add(new Label("class", MainMenu.style));
		editorTable.add(classLabel);
		editorTable.row();
		editorTable.add(new Label("strength", MainMenu.style));
		editorTable.add(strengthLabel);
		editorTable.row();
		editorTable.add(new Label("dexterity", MainMenu.style));
		editorTable.add(dexterityLabel);
		editorTable.row();
		editorTable.add(new Label("constitution", MainMenu.style));
		editorTable.add(constitutionLabel);
		editorTable.row();
		editorTable.add(new Label("wisdom", MainMenu.style));
		editorTable.add(wisdomLabel);
		editorTable.row();
		editorTable.add(new Label("intelligence", MainMenu.style));
		editorTable.add(intellegenceLabel);
		editorTable.row();
		editorTable.add(new Label("charisma", MainMenu.style));
		editorTable.add(charismaLabel);
		editorTable.row();
		editorTable.add(new Label("hit point", MainMenu.style));
		editorTable.add(hitpointLabel);
		editorTable.row();
		editorTable.add(new Label("armor class", MainMenu.style));
		editorTable.add(armorClassLabel);
		editorTable.row();
		editorTable.add(new Label("attach bonus", MainMenu.style));
		editorTable.add(attackBonusLabel);
		editorTable.row();
		editorTable.add(new Label("damage bonus", MainMenu.style));
		editorTable.add(damageBonusLaber);
		editorTable.row();

		stage.addActor(editorTable);

		characterSaveButton = new TextButton("SAVE", MainMenu.buttonStyle);
		characterSaveButton.setWidth(Gdx.graphics.getWidth() / 9);
		characterSaveButton.setHeight(Gdx.graphics.getHeight() / 9);
		characterSaveButton.setPosition((Gdx.graphics.getWidth() * 1 / 2) + 50 , (Gdx.graphics.getHeight() / 20));
		characterSaveButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (levelText.getText().matches("^[1-9]$") && Integer.parseInt(wisdomLabel.getText().toString())!=0 ) {
					character.setLevel(Integer.parseInt(levelText.getText()));
					character.setName(nameText.getText());
					MainMenu.characterInventory.addToInventory(character);
					MainMenu.characterInventory.saveToFile();
					inventoryTable.clearChildren();
					buildInventoryMatrix();
					addInventoryMatrixListener();
					character = new Character();
					initialEditorItem();
				}
                else{
                	new Dialog("Error", MainMenu.skin, "dialog") {
                	}.text("Character input value error").button("OK", true).key(Keys.ENTER, true)
                	    .show(stage);
                }
				return true;
			}
		});
		stage.addActor(characterSaveButton);



		equipmentPageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/equipment.png")))));
		equipmentPageButton.setWidth(Gdx.graphics.getWidth() / 15);
		equipmentPageButton.setHeight(Gdx.graphics.getHeight() / 15);
		equipmentPageButton.setPosition((Gdx.graphics.getWidth() * 1 / 2)  - 75 , (Gdx.graphics.getHeight() / 3));
		equipmentPageButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (levelText.getText().matches("^[1-9]$")) {
					character.setLevel(Integer.parseInt(levelText.getText()));
					character.setName(nameText.getText());
					game.setScreen(new EquipmentEditorScreen(game, character));
				}
				return true;
			}
		});
		stage.addActor(equipmentPageButton);

		backpackButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/backpack.png")))));
		backpackButton.setWidth(Gdx.graphics.getWidth() / 15);
		backpackButton.setHeight(Gdx.graphics.getHeight() / 15);
		backpackButton.setPosition((Gdx.graphics.getWidth() * 1 / 2)  - 75 , (Gdx.graphics.getHeight() / 5));
		backpackButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (levelText.getText().matches("^[1-9]$")) {
					character.setLevel(Integer.parseInt(levelText.getText()));
					character.setName(nameText.getText());
					game.setScreen(new BackpackEditorScreen(game, character));
				}
				return true;
			}
		});
		stage.addActor(backpackButton);


		diceButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/dice.png")))));
		diceButton.setWidth(Gdx.graphics.getWidth() / 15);
		diceButton.setHeight(Gdx.graphics.getHeight() / 15);
		diceButton.setPosition((Gdx.graphics.getWidth() * 1 / 2)  - 75 , (Gdx.graphics.getHeight() / 12));
		diceButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (levelText.getText().matches("^[1-9]$") ) {
					character.setLevel(Integer.parseInt(levelText.getText()));
					int multiplier = Integer.parseInt(levelText.getText()) / 2;
					character.setStrength(Dice.roll(Dice.DICENUMBER, Dice.DICESIDE + multiplier ));
					character.setDexterity(Dice.roll(Dice.DICENUMBER, Dice.DICESIDE + multiplier ));
					character.setConstitution(Dice.roll(Dice.DICENUMBER, Dice.DICESIDE + multiplier ));
					character.setWisdom(Dice.roll(Dice.DICENUMBER, Dice.DICESIDE + multiplier ));
					character.setIntelligence(Dice.roll(Dice.DICENUMBER, Dice.DICESIDE + multiplier ));
					character.setCharisma(Dice.roll(Dice.DICENUMBER, Dice.DICESIDE + multiplier ));

					strengthLabel.setText(Integer.toString(character.getStrength()));
					dexterityLabel.setText(Integer.toString(character.getDexterity()));
					constitutionLabel.setText(Integer.toString(character.getConstitution()));
					wisdomLabel.setText(Integer.toString(character.getWisdom()));
					intellegenceLabel.setText(Integer.toString(character.getIntelligence()));
					charismaLabel.setText(Integer.toString(character.getCharisma()));

					character.setHitPoints(AbilityModifier.hitPointModifier(character.getConstitution(), character.getLevel()));
					System.out.println("Dexterity is" + character.getDexterity() );
					character.setArmorClass(AbilityModifier.armorClassModifier(character.getDexterity()));
					character.setAttackBonus(AbilityModifier.attachBonusModifier(character.getStrength(), character.getDexterity(), character.getLevel()));
					character.setDamageBonus(AbilityModifier.damageBonusModifier(character.getStrength()));

					hitpointLabel.setText(Integer.toString(character.getHitPoints()));
					armorClassLabel.setText(Integer.toString(character.getArmorClass()));
					attackBonusLabel.setText(Integer.toString(character.getAttackBonus()));
					damageBonusLaber.setText(Integer.toString(character.getDamageBonus()));
				}
				return true;
			}
		});
		stage.addActor(diceButton);

		// Right hand side
		characterInfoLabel = new Label("", MainMenu.style);
		characterInfoLabel.setPosition((Gdx.graphics.getWidth() * 3 / 7), (Gdx.graphics.getHeight() * 1 / 5));
		stage.addActor(characterInfoLabel);

		inventoryTable = new Table();

		inventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 4 / 5);
		inventoryTable.setPosition(Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() * 1 / 6);
		//inventoryTable.setBounds(Gdx.graphics.getWidth() / 48, 0, Gdx.graphics.getWidth() - ( Gdx.graphics.getWidth() / 24), Gdx.graphics.getHeight());

		inventoryMatrix = new ImageButton[PublicParameter.characterInventoryRow * PublicParameter.characterInventoryColumn];
		buildInventoryMatrix();
		addInventoryMatrixListener();

		stage.addActor(inventoryTable);
	}

	private void initialEditorItem() {
		raceLabel.setText(character.getRaceType().toString());
		characterImage.setDrawable(new SpriteDrawable(new Sprite(character.getTexture())));
		levelText.setText(Integer.toString(character.getLevel()));
		nameText.setText(character.getName());
		strengthLabel.setText(Integer.toString(character.getStrength()));
		dexterityLabel.setText(Integer.toString(character.getDexterity()));
		constitutionLabel.setText(Integer.toString(character.getConstitution()));
		wisdomLabel.setText(Integer.toString(character.getWisdom()));
		intellegenceLabel.setText(Integer.toString(character.getIntelligence()));
		charismaLabel.setText(Integer.toString(character.getCharisma()));
		hitpointLabel.setText(Integer.toString(character.getHitPoints()));
		armorClassLabel.setText(Integer.toString(character.getArmorClass()));
		attackBonusLabel.setText(Integer.toString(character.getAttackBonus()));
		damageBonusLaber.setText(Integer.toString(character.getDamageBonus()));

	}

	private void buildInventoryMatrix() {
		for (int i = 0; i < PublicParameter.characterInventoryRow; i++) {
			for (int j = 0; j < PublicParameter.characterInventoryColumn; j++) {
				if ((i * PublicParameter.characterInventoryColumn) + j < MainMenu.characterInventory.getChatacterPack().size ) {
					inventoryMatrix[(i * PublicParameter.characterInventoryColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(MainMenu.characterInventory.getChatacterPack().get(i * PublicParameter.characterInventoryColumn + j).getTexture())));
				} else {
					inventoryMatrix[(i * PublicParameter.characterInventoryColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
				}
				ImageButton tempButton = inventoryMatrix[(i * PublicParameter.characterInventoryColumn) + j];
				inventoryTable.add(tempButton).width(PublicParameter.characterCellWidth).height(PublicParameter.characterCellHeight).space(15);
			}
			inventoryTable.row();
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.input.setInputProcessor(stage);
		stage.act();

		batch.enableBlending();

		batch.begin();

		stage.getBatch().begin();
		stage.getBatch().draw(backgroundTexture, 0, 0,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		stage.getBatch().end();

		stage.draw();

		batch.end();
	}

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


	private void addInventoryMatrixListener() {
		for (int i = 0; i < MainMenu.characterInventory.getChatacterPack().size ; i++){
			inventoryMatrix[i].addListener(new ClickListener(i) {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					character = new Character(
							MainMenu.characterInventory.getChatacterPack().get(getButton()).getName(),
							MainMenu.characterInventory.getChatacterPack().get(getButton()).getLevel(),
							MainMenu.characterInventory.getChatacterPack().get(getButton()).getRaceType(),
							MainMenu.characterInventory.getChatacterPack().get(getButton()).getAllAttributes(),
							MainMenu.characterInventory.getChatacterPack().get(getButton()).getBackpack(),
							MainMenu.characterInventory.getChatacterPack().get(getButton()).getEquipment());
					initialEditorItem();
					MainMenu.characterInventory.getChatacterPack().removeIndex(getButton());
					MainMenu.characterInventory.saveToFile();
					inventoryTable.clearChildren();
					buildInventoryMatrix();
					addInventoryMatrixListener();
					return true;
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					characterInfoLabel.setText(MainMenu.characterInventory.getChatacterPack().get(getButton()).toString());
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					characterInfoLabel.setText("");
				}
			});
		}
	}


}
