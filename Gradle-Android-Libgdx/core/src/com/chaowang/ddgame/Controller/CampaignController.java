package com.chaowang.ddgame.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.chaowang.ddgame.View.CampaignEditorScreen;
import com.chaowang.ddgame.View.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import com.chaowang.ddgame.CampaignModel.Campaign;
import com.chaowang.ddgame.MapModel.Map;
/**
 * controller for campaign
 * @author chao wang
 * @version 1.0
 */
public class CampaignController {

    private Array<Map> mapList;
    private Map map;
    private Campaign campaign;
    private CampaignEditorScreen view;
    /**
     * 
     * constructor
     * @param model
     * @param view
     */
    public CampaignController(Campaign model, CampaignEditorScreen view){
        this.view = view;
        this.campaign = model;
        mapList = new Array<Map>();
        map = new Map();
    }
    /**
     * control to save the campaign information after pressing save button 
     */
    public void controlSaveButton(){
        if (! view.nameField.getText().equals("") && mapList.size > 1 ) {
            campaign.setName(view.nameField.getText());
            campaign.setMapPack(mapList);
            MainMenuScreen.campaignInventory.addToCampaignPack(campaign);
            MainMenuScreen.campaignInventory.saveToFile();
            view.nameField.setText("");
            mapList.clear();
            view.campaignTable.clearChildren();
            view.campaignInventoryTable.clearChildren();
            buildCampaignInventoryMatrix();
            addCampaignInventoryMatrixListener();
        }
        else{
            new Dialog("Error", MainMenuScreen.skin, "dialog") {
            }.text("Campaign name cannot be empty, map number > 1").button("OK", true).key(Input.Keys.ENTER, true)
                    .show(view.stage);
        }
    }

