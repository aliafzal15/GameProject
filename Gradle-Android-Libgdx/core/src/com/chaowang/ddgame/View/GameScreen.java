package com.chaowang.ddgame.View;

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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chaowang.ddgame.CampaignModel.Campaign;
import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.DialogueSystem.Dialogue;
import com.chaowang.ddgame.DialogueSystem.DialogueNode;
import com.chaowang.ddgame.GameController.DialogueController;
import com.chaowang.ddgame.GameController.GameScreenController;
import com.chaowang.ddgame.GameController.PlayerController;
import com.chaowang.ddgame.GameUI.DialogueBox;
import com.chaowang.ddgame.ItemModel.Item;
import com.chaowang.ddgame.MapModel.Wall;
import com.chaowang.ddgame.PlayModel.Player;
import com.chaowang.ddgame.MapModel.Map;

import com.chaowang.ddgame.GameUI.OptionBox;
import com.chaowang.ddgame.PublicParameter;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

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
    // followed from video
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


    private Player player;
    private Map mapModel;
    private Campaign campaign;
    private Iterator<Vector2> keySetIterator ;
    private boolean isHitObject;
    private static int count=0;

    public GameScreen(Game game, Character character,Map map, Campaign camp) {
        this(game,new Player(new Vector2(1,1), character),map,camp);

        if(mapModel.getEntryDoor().y - player.getBound().getHeight() > 0 ){
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y - player.getBound().getHeight()));
        } else {
            player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                    mapModel.getEntryDoor().y +  mapModel.getEntryDoor().getHeight()));
        }
    }

    public GameScreen(Game game, Player player,Map map){
        this.game = game;
        this.player = player;
        this.mapModel = map;
        this.map = new TmxMapLoader().load("terrain/terrain"+mapModel.getSize() + "x" + mapModel.getSize() + ".tmx");

        playerController = new PlayerController(player, this);
        dialogueController = new DialogueController(dialogBox, optionBox, messageDialog);
        screenController = new GameScreenController(this,this.mapModel, this.player);

        if(mapModel.getLevel() != player.getCharacter().getLevel()){
            mapModel.adjustLevel(player.getCharacter().getLevel());
        }

    }

    public GameScreen(Game game, Player player,Map map, Campaign camp){
        this.game = game;
        this.player = player;
        this.mapModel = map;
        this.campaign = new Campaign(camp);
        batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("terrain/terrain"+mapModel.getSize() + "x" + mapModel.getSize() + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(this.map);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initUI();

        playerController = new PlayerController(player, this);
        dialogueController = new DialogueController(dialogBox, optionBox, messageDialog);
        screenController = new GameScreenController(this,this.mapModel, this.player);

        dialogue = new Dialogue();
        DialogueNode node1 = new DialogueNode("Hello Adventurer!\n Welcome to town.", 0);
        DialogueNode node2 = new DialogueNode("How can I help you?", 1);

        node1.makeLinear(node2.getID());
        node2.addChoice("Trade", 2);
        node2.addChoice("Leave", 3);
        dialogue.addNode(node1);
        dialogue.addNode(node2);

        // from show
        if(mapModel.getLevel() != player.getCharacter().getLevel()){
            mapModel.adjustLevel(player.getCharacter().getLevel());
        }

    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    @Override
    public void show() {

//        Gdx.input.setInputProcessor(playerController);
//        Gdx.input.setInputProcessor(dialogueController);
        Gdx.input.setInputProcessor(uiStage);

        //stage.addActor(fade);
//        stage.draw();
        uiStage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(2)));
    }

    @Override
    public void render(float delta)  {
        isHitObject = false;

        renderer.setView(cam);
        renderer.render();

        cam.position.set(player.getPosition().x + (player.getCurrentFrame().getRegionWidth() / 2), player.getPosition().y + player.getCurrentFrame().getRegionHeight() / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        dialogueController.update(delta);
        uiStage.act(delta);


        batch.begin();
        batch.draw(player.getCurrentFrame(), player.getPosition().x, player.getPosition().y );

        mapModel.getEntryDoor().draw(batch);
        mapModel.getExitDoor().draw(batch);

        //draw walls on screen
        for(Wall cur : mapModel.getWallLocationList() ){
            cur.draw(batch);
            if(player.getBound().overlaps(cur)){
                playerController.reAdjust(0);
                isHitObject = true;
            }
        }

        // draw items on screen
        keySetIterator = mapModel.getItemLocationList().keySet().iterator();
        Vector2 cur;

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            mapModel.getItemLocationList().get(cur).draw(batch, cur);
            if(player.getBound().overlaps(mapModel.getItemLocationList().get(cur)) ){
                playerController.pickupItem(mapModel.getItemLocationList().get(cur));
                dialogueController.animateText(mapModel.getItemLocationList().get(cur).toString()+"  found!");
                keySetIterator.remove();
            }
        }

        // draw NPC on screen
        keySetIterator = mapModel.getFriendLocationList().keySet().iterator();

        while(keySetIterator.hasNext()){
             cur = keySetIterator.next();
            mapModel.getFriendLocationList().get(cur).draw(batch, cur, true);
            if(player.getBound().overlaps(mapModel.getFriendLocationList().get(cur).getBound()) ){
                playerController.reAdjust(5);
                isHitObject = true;
                dialogueController.startDialogue(dialogue);
                if(!dialogueController.isIndexFlag()) {
                    System.out.println("looping");
                } else {
                    if (dialogueController.getAnswerIndex() == 0) {
                        game.setScreen(new GameItemExchangeScreen(game,player,mapModel,campaign, cur, true));
                    }
                }
//                new Thread(new Runnable() {
//                    public void run() {
//                        if(! dialogueController.isIndexFlag()){
//                            System.out.println("looping");
//                            try{Thread.sleep(500);}
//                            catch(Exception e){ e.printStackTrace();}
//                        } else{
//                            if(dialogueController.getAnswerIndex() ==0 ){
//                                game.setScreen(new GameSelectionScreen(game));
//                            }
//                        }
//                    }
//                }).start();
            }
        }

        // draw enemy on screen
        keySetIterator = mapModel.getEnemyLocationList().keySet().iterator();

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            mapModel.getEnemyLocationList().get(cur).draw(batch, cur, false);
            if(player.getBound().overlaps(mapModel.getEnemyLocationList().get(cur).getBound()) ){
                playerController.reAdjust(5);
                isHitObject = true;
                if(! mapModel.getEnemyLocationList().get(cur).isDead()){
                    mapModel.getEnemyLocationList().get(cur).underAttack();
                } else{
                    game.setScreen(new GameItemExchangeScreen(game,player,mapModel,campaign, cur, false));
                }
            }
        }

        //draw player on screen
        if(player.getBound().overlaps(mapModel.getExitDoor()) ) {
            if (player.getPosition().y + player.getBound().getHeight() <= mapModel.getExitDoor().y + 1f) {
            	if(screenController.isEnemyAllDead()){
                    if (campaign.getMapPack().size == count + 1) {
                        player.setPosition(new Vector2(-1000,-1000));
                        uiStage.addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new MainMenuScreen(game));
                            }
                        })));
                    } else {
                        player.getCharacter().promoteUp();
                        count++;
                        player.setPosition(new Vector2(-1000,-1000));
                        uiStage.addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                //campaign.getMapPack().removeIndex(0);
                                System.out.println("loading map number "+count);
                                game.setScreen(new GameScreen(game, player.getCharacter(), campaign.getMapPack().get(count), campaign));
                            }
                        })));
                    }
            	}else{
                    dialogueController.animateText("There are still hostile monsters survive on the map");
                    playerController.reAdjust(5);
                    isHitObject = true;
            	}
            } else {
                playerController.reAdjust(0);
                isHitObject = true;
            }
        }


        if(player.getBound().overlaps(mapModel.getEntryDoor()) ){
            playerController.reAdjust(0);
            isHitObject = true;
        }

        if(! isHitObject ){
            playerController.keyDown();
        }
        batch.end();

        dialogueController.keyUp();
        uiStage.draw();
        screenController.onClickListen();

    }

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
                game.setScreen(new GamePlayerEditorScreen(game, player, mapModel, campaign));
                return true;
            }
        });
        playerEditTable.add(playerEditorBtn).left().top().width(60).height(80);

        root.add(itemTable).expand().align(Align.right).bottom(); //.padBottom(20f);
        root.add(abilityTable).expand().align(Align.left).bottom().row();
        root.add(dialogTable).colspan(2).align(Align.center).top().maxHeight(360);
        root.add(playerEditTable).right().bottom().width(60).height(80);
    }

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

    private Table constructAbilityTable() {
        abilityLabel = new Label("",MainMenuScreen.skin);
        abilityLabel.setVisible(false);
        abilityLabel.setFontScale(.8f);

        Table abilityTable = new Table();
        abilityTable.add(abilityLabel).expand();
        return abilityTable;
    }

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

    public OrthographicCamera getCam() {
        return cam;
    }

    public Label getAbilityLabel() {
        return abilityLabel;
    }

    public Image[] getBackpackMatrix() {
        return backpackMatrix;
    }

    public Image[] getEquipmentMatrix() {
        return equipmentMatrix;
    }

	public Label getItemInfoLabel() {
		return itemInfoLabel;
	}

    public Map getMapModel() {
        return mapModel;
    }
}
