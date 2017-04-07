package com.chaowang.ddgame.GameView;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.GameModel.DialogueSystem.Dialogue;
import com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode;
import com.chaowang.ddgame.GameModel.StrategyPattern.ComputerPlayerStrategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.HumanPlayerStrategy;
import com.chaowang.ddgame.GameController.DialogueController;
import com.chaowang.ddgame.GameController.GameScreenController;
import com.chaowang.ddgame.GameController.PlayerController;
import com.chaowang.ddgame.GameUtl.DialogueBox;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.MapModel.Wall;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.MenuModel.MapModel.Map;

import com.chaowang.ddgame.GameUtl.OptionBox;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 * View for Game Selection
 * @author chao wang
 * @version 2.0
 */
public class GameScreen implements Observer, Screen{

    private Game game;
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera cam;
    // dialog and UI component
    private Stage uiStage;
    private Table root;
    private OptionBox optionBox;
    private DialogueBox dialogBox, messageDialog;
    private Dialogue dialogue;
    private DialogueController dialogueController;
    private PlayerController playerController;
    private GameScreenController screenController;
    private Label abilityLabel, itemInfoLabel;
    private Image[] backpackMatrix, equipmentMatrix;
    private TextButton playerEditorBtn;

    // game attributes
    private GameActor player, npc;
    private Map mapModel;
    private Campaign campaign;
    private HashMap<Vector2,GameActor> npcList;
    private Iterator<Vector2> keySetIterator ;
    private boolean isHitObject, isUserPlay;
    private static int count=0;

    /**
     * constructor
     * @param game
     * @param character
     * @param map
     * @param camp
     */
    public GameScreen(Game game, Character character,Map map, Campaign camp, boolean isUserPlay) {

        this(game,new Player(new Vector2(1,1), character),map,camp, isUserPlay);
        
        if(mapModel.getEntryDoor().y - player.getBound().getHeight() > 0 ){
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y - player.getBound().getHeight()));
        } else {
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y +  mapModel.getEntryDoor().getHeight()));
        }
    }

    /**
     * constructor
     * @param game
     * @param player
     * @param map
     * @param camp
     */
    public GameScreen(Game game, GameActor player,Map map, Campaign camp, boolean isUserPlay){
        this.game = game;
        this.player = player;
        this.mapModel = map;
        this.campaign = new Campaign(camp);
        this.isUserPlay = isUserPlay;
        batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("terrain/terrain"+mapModel.getSize() + "x" + mapModel.getSize() + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(this.map);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initUI();


        // extract all the character from map to npcList
        npcList = new HashMap<Vector2, GameActor>();
        Vector2 cur;
        keySetIterator = mapModel.getEnemyLocationList().keySet().iterator();
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            npcList.put(cur, new NPC(cur,mapModel.getEnemyLocationList().get(cur), false));
        }
        keySetIterator = mapModel.getFriendLocationList().keySet().iterator();
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            npcList.put(cur, new NPC(cur,mapModel.getFriendLocationList().get(cur), true));
        }

        // instentiate controller
        playerController = new PlayerController((Player)player, this);
        dialogueController = new DialogueController(dialogBox, optionBox, messageDialog);
        screenController = new GameScreenController(this,this.mapModel, (Player)this.player);

        dialogue = new Dialogue();
        DialogueNode node1 = new DialogueNode("Your Turn starts", 0);
        DialogueNode node2 = new DialogueNode("How do you want your move ?", 1);

        node1.makeLinear(node2.getID());
        node2.addChoice("Move", 2);
        node2.addChoice("Fight",3);
        node2.addChoice("Trade", 4);
        dialogue.addNode(node1);
        dialogue.addNode(node2);

        // from show
        if(mapModel.getLevel() != player.getCharacter().getLevel()){
            mapModel.adjustLevel(player.getCharacter().getLevel());
        }


    }
    /**
     * get map
     * @return
     */
    public TiledMap getMap() {
        return map;
    }
    /**
     * set map
     * @param map
     */
    public void setMap(TiledMap map) {
        this.map = map;
    }
    /**
     * show map on screen
     */
    @Override
    public void show() {

//        Gdx.input.setInputProcessor(playerController);
//        Gdx.input.setInputProcessor(dialogueController);
        Gdx.input.setInputProcessor(uiStage);

        MainMenuScreen.logArea.setPosition(Gdx.graphics.getWidth()/80,Gdx.graphics.getHeight() /80 );
        MainMenuScreen.logArea.setSize(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/6);
        uiStage.addActor(MainMenuScreen.logArea);
        uiStage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(2)));


        if(isUserPlay){
            player.setStrategy(new HumanPlayerStrategy(this));
        } else{
            player.setStrategy(new ComputerPlayerStrategy(this));
        }