    /**
     * build matrix structure for campaign
     */
    public void buildCampaignMatrix() {
        Label tmpLabel;
        for (int i = 0; i < PublicParameter.MAP_INVENTORY_ROW; i++) {
            for (int j = 0; j < PublicParameter.MAP_INVENTORY_ROW; j++) {
                if ((i * PublicParameter.MAP_INVENTORY_ROW) + j < mapList.size) {
                    view.campaignMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j] = new Label(mapList.get((i * PublicParameter.MAP_INVENTORY_ROW) + j).toString()+"--", MainMenuScreen.skin);
                } else {
                    view.campaignMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j] = new Label("", MainMenuScreen.skin);
                }
                view.campaignMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j].setAlignment(Align.center);
                tmpLabel = view.campaignMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j];
                view.campaignTable.add(tmpLabel).width(PublicParameter.MAP_CELL_WIDTH).height(PublicParameter.MAP_CELL_HEIGHT).space(18);
            }
            view.campaignTable.row();
        }
    }
    /**
     * build matrix structure for map inventory
     */
    public void buildMapInventoryMatrix() {
        Label tmpLabel;
        BitmapFont font = new BitmapFont(Gdx.files.internal("Style/default.fnt"),false);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
        for (int i = 0; i < PublicParameter.MAP_INVENTORY_ROW; i++) {
            for (int j = 0; j < PublicParameter.MAP_INVENTORY_ROW; j++) {
                if ((i * PublicParameter.MAP_INVENTORY_ROW) + j < MainMenuScreen.mapInventory.getMapPack().size) {
                    view.mapInventoryMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j] = new Label(MainMenuScreen.mapInventory.getMapPack().get((i * PublicParameter.MAP_INVENTORY_ROW) + j).toString(), style);
                } else {
                    view.mapInventoryMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j] = new Label("", style);
                }
                view.mapInventoryMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j].setAlignment(Align.center);
                tmpLabel = view.mapInventoryMatrix[(i * PublicParameter.MAP_INVENTORY_ROW) + j];
                view.mapInventoryTable.add(tmpLabel).width(PublicParameter.MAP_CELL_WIDTH).height(PublicParameter.MAP_CELL_HEIGHT).space(18);
            }
            view.mapInventoryTable.row();
        }
    }

    /**
     * build matrix structure for campaign inventory
     */
    public void buildCampaignInventoryMatrix() {
        Label tmpLabel;
        for (int i = 0; i < PublicParameter.Campaign_INVENTORY_SIZE; i++) {
            if (i < MainMenuScreen.campaignInventory.getCampaignPack().size) {
                view.campaignInventoryMatrix[i] = new Label(MainMenuScreen.campaignInventory.getCampaignPack().get(i).toString() + "--", MainMenuScreen.style);
            } else {
                view.campaignInventoryMatrix[i] = new Label("", MainMenuScreen.style);
            }
            tmpLabel = view.campaignInventoryMatrix[(i)];
            view.campaignInventoryTable.add(tmpLabel).width(PublicParameter.MAP_CELL_WIDTH).height(PublicParameter.MAP_CELL_HEIGHT).space(18);
        }
    }

    /**
     * add listener to listen campaign matrix
     */
    public void addCampaignMatrixListener() {
        for (int i = 0; i < mapList.size ; i++){
            view.campaignMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    mapList.removeIndex(getButton());
                    view.campaignTable.clearChildren();
                    buildCampaignMatrix();
                    addCampaignMatrixListener();
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.inventoryCampaignInfoLabel.setText(mapList.get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.inventoryCampaignInfoLabel.setText("");
                }
            });
        }
    }

    /**
     * add listener to listen campaign inventory matrix
     */
    public void addCampaignInventoryMatrixListener() {
        for (int i = 0; i < MainMenuScreen.campaignInventory.getCampaignPack().size ; i++){
            view.campaignInventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Campaign campaignPointer = MainMenuScreen.campaignInventory.getCampaignPack().removeIndex(getButton());
                    view.nameField.setText(campaignPointer.getName());
                    mapList.clear();
                    for(Map m : campaignPointer.getMapPack()){
                        mapList.add(m);
                    }
                    view.campaignTable.clearChildren();
                    buildCampaignMatrix();
                    addCampaignMatrixListener();
                    MainMenuScreen.campaignInventory.saveToFile();
                    view.campaignInventoryTable.clearChildren();
                    buildCampaignInventoryMatrix();
                    addCampaignInventoryMatrixListener();
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.inventoryCampaignInfoLabel.setText(MainMenuScreen.campaignInventory.getCampaignPack().get(getButton()).getMapPack().toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.inventoryCampaignInfoLabel.setText("");
                }
            });
        }
    }
    /**
     * add listener to listen map inventory matrix
     */
    public void addMapInventoryMatrixListener() {
        for (int i = 0; i < MainMenuScreen.mapInventory.getMapPack().size ; i++){
            view.mapInventoryMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    map = new Map(MainMenuScreen.mapInventory.getMapPack().get(getButton()).getLevel(),
                            MainMenuScreen.mapInventory.getMapPack().get(getButton()).getSize(),
                            MainMenuScreen.mapInventory.getMapPack().get(getButton()).getName());
                    map.setEntryDoor(MainMenuScreen.mapInventory.getMapPack().get(getButton()).getEntryDoor());
                    map.setExitDoor(MainMenuScreen.mapInventory.getMapPack().get(getButton()).getExitDoor());
                    map.setWallLocationList(MainMenuScreen.mapInventory.getMapPack().get(getButton()).getWallLocationList());
                    map.setItemLocationList(MainMenuScreen.mapInventory.getMapPack().get(getButton()).getItemLocationList());
                    map.setFriendLocationList(MainMenuScreen.mapInventory.getMapPack().get(getButton()).getFriendLocationList());
                    map.setEnemyLocationList(MainMenuScreen.mapInventory.getMapPack().get(getButton()).getEnemyLocationList());
                    mapList.add(map);
                    view.campaignTable.clearChildren();
                    buildCampaignMatrix();
                    addCampaignMatrixListener();

                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.inventoryMapInfoLabel.setText(MainMenuScreen.mapInventory.getMapPack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.inventoryMapInfoLabel.setText("");
                }
            });
        }
    }

}
