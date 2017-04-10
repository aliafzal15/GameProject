package com.chaowang.ddgame.GameController;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponModel;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
/**
 * NPC controller
 * @author chao wang
 * @version 3.0
 */
public class NPCcontroller {
    private NPC npc;
    private GameScreen gameScreen;
    private Rectangle mapBound;
    private String movement, prevMovement;
    private boolean isStartToMove;  // flag to determine status of start moving
    private boolean ableToAttack;  // flag to reject attack, while moving
    private float walkingDistance;   // measure the distance for each move
    private Vector2 positionBeforeMove;
    private Circle rangeAttackrange;
    private Rectangle meleeAttackRangeX, meleeAttackRangeY;
    private Iterator<Vector2> npcIterator ;
    private Vector2 npcVectorPointer;
    private ShapeRenderer shapeRenderer;


    /**
     * constructor
     * @param p
     * @param screen
     */
    public NPCcontroller(NPC p, GameScreen screen){
        this.npc = p;
        this.gameScreen = screen;
        MapProperties prop = gameScreen.getMap().getProperties();
        mapBound = new Rectangle(0, 0, prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class), prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));
        walkingDistance =0f;
        isStartToMove =false;
        ableToAttack = true;
        prevMovement = "up";
        if(this.npc != null){
            positionBeforeMove = new Vector2(this.npc.getPosition());
        }
        meleeAttackRangeX = new Rectangle();
        meleeAttackRangeY = new Rectangle();
        rangeAttackrange =new Circle();
        shapeRenderer = new ShapeRenderer();

        npcIterator = gameScreen.getNpcList().keySet().iterator();
        if(npcIterator.hasNext()){
        	npcVectorPointer = npcIterator.next();
        }
    }
   
    /**
     * Pattern for walking in square
     */
    public void walkInSquarePattern(){

        if(prevMovement.equals("up")){  //walk to left
    		friendlyWalkTo(this.npc.getPosition().x - PublicParameter.GAME_PIXEL_SIZE* 3f, this.npc.getPosition().y);
    	} else if(prevMovement.equals("left")){ //walk down
    		friendlyWalkTo(this.npc.getPosition().x , this.npc.getPosition().y - PublicParameter.GAME_PIXEL_SIZE* 3f);
    	} else if(prevMovement.equals("down")){  // walk to right
    		friendlyWalkTo(this.npc.getPosition().x + PublicParameter.GAME_PIXEL_SIZE* 3f, this.npc.getPosition().y);
    	} else if(prevMovement.equals("right")){  //walk up
    		friendlyWalkTo(this.npc.getPosition().x , this.npc.getPosition().y + PublicParameter.GAME_PIXEL_SIZE* 3f);
    	}
    }
    /**
     * walk movement operation(friendly)
     * @param x
     * @param y
     */
    private void friendlyWalkTo(float x, float y){

//		if(Gdx.input.isKeyPressed(Input.Keys.F )){
//			isStartToMove=true;
//        }
    	if(isStartToMove){     	// moving and terminate move
    		if(walkingDistance > PublicParameter.GAME_PIXEL_SIZE*3 ){
                if(x < npc.getPosition().x){
                	prevMovement = "left";
                } else if(y < npc.getPosition().y){
                	prevMovement = "down";
                } else if (x > npc.getPosition().x){
                	prevMovement = "right";
                } else{
                	prevMovement = "up";
                }
                isStartToMove=false;
                positionBeforeMove.set(npc.getPosition());
                walkingDistance =0f;
                // flag isActorPlaying to be true for next player, put npc in queue
                gameScreen.startNextRound();
            } else{
        		keyDown( x,  y);
    		}
    	}
    }
    /**
     * walk movement operation(aggressively)
     * @param x
     * @param y
     */
    public void aggressivelyWalkTo(float x, float y){

    	if(isStartToMove){
            // moving and terminate move
            if(walkingDistance > PublicParameter.GAME_PIXEL_SIZE*3 ){
                isStartToMove=false;
                ableToAttack = true;
                positionBeforeMove.set(npc.getPosition());
                walkingDistance =0f;
                gameScreen.startNextRound();
            } else{
        		keyDown( x,  y);
                ableToAttack = false;
    		}
    	}
    }
    
    /**
     * auto control the npc movement to destination (x,y)
     */
    public void keyDown(float x, float y){

        if(npc.getPosition().y < y  ){
            movement = "up";
            walkingDistance += 1*2f;
            if(npc.getPosition().y + npc.getBound().height< mapBound.getHeight() ){
                npc.move(0,1);
            }
        }
        if(npc.getPosition().y > y ){
            movement = "down";
            walkingDistance += 1*2f;
            if(npc.getPosition().y > mapBound.getY() ){
                npc.move(0,-1);
            }
        }
        if(npc.getPosition().x > x ){
            movement = "left";
            walkingDistance += 1*2f;
            if(npc.getPosition().x > mapBound.getX()){
                npc.move(-1,0);
            }
        }
        if(npc.getPosition().x < x ) {
            movement = "right";
            walkingDistance += 1*2f;
            if(npc.getPosition().x + npc.getBound().getWidth() < mapBound.getWidth()){
                npc.move(1, 0);
            }
        }
    }
    
    /**
     * pick items by players themself
     * @param item different kinds of items
     */
    public void pickupItem(Item item){
        npc.getCharacter().addToBackpack(item);

    }
    
    /**
     * adjust the location of player when moving
     */
    public void walkReAdjust(float span){
        if(span <= 0 ){
            span = 0.5f;
        }
        if(movement == "up"){
            npc.move(0,-1 * span);
        }
        if(movement == "down"){
        	npc.move(0,1 * span);
        }
        if(movement == "left"){
        	npc.move(1 * span,0);
        }
        if(movement == "right"){
        	npc.move(-1 * span,0);
        }
    }
    /**
     * NPC get function
     * @return
     */
	public NPC getNpc() {
		return npc;
	}

	/**
	 * NPC set function
	 * @param npc
	 */
	public void setNpc(NPC npc) {
		this.npc = npc;
        positionBeforeMove = new Vector2(this.npc.getPosition());
	}

	/**
	 * attack player
	 * @return
	 */
	public boolean findPlayerToAttack() {
		rangeAttackrange.set(npc.getPosition().x + npc.getBound().width /2, npc.getPosition().y + npc.getBound().height / 2, PublicParameter.GAME_PIXEL_SIZE * PublicParameter.RANGE_WEAPON_ATTACK_CELL);
        meleeAttackRangeX.set(npc.getPosition().x - npc.getBound().width, npc.getPosition().y, npc.getBound().width *3 , npc.getBound().height);
        meleeAttackRangeY.set(npc.getPosition().x, npc.getPosition().y - npc.getBound().height, npc.getBound().width, npc.getBound().height * 3);

        if(npc.getCharacter().getEquipment().get(Item.ItemType.WEAPON) !=null
            && npc.getCharacter().getEquipment().get(Item.ItemType.WEAPON).getWeaponType()== WeaponModel.WeaponType.RANGE){
            if(rangeAttackrange.contains(gameScreen.getPlayer().getPosition().x+gameScreen.getPlayer().getBound().width / 2,
                    gameScreen.getPlayer().getPosition().y+gameScreen.getPlayer().getBound().height / 2)){
                return true;
            }
        }else{
            if((meleeAttackRangeX.overlaps(gameScreen.getPlayer().getBound())
                    || meleeAttackRangeY.overlaps(gameScreen.getPlayer().getBound()))){
                return true;
            }
        }

		return false;
	}

	/**
	 * attack NPC
	 * @return
	 */
	public Vector2 findNPCtoAttack() {
        rangeAttackrange.set(npc.getPosition().x + npc.getBound().width /2, npc.getPosition().y + npc.getBound().height / 2, PublicParameter.GAME_PIXEL_SIZE*2);
        meleeAttackRangeX.set(npc.getPosition().x - npc.getBound().width, npc.getPosition().y, npc.getBound().width *3 , npc.getBound().height);
        meleeAttackRangeY.set(npc.getPosition().x, npc.getPosition().y - npc.getBound().height, npc.getBound().width, npc.getBound().height * 3);

        npcIterator = gameScreen.getNpcList().keySet().iterator();
        while(npcIterator.hasNext()){
        	npcVectorPointer = npcIterator.next();
            if(npc.getCharacter().getEquipment().get(Item.ItemType.WEAPON) !=null
                    && npc.getCharacter().getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE) {
                if(rangeAttackrange.contains(npcVectorPointer.x + gameScreen.getNpcList().get(npcVectorPointer).getBound().width /2
                        , npcVectorPointer.y + gameScreen.getNpcList().get(npcVectorPointer).getBound().height /2)
                        && ((NPC)gameScreen.getNpcList().get(npcVectorPointer)).isFriendly()){
                    return npcVectorPointer;
                }
            } else{
                if((meleeAttackRangeX.overlaps(gameScreen.getNpcList().get(npcVectorPointer).getBound()) || meleeAttackRangeY.overlaps(gameScreen.getNpcList().get(npcVectorPointer).getBound()))
                        && ((NPC)gameScreen.getNpcList().get(npcVectorPointer)).isFriendly()){
                    return npcVectorPointer;
                }
            }


        }
		return null;
	}
	/**
	 * render range
	 */
    public void renderRangeArea() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(gameScreen.getCam().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(rangeAttackrange.x, rangeAttackrange.y, rangeAttackrange.radius);
        shapeRenderer.setColor(new Color(0, 1, 0, 0.1f));
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    /**
     * decide move status
     * @return
     */
    public boolean isStartToMove() {
        return isStartToMove;
    }
    /**
     * set startToMove value
     * @param startToMove
     */
    public void setStartToMove(boolean startToMove) {
        isStartToMove = startToMove;
    }
    /**
     * decide attack status
     * @return
     */
    public boolean isAbleToAttack() {
        return ableToAttack;
    }
    /**
     * set attack status value
     * @param ableToAttack
     */
    public void setAbleToAttack(boolean ableToAttack) {
        this.ableToAttack = ableToAttack;
    }
}
