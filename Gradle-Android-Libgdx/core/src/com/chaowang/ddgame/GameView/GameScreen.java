package com.chaowang.ddgame.GameView;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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
import com.chaowang.ddgame.GameModel.StrategyPattern.AggressiveNPCStrategy;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.GameModel.DialogueSystem.Dialogue;
import com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode;
import com.chaowang.ddgame.GameModel.StrategyPattern.ComputerPlayerStrategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.FreezingComputerPlayerStrategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.FreezingHumanPlayerStrategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.FreezingNPCStategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.FriendlyNPCStrategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.FrighteningNPCStrategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.FrighteningPlayerStrategy;
import com.chaowang.ddgame.GameModel.StrategyPattern.HumanPlayerStrategy;
import com.chaowang.ddgame.GameController.DialogueController;
import com.chaowang.ddgame.GameController.GameScreenController;
import com.chaowang.ddgame.GameController.NPCcontroller;
import com.chaowang.ddgame.GameController.PlayerController;
import com.chaowang.ddgame.GameUtl.DialogueBox;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.WeaponSpecialEnchantment;
import com.chaowang.ddgame.MenuModel.MapModel.Wall;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.MenuModel.MapModel.Map;

import com.chaowang.ddgame.GameUtl.OptionBox;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.util.CharacterScoreModifier;
import com.chaowang.ddgame.util.Dice;
import com.chaowang.ddgame.util.IntVector2Pair;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.WeaponSpecialEnchantment.WeaponEnchantement;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

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
    private NPCcontroller npcController;
    private GameScreenController screenController;
    private Label abilityLabel, itemInfoLabel;
    private Image[] backpackMatrix, equipmentMatrix;
    private TextButton playerEditorBtn;

    // game attributes
    private Player player;
    private NPC npcPointer;
    private Map mapModel;
    private Campaign campaign;
    private HashMap<Vector2,NPC> npcList;
    private LinkedList<IntVector2Pair> playOrderList;
    private IntVector2Pair currentRollVectorEntry;
    private Iterator<Vector2> keySetIterator ;
    private boolean isHitObject, isUserPlay, isActorPlaying;
    private int playerOrNPC = -1; //  1 is player, 2 is NPC
    private static int count=0;

    /**
     * constructor
     * @param game
     * @param character
     * @param map
     * @param camp
     */
    public GameScreen(Game game, Character character,Map map, Campaign camp, boolean isUserPlay) {
        this(game,new Player(new Vector2(1,1), character),map,camp, new HashMap<Vector2, NPC>(), new LinkedList<IntVector2Pair>(), isUserPlay);
    }

    /**
     * constructor
     * @param game
     * @param player
     * @param map
     * @param camp
     * @param actorList 
     */
    public GameScreen(Game game, Player player,Map map, Campaign camp, HashMap<Vector2, NPC> actorList, LinkedList<IntVector2Pair> playOrderList2 , boolean isUserPlay){
        this.game = game;
        this.player = player;
        this.mapModel = map;
        this.campaign = new Campaign(camp);
        this.npcList = actorList;
        this.playOrderList = playOrderList2;
        this.isUserPlay = isUserPlay;
        batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("terrain/terrain"+mapModel.getSize() + "x" + mapModel.getSize() + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(this.map);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        isActorPlaying = false;

        // re adjust player position, if player enter a new map
        if(player.getPosition().x ==1){
            if(mapModel.getEntryDoor().y - player.getBound().getHeight() > 0 ){
                player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                        mapModel.getEntryDoor().y - player.getBound().getHeight()));
            } else {
                player.setPosition(new Vector2(mapModel.getEntryDoor().x + mapModel.getEntryDoor().width / 2 - player.getBound().getWidth() /2,
                        mapModel.getEntryDoor().y +  mapModel.getEntryDoor().getHeight()));
            }
        }

        initUI();

        if(mapModel.getLevel() != player.getCharacter().getLevel()){
            mapModel.adjustLevel(player.getCharacter().getLevel());
        }

        // extract all the character from map to npcList, queue them in play order list, decide precedence by dice roll

        if(playOrderList.size()==0 ){
            initializeNpcListAndOrder(player);
        }
        mapModel.getFriendLocationList().clear();
        mapModel.getEnemyLocationList().clear();

        // instentiate controller
        playerController = new PlayerController(player, this);
        npcController = new NPCcontroller((NPC)npcPointer,this);
        dialogueController = new DialogueController(dialogBox, optionBox, messageDialog);
        screenController = new GameScreenController(this,this.mapModel, (Player)this.player);

        initializeDialogue();

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
        MainMenuScreen.logArea.setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);

        uiStage.addActor(MainMenuScreen.logArea);
        uiStage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(2)));

        isActorPlaying=true; //start to queue and let player NPC play
    }
    /**
     * set background
     */
    @Override
    public void render(float delta)  {

        isHitObject = false;
        if(isActorPlaying){
            isActorPlaying = false;
            turn();
        }

        renderer.setView(cam);
        renderer.render();

        if(playerOrNPC ==1){
            player.executeSetupCameraStategy();
        } else if(playerOrNPC ==2){
            npcPointer.executeSetupCameraStategy();
        }

        batch.begin();
        mapModel.getEntryDoor().draw(batch);
        mapModel.getExitDoor().draw(batch);
        batch.draw(((Player)player).getCurrentFrame(), player.getPosition().x, player.getPosition().y );

        if(playerOrNPC ==1){
            player.renderInteraction();
        } else if(playerOrNPC ==2){
            npcPointer.renderInteraction();
        }

        batch.end();
        if(playerOrNPC ==1){
            player.updateDialogueStage(delta);
        } else if(playerOrNPC ==2){
            npcPointer.updateDialogueStage(delta);
        }

    }
    /**
     * turn screen
     */
    private void turn(){
        currentRollVectorEntry = playOrderList.poll();
        if(currentRollVectorEntry.getValue().epsilonEquals(player.getPosition(),0.1f)){
            playerOrNPC =1;
            // ENCHANTMENT effect, burning
            if(player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()] >10){
                player.getCharacter().reduceHitPoints(player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()] % 10);
                player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()] -= 10;
            }
            // ENCHANTMENT effect, slaying
            if(player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.SLAYING.getIndex()] > 0){
                MainMenuScreen.logArea.appendText("yourself is slaying\n");
                player.getCharacter().makeDead();
                player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.SLAYING.getIndex()] = 0;
            }
            // check player is dead
            if (player.getCharacter().isDead()) {
                exitIfPlayerDie();
            } else{
                if(isUserPlay){
                    player.setStrategy(new HumanPlayerStrategy(this));
                    // ENCHANTMENT effect, freezing
                    if(player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()] > 0){
                        player.setStrategy(new FreezingHumanPlayerStrategy(this));
                        MainMenuScreen.logArea.appendText("yourself is freezing\n");
                        player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()] -= 1;
                    }
                } else{
                    player.setStrategy(new ComputerPlayerStrategy(this));
                    // ENCHANTMENT effect, freezing
                    if(player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()] > 0){
                        player.setStrategy(new FreezingComputerPlayerStrategy(this));
                        MainMenuScreen.logArea.appendText("yourself is freezing\n");
                        player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()] -= 1;
                    }
                }
                // ENCHANTMENT effect, FRIGHTENING
                if(player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FRIGHTENING.getIndex()] > 0){
                    player.setStrategy(new FrighteningPlayerStrategy(this));
                    MainMenuScreen.logArea.appendText("yourself is frightening\n");
                    player.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FRIGHTENING.getIndex()] -= 1;
                }
                playerController.setStartToMove(true);
                playerController.setDecideToStop(false);
                MainMenuScreen.logArea.appendText("yourself start to play\n");
            }
        } else{
            playerOrNPC =2;
            // ENCHANTMENT effect, burning
            if(npcList.get(currentRollVectorEntry.getValue()).getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()] >10){
                int damagePoint = npcList.get(currentRollVectorEntry.getValue()).getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()] % 10;
                npcList.get(currentRollVectorEntry.getValue()).getCharacter().reduceHitPoints(damagePoint);
                npcList.get(currentRollVectorEntry.getValue()).getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()] -= 10;
            }
            // ENCHANTMENT effect, slaying
            if(npcList.get(currentRollVectorEntry.getValue()).getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.SLAYING.getIndex()] > 0){
                npcList.get(currentRollVectorEntry.getValue()).getCharacter().makeDead();
                MainMenuScreen.logArea.appendText(npcList.get(currentRollVectorEntry.getValue()).getCharacter().getName() + " is slaying\n");
                npcList.get(currentRollVectorEntry.getValue()).getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.SLAYING.getIndex()] = 0;
            }
            // if dead, skip the turn
            if(npcList.get(currentRollVectorEntry.getValue()).getCharacter().isDead()){
                MainMenuScreen.logArea.appendText(npcList.get(currentRollVectorEntry.getValue()).getCharacter().getName()+ "is dead, skip play\n");
                startNextRound();
                playerOrNPC = -1;
            } else{
                npcPointer = npcList.remove(currentRollVectorEntry.getValue());
                npcController.setNpc((NPC)npcPointer);
                // ENCHANTMENT effect, pacifying
                if(npcPointer != null && npcPointer.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.PACIFYING.getIndex()] ==1){
                    ((NPC)npcPointer).setFriendly(true);
                    MainMenuScreen.logArea.appendText(npcPointer.getCharacter().getName() + " is pacifying\n");
                    npcPointer.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.PACIFYING.getIndex()] = 0;
                }
                // friend or not change strategy
                if(((NPC)npcPointer).isFriendly()){
                    npcPointer.setStrategy(new FriendlyNPCStrategy(this));
                }else{
                    npcPointer.setStrategy(new AggressiveNPCStrategy(this));
                }
                // ENCHANTMENT effect, FREEZING
                if(npcPointer != null && npcPointer.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()] > 0){
                    npcPointer.setStrategy(new FreezingNPCStategy(this));
                    MainMenuScreen.logArea.appendText(npcPointer.getCharacter().getName() + " is freezing\n");
                    npcPointer.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()] -= 1;
                }
                // ENCHANTMENT effect, Frightening
                if(npcPointer != null && npcPointer.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FRIGHTENING.getIndex()] > 0){
                    npcPointer.setStrategy(new FrighteningNPCStrategy(this));
                    MainMenuScreen.logArea.appendText(npcPointer.getCharacter().getName() + " is frightening\n");
                    npcPointer.getCharacter().getWeaponEnchantmentInfection()[WeaponSpecialEnchantment.WeaponEnchantement.FRIGHTENING.getIndex()] -= 1;
                }
                npcController.setStartToMove(true);
                MainMenuScreen.logArea.appendText(npcPointer.getCharacter().getName() + " start to play\n");
            }
        }
    }
    /**
     * exit game when die
     */
    private void exitIfPlayerDie() {
        if (player.getCharacter().isDead()) {
            playerController.setStartToMove(false);  // does not allow next npc play, in fade out 3 seconds
            npcController.setStartToMove(false);  // does not allow next npc play, in fade out 3 seconds
            player.setPosition(new Vector2(-1000, -1000));
            uiStage.addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                @Override
                public void run() {
                    MainMenuScreen.logArea.clear();
                    game.setScreen(new MainMenuScreen(game));
                }
            })));
        }
    }
    /**
     * to next round
     */
    public void startNextRound() {
        if(playerOrNPC==1){
            currentRollVectorEntry.setValue(player.getPosition());
            playOrderList.offer(currentRollVectorEntry);
            isActorPlaying = true;
        } else if(playerOrNPC == 2){
            if(npcPointer!= null){
                currentRollVectorEntry.setValue(npcPointer.getPosition());
                playOrderList.offer(currentRollVectorEntry);
                npcList.put(currentRollVectorEntry.getValue(),npcPointer);
            }
            isActorPlaying = true;
        }
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
     * initialize Npc List And Order
     * @param player
     */
    private void initializeNpcListAndOrder(GameActor player) {
        Vector2 cur;
        int diceRoll;
        keySetIterator = mapModel.getEnemyLocationList().keySet().iterator();
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            diceRoll = Dice.roll(1, 20);
            MainMenuScreen.logArea.appendText(mapModel.getEnemyLocationList().get(cur).getName() + " roll dice :"+diceRoll + "+ DexModifier\n");
            diceRoll += CharacterScoreModifier.abilityModifier(mapModel.getEnemyLocationList().get(cur).getDexterity());
            playOrderList.add(new IntVector2Pair(diceRoll, new Vector2(cur)));
            npcList.put(cur, new NPC(cur,mapModel.getEnemyLocationList().get(cur), false));
        }
        keySetIterator = mapModel.getFriendLocationList().keySet().iterator();
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            diceRoll = Dice.roll(1, 20) + CharacterScoreModifier.abilityModifier(mapModel.getFriendLocationList().get(cur).getDexterity());
            MainMenuScreen.logArea.appendText(mapModel.getFriendLocationList().get(cur).getName() + " roll dice :"+diceRoll + "\n");
            playOrderList.add(new IntVector2Pair(diceRoll, new Vector2(cur)));
            npcList.put(cur, new NPC(cur,mapModel.getFriendLocationList().get(cur), true));
        }

        // add player to play order linked list
        diceRoll = Dice.roll(1, 20) + CharacterScoreModifier.abilityModifier(player.getCharacter().getDexterity()); //dice roll+ deterity ability modifier
        MainMenuScreen.logArea.appendText("yourself roll dice :"+diceRoll + "\n");
        playOrderList.add(new IntVector2Pair(diceRoll, new Vector2(player.getPosition())));
        Collections.sort(playOrderList);
    }
    /**
     * initialize Dialogue
     */
    private void initializeDialogue() {
        dialogue = new Dialogue();
        DialogueNode node1 = new DialogueNode("Your Turn starts", 0);
        DialogueNode node2 = new DialogueNode("How do you want your move ?", 1);

        node1.makeLinear(node2.getID());
        node2.addChoice("Move", 2);
        node2.addChoice("Fight",3);
        node2.addChoice("Trade", 4);
        dialogue.addNode(node1);
        dialogue.addNode(node2);
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
                resetPlayersTurn();
                game.setScreen(new GamePlayerEditorScreen(game, player, mapModel, campaign, npcList, playOrderList, isUserPlay));
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
     * reset Players' Turn
     */
    private void resetPlayersTurn() {
        MainMenuScreen.logArea.appendText("reset player's turn before editor");
        player.setPosition(playerController.getPositionBeforeMove());
        currentRollVectorEntry.setValue(player.getPosition());
        playOrderList.addFirst(currentRollVectorEntry);
        if(player.getCharacter().getWeaponEnchantmentInfection()[WeaponEnchantement.BURNING.getIndex()] >10){
            int lostHp = player.getCharacter().getWeaponEnchantmentInfection()[WeaponEnchantement.BURNING.getIndex()] % 10;
            player.getCharacter().setHitPoints(player.getCharacter().getHitPoints() + lostHp);
            player.getCharacter().getWeaponEnchantmentInfection()[WeaponEnchantement.BURNING.getIndex()] += 10;
        }
        if(player.getCharacter().getWeaponEnchantmentInfection()[WeaponEnchantement.FREEZING.getIndex()] >0){
            player.getCharacter().getWeaponEnchantmentInfection()[WeaponEnchantement.FREEZING.getIndex()] += 1;
        }
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
    /**
     * is Hit Object or not
     * @return
     */
	public boolean isHitObject() {
		return isHitObject;
	}
	/**
	 * set Hit Object
	 * @param isHitObject
	 */
	public void setHitObject(boolean isHitObject) {
		this.isHitObject = isHitObject;
	}
	/**
	 * get Dialogue
	 * @return
	 */
    public Dialogue getDialogue() {
        return dialogue;
    }
    /**
     * get Dialogue Controller
     * @return
     */
    public DialogueController getDialogueController() {
        return dialogueController;
    }
    /**
     * get Player
     * @return
     */
	public Player getPlayer() {
		return (Player)player;
	}
	/**
	 * get Npc
	 * @return
	 */
	public GameActor getNpc() {
		return npcPointer;
	}
	/**
	 * get Batch
	 * @return
	 */
	public SpriteBatch getBatch() {
		return batch;
	}
	/**
	 * get Player Controller
	 * @return
	 */
	public PlayerController getPlayerController() {
		return playerController;
	}
	/**
	 * get Npc Controller
	 * @return
	 */
	public NPCcontroller getNpcController() {
		return npcController;
	}
	/**
	 * get Game
	 * @return
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * get Screen Controller
	 * @return
	 */
	public GameScreenController getScreenController() {
		return screenController;
	}
	/**
	 * get Campaign
	 * @return
	 */
	public Campaign getCampaign() {
		return campaign;
	}
	/**
	 * get UI Stage
	 * @return
	 */
	public Stage getUiStage() {
		return uiStage;
	}
	/**
	 * get Dialog Box
	 * @return
	 */
    public DialogueBox getDialogBox() {
        return dialogBox;
    }
    /**
     * get NPC List
     * @return
     */
    public HashMap<Vector2, NPC> getNpcList() {
        return npcList;
    }
    /**
     * set NPC List value
     * @param npcList
     */
    public void setNpcList(HashMap<Vector2, NPC> npcList) {
        this.npcList = npcList;
    }
    /**
     * decide play or not
     * @return
     */
    public boolean isUserPlay() {
		return isUserPlay;
	}
    /**
     * get Message Dialog
     * @return
     */
    public DialogueBox getMessageDialog() {
		return messageDialog;
	}
    /**
     * get Count
     * @return
     */
	public static int getCount() {
		return count;
	}
	/**
	 * get Player Editor button
	 * @return
	 */
    public TextButton getPlayerEditorBtn() {
        return playerEditorBtn;
    }
    /**
     * get Current Roll Vector Entry
     * @return
     */
    public IntVector2Pair getCurrentRollVectorEntry() {
        return currentRollVectorEntry;
    }
    /**
     * set Current Roll Vector Entry
     * @param currentRollVectorEntry
     */
    public void setCurrentRollVectorEntry(IntVector2Pair currentRollVectorEntry) {
        this.currentRollVectorEntry = currentRollVectorEntry;
    }
    /**
     * set Count
     * @param count
     */
    public static void setCount(int count) {
		GameScreen.count = count;
	}
    /**
     * decide is actor playing or not
     * @return
     */
    public boolean isActorPlaying() {
        return isActorPlaying;
    }
    /**
     * set actor playing
     * @param actorPlaying
     */
    public void setActorPlaying(boolean actorPlaying) {
        isActorPlaying = actorPlaying;
    }
    /**
     * get play order List
     * @return
     */
    public LinkedList<IntVector2Pair> getplayOrderList() {
        return playOrderList;
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
    
}
