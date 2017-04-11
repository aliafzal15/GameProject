package com.chaowang.ddgame.GameModel.StrategyPattern;

import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuModel.MapModel.Wall;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
/**
 * strategy pattern for AggressiveNPC
 * @author chao wang
 * @version 3.0
 */
public class AggressiveNPCStrategy implements Strategy {

	private GameScreen screen;
    private Iterator<Vector2> keySetIterator ;
    private Vector2 npcPointer;
    private Iterator<Map.Entry<Vector2,GameActor>> entrySetIterator;
    private Map.Entry<Vector2,GameActor> entry;

    /**
     * construct
     * @param gameScreen
     */
    public AggressiveNPCStrategy(GameScreen gameScreen){
		this.screen = gameScreen;
	}
	/**
	 * setup Camera
	 */
	@Override
	public void setupCamera() {
		screen.getCam().position.set(screen.getNpc().getPosition().x + (screen.getNpc().getBound().width / 2), screen.getNpc().getPosition().y + screen.getNpc().getBound().height / 2, 0);
		screen.getBatch().setProjectionMatrix(screen.getCam().combined);
		screen.getCam().update();
        screen.getPlayerEditorBtn().setVisible(false);
	}
	/**
	 * render Interaction
	 */
	@Override
	public void renderInteraction() {

        screen.getBatch().draw(screen.getNpc().getCharacter().getTexture(), screen.getNpc().getPosition().x, screen.getNpc().getPosition().y, screen.getNpc().getBound().width, screen.getNpc().getBound().height);
        //draw walls on screen
        for(Wall wallCur : screen.getMapModel().getWallLocationList() ){
        	wallCur.draw(screen.getBatch());
            if(screen.getNpc().getBound().overlaps(wallCur)){
            	screen.getNpcController().walkReAdjust(0);
                screen.setHitObject(true);            }
        }

        // draw items on screen
        keySetIterator = screen.getMapModel().getItemLocationList().keySet().iterator();
        if(screen.getMessageDialog().isFinished()){
            screen.getMessageDialog().setVisible(false);
        }
        while(keySetIterator.hasNext()){
            npcPointer = keySetIterator.next();
            screen.getMapModel().getItemLocationList().get(npcPointer).draw(screen.getBatch(), npcPointer);
            if(screen.getNpc().getBound().overlaps(screen.getMapModel().getItemLocationList().get(npcPointer)) ){
            	screen.getNpcController().pickupItem(screen.getMapModel().getItemLocationList().get(npcPointer));
                screen.getMessageDialog().setVisible(true);
                screen.getDialogueController().animateText(screen.getMapModel().getItemLocationList().get(npcPointer).toString()+"  found!");
                keySetIterator.remove();
            }
        }

        // draw npc on screen
//        entrySetIterator = screen.getNpcList().entrySet().iterator();
//        while(entrySetIterator.hasNext()){
//            entry = entrySetIterator.next();
//            entry.getValue().getCharacter().draw(screen.getBatch(), entry.getKey(), ((NPC)entry.getValue()).isFriendly());
//            if(screen.getPlayer().getBound().overlaps(entry.getValue().getBound()) ){
//                screen.getPlayerController().reAdjust(5);
//                screen.setHitObject(true);
//            }
//        }

        keySetIterator = screen.getNpcList().keySet().iterator();

        while(keySetIterator.hasNext()){
        	npcPointer = keySetIterator.next();
        	screen.getNpcList().get(npcPointer).getCharacter().draw(screen.getBatch(), npcPointer, ((NPC)screen.getNpcList().get(npcPointer)).isFriendly());
            if(screen.getNpc().getBound().overlaps(screen.getNpcList().get(npcPointer).getBound()) ){
            	screen.getNpcController().walkReAdjust(5);
                screen.setHitObject(true);
            }
        }


        if(screen.getNpc().getBound().overlaps(screen.getMapModel().getEntryDoor())
                || screen.getNpc().getBound().overlaps(screen.getMapModel().getExitDoor())
                || screen.getNpc().getBound().overlaps(screen.getPlayer().getBound())){
        	screen.getNpcController().walkReAdjust(5);
            screen.setHitObject(true);  
        }

        if(!screen.getNpc().getCharacter().isDead()){
            if(screen.getNpcController().findPlayerToAttack() && !screen.getPlayer().getCharacter().isDead() && screen.getNpcController().isAbleToAttack()){
                MainMenuScreen.logArea.appendText(screen.getNpc().getCharacter().getName()+ " is attacking you\n");
                screen.getPlayer().getCharacter().underAttack(screen.getNpcController().getNpc().getCharacter());
                screen.startNextRound();
            } else if(screen.getNpcController().findNPCtoAttack() != null && screen.getNpcController().isAbleToAttack() ){
                npcPointer = screen.getNpcController().findNPCtoAttack();
                GameActor tmp= screen.getNpcList().remove(npcPointer);
                tmp.getCharacter().underAttack(screen.getNpcController().getNpc().getCharacter());
                ((NPC)tmp).setFriendly(false);
                screen.getNpcList().put(tmp.getPosition(), tmp);
                screen.startNextRound();
            } else{
                screen.getNpcController().aggressivelyWalkTo(screen.getPlayer().getPosition().x, screen.getPlayer().getPosition().y);
            }
        }
	}
	/**
	 * update dialogue
	 */
	@Override
	public void updateDialogueStage(float delta) {
        screen.getUiStage().act(delta);
        screen.getUiStage().draw();
	}


}
