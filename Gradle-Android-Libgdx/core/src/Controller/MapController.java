package Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.MainMenuScreen;
import com.chaowang.ddgame.MapEditorScreen;

import Items.Item;
import Map.Map;
import Character.Character;
import util.MazeSolver;
/**
 * controller for maps
 * @author chao wang
 * @version 1.0
 */
public class MapController {
    private  Map map;
    private MapEditorScreen view;
    private int matrixPointer = 0;
    private Item itemCarrier;
    private Character characterCarrier;
    /**
     * constructor
     * @param model
     * @param view
     */
    public MapController(Map model, MapEditorScreen view){
        this.map = model;
        this.view = view;
        matrixPointer = 0;
        itemCarrier = new Item();
        characterCarrier = new Character();
    }
    /**
     * controller for confirming all input information on map editor page
     */
    public void controlConfirmButton(){
        if (view.sizeField.getText().matches("^[1-9]$|^0[1-9]$|^1[0-1]$") &&
                ( ! view.nameField.getText().equals(""))) {
            if(map.getSize() != Integer.parseInt(view.sizeField.getText())){
                view.mapTable.clearChildren();
                map = new Map();
                view.mapMatrix = new ImageButton[Integer.parseInt(view.sizeField.getText()) * Integer.parseInt(view.sizeField.getText())];
                map.setSize(Integer.parseInt(view.sizeField.getText()));
                map.setName(view.nameField.getText());
                buildMapMatrix();
                addMapMatrixListener();
                view.sizeField.setDisabled(true);
                view.nameField.setDisabled(true);
                view.confirmButton.setTouchable(Touchable.disabled);
            } else{
                map.setName(view.nameField.getText());
                view.sizeField.setDisabled(true);
                view.nameField.setDisabled(true);
                view.confirmButton.setTouchable(Touchable.disabled);
            }
        }
    }

