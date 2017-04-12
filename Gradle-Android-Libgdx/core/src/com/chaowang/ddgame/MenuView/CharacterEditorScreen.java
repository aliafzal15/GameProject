package com.chaowang.ddgame.MenuView;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.MenuModel.CharacterModel.Abilities;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuController.CharacterController;
import com.chaowang.ddgame.PublicParameter;

/**
 * view for character editor
 * @author chao wang
 * @version 1.0
 */
public class CharacterEditorScreen implements Screen {

    private Game game;
    public Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    public Table editorTable, inventoryTable;
    public TextButton raceLeftButton, raceRightButton, levelLeftButton, levelRightButton,fighterLeftButton, fighterRightButton, characterSaveButton, mainPageButton, confirmButton;
    private Character character;
    private CharacterController controller;
    public Label raceLabel, levelLabel, fighterTypeLabel, promotePointLabel;
    public Label hitpointLabel, attackBonusLabel, damageBonusLaber, armorClassLabel,
            strengthLabel, dexterityLabel, constitutionLabel, wisdomLabel, intellegenceLabel, charismaLabel, characterInfoLabel;
    public TextField[] bonusField;
    public TextField nameText;
    public Image characterImage;

    public ImageButton[] inventoryMatrix;
    private ImageButton diceButton ,backpackButton,equipmentPageButton;
    /**
     * Constructor
     * @param game
     */
    public CharacterEditorScreen(Game game) {
        this.game = game;
    }
    /**
     * Constructor
     * @param game
     * @param character
     */
    public CharacterEditorScreen(Game game, Character character) {
        this.game = game;
        this.character = character;
    }
    /**
     * show whole character view on screen
     */
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
        batch = new SpriteBatch();
        //log label

