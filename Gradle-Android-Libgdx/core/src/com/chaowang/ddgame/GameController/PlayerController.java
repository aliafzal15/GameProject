package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponModel;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.GameView.GameScreen;

import java.util.Iterator;
import java.util.Map;

/**
 * controller for player
 * @author chao wang
 * @version 2.0
 */
public class PlayerController{

    private Player player;
    private GameScreen view;
    private Rectangle mapBound;
    private float stateTime;
    private String movement;
    private boolean isStartToMove, decideToStop;  // flag to determine status of start moving, and status of stop if press "F"
    private boolean ableToAttack;  // flag to reject attack, while moving
    private float walkingDistance;   // measure the distance for each move
    private Vector2 positionBeforeMove;
    private Rectangle playerTradeRange, meleeAttackRangeX, meleeAttackRangeY;
    private ShapeRenderer shapeRenderer;
    private Circle rangeAttackrange;
    private Iterator<Vector2> keySetIterator, enemyIterator ;
    private Vector2 cur, enemyPointer;
    private Vector3 touch;
    private Map.Entry<Vector2,GameActor> entry;
    private Iterator<Map.Entry<Vector2,GameActor>> entrySetIterator;


    /**
     * constructor
     * @param p
     * @param screen
     */
    public PlayerController(Player p, GameScreen screen){
        this.player = p;
        this.view = screen;
        MapProperties prop = view.getMap().getProperties();
        mapBound = new Rectangle(0, 0, prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class), prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));
        stateTime = 0f;
        isStartToMove =false;
        decideToStop =false;
        ableToAttack = true;
        walkingDistance =0f;
        positionBeforeMove = new Vector2(player.getPosition());
        shapeRenderer = new ShapeRenderer();
        touch = new Vector3();
        playerTradeRange = new Rectangle();
        meleeAttackRangeX = new Rectangle();
        meleeAttackRangeY = new Rectangle();
        rangeAttackrange =new Circle();
        enemyIterator = view.getNpcList().keySet().iterator();
        if(enemyIterator.hasNext()){
            enemyPointer = enemyIterator.next();
        }
    }
    public PlayerController(Player p){
        this.player = p;
    }

    public void movePlayer(){
    	// pin the position before move
    	if(isStartToMove==false){
    		isStartToMove=true;
    		decideToStop = false;
    	}else{             // moving and terminate move
            if(walkingDistance > PublicParameter.GAME_PIXEL_SIZE*3 ||  //player.getPosition().dst(positionBeforeMove)
    				decideToStop){
                isStartToMove=false;
//                decideToStop = false;
                positionBeforeMove.set(player.getPosition());
                walkingDistance =0f;
                keyDown(false);  // player does not move if out of area, or hit F
                // flag isActorPlaying to be true for next player, put npc in queue
                view.startNextRound();
//                gameScreen.getDialogueController().startDialogue(gameScreen.getDialogue());
            } else{
        		renderMovementArea(positionBeforeMove);    //draw movement area
    			if(!view.isHitObject()){				//passed from GameScreen, if iteration says player hit someone,
        			keyDown(true);
    			}
    			if(Gdx.input.isKeyPressed(Input.Keys.F )){
    				isStartToMove=false;
    				decideToStop = true;
                    positionBeforeMove.set(player.getPosition());
                    walkingDistance =0f;
                    // flag isActorPlaying to be true for next player, put npc in queue
                    view.startNextRound();
                    //                    gameScreen.getDialogueController().startDialogue(gameScreen.getDialogue());
                }
    		}
    	}
    }


    public void walkTo(float x, float y){

//		if(Gdx.input.isKeyPressed(Input.Keys.F )){
//			isStartToMove=true;
//        }
    	if(isStartToMove){ //    	moving and terminate move
    		if(walkingDistance > PublicParameter.GAME_PIXEL_SIZE*3 ){
                isStartToMove=false;
                ableToAttack = true;
                positionBeforeMove.set(player.getPosition());
                walkingDistance =0f;
                // flag isActorPlaying to be true for next player, put npc in queue
                view.startNextRound();
    		} else{
        		keyDown( x,  y);
                ableToAttack = false;
            }
    	}
    }

    /**
     * use keyboard to control the players' movement
     */
    public void keyDown(boolean ableToMove){

    	if(ableToMove){
    		if(stateTime < 4 ){
                stateTime += Gdx.graphics.getDeltaTime() ;
            }else{
                stateTime = 0;
            }

            if(Gdx.input.isKeyPressed(Input.Keys.W )  && player.getPosition().y + player.getCurrentFrame().getRegionHeight()< mapBound.getHeight() ){
                player.move(0,1);
                movement = "up";
                walkingDistance += 1*2f;
                player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(12 + (stateTime *4) % 4));
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.S ) && player.getPosition().y > mapBound.getY() ){
                player.move(0,-1);
                movement = "down";
                walkingDistance += 1*2f;
                player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(0 + (stateTime *4) % 4));
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.A) && player.getPosition().x > mapBound.getX()){
                player.move(-1,0);
                movement = "left";
                walkingDistance += 1*2f;
                player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(4 + (stateTime *4) % 4));
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.D ) && player.getPosition().x + player.getCurrentFrame().getRegionWidth() < mapBound.getWidth()) {
                player.move(1, 0);
                movement = "right";
                walkingDistance += 1*2f;
                player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(8 + (stateTime * 4) % 4));
            }
    	}

    }

    /**
     * players' auto movement to position (x,y)
     */
    public void keyDown(float x, float y){

        if(stateTime < 4 ){
            stateTime += Gdx.graphics.getDeltaTime() ;
        }else{
            stateTime = 0;
        }

        if(player.getPosition().y < y  && player.getPosition().y + player.getCurrentFrame().getRegionHeight()< mapBound.getHeight() ){
            player.move(0,1);
            movement = "up";
            walkingDistance += 1*2f;
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(12 + (stateTime *4) % 4));
        }
        if(player.getPosition().y > y && player.getPosition().y > mapBound.getY() ){
            player.move(0,-1);
            movement = "down";
            walkingDistance += 1*2f;
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(0 + (stateTime *4) % 4));
        }
        if(player.getPosition().x > x && player.getPosition().x > mapBound.getX()){
            player.move(-1,0);
            movement = "left";
            walkingDistance += 1*2f;
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(4 + (stateTime *4) % 4));
        }
        if(player.getPosition().x < x && player.getPosition().x + player.getCurrentFrame().getRegionWidth() < mapBound.getWidth()) {
            player.move(1, 0);
            movement = "right";
            walkingDistance += 1*2f;
            player.setCurrentFrame((TextureRegion) player.getAnimation().getKeyFrame(8 + (stateTime * 4) % 4));
        }
    }

    /**
     * adjust the location of player when moving
     */
    public void reAdjust(float span){
        if(span <= 0 ){
            span = 0.5f;
        }
        if(movement == "up"){
        	player.move(0,-1 * span);
        }
        else if(movement == "down"){
        	player.move(0,1 * span);
        }
        else if(movement == "left"){
        	player.move(1 * span,0);
        }
        else if(movement == "right"){
        	player.move(-1 * span,0);
        }

    }
    public void teleport(float x, float y){
        player.move(x,y);
    }

    /**
     * adjust the location of player when auto moving
     */
    public void walkReAdjust(float span){
        if(span <= 0 ){
            span = 0.5f;
        }
        if(movement == "up"){
            player.move(0,-1 * span);
        }
        if(movement == "down"){
            player.move(0,1 * span);
        }
        if(movement == "left"){
            player.move(1 * span,0);
        }
        if(movement == "right"){
            player.move(-1 * span,0);
        }
    }

    /**
     * decide game state
     * @return
     */
    public boolean isEnemyAllDead(){
        if(view.getNpcList().size() == 0){
            return true;
        }
        keySetIterator = view.getNpcList().keySet().iterator();
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            if(! view.getNpcList().get(cur).getCharacter().isDead() && !((NPC)view.getNpcList().get(cur)).isFriendly()){
                return false;
            }
        }
        return true;

    }
    public Vector2 tradeWithFriend() {
        keySetIterator = view.getNpcList().keySet().iterator();
        playerTradeRange.set(player.getBound().x - 20, player.getBound().y -20, player.getBound().width +50, player.getBound().height +50);
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            if(playerTradeRange.overlaps(view.getNpcList().get(cur).getBound()) && ((NPC)view.getNpcList().get(cur)).isFriendly() ){
                player.changeFacingDirection(cur);
                return cur;
            }
        }
        return null;
    }

    public void meleeAttackEnemy(){
        meleeAttackRangeX.set(player.getPosition().x - PublicParameter.MELEE_WEAPON_ATTACK_CELL * player.getBound().width, player.getPosition().y, player.getBound().width *(2 * PublicParameter.MELEE_WEAPON_ATTACK_CELL+1), player.getBound().height);
        meleeAttackRangeY.set(player.getPosition().x, PublicParameter.MELEE_WEAPON_ATTACK_CELL * player.getPosition().y - player.getBound().height, player.getBound().width, player.getBound().height * (2 * PublicParameter.MELEE_WEAPON_ATTACK_CELL+1));

        if(!((NPC)view.getNpcList().get(enemyPointer)).isFriendly()
                &&(meleeAttackRangeX.overlaps(view.getNpcList().get(enemyPointer).getBound())
                || meleeAttackRangeY.overlaps(view.getNpcList().get(enemyPointer).getBound()))
                && ! view.getNpcList().get(enemyPointer).getCharacter().isDead()){
            renderMeleeArea();
            if(Gdx.input.isTouched() && Gdx.input.isKeyPressed(Input.Keys.K )) {
                touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                view.getCam().unproject(touch);
                if(view.getNpcList().get(enemyPointer).getBound().contains(touch.x,touch.y)
                		&& (meleeAttackRangeX.contains(touch.x,touch.y) || meleeAttackRangeY.contains(touch.x,touch.y))){
                    MainMenuScreen.logArea.appendText(" You are attacking "+view.getNpcList().get(enemyPointer).getCharacter().getName()+"\n");
                    view.getNpcList().get(enemyPointer).getCharacter().underAttack(player.getCharacter());
                    view.startNextRound();
                }
            }
        } else{
            if(enemyIterator.hasNext()){
                enemyPointer = enemyIterator.next();
            } else{
                view.getDialogueController().setAnswerIndex(0);
                enemyIterator = view.getNpcList().keySet().iterator();
                enemyPointer = enemyIterator.next();
                view.getDialogueController().animateText("Cannot find enemy NPC to attack, change to move!");
            }
        }
    }

    public void rangeAttackEnemy(){
        rangeAttackrange.set(player.getPosition().x + player.getBound().width / 2 ,player.getPosition().y + player.getBound().height /2, PublicParameter.GAME_PIXEL_SIZE * PublicParameter.RANGE_WEAPON_ATTACK_CELL);

        if(!((NPC)view.getNpcList().get(enemyPointer)).isFriendly()
                && rangeAttackrange.contains(enemyPointer.x + view.getNpcList().get(enemyPointer).getBound().width /2, enemyPointer.y + view.getNpcList().get(enemyPointer).getBound().height /2)
                && ! view.getNpcList().get(enemyPointer).getCharacter().isDead()){
            renderRangeArea();
            if(Gdx.input.isTouched() && Gdx.input.isKeyPressed(Input.Keys.K )) {
                touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                view.getCam().unproject(touch);
                if(view.getNpcList().get(enemyPointer).getBound().contains(touch.x,touch.y)
                		&& rangeAttackrange.contains(touch.x,touch.y)){
                    MainMenuScreen.logArea.appendText(" You are attacking "+view.getNpcList().get(enemyPointer).getCharacter().getName()+"\n");
                    view.getNpcList().get(enemyPointer).getCharacter().underAttack(player.getCharacter());
                    view.startNextRound();
                }
            }
        } else{
            if(enemyIterator.hasNext()){
                enemyPointer = enemyIterator.next();
            } else{
                view.getDialogueController().setAnswerIndex(0);
                enemyIterator = view.getNpcList().keySet().iterator();
                enemyPointer = enemyIterator.next();
                view.getDialogueController().animateText("Cannot find enemy NPC to attack, change to move!");
            }
        }
    }

    public Vector2 findEnemyInAttackRange() {
        rangeAttackrange.set(player.getPosition().x + player.getBound().width / 2 ,player.getPosition().y + player.getBound().height /2, PublicParameter.GAME_PIXEL_SIZE * PublicParameter.RANGE_WEAPON_ATTACK_CELL);
        meleeAttackRangeX.set(player.getPosition().x - PublicParameter.MELEE_WEAPON_ATTACK_CELL * player.getBound().width, player.getPosition().y, player.getBound().width * (2 * PublicParameter.MELEE_WEAPON_ATTACK_CELL+1) , player.getBound().height);
        meleeAttackRangeY.set(player.getPosition().x, player.getPosition().y - PublicParameter.MELEE_WEAPON_ATTACK_CELL * player.getBound().height, player.getBound().width, player.getBound().height * (2 * PublicParameter.MELEE_WEAPON_ATTACK_CELL+1));

        keySetIterator = view.getNpcList().keySet().iterator();
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            if(player.getCharacter().getEquipment().get(Item.ItemType.WEAPON) !=null
                    &&player.getCharacter().getEquipment().get(Item.ItemType.WEAPON).getWeaponType()== WeaponModel.WeaponType.RANGE){
                if(rangeAttackrange.contains(cur.x + view.getNpcList().get(cur).getBound().width /2, cur.y + view.getNpcList().get(cur).getBound().height /2)
                        && !((NPC)view.getNpcList().get(cur)).isFriendly()
                        && ! view.getNpcList().get(cur).getCharacter().isDead()){
                    return cur;
                }
            } else {
                if ((meleeAttackRangeX.overlaps(view.getNpcList().get(cur).getBound()) || meleeAttackRangeY.overlaps(view.getNpcList().get(cur).getBound()))
                        && !((NPC) view.getNpcList().get(cur)).isFriendly()
                        && !view.getNpcList().get(cur).getCharacter().isDead()) {
                    return cur;
                }
            }
        }
        return null;
    }



    public Vector2 findEnemyToAttack() {
//        entrySetIterator = view.getNpcList().entrySet().iterator();
//        while(entrySetIterator.hasNext()){
//            entry = entrySetIterator.next();
//            if( !((NPC)entry.getValue()).isFriendly()
//                    && ! entry.getValue().getCharacter().isDead()){
//                return entry.getKey();
//            }
//        }
        keySetIterator = view.getNpcList().keySet().iterator();

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            if(!((NPC)view.getNpcList().get(cur)).isFriendly()
                    && ! view.getNpcList().get(cur).getCharacter().isDead()){
                return cur;
            }
        }
        return null;
    }

    /**
     * pick items by players themself
     * @param item different kinds of items
     */
    public void pickupItem(Item item){
        player.getCharacter().addToBackpack(item);

    }

    public void setStartToMove(boolean startToMove) {
        isStartToMove = startToMove;
    }

    public boolean isDecideToStop() {
        return decideToStop;
    }

    public void setDecideToStop(boolean decideToStop) {
        this.decideToStop = decideToStop;
    }

    public void renderMeleeArea() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(view.getCam().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(meleeAttackRangeX.x, meleeAttackRangeX.y, meleeAttackRangeX.width, meleeAttackRangeX.height);
        shapeRenderer.rect(meleeAttackRangeY.x, meleeAttackRangeY.y, meleeAttackRangeY.width, meleeAttackRangeY.height);
        shapeRenderer.setColor(new Color(0, 1, 0, 0.1f));
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


	public void renderMovementArea(Vector2 center) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(view.getCam().combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.circle(center.x + player.getBound().width / 2 , center.y + player.getBound().height  /2, PublicParameter.GAME_PIXEL_SIZE*3);
		shapeRenderer.setColor(new Color(0, 1, 0, 0.1f));
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

    public void renderRangeArea() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(view.getCam().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(rangeAttackrange.x, rangeAttackrange.y, rangeAttackrange.radius);
        shapeRenderer.setColor(new Color(0, 1, 0, 0.1f));
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public boolean isAbleToAttack() {
        return ableToAttack;
    }

    public void setAbleToAttack(boolean ableToAttack) {
        this.ableToAttack = ableToAttack;
    }
}
//    @Override
//    public boolean keyDown(int keycode) {
//        if(stateTime < 4 ){
//            stateTime += Gdx.graphics.getDeltaTime();
//        }else{
//            stateTime = 0;
//        }
//
//        if(keycode == Input.Keys.UP && player.getPosition().y < mapBound.getHeight()){
//            player.move(0,1);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(12 + stateTime));
//        }
//        if(keycode == Input.Keys.DOWN && player.getPosition().y > mapBound.getY() ){
//            player.move(0,-1);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(0 + stateTime));
//        }
//        if(keycode == Input.Keys.LEFT && player.getPosition().x > mapBound.getX()){
//            player.move(-1,0);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(4 + stateTime));
//        }
//        if(keycode == Input.Keys.RIGHT && player.getPosition().x < mapBound.getWidth()){
//            player.move(1,0);
//            player.setCurrentFrame((TextureRegion)player.getAnimation().getKeyFrame(8 + stateTime));
//        }
//        return false;
//    }
