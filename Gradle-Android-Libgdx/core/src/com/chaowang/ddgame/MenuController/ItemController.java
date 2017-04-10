package com.chaowang.ddgame.MenuController;

/**
 * controller for item
 * @author chao wang
 * @version 1.0
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.MenuModel.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.WeaponSpecialEnchantment;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponModel;
import com.chaowang.ddgame.MenuView.ItemEditorScreen;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import com.chaowang.ddgame.MenuModel.ItemModel.Item;
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
        if(model.getItemType() == Item.ItemType.WEAPON){
            model.setWeaponModel(new WeaponModel());
            view.weaponTypeLabel.setText(model.getWeaponType().toString());
        } else{
            model.setWeaponModel(null);
            view.weaponTypeLabel.setText("");
        }
        view.itemLabel.setText(model.getItemType().toString());
        view.abilityLabel.setText(model.getEnchantedAbility().toString());
        view.itemImage.setDrawable(new SpriteDrawable(new Sprite(model.getTexture())));
        setWeaponOptionVisibility();
    }
    /**
     * controller for switching to next item information after pressing button  
     */
    public void controlItemRightButton() {
        model.nextItem();
        if(model.getItemType() == Item.ItemType.WEAPON){
            model.setWeaponModel(new WeaponModel());
            view.weaponTypeLabel.setText(model.getWeaponType().toString());
        } else{
            model.setWeaponModel(null);
            view.weaponTypeLabel.setText("");
        }
        view.itemLabel.setText(model.getItemType().toString());
        view.abilityLabel.setText(model.getEnchantedAbility().toString());
        view.itemImage.setDrawable(new SpriteDrawable(new Sprite(model.getTexture())));
        setWeaponOptionVisibility();

    }

    /**
     * en visible weapon editor if item becomes a weapon
     */
    public void setWeaponOptionVisibility(){
        if(view.itemLabel.getText().toString().equals(Item.ItemType.WEAPON.toString())){
            view.weaponTypeLabel.setVisible(true);
            view.weaponTypeLeftButton.setVisible(true);
            view.weaponTypeRightButton.setVisible(true);
            for(CheckBox checkBox : view.weaponEnchantCheckBoxArr){
                checkBox.setVisible(true);
            }
        } else{
            view.weaponTypeLabel.setVisible(false);
            view.weaponTypeLeftButton.setVisible(false);
            view.weaponTypeRightButton.setVisible(false);
            for(CheckBox checkBox : view.weaponEnchantCheckBoxArr){
                checkBox.setVisible(false);
            }
        }
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
     * controller for switching to previous ability level after pressing button
     */
    public void controlWeaponTypeLeftButton(){
        model.previousWeaponType();
        view.weaponTypeLabel.setText(model.getWeaponModel().getWeaponType().toString());
    }
    /**
     * controller for switching to next ability level after pressing button
     */
    public void controlWeaponTypeRightButton(){
        model.nextWeaponTypes();
        view.weaponTypeLabel.setText(model.getWeaponModel().getWeaponType().toString());
    }
    /**
     * controller for saving information after pressing save button
     */
    public void controlSaveButton(){
        if (view.levelText.getText().matches("^[1-5]$")) {
            if(model.getWeaponModel()!= null && model.getWeaponType()== WeaponModel.WeaponType.RANGE && model.getEnchantedAbility()== EnchantedAbility.DAMAGEBONUS){
                new Dialog("Error", MainMenuScreen.skin, "dialog") {
                }.text("Range weapon cannot have damage bonus").button("OK", true).key(Input.Keys.ENTER, true)
                        .show(view.stage);
            }else{
                model.setLevel(Integer.parseInt(view.levelText.getText()));
                model.setName(view.nameText.getText());
                if(model.getItemType()== Item.ItemType.WEAPON){
                    saveWeaponEnchantment(view.weaponEnchantCheckBoxArr);
                }
                MainMenuScreen.itemInventory.addToInventory(model);
                MainMenuScreen.itemInventory.saveToFile();
                view.inventoryTable.clearChildren();
                buildInventoryMatrix();
                addInventoryMatrixListener();
                model = new Item();
                initialEditorItem();
            }
        }
        else{
            new Dialog("Error", MainMenuScreen.skin, "dialog") {
            }.text("Item input value error").button("OK", true).key(Input.Keys.ENTER, true)
                    .show(view.stage);
        }
    }

    private void saveWeaponEnchantment(CheckBox[] weaponEnchantCheckBoxArr) {
        boolean[] weaponEnchantBoolArr = new boolean[weaponEnchantCheckBoxArr.length];
        for(int i = 0; i < weaponEnchantBoolArr.length; i++){
            if(weaponEnchantCheckBoxArr[i].isChecked()){
                weaponEnchantBoolArr[i]=true;
            }
        }
        model.addWeaponEnchantment(weaponEnchantBoolArr);
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
        if(model.getItemType()== Item.ItemType.WEAPON){
            view.weaponTypeLabel.setText(model.getWeaponType().toString());
            boolean[] tmp = model.getWeaponModel().getWeaponEnhantmentEqu();
            for (int i = 0; i < WeaponSpecialEnchantment.WeaponEnchantement.values().length; i++){
                if(tmp[i]){
                    view.weaponEnchantCheckBoxArr[i].setChecked(true);
                } else{
                    view.weaponEnchantCheckBoxArr[i].setChecked(false);
                }
            }
        } else{
            view.weaponTypeLabel.setText("");
            for(CheckBox checkbox : view.weaponEnchantCheckBoxArr){
                checkbox.setChecked(false);
            }
        }
        setWeaponOptionVisibility();
    }
    /**
     * build matrix structure for item inventory
     */
    public void buildInventoryMatrix() {
        for (int i = 0; i < PublicParameter.ITEM_INVENTORY_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_INVENTORY_COLUMN; j++) {
                if ((i * PublicParameter.ITEM_INVENTORY_COLUMN) + j < MainMenuScreen.itemInventory.getItemPack().size) {
                    view.inventoryMatrix[(i * PublicParameter.ITEM_INVENTORY_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(MainMenuScreen.itemInventory.getItemPack().get(i * PublicParameter.ITEM_INVENTORY_COLUMN + j).getTexture())));
                } else {
                    view.inventoryMatrix[(i * PublicParameter.ITEM_INVENTORY_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
                ImageButton tempButton = view.inventoryMatrix[(i * PublicParameter.ITEM_INVENTORY_COLUMN) + j];
                view.inventoryTable.add(tempButton).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT).space(15);
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
                            MainMenuScreen.itemInventory.getItemPack().get(getButton()).getEnchantedAbility(),
                            MainMenuScreen.itemInventory.getItemPack().get(getButton()).getWeaponModel());
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