//        enemyIterator = mapModel.getEnemyLocationList().keySet().iterator();
//        enemyPointer = enemyIterator.next();
    }
    /**
     * set background
     */
    @Override
    public void render(float delta)  {

        isHitObject = false;

        renderer.setView(cam);
        renderer.render();

//        cam.position.set(player.getPosition().x + (player.getCurrentFrame().getRegionWidth() / 2), player.getPosition().y + player.getCurrentFrame().getRegionHeight() / 2, 0);
//        batch.setProjectionMatrix(cam.combined);
//        cam.update();
        player.executeSetupCameraStategy();

        batch.begin();
        mapModel.getEntryDoor().draw(batch);
        mapModel.getExitDoor().draw(batch);
//
//        batch.draw(player.getCurrentFrame(), player.getPosition().x, player.getPosition().y );
//        //draw walls on screen
//        for(Wall wallCur : mapModel.getWallLocationList() ){
//            wallCur.draw(batch);
//            if(player.getBound().overlaps(wallCur)){
//                playerController.reAdjust(0);
//                isHitObject = true;
//            }
//        }
//
//
//        Vector2 cur;
//            // draw items on screen
//        keySetIterator = mapModel.getItemLocationList().keySet().iterator();
//
//        if(messageDialog.isFinished()){
//            messageDialog.setVisible(false);
//        }
//        while(keySetIterator.hasNext()){
//            cur = keySetIterator.next();
//            mapModel.getItemLocationList().get(cur).draw(batch, cur);
//            if(player.getBound().overlaps(mapModel.getItemLocationList().get(cur)) ){
//                playerController.pickupItem(mapModel.getItemLocationList().get(cur));
//                messageDialog.setVisible(true);
//                dialogueController.animateText(mapModel.getItemLocationList().get(cur).toString()+"  found!");
//                keySetIterator.remove();
//            }
//        }
//
//        // draw enemy on screen
//        keySetIterator = mapModel.getEnemyLocationList().keySet().iterator();
//        while(keySetIterator.hasNext()){
//            cur = keySetIterator.next();
//            mapModel.getEnemyLocationList().get(cur).draw(batch, cur, false);
//            if(player.getBound().overlaps(mapModel.getEnemyLocationList().get(cur).getBound()) ){
//                playerController.reAdjust(5);
//                isHitObject = true;
//                if(! mapModel.getEnemyLocationList().get(cur).isDead()){
//                    mapModel.getEnemyLocationList().get(cur).underAttack();
//                } else{
//                	playerController.teleport(0, -8);
//                }
//            }
//        }
//
//
//        // draw NPC on screen
//        keySetIterator = mapModel.getFriendLocationList().keySet().iterator();
//
//        while(keySetIterator.hasNext()){
//             cur = keySetIterator.next();
//            mapModel.getFriendLocationList().get(cur).draw(batch, cur, true);
//            if(player.getBound().overlaps(mapModel.getFriendLocationList().get(cur).getBound()) ){
//                playerController.reAdjust(5);
//                isHitObject = true;
//            }
//        }
//
//        //draw exit door on screen, exit mechanism
//        if(player.getBound().overlaps(mapModel.getExitDoor()) ) {
//            if (campaign.getMapPack().size == count + 1) {
//                player.setPosition(new Vector2(-1000,-1000));
//                uiStage.addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
//                    @Override
//                    public void run() {
//                        MainMenuScreen.logArea.clear();
//                        game.setScreen(new MainMenuScreen(game));
//                    }
//                })));
//            } else {
//                player.getCharacter().promoteUp();
//                count++;
//                player.setPosition(new Vector2(-1000,-1000));
//                uiStage.addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
//                    @Override
//                    public void run() {
//                        //campaign.getMapPack().removeIndex(0);
//                        System.out.println("loading map number "+count);
//                        MainMenuScreen.logArea.clear();
//                        game.setScreen(new GameScreen(game, player.getCharacter(), campaign.getMapPack().get(count), campaign));
//                    }
//                })));
//            }
//        }
//
//
//        if(player.getBound().overlaps(mapModel.getEntryDoor()) ){
//            playerController.teleport(0, -5);
//            playerController.reAdjust(0);
//            isHitObject = true;
//        }
//
//
//        if(enemyPointer != null && ! isHitObject && ! mapModel.getEnemyLocationList().get(enemyPointer).isDead() ){
//            playerController.walkTo(mapModel.getEnemyLocationList().get(enemyPointer).getBound().x, mapModel.getEnemyLocationList().get(enemyPointer).getBound().y);
//        } else{
//            if(enemyIterator.hasNext() && mapModel.getEnemyLocationList().get(enemyPointer).isDead()){
//                enemyPointer = enemyIterator.next();
//            }
//            if(!enemyIterator.hasNext()){
//            	playerController.walkTo(mapModel.getExitDoor().x + player.getBound().x, mapModel.getExitDoor().y - (player.getBound().y * 3 /4));
//            }
//        }

        player.renderInteraction();

        batch.end();
        player.updateDialogueStage(delta);
//    //        dialogueController.update(delta);
//    //        dialogueController.keyUp();
//        uiStage.act(delta);
//        uiStage.draw();
//    //        screenController.onClickListen();
    }

    /**
     * update the size of map
     */
    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
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
        uiStage.dispose();
    }

    /**
     * initial map view
     */
    private void initUI(){
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().update(Gdx.graphics.getWidth() , Gdx.graphics.getHeight() );
        //uiStage.setDebugAll(true);

        root = new Table();
        //root.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight() );
        root.setFillParent(true);
        uiStage.addActor(root);

        Table itemTable = constructItemTable();
        Table abilityTable = constructAbilityTable();
        Table dialogTable = constructDialogTable();
        Table playerEditTable = new Table();
        playerEditorBtn =new TextButton("I", MainMenuScreen.buttonStyle);
        playerEditorBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GamePlayerEditorScreen(game, (Player)player, mapModel, campaign, isUserPlay));
                return true;
            }
        });
        playerEditTable.add(playerEditorBtn).left().top().width(60).height(80);

        root.add(itemTable).expand().align(Align.right).bottom(); //.padBottom(20f);
        root.add(abilityTable).expand().align(Align.left).bottom().row();
        root.add(dialogTable).colspan(2).align(Align.center).top().maxHeight(360);
        root.add(playerEditTable).right().bottom().width(60).height(80);
    }
    /**
     * construct dialog table
     * @return
     */
    private Table constructDialogTable() {
        messageDialog = new DialogueBox(MainMenuScreen.skin);
        messageDialog.setVisible(false);
        dialogBox = new DialogueBox(MainMenuScreen.skin);
        dialogBox.setVisible(false);
        optionBox = new OptionBox(MainMenuScreen.skin);
        optionBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(messageDialog).colspan(2).top().row();
        dialogTable.add(dialogBox).expand().bottom();
        dialogTable.add(optionBox).width(Gdx.graphics.getWidth() / 5);
        return dialogTable;
    }
    /**
     * construct item table
     * @return
     */
    private Table constructItemTable(){
        equipmentMatrix = new Image[PublicParameter.ITEM_TYPE_COUNT];
        backpackMatrix = new Image[PublicParameter.ITEM_BACKPACK_ROW * PublicParameter.ITEM_BACKPACK_COLUMN];
        itemInfoLabel = new Label("", MainMenuScreen.skin);
        itemInfoLabel.setFontScale(.5f);
        itemInfoLabel.setVisible(false);

        Table itemTable = new Table();
        for (int i = 0; i < PublicParameter.ITEM_TYPE_COUNT; i++) {
            equipmentMatrix[i] = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
            equipmentMatrix[i].setVisible(false);
            itemTable.add(equipmentMatrix[i]).width(PublicParameter.ITEM_CELL_WIDTH / 2).height(PublicParameter.ITEM_CELL_HEIGHT / 2).fill().space(5);
        }
        itemTable.row();
        Image tempPointer;
        for (int i = 0; i < PublicParameter.ITEM_BACKPACK_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_BACKPACK_COLUMN; j++) {
                backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j].setVisible(false);
                tempPointer = backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j];
                itemTable.add(tempPointer).width(PublicParameter.ITEM_CELL_WIDTH / 2).height(PublicParameter.ITEM_CELL_HEIGHT / 2).fill().space(5);
            }
            itemTable.row();
        }
        itemTable.add(itemInfoLabel).colspan(7);
        return itemTable;
    }
    /**
     * construct ability table
     * @return
     */
    private Table constructAbilityTable() {
        abilityLabel = new Label("", MainMenuScreen.skin);
        abilityLabel.setVisible(false);
        abilityLabel.setFontScale(.8f);

        Table abilityTable = new Table();
        abilityTable.add(abilityLabel).expand();
        return abilityTable;
    }
    /**
     * update for observer
     */
    @Override
	public void update(Observable arg0, Object arg1) {
        if((Integer)arg1 == 0){
            abilityLabel.setVisible(true);
            abilityLabel.setText(((Character)arg0).displayAllAtributes());
        }
        if((Integer)arg1 == 1){
            updateBackpackMatrix(arg0, arg1);
            updateEquipmentMatrix(arg0, arg1);
            for(Image image : backpackMatrix){
                image.setVisible(true);
            }
            for(Image image : equipmentMatrix){
                image.setVisible(true);
            }
            abilityLabel.setText(((Character)arg0).displayAllAtributes());
            itemInfoLabel.setVisible(true);
        }
	}

    /**
     *
     * update matrix structure for equipment
     */
    public void updateEquipmentMatrix(Observable arg0, Object arg1) {
        for (int i = 0; i < PublicParameter.ITEM_TYPE_COUNT; i++) {
            if(((Character)arg0).getEquipment().get(Item.ItemType.getItemType(i)) != null){
                equipmentMatrix[i].setDrawable(new TextureRegionDrawable(new TextureRegion(((Character)arg0).getEquipment().get(Item.ItemType.getItemType(i)).getTexture())));
            }else{
                equipmentMatrix[i].setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
            }
        }
    }

    /**
     *
     * update matrix structure for backpack
     */
    public void updateBackpackMatrix(Observable arg0, Object arg1) {
        for (int i = 0; i < PublicParameter.ITEM_BACKPACK_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_BACKPACK_COLUMN; j++) {
                if ((i * PublicParameter.ITEM_BACKPACK_COLUMN) + j < ((Character)arg0).getBackpack().size()) {
                    backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j].setDrawable(new TextureRegionDrawable(new TextureRegion(((Character)arg0).getBackpack().get(i * PublicParameter.ITEM_BACKPACK_COLUMN + j).getTexture())));
                } else {
                    backpackMatrix[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j].setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png"))))); //= new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
            }
        }
    }

    /**
     * getter for Camera
     * @return
     */
    public OrthographicCamera getCam() {
        return cam;
    }

    /**
     * getter for ability label
     * @return
     */
    public Label getAbilityLabel() {
        return abilityLabel;
    }
    /**
     * getter for Backpack Image[]
     * @return
     */
    public Image[] getBackpackMatrix() {
        return backpackMatrix;
    }
    /**
     * getter for equipment Image[]
     * @return
     */
    public Image[] getEquipmentMatrix() {
        return equipmentMatrix;
    }
    /**
     * getter for item Information Label
     * @return
     */
	public Label getItemInfoLabel() {
		return itemInfoLabel;
	}
    /**
     * getter for Game map
     * @return
     */
    public Map getMapModel() {
        return mapModel;
    }

	public boolean isHitObject() {
		return isHitObject;
	}

	public void setHitObject(boolean isHitObject) {
		this.isHitObject = isHitObject;
	}

    public Dialogue getDialogue() {
        return dialogue;
    }

    public DialogueController getDialogueController() {
        return dialogueController;
    }

	public Player getPlayer() {
		return (Player)player;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public PlayerController getPlayerController() {
		return playerController;
	}

	public Game getGame() {
		return game;
	}


	public GameScreenController getScreenController() {
		return screenController;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public Stage getUiStage() {
		return uiStage;
	}

    public DialogueBox getDialogBox() {
        return dialogBox;
    }

    public HashMap<Vector2, GameActor> getNpcList() {
        return npcList;
    }

    public void setNpcList(HashMap<Vector2, GameActor> npcList) {
        this.npcList = npcList;
    }

    public boolean isUserPlay() {
		return isUserPlay;
	}
    
    public DialogueBox getMessageDialog() {
		return messageDialog;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		GameScreen.count = count;
	}


    
}
