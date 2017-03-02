package Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.ItemEditorScreen;
import com.chaowang.ddgame.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import Items.Item;
/**
 * controller for items
 * @author chao wang
 * @version 1.0
 */
public class ItemController {
    private Item model;
    private ItemEditorScreen view;
    /**
     * constructor
     * @param view
     * @param model
     */
    public ItemController(ItemEditorScreen view, Item model){
        this.model = model;
        this.view = view;
    }
    /**
     * controller for switching to previous item information after pressing button  
     */
    public void controlItemLeftButton(){
        model.previousItem();
        view.itemLabel.setText(model.getItemType().toString());
        view.abilityLabel.setText(model.getEnchantedAbility().toString());
        view.itemImage.setDrawable(new SpriteDrawable(new Sprite(model.getTexture())));
    }
    /**
     * controller for switching to next item information after pressing button  
     */
    public void controlItemRightButton() {
        model.nextItem();
        view.itemLabel.setText(model.getItemType().toString());
        view.abilityLabel.setText(model.getEnchantedAbility().toString());
        view.itemImage.setDrawable(new SpriteDrawable(new Sprite(model.getTexture())));
    }
    /**
     * controller for switching to previous ability level after pressing button  
     */
    public void controlAbilityLeftButton(){
        model.previousAbility();
        view.abilityLabel.setText(model.getEnchantedAbility().toString());
    }
    /**
     * controller for switching to next ability level after pressing button  
     */
    public void controlAbilityRightButton(){
        model.nextAbility();
        view.abilityLabel.setText(model.getEnchantedAbility().toString());
    }
    /**
     * controller for saving information after pressing save button
     */
    public void controlSaveButton(){
        if (view.levelText.getText().matches("^[1-5]$")) {
            model.setLevel(Integer.parseInt(view.levelText.getText()));
            model.setName(view.nameText.getText());
            MainMenuScreen.itemInventory.addToInventory(model);
            MainMenuScreen.itemInventory.saveToFile();
            view.inventoryTable.clearChildren();
            buildInventoryMatrix();
            addInventoryMatrixListener();
            model = new Item();
            initialEditorItem();
        }
        else{
            new Dialog("Error", MainMenuScreen.skin, "dialog") {
            }.text("Item input value error").button("OK", true).key(Input.Keys.ENTER, true)
                    .show(view.stage);
        }
    }
    /**
     * set up initial item editor page
     */
    public void initialEditorItem() {
        view.itemLabel.setText(model.getItemType().toString());
        view.abilityLabel.setText(model.getEnchantedAbility().toString());
        view.itemImage.setDrawable(new SpriteDrawable(new Sprite(model.getTexture())));
        view.levelText.setText(Integer.toString(model.getLevel()));
        view.nameText.setText(model.getName());
    }
    /**
     * build matrix structure for item inventory
     */
    public void buildInventoryMatrix() {
        for (int i = 0; i < PublicParameter.itemInventoryRow; i++) {
            for (int j = 0; j < PublicParameter.itemInventoryColumn; j++) {
                if ((i * PublicParameter.itemInventoryColumn) + j < MainMenuScreen.itemInventory.getItemPack().size) {
                    view.inventoryMatrix[(i * PublicParameter.itemInventoryColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(MainMenuScreen.itemInventory.getItemPack().get(i * PublicParameter.itemInventoryColumn + j).getTexture())));
                } else {
                    view.inventoryMatrix[(i * PublicParameter.itemInventoryColumn) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/items/unknown.png")))));
                }
                ImageButton tempButton = view.inventoryMatrix[(i * PublicParameter.itemInventoryColumn) + j];
                view.inventoryTable.add(tempButton).width(PublicParameter.itemCellWidth).height(PublicParameter.itemCellHeight).space(15);
            }
            view.inventoryTable.row();
        }
    }
    /**
     * listen any changes from item inventory
     */
    public void addInventoryMatrixListener() {
        for (int i = 0; i < MainMenuScreen.itemInventory.getItemPack().size ; i++){
            view.inventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    model = new Item(MainMenuScreen.itemInventory.getItemPack().get(getButton()).getItemType(),
                            MainMenuScreen.itemInventory.getItemPack().get(getButton()).getName(),
                            MainMenuScreen.itemInventory.getItemPack().get(getButton()).getLevel(),
                            MainMenuScreen.itemInventory.getItemPack().get(getButton()).getEnchantedAbility());
                    initialEditorItem();
                    MainMenuScreen.itemInventory.getItemPack().removeIndex(getButton());
                    MainMenuScreen.itemInventory.saveToFile();
                    view.inventoryTable.clearChildren();
                    buildInventoryMatrix();
                    addInventoryMatrixListener();
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.itemInfoLabel.setText(MainMenuScreen.itemInventory.getItemPack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.itemInfoLabel.setText("");
                }
            });
        }
    }



}
