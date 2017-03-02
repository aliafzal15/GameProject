package com.chaowang.ddgame;

import com.badlogic.gdx.Gdx;

import com.chaowang.ddgame.ItemModel.Item;

/**
 * class for storing public parameters
 * @author chao wang
 * @version 1.0
 */
public interface PublicParameter {

    int characterInventoryRow = 5, characterInventoryColumn = 5;
    int characterCellWidth = Gdx.graphics.getHeight() * 3 / 5 / characterInventoryRow;
    int characterCellHeight = Gdx.graphics.getHeight() * 3 / 5 / characterInventoryColumn;
    int itemInventoryRow = 7, itemInventoryColumn = 7;
    int itemCellWidth = Gdx.graphics.getHeight() * 5 / 8 / itemInventoryRow;
    int itemCellHeight = Gdx.graphics.getHeight() * 5 / 8 / itemInventoryColumn;
    int itemBackpackRow = 2, itemBackpackColumn = 5;
    int itemTypeCount = Item.ItemType.values().length;
    int mapPixelType = 4, mapPixelSize = 64;
    int mapInventoryRow = 5, mapInventoryColumn = 5;
    int mapCellWidth = Gdx.graphics.getHeight() * 7 / 8 / itemInventoryRow;
    int mapCellHeight = Gdx.graphics.getHeight() * 3 / 8 / itemInventoryColumn;
    int campaignInventorySize = 6;


}
