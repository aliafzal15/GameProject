package com.chaowang.ddgame.GameView;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.CampaignModel.Campaign;
import com.chaowang.ddgame.CharacterModel.Abilities;
import com.chaowang.ddgame.GameController.PlayerEditorController;
import com.chaowang.ddgame.MapModel.Map;
import com.chaowang.ddgame.GameModel.Player;
/**
 * view for editor screen
 * @author chao wang 
 * @version 2.0
 */
public class GamePlayerEditorScreen implements Screen{

    private Game game;
    public Stage stage;
    private SpriteBatch batch;
    public TextButton backwardButton, confirmButton;
    private Texture backgroundTexture;

    private ImageButton[] backpackMatrix, equipmentMatrix;
    private Table backpackTable, equipmentTable;
    private Player player;
    private Map mapModel;
    private Campaign campaign;
    private PlayerEditorController controller;
    private Label backpackItemInfoLabel;
    
    public Table editorTable;
    public Label raceLabel, levelLabel, fighterTypeLabel, promotePointLabel, nameLabel;
    public Label hitpointLabel, attackBonusLabel, damageBonusLaber, armorClassLabel,
            strengthLabel, dexterityLabel, constitutionLabel, wisdomLabel, intellegenceLabel, charismaLabel, characterInfoLabel;
    public TextField[] bonusField;
    public Image characterImage;


