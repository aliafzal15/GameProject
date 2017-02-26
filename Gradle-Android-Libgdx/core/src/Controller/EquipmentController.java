package Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.EquipmentEditorScreen;
import com.chaowang.ddgame.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import Character.Character;
import Items.Item;

public class EquipmentController {

    private EquipmentEditorScreen view;
    private Character character;

    public EquipmentController(EquipmentEditorScreen view, Character model){
        this.view = view;
        character = model;
    }


    public void buildBackpackMatrix() {
        for (int i = 0; i < PublicParameter.itemBackpackRow; i++) {
            for (int j = 0; j < PublicParameter.itemBackpackColumn; j++) {
                if ((i * PublicParameter.itemBackpackColumn) + j < character.getBackpack().size()) {
                    view.backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getBackpack().get(i * PublicParameter.itemBackpackColumn + j).getTexture())));
                } else {
                    view.backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
                }
                ImageButton tempButton = view.backpackMatrix[(i * PublicParameter.itemBackpackColumn) + j];
                view.backpackTable.add(tempButton).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight).space(25);
            }
            view.backpackTable.row();
        }
    }

    public void buildEquipmentMatrix() {

        view.equipmentTable.add(new Label("", MainMenuScreen.style));
        if(character.getEquipment().get(Item.ItemType.HELMET) != null){
            view.equipmentMatrix[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.HELMET).getTexture())));
        }else{
            view.equipmentMatrix[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[0]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        view.equipmentTable.row();

        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        if(character.getEquipment().get(Item.ItemType.ARMOR) != null){
            view.equipmentMatrix[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.ARMOR).getTexture())));
        }else{
            view.equipmentMatrix[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[1]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        view.equipmentTable.row();

        if(character.getEquipment().get(Item.ItemType.WEAPON) != null){
            view.equipmentMatrix[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.WEAPON).getTexture())));
        }else{
            view.equipmentMatrix[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[2]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        if(character.getEquipment().get(Item.ItemType.BELT) != null){
            view.equipmentMatrix[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.BELT).getTexture())));;
        }else{
            view.equipmentMatrix[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[3]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        if(character.getEquipment().get(Item.ItemType.SHIELD) != null){
            view.equipmentMatrix[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.SHIELD).getTexture())));
        }else{
            view.equipmentMatrix[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[4]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        view.equipmentTable.row();

        view.equipmentTable.add(new Label("", MainMenuScreen.style)).width(80);
        if(character.getEquipment().get(Item.ItemType.BOOTS) != null){
            view.equipmentMatrix[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.BOOTS).getTexture())));
        }else{
            view.equipmentMatrix[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[5]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);
        if(character.getEquipment().get(Item.ItemType.RING) != null){
            view.equipmentMatrix[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(character.getEquipment().get(Item.ItemType.RING).getTexture())));
        }else{
            view.equipmentMatrix[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
        }
        view.equipmentTable.add(view.equipmentMatrix[6]).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight);

    }


    public void addBackpackMatrixListener() {
        for (int i = 0; i < character.getBackpack().size() ; i++) {
            view.backpackMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (character.getBackpack().get(button).getLevel() <= (1 + character.getLevel() / 2)
                            && !character.getEquipment().containsKey(character.getBackpack().get(getButton()).getItemType())) {
                        Item itemtmp = character.getBackpack().remove(getButton());
                        character.loadEquipment(itemtmp);
                        view.equipmentTable.clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        view.backpackTable.clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    else{
                        new Dialog("Error", MainMenuScreen.skin, "dialog") {
                        }.text("Item level not same as character level").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
                    }
                    return true;
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.backpackItemInfoLabel.setText(character.getBackpack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.backpackItemInfoLabel.setText("");
                }
            });
        }
    }


    public void addEquipmentMatrixListener() {
        for (int i = 0; i < view.equipmentMatrix.length ; i++){
            view.equipmentMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(character.getEquipment().containsKey(Item.ItemType.getItemType(getButton()))){
                        character.getBackpack().add(character.removeEquipment(Item.ItemType.getItemType(getButton())));
                        view.equipmentTable.clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        view.backpackTable.clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if(character.getEquipment().get(Item.ItemType.getItemType(getButton())) != null){
                        view.equipmentItemInfoLabel.setText(character.getEquipment().get(Item.ItemType.getItemType(getButton())).toString());
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.equipmentItemInfoLabel.setText("");
                }
            });
        }
    }

}