        MainMenuScreen.logArea.setPosition(Gdx.graphics.getWidth()*3/4,Gdx.graphics.getHeight() /40 );
        MainMenuScreen.logArea.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/8);
        stage.addActor(MainMenuScreen.logArea);

        mainPageButton = new TextButton("<--", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        mainPageButton.setWidth(Gdx.graphics.getWidth() / 20 );
        mainPageButton.setHeight(Gdx.graphics.getHeight() / 10);
        mainPageButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
        mainPageButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                game.setScreen(new com.chaowang.ddgame.MenuView.MainMenuScreen(game));
                return true;
            }
        });
        stage.addActor(mainPageButton);

        if(character == null){
            character = new Character();
        }
        controller = new CharacterController(this, this.character);
        nameText = new TextField(character.getName(), com.chaowang.ddgame.MenuView.MainMenuScreen.skin);

        if(character.getStrength() > 0){
            levelLabel = new Label(Integer.toString(character.getLevel()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        }
        else{
            levelLabel = new Label("1", com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        }

        raceLabel = new Label(character.getRaceType().toString(), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        characterImage = new Image(character.getTexture());

        raceLeftButton = new TextButton("<", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        raceLeftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlRaceLeftButton();
                return true;
            }
        });

        raceRightButton = new TextButton(">", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        raceRightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlRaceRightButotn();
                return true;
            }
        });

        fighterTypeLabel = new Label(character.getFighterType().toString(), com.chaowang.ddgame.MenuView.MainMenuScreen.style);

        fighterLeftButton = new TextButton("<", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        fighterLeftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlFighterLeftButton();
                return true;
            }
        });

        fighterRightButton = new TextButton(">", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        fighterRightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlFighterRightButotn();
                return true;
            }
        });


        levelLeftButton = new TextButton("<", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        levelLeftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlLevelLeftButton();
                return true;
            }
        });
        levelLeftButton.setTouchable(Touchable.disabled);

        levelRightButton = new TextButton(">", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        levelRightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlLevelRightButton();
                return true;
            }
        });
        levelRightButton.setTouchable(Touchable.disabled);

        confirmButton = new TextButton("OK", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        confirmButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlConfirmButton();
                return true;
            }
        });
        confirmButton.setTouchable(Touchable.disabled);

        promotePointLabel = new Label(Integer.toString(character.getPromotionPoint()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        strengthLabel = new Label(Integer.toString(character.getStrength()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        dexterityLabel = new Label(Integer.toString(character.getDexterity()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        constitutionLabel = new Label(Integer.toString(character.getConstitution()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        wisdomLabel = new Label(Integer.toString(character.getWisdom()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        intellegenceLabel = new Label(Integer.toString(character.getIntelligence()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        charismaLabel = new Label(Integer.toString(character.getCharisma()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        hitpointLabel = new Label(Integer.toString(character.getHitPoints()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        attackBonusLabel  = new Label(Integer.toString(character.getAttackBonus()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        damageBonusLaber  = new Label(Integer.toString(character.getDamageBonus()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        armorClassLabel  = new Label(Integer.toString(character.getArmorClass()), com.chaowang.ddgame.MenuView.MainMenuScreen.style);

        bonusField = new TextField[Abilities.ABILITYSIZE];
        for (int i = 0;  i < bonusField.length ; i ++){
            bonusField[i] =  new TextField(Integer.toString(character.getAbilityBonusArr()[i]), com.chaowang.ddgame.MenuView.MainMenuScreen.skin);
            bonusField[i].setDisabled(true);
        }

        editorTable = new Table();

        editorTable.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() );
        editorTable.setPosition(Gdx.graphics.getWidth() / 10, 2);

        editorTable.add(new Label("", com.chaowang.ddgame.MenuView.MainMenuScreen.style)).width(80);
        editorTable.add(characterImage).maxSize(250, 250).center();
        editorTable.row();
        editorTable.add(new Label("Name", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(nameText);
        editorTable.row();
        editorTable.add(levelLeftButton).size(50, 50);
        editorTable.add(levelLabel).center();
        editorTable.add(levelRightButton).size(50, 50).expandX();
        editorTable.row();
        editorTable.add(raceLeftButton).size(50, 50);
        editorTable.add(raceLabel).center();
        editorTable.add(raceRightButton).size(50, 50).expandX();
        editorTable.row();
        editorTable.add(fighterLeftButton).size(50, 50);
        editorTable.add(fighterTypeLabel).center();
        editorTable.add(fighterRightButton).size(50, 50).expandX();
        editorTable.row();
        editorTable.add(new Label("Promo Point", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(promotePointLabel);
        editorTable.add(confirmButton).size(50, 50).expandX();
        editorTable.row();
        editorTable.add(new Label("strength", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(strengthLabel);
        editorTable.add(bonusField[0]).width(30);
        editorTable.row();
        editorTable.add(new Label("dexterity", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(dexterityLabel);
        editorTable.add(bonusField[1]).width(30);
        editorTable.row();
        editorTable.add(new Label("constitution", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(constitutionLabel);
        editorTable.add(bonusField[2]).width(30);
        editorTable.row();
        editorTable.add(new Label("wisdom", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(wisdomLabel);
        editorTable.add(bonusField[3]).width(30);
        editorTable.row();
        editorTable.add(new Label("intelligence", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(intellegenceLabel);
        editorTable.add(bonusField[4]).width(30);
        editorTable.row();
        editorTable.add(new Label("charisma", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(charismaLabel);
        editorTable.add(bonusField[5]).width(30);
        editorTable.row();
        editorTable.add(new Label("hit point", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(hitpointLabel);
        editorTable.row();
        editorTable.add(new Label("armor class", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(armorClassLabel);
        editorTable.row();
        editorTable.add(new Label("attach bonus", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(attackBonusLabel);
        editorTable.row();
        editorTable.add(new Label("damage bonus", com.chaowang.ddgame.MenuView.MainMenuScreen.style));
        editorTable.add(damageBonusLaber);
        editorTable.row();

        stage.addActor(editorTable);

        characterSaveButton = new TextButton("SAVE", com.chaowang.ddgame.MenuView.MainMenuScreen.buttonStyle);
        characterSaveButton.setWidth(Gdx.graphics.getWidth() / 9);
        characterSaveButton.setHeight(Gdx.graphics.getHeight() / 9);
        characterSaveButton.setPosition((Gdx.graphics.getWidth() * 1 / 2) + 50 , (Gdx.graphics.getHeight() / 20));
        characterSaveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlSaveButton();
                return true;
            }
        });
        stage.addActor(characterSaveButton);

        equipmentPageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("equipment.png")))));
        equipmentPageButton.setWidth(Gdx.graphics.getWidth() / 15);
        equipmentPageButton.setHeight(Gdx.graphics.getHeight() / 15);
        equipmentPageButton.setPosition((Gdx.graphics.getWidth() * 1 / 2)  - 75 , (Gdx.graphics.getHeight() / 3));
        equipmentPageButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (levelLabel.getText().toString().matches("^[1-9]$|^0[1-9]$|^1[0]$|^")) {
                    controller.controlSwitchPageButton();
                    game.setScreen(new EquipmentEditorScreen(game, controller.getCharacter()));
                }
                return true;
            }
        });
        stage.addActor(equipmentPageButton);

        backpackButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpack.png")))));
        backpackButton.setWidth(Gdx.graphics.getWidth() / 15);
        backpackButton.setHeight(Gdx.graphics.getHeight() / 15);
        backpackButton.setPosition((Gdx.graphics.getWidth() * 1 / 2)  - 75 , (Gdx.graphics.getHeight() / 5));
        backpackButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (levelLabel.getText().toString().matches("^[1-9]$|^0[1-9]$|^1[0]$|^")) {
                    controller.controlSwitchPageButton();
                    game.setScreen(new com.chaowang.ddgame.MenuView.BackpackEditorScreen(game, controller.getCharacter()));
                }
                return true;
            }
        });
        stage.addActor(backpackButton);

        diceButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("dice.png")))));
        diceButton.setWidth(Gdx.graphics.getWidth() / 15);
        diceButton.setHeight(Gdx.graphics.getHeight() / 15);
        diceButton.setPosition((Gdx.graphics.getWidth() * 1 / 2)  - 75 , (Gdx.graphics.getHeight() / 12));
        diceButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlDiceButton();
                return true;
            }
        });
        stage.addActor(diceButton);

        // Right hand side
        characterInfoLabel = new Label("", com.chaowang.ddgame.MenuView.MainMenuScreen.style);
        characterInfoLabel.setPosition((Gdx.graphics.getWidth() * 3 / 7), (Gdx.graphics.getHeight() * 1 / 5));
        stage.addActor(characterInfoLabel);

        inventoryTable = new Table();

        inventoryTable.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 4 / 5);
        inventoryTable.setPosition(Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() * 1 / 6);

        inventoryMatrix = new ImageButton[PublicParameter.CHARACTER_INVENTORY_ROW * PublicParameter.CHARACTER_INVENTORY_COLUMN];
        controller.buildInventoryMatrix();
        controller.addInventoryMatrixListener();

        stage.addActor(inventoryTable);
    }

    /**
     * put image on background
     */
    @Override
    public void render(float delta) {
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
    /**
     * update the editor view
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
    /**
     * get character
     * @return  character
     */
    public Character getCharacter() {
        return character;
    }
    /**
     * set character
     * @param character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }
}