    /**
     * constructor
     * @param game
     */
    public GamePlayerEditorScreen (Game game, Player player, Map map, Campaign camp) {
        this.game = game;
        this.player = player;
        this.mapModel = map;
        this.campaign = camp;
    }
    /**
     * show whole equipment editor on screen
     */
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("EditorBackground.jpg"));
        batch = new SpriteBatch();

        controller = new PlayerEditorController(this,this.player);

        backwardButton = new TextButton("<--", MainMenuScreen.buttonStyle);
        backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
        backwardButton.setHeight(Gdx.graphics.getHeight() / 10);
        backwardButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
        backwardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, player, mapModel, campaign));
                return true;
            }
        });
        stage.addActor(backwardButton);

        nameLabel = new Label(player.getCharacter().getName(), MainMenuScreen.style);
        levelLabel = new Label(Integer.toString(player.getCharacter().getLevel()), MainMenuScreen.style);
        raceLabel = new Label(player.getCharacter().getRaceType().toString(), MainMenuScreen.style);
        characterImage = new Image(player.getCharacter().getTexture());
        fighterTypeLabel = new Label(player.getCharacter().getFighterType().toString(), MainMenuScreen.style);

        confirmButton = new TextButton("OK", MainMenuScreen.buttonStyle);
        confirmButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.controlConfirmButton();
                return true;
            }
        });
        promotePointLabel = new Label(Integer.toString(player.getCharacter().getPromotionPoint()), MainMenuScreen.style);
        strengthLabel = new Label(Integer.toString(player.getCharacter().getStrength()), MainMenuScreen.style);
        dexterityLabel = new Label(Integer.toString(player.getCharacter().getDexterity()), MainMenuScreen.style);
        constitutionLabel = new Label(Integer.toString(player.getCharacter().getConstitution()), MainMenuScreen.style);
        wisdomLabel = new Label(Integer.toString(player.getCharacter().getWisdom()), MainMenuScreen.style);
        intellegenceLabel = new Label(Integer.toString(player.getCharacter().getIntelligence()), MainMenuScreen.style);
        charismaLabel = new Label(Integer.toString(player.getCharacter().getCharisma()), MainMenuScreen.style);
        hitpointLabel = new Label(Integer.toString(player.getCharacter().getHitPoints()), MainMenuScreen.style);
        attackBonusLabel  = new Label(Integer.toString(player.getCharacter().getAttackBonus()), MainMenuScreen.style);
        damageBonusLaber  = new Label(Integer.toString(player.getCharacter().getDamageBonus()), MainMenuScreen.style);
        armorClassLabel  = new Label(Integer.toString(player.getCharacter().getArmorClass()), MainMenuScreen.style);

        bonusField = new TextField[Abilities.ABILITYSIZE];
        for (int i = 0;  i < bonusField.length ; i ++){
            bonusField[i] =  new TextField(Integer.toString(player.getCharacter().getAbilityBonusArr()[i]), MainMenuScreen.skin);
        }


        editorTable = new Table();

        editorTable.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() );
        editorTable.setPosition(Gdx.graphics.getWidth() / 10, 2);

        editorTable.add(new Label("", MainMenuScreen.style)).width(80);
        editorTable.add(characterImage).maxSize(200, 200).center();
        editorTable.row();
        editorTable.add(new Label("Name", MainMenuScreen.style));
        editorTable.add(nameLabel);
        editorTable.row();
        editorTable.add(new Label("Level", MainMenuScreen.style));
        editorTable.add(levelLabel);
        editorTable.row();
        editorTable.add(new Label("Race", MainMenuScreen.style));
        editorTable.add(raceLabel).center();
        editorTable.row();
        editorTable.add(new Label("Fighter Type", MainMenuScreen.style));
        editorTable.add(fighterTypeLabel).center();
        editorTable.row();
        editorTable.add(new Label("Promo Point", MainMenuScreen.style));
        editorTable.add(promotePointLabel);
        editorTable.add(confirmButton).size(50, 50).expandX();
        editorTable.row();
        editorTable.add(new Label("strength", MainMenuScreen.style));
        editorTable.add(strengthLabel);
        editorTable.add(bonusField[0]).width(30);
        editorTable.row();
        editorTable.add(new Label("dexterity", MainMenuScreen.style));
        editorTable.add(dexterityLabel);
        editorTable.add(bonusField[1]).width(30);
        editorTable.row();
        editorTable.add(new Label("constitution", MainMenuScreen.style));
        editorTable.add(constitutionLabel);
        editorTable.add(bonusField[2]).width(30);
        editorTable.row();
        editorTable.add(new Label("wisdom", MainMenuScreen.style));
        editorTable.add(wisdomLabel);
        editorTable.add(bonusField[3]).width(30);
        editorTable.row();
        editorTable.add(new Label("intelligence", MainMenuScreen.style));
        editorTable.add(intellegenceLabel);
        editorTable.add(bonusField[4]).width(30);
        editorTable.row();
        editorTable.add(new Label("charisma", MainMenuScreen.style));
        editorTable.add(charismaLabel);
        editorTable.add(bonusField[5]).width(30);
        editorTable.row();
        editorTable.add(new Label("hit point", MainMenuScreen.style));
        editorTable.add(hitpointLabel);
        editorTable.row();
        editorTable.add(new Label("armor class", MainMenuScreen.style));
        editorTable.add(armorClassLabel);
        editorTable.row();
        editorTable.add(new Label("attach bonus", MainMenuScreen.style));
        editorTable.add(attackBonusLabel);
        editorTable.row();
        editorTable.add(new Label("damage bonus", MainMenuScreen.style));
        editorTable.add(damageBonusLaber);
        editorTable.row();

        stage.addActor(editorTable);
        
        
        // back pack table / right top
        backpackItemInfoLabel = new Label("", MainMenuScreen.style);
        backpackItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 2), (Gdx.graphics.getHeight() / 8));
        stage.addActor(backpackItemInfoLabel);

        backpackTable = new Table();
        backpackTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backpackBackground.png")))));
        backpackTable.setSize(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() * 1 / 3);
        backpackTable.setPosition(Gdx.graphics.getWidth() * 19 / 40, Gdx.graphics.getHeight() * 1 / 8);

        backpackMatrix = new ImageButton[PublicParameter.ITEM_BACKPACK_ROW * PublicParameter.ITEM_BACKPACK_COLUMN];
        controller.buildBackpackMatrix();
        controller.addBackpackMatrixListener();

        stage.addActor(backpackTable);

        
        // equipment table / right buttom
        equipmentTable = new Table();

        equipmentTable.setSize(Gdx.graphics.getWidth() / 8 , Gdx.graphics.getHeight() * 1 / 3);
        equipmentTable.setPosition( Gdx.graphics.getWidth() * 5 / 8 , Gdx.graphics.getHeight() * 1 / 2);
        equipmentTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("MaleHumanUnderwear.png")))));

        equipmentMatrix = new ImageButton[PublicParameter.ITEM_TYPE_COUNT];
        controller.buildEquipmentMatrix();
        controller.addEquipmentMatrixListener();

        stage.addActor(equipmentTable);

    }
    /**
     * put image on background
     */
    @Override
    public void render(float delta) {
//
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    /**
     * get backpack matrix
     * @return ImageButton[]
     */
	public ImageButton[] getBackpackMatrix() {
		return backpackMatrix;
	}
	/**
	 * get equipment matrix
	 * @return ImageButton[] 
	 */
	public ImageButton[] getEquipmentMatrix() {
		return equipmentMatrix;
	}
	/**
	 * get backpack table
	 * @return
	 */
	public Table getBackpackTable() {
		return backpackTable;
	}
	/**
	 * get equipment table
	 * @return Table
	 */
	public Table getEquipmentTable() {
		return equipmentTable;
	}
	/**
	 * get backpack information table
	 * @return Label
	 */
	public Label getBackpackItemInfoLabel() {
		return backpackItemInfoLabel;
	}


}