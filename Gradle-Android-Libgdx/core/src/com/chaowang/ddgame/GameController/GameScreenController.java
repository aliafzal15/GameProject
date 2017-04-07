package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.MapModel.Map;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * controller for game screen
 * @author chao wang
 * @version 2.0
 */

public class GameScreenController {

    private GameScreen view;
    private Map mapModel;
    private Player player;
    private Vector3 touch;
    private Vector2 pointer, enemyPointer;
    private Iterator<Vector2> keySetIterator, enemyIterator ;
    private Rectangle playerTradeRange, meleeAttackRangeX, meleeAttackRangeY;
    private ShapeRenderer shapeRenderer;
    private Circle rangelAttackrange;
    private Vector2 cur;

    /**
     * construct
     * @param screen
     * @param map
     * @param role
     */
    public GameScreenController(GameScreen screen, Map map ,Player role){
        this.view = screen;
        this.mapModel = map;
        this.player = role;
        player.getCharacter().addObserver(view);
        Iterator<Vector2> keySetIterator = view.getNpcList().keySet().iterator();
        while(keySetIterator.hasNext()) {
            view.getNpcList().get(keySetIterator.next()).getCharacter().addObserver(view);
        }
        touch = new Vector3();
        playerTradeRange = new Rectangle();
        meleeAttackRangeX = new Rectangle();
        meleeAttackRangeY = new Rectangle();
        rangelAttackrange =new Circle();
        shapeRenderer = new ShapeRenderer();

        enemyIterator = view.getNpcList().keySet().iterator();
        if(enemyIterator.hasNext()){
            enemyPointer = enemyIterator.next();
        }
    }
    /**
     * listener to monitor any changes
     */
    public void onClickListen(){

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE )){
            view.getAbilityLabel().setVisible(false);
            for(Image image : view.getBackpackMatrix()){
                image.setVisible(false);
            }
            for(Image image : view.getEquipmentMatrix()){
                image.setVisible(false);
            }
            view.getItemInfoLabel().setVisible(false);
        }

        if(Gdx.input.isTouched() && Gdx.input.isKeyPressed(Input.Keys.I )){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            view.getCam().unproject(touch);
            if(player.getBound().contains(touch.x,touch.y)){
                player.getCharacter().previewLoadEquipment(-1);
                enableItemPreviewTouch();
            }

            Iterator<Vector2> keySetIterator = view.getNpcList().keySet().iterator();
            while(keySetIterator.hasNext()) {
                pointer = keySetIterator.next();
                if(view.getNpcList().get(pointer).getBound().contains(touch.x, touch.y)){
                    view.getNpcList().get(pointer).getCharacter().previewLoadEquipment(-1);
                    disableItemPreviewTouch();
                }
            }
        } else if(Gdx.input.isTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            view.getCam().unproject(touch);
            if(player.getBound().contains(touch.x,touch.y)){
                player.getCharacter().previewAllAttribute();
            }
            Iterator<Vector2> keySetIterator = view.getNpcList().keySet().iterator();
            while(keySetIterator.hasNext()) {
                pointer = keySetIterator.next();
                if(view.getNpcList().get(pointer).getBound().contains(touch.x, touch.y)){
                    view.getNpcList().get(pointer).getCharacter().previewAllAttribute();
                }
            }
        }
    }
    /**
     * add listener for preview item images
     */
	public void enableItemPreviewTouch() {
		for (int i = 0; i < view.getBackpackMatrix().length ; i++) {
			view.getBackpackMatrix()[i].setTouchable(Touchable.enabled);
		    view.getBackpackMatrix()[i].addListener(new ClickListener(i) {
		        @Override
		        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		            if ( getButton() < player.getCharacter().getBackpack().size()
		            		&& !player.getCharacter().getEquipment().containsKey(player.getCharacter().getBackpack().get(getButton()).getItemType())) {
		                player.getCharacter().previewLoadEquipment(getButton());
		            }
		            return true;
		        }
		        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		            if ( getButton() < player.getCharacter().getBackpack().size())  {
		                view.getItemInfoLabel().setText(player.getCharacter().getBackpack().get(getButton()).toString());
		            }
		        }

		        @Override
		        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		        	view.getItemInfoLabel().setText("");
		        }

		    });
		}

		for (int i = 0; i < view.getEquipmentMatrix().length ; i++){
			view.getEquipmentMatrix()[i].setTouchable(Touchable.enabled);
		    view.getEquipmentMatrix()[i].addListener(new ClickListener(i) {
		        @Override
		        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		            if(player.getCharacter().getEquipment().containsKey(Item.ItemType.getItemType(getButton()))){
		                player.getCharacter().previewUnloadEquipment(getButton());
		            }
		            return true;
		        }
		        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		            if(player.getCharacter().getEquipment().containsKey(Item.ItemType.getItemType(getButton()))){
		                view.getItemInfoLabel().setText(player.getCharacter().getEquipment().get(Item.ItemType.getItemType(getButton())).toString());
		            }
		        }

		        @Override
		        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		            view.getItemInfoLabel().setText("");
		        }
		    });
		}
	}
	/**
	 * stop implementing items
	 */
	public void disableItemPreviewTouch() {
		for(Image image : view.getBackpackMatrix()){
		    image.setTouchable(Touchable.disabled);
		}
		for(Image image : view.getEquipmentMatrix()){
		    image.setTouchable(Touchable.disabled);
		}
		view.getItemInfoLabel().setVisible(false);
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

    public void attackEnemy(){
        meleeAttackRangeX.set(player.getBound().x - player.getBound().width, player.getBound().y, player.getBound().width *3 , player.getBound().height);
        meleeAttackRangeY.set(player.getBound().x, player.getBound().y - player.getBound().height, player.getBound().width, player.getBound().height * 3);

        if(!((NPC)view.getNpcList().get(enemyPointer)).isFriendly()
                &&(meleeAttackRangeX.overlaps(view.getNpcList().get(enemyPointer).getBound())
                    || meleeAttackRangeY.overlaps(view.getNpcList().get(enemyPointer).getBound()))
        		&& ! view.getNpcList().get(enemyPointer).getCharacter().isDead()){
            renderMeleeArea();
            if(Gdx.input.isTouched() && Gdx.input.isKeyPressed(Input.Keys.K )) {
                touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                view.getCam().unproject(touch);
                if(view.getNpcList().get(enemyPointer).getBound().contains(touch.x,touch.y)){
                    MainMenuScreen.logArea.appendText(view.getNpcList().get(enemyPointer).getCharacter().getName() + " is under attack");
                    view.getNpcList().get(enemyPointer).getCharacter().underAttack();
                    view.getDialogueController().startDialogue(view.getDialogue());
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

}
