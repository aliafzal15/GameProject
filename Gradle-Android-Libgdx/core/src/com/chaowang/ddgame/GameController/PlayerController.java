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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.GameView.GameScreen;

/**
 * controller for player
 * @author chao wang
 * @version 2.0
 */
public class PlayerController extends InputAdapter{

    private Player player;
    private GameScreen gameScreen;
    private Rectangle mapBound;
    private float stateTime;
    private String movement;
    private boolean isStartToMove, decideToStop;  // flag to determine status of start moving, and status of stop if press "F"
    private float walkingDistance;   // measure the distance for each move
    private Vector2 positionBeforeMove;
    private ShapeRenderer shapeRenderer;

    /**
     * constructor
     * @param p
     * @param screen
     */
    public PlayerController(Player p, GameScreen screen){
        this.player = p;
        this.gameScreen = screen;
        MapProperties prop = gameScreen.getMap().getProperties();
        mapBound = new Rectangle(0, 0, prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class), prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));
        stateTime = 0f;
        isStartToMove =false;
        decideToStop =false;
        walkingDistance =0f;
        positionBeforeMove = new Vector2(player.getPosition());
        shapeRenderer = new ShapeRenderer();
    }
    public PlayerController(Player p){
        this.player = p;
    }

    public void movePlayer(){
    	// pin the position before move
    	if(isStartToMove==false){
    		positionBeforeMove.set(player.getPosition());
    		isStartToMove=true;
    		decideToStop = false;
            walkingDistance =0f;
    	// moving and terminate move
    	}else{
    		if(walkingDistance > PublicParameter.GAME_PIXEL_SIZE*3 ||  //player.getPosition().dst(positionBeforeMove)
    				decideToStop){
    			keyDown(false);  // player does not move if out of area, or hit F
    		} else{
        		renderMovementArea(positionBeforeMove);    //draw movement area
    			if(!gameScreen.isHitObject()){				//passed from GameScreen, if iteration says player hit someone,
        			keyDown(true);
    			}
    			if(Gdx.input.isKeyPressed(Input.Keys.F )){
    				isStartToMove=false;
    				decideToStop = true;
    			}
    		}
    	}
    }
    
	public void renderMovementArea(Vector2 center) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(gameScreen.getCam().combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.circle(center.x, center.y, PublicParameter.GAME_PIXEL_SIZE*3);
		shapeRenderer.setColor(new Color(0, 1, 0, 0.1f));
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
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
    /**
     * pick items by players themself
     * @param item different kinds of items
     */
    public void pickupItem(Item item){
        player.getCharacter().addToBackpack(item);

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
