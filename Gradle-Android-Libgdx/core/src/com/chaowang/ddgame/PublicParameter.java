package com.chaowang.ddgame;

import com.badlogic.gdx.Gdx;

import com.chaowang.ddgame.MenuModel.ItemModel.Item;

/**
 * class for storing public parameters
 * @author chao wang
 * @version 1.0
 */
public interface PublicParameter {
    int CHARACTER_MAX_LEVEL = 20;
    int CHARACTER_INVENTORY_ROW = 5, CHARACTER_INVENTORY_COLUMN = 5;
    int CHARACTER_CELL_WIDTH = Gdx.graphics.getHeight() * 3 / 5 / CHARACTER_INVENTORY_ROW;
    int CHARACTER_CELL_HEIGHT = Gdx.graphics.getHeight() * 3 / 5 / CHARACTER_INVENTORY_COLUMN;
    int ITEM_INVENTORY_ROW = 7, ITEM_INVENTORY_COLUMN = 7;
    int ITEM_CELL_WIDTH = Gdx.graphics.getHeight() * 5 / 8 / ITEM_INVENTORY_ROW;
    int ITEM_CELL_HEIGHT = Gdx.graphics.getHeight() * 5 / 8 / ITEM_INVENTORY_COLUMN;
    int ITEM_BACKPACK_ROW = 2, ITEM_BACKPACK_COLUMN = 5, ITEM_BACKPACK_SIZE = ITEM_BACKPACK_ROW * ITEM_BACKPACK_COLUMN;
    int ITEM_TYPE_COUNT = Item.ItemType.values().length;
    int MAP_PIXEL_TYPE = 4, MAP_PIXEL_SIZE = 128;
    int MAP_INVENTORY_ROW = 5, MAP_INVENTORY_COLUMN = 5, CAMPAIGN_INVENTORY_ROW = 5, CAMPAIGN_INVENTORY_COLUMN = 5;
    int MAP_CELL_WIDTH = Gdx.graphics.getHeight() * 7 / 8 / ITEM_INVENTORY_ROW;
    int MAP_CELL_HEIGHT = Gdx.graphics.getHeight() * 3 / 8 / ITEM_INVENTORY_COLUMN;
    int Campaign_INVENTORY_SIZE = 6;

    // for tutorial testing
    int TILE_SIZE = 16;
    float SCALE = 2f;
    float SCALED_TILE_SIZE = TILE_SIZE * SCALE;

}
