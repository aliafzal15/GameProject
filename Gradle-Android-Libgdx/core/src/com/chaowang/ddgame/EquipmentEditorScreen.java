package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Items.Item;
import Character.Character;

public class EquipmentEditorScreen implements Screen{

    private Game game;
    private Stage stage;
    private SpriteBatch batch;
    private TextButton backwardButton, saveButton;
    private Texture backgroundTexture;

    private ImageButton[] backpackMatrix, equipmentMatrix;
    private Table backpackTable, equipmentTable;
    private Character character;
    private Label equipmentItemInfoLabel, backpackItemInfoLabel;


    public EquipmentEditorScreen (Game game, Character character) {
        this.game = game;
        this.character = character;
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("android/assets/EditorBackground.jpg"));
        batch = new SpriteBatch();

        backwardButton = new TextButton("<--", MainMenu.buttonStyle);
        backwardButton.setWidth(Gdx.graphics.getWidth() / 20 );
        backwardButton.setHeight(Gdx.graphics.getHeight() / 10);
        backwardButton.setPosition((Gdx.graphics.getWidth() * 1 /30 ) , (Gdx.graphics.getHeight() * 9 / 10 ) );
        backwardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CharacterEditorScreen(game, character));
                return true;
            }
        });
        stage.addActor(backwardButton);

        backpackItemInfoLabel = new Label("", MainMenu.style);
        backpackItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 2), (Gdx.graphics.getHeight() / 8));
        stage.addActor(backpackItemInfoLabel);

        equipmentItemInfoLabel = new Label("", MainMenu.style);
        equipmentItemInfoLabel.setPosition((Gdx.graphics.getWidth() * 1 / 20), (Gdx.graphics.getHeight() / 8));
        stage.addActor(equipmentItemInfoLabel);

        backpackTable = new Table();
        backpackTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/backpackBackground.png")))));
        backpackTable.setSize(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() * 1 / 3);
        backpackTable.setPosition(Gdx.graphics.getWidth() * 3 / 7, Gdx.graphics.getHeight() * 1 / 3);

        backpackMatrix = new ImageButton[PublicParameter.itemBackpackRow * PublicParameter.itemBackpackColumn];
        buildBackpackMatrix();
        addBackpackMatrixListener();

        stage.addActor(backpackTable);


        equipmentTable = new Table();

        equipmentTable.setSize(Gdx.graphics.getWidth() / 8 , Gdx.graphics.getHeight() * 1 / 3);
        equipmentTable.setPosition( Gdx.graphics.getWidth() / 20 , Gdx.graphics.getHeight() * 1 / 3);
        equipmentTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/MaleHumanUnderwear.png")))));

        equipmentMatrix = new ImageButton[PublicParameter.itemTypeCount];
        buildEquipmentMatrix();
        addEquipmentMatrixListener();

        stage.addActor(equipmentTable);

    }



    private void buildBackpackMatrix() {
        for (int i = 0; i < PublicParameter.itemBackpackRow; i++) {
            for (int j = 0; j < PublicParameter.itemBackpackColumn; j++) {
                if ((i * PublicParameter.itemBackpackColumn) + j < character.getBackpack().size()) {
                    backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getBackpack().get(i * PublicParameter.itemBackpackColumn + j).getTexture())));
                } else {
                    backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
                }
                ImageButton tempButton = backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j];
                backpackTable.add(tempButton).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight).space(25);
            }
            backpackTable.row();
        }
    }

    private void buildEquipmentMatrix() {

        equipmentTable.add(new Label("", MainMenu.style));
        if(character.getEquipment().get(Item.ItemType.HELMET) != null){
            equipmentMatrix[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.HELMET).getTexture())));
        }else{
            equipmentMatrix[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        equipmentTable.add(equipmentMatrix[0]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        equipmentTable.add(new Label("", MainMenu.style)).width(80);
        equipmentTable.row();

        equipmentTable.add(new Label("", MainMenu.style)).width(80);
        if(character.getEquipment().get(Item.ItemType.ARMOR) != null){
            equipmentMatrix[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.ARMOR).getTexture())));
        }else{
            equipmentMatrix[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        equipmentTable.add(equipmentMatrix[1]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        equipmentTable.add(new Label("", MainMenu.style)).width(80);
        equipmentTable.row();

        if(character.getEquipment().get(Item.ItemType.WEAPON) != null){
            equipmentMatrix[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.WEAPON).getTexture())));
        }else{
            equipmentMatrix[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        equipmentTable.add(equipmentMatrix[2]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        if(character.getEquipment().get(Item.ItemType.BELT) != null){
            equipmentMatrix[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.BELT).getTexture())));;
        }else{
            equipmentMatrix[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        equipmentTable.add(equipmentMatrix[3]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        if(character.getEquipment().get(Item.ItemType.SHIELD) != null){
            equipmentMatrix[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.SHIELD).getTexture())));
        }else{
            equipmentMatrix[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        equipmentTable.add(equipmentMatrix[4]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        equipmentTable.row();

        equipmentTable.add(new Label("", MainMenu.style)).width(80);
        if(character.getEquipment().get(Item.ItemType.BOOTS) != null){
            equipmentMatrix[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.BOOTS).getTexture())));
        }else{
            equipmentMatrix[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        equipmentTable.add(equipmentMatrix[5]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        if(character.getEquipment().get(Item.ItemType.RING) != null){
            equipmentMatrix[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.RING).getTexture())));
        }else{
            equipmentMatrix[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        equipmentTable.add(equipmentMatrix[6]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);

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



    private void addBackpackMatrixListener() {
        for (int i = 0; i < character.getBackpack().size() ; i++) {
            backpackMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (character.getBackpack().get(button).getLevel() == character.getLevel()
                            && !character.getEquipment().containsKey(character.getBackpack().get(getButton()).getItemType())) {
                        Item itemtmp = character.getBackpack().remove(getButton());
                        character.loadEquipment(itemtmp);
                        equipmentTable.clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        backpackTable.clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    else{
                        new Dialog("Error", MainMenu.skin, "dialog") {
                        }.text("Item level not same as character level").button("OK", true).key(Input.Keys.ENTER, true).show(stage);
                    }
                    return true;
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    backpackItemInfoLabel.setText(character.getBackpack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    backpackItemInfoLabel.setText("");
                }
            });
        }
    }


    private void addEquipmentMatrixListener() {
        for (int i = 0; i < equipmentMatrix.length ; i++){
            equipmentMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(character.getEquipment().containsKey(Item.ItemType.getItemType(getButton()))){
                        character.getBackpack().add(character.removeEquipment(Item.ItemType.getItemType(getButton())));
                        equipmentTable.clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        backpackTable.clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if(character.getEquipment().get(Item.ItemType.getItemType(getButton())) != null){
                        equipmentItemInfoLabel.setText(character.getEquipment().get(Item.ItemType.getItemType(getButton())).toString());
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    equipmentItemInfoLabel.setText("");
                }
            });
        }
    }

}