    /**
     * build matrix structure for map
     */
    private void buildMapMatrix() {
        int size = map.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (map.getLocationMatrix()[i][j]){
                    case -3:
                        view.mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/enemy.png")))));
                        break;
                    case -2:
                        view.mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/friend.png")))));
                        break;
                    case -1:
                        view.mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/chest.png")))));
                        break;
                    case 1:
                        view.mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/wall.png")))));
                        break;
                    case 2:
                        view.mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/entryDoor.png")))));
                        break;
                    case 3:
                        view.mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/exitDoor.png")))));
                        break;
                    default:
                        view.mapMatrix[(i * size) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/pave.png")))));
                        break;
                }
                view.mapTable.add(view.mapMatrix[(i * size) + j]).fill();
            }
            view.mapTable.row();
        }
    }
    /**
     * 
     * list items information
     */
    public void buildElementList() {

        for (int i = 0 ; i < view.elementList.length ; i++){
            switch (i){
                case 1:
                    view.elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/wall.png")))));
                    break;
                case 2:
                    view.elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/entryDoor.png")))));
                    break;
                case 3:
                    view.elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/exitDoor.png")))));
                    break;
                default:
                    view.elementList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("map/pave.png")))));
                    break;
            }
            view.elementTable.add(view.elementList[i]).fill().expandX();
        }
        view.elementTable.row();
        view.elementTable.add(new Label("Item", MainMenuScreen.style));
        view.itemSelectBox = new SelectBox<String>(MainMenuScreen.skin);
        view.itemSelectBox.setItems(MainMenuScreen.itemInventory.getItemPackInfo());
        view.elementTable.add(view.itemSelectBox).colspan(3);
        view.elementTable.row();
        view.elementTable.add(new Label("Friendly ", MainMenuScreen.style));
        view.friendlySelectBox = new SelectBox<String>(MainMenuScreen.skin);
        view.friendlySelectBox.setItems(MainMenuScreen.characterInventory.getCharacterListInfo());
        view.elementTable.add(view.friendlySelectBox).colspan(3);
        view.elementTable.row();
        view.elementTable.add(new Label("Hostile ", MainMenuScreen.style));
        view.hostileSelectBox = new SelectBox<String>(MainMenuScreen.skin);
        view.hostileSelectBox.setItems(MainMenuScreen.characterInventory.getCharacterListInfo());
        view.elementTable.add(view.hostileSelectBox).colspan(3);
        view.elementTable.row();
        view.elementTable.add(new Label("Maps ", MainMenuScreen.style));
        view.mapSelectBox = new SelectBox<String>(MainMenuScreen.skin);
        view.mapSelectBox.setItems(MainMenuScreen.mapInventory.getMapListInfo());
        view.elementTable.add(view.mapSelectBox).colspan(3);
    }
    /**
     * controller for saving information after pressing save button
     */
    public void controlSaveButton(){
    	if(!view.confirmButton.isTouchable()){
            int entryCount = map.validateEntryDoor();
            int exitCount = map.validateExitDoor();
            if (Integer.parseInt(view.sizeField.getText()) == map.getSize() && entryCount ==1 && exitCount ==1 ) {
                MazeSolver solver = new MazeSolver(map.getLocationMatrix());
                if(! solver.isInSurroundings()){
                    if(solver.solveMaze()){
                        map.getWallLocationList().clear();
                        map.addWall();
                        MainMenuScreen.mapInventory.addToInventory(map);
                        MainMenuScreen.mapInventory.saveToFile();
                        view.sizeField.setText("");
                        view.sizeField.setDisabled(false);
                        view.nameField.setText("");
                        view.nameField.setDisabled(false);
                        view.confirmButton.setTouchable(Touchable.enabled);
                        view.mapTable.clearChildren();
                    }
                    else{
                        new Dialog("Error", MainMenuScreen.skin, "dialog") {
                        }.text("No path to exit").button("OK", true).key(Input.Keys.ENTER, true)
                                .show(view.stage);
                    }
                }
                else{
                    new Dialog("Error", MainMenuScreen.skin, "dialog") {
                    }.text("Map entry and exit are attached").button("OK", true).key(Input.Keys.ENTER, true)
                            .show(view.stage);
                }
            }
            else{
                new Dialog("Error", MainMenuScreen.skin, "dialog") {
                }.text("Map need 1 entry door 1 exit door").button("OK", true).key(Input.Keys.ENTER, true)
                        .show(view.stage);
            }
    	} else{
            new Dialog("Error", MainMenuScreen.skin, "dialog") {
            }.text("Need to hit confirm button first").button("OK", true).key(Input.Keys.ENTER, true)
                    .show(view.stage);
    	}

    }
    /**
     * listen any changes from items list
     */
    public void addElementListListener() {
        for (int i = 0; i < view.elementList.length; i++) {
            view.elementList[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    matrixPointer = getButton();
                    return true;
                }
            });
        }

        view.itemSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = view.itemSelectBox.getSelected();
                if(!text.equals("")){
                    int index = Integer.parseInt(text.substring(0, text.indexOf('-')));
                    matrixPointer = -1;
                    itemCarrier = MainMenuScreen.itemInventory.getItemPack().get(index);
                } else{
                    matrixPointer = 0;
                }
            }
        });

        view.friendlySelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = view.friendlySelectBox.getSelected();
                if(!text.equals("")){
                    int index = Integer.parseInt(text.substring(0, text.indexOf('-')));
                    matrixPointer = -2;
                    characterCarrier = MainMenuScreen.characterInventory.getChatacterPack().get(index);
                } else{
                    matrixPointer = 0;
                }
            }
        });

        view.hostileSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = view.hostileSelectBox.getSelected();
                if(!text.equals("")){
                    int index = Integer.parseInt(text.substring(0, text.indexOf('-')));
                    matrixPointer = -3;
                    characterCarrier = MainMenuScreen.characterInventory.getChatacterPack().get(index);
                } else{
                    matrixPointer = 0;
                }
            }
        });

        view.mapSelectBox.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String text = view.mapSelectBox.getSelected();
                if(!text.equals("")){
                    int index = Integer.parseInt(text.substring(0, text.indexOf('-')));
                    map = MainMenuScreen.mapInventory.getMapPack().get(index);
                    view.nameField.setText(MainMenuScreen.mapInventory.getMapPack().get(index).getName());
                    view.sizeField.setText(Integer.toString(MainMenuScreen.mapInventory.getMapPack().get(index).getSize()));
                    view.sizeField.setDisabled(false);
                    view.nameField.setDisabled(false);
                    view.confirmButton.setTouchable(Touchable.enabled);
                    MainMenuScreen.mapInventory.getMapPack().removeIndex(index);
                    view.mapTable.clearChildren();
                    MainMenuScreen.mapInventory.saveToFile();
                    view.mapMatrix = new ImageButton[map.getSize() * map.getSize()];
                    buildMapMatrix();
                    addMapMatrixListener();
                    view.mapSelectBox.setItems(MainMenuScreen.mapInventory.getMapListInfo());
                } else {
                    view.mapSelectBox.setItems(MainMenuScreen.mapInventory.getMapListInfo());
                }
                return true;
            }
        });
    }
    /**
     * listen any changes from map matrix
     */
    private void addMapMatrixListener() {
        for (int i = 0; i < view.mapMatrix.length; i++) {
            view.mapMatrix[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    int i = getButton() / map.getSize();
                    int j = getButton() % map.getSize();
                    switch (map.getLocationMatrix()[i][j]){
                        case -3:
                            map.removeEnemyLocationList(i,j);
                            break;
                        case -2:
                            map.removeFriendLocationList(i,j);
                            break;
                        case -1:
                            map.removeItemLocationList(i,j);
                            break;
                    }
                    map.getLocationMatrix()[i][j] = matrixPointer;
                    switch (map.getLocationMatrix()[i][j]){
                        case -3:
                            map.addEnemyLocationList(i,j,characterCarrier);
                            break;
                        case -2:
                            map.addFriendLocationList(i,j,characterCarrier);
                            break;
                        case -1:
                            map.addItemLocationList(i,j, itemCarrier);
                            break;
                    }
                    view.mapTable.clearChildren();
                    buildMapMatrix();
                    addMapMatrixListener();
                    return true;
                }
            });
        }
    }

}
