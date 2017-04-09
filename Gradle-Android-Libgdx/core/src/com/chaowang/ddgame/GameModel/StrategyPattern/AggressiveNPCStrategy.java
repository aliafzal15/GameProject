package com.chaowang.ddgame.GameModel.StrategyPattern;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuModel.MapModel.Wall;

public class AggressiveNPCStrategy implements Strategy {

	private GameScreen screen;
    private Iterator<Vector2> keySetIterator ;
    private Vector2 npcPointer;
    
	public AggressiveNPCStrategy(GameScreen gameScreen){
		this.screen = gameScreen;
	}
	
	@Override
	public void setupCamera() {
		screen.getCam().position.set(screen.getNpc().getPosition().x + (screen.getNpc().getBound().width / 2), screen.getNpc().getPosition().y + screen.getNpc().getBound().height / 2, 0);
		screen.getBatch().setProjectionMatrix(screen.getCam().combined);
		screen.getCam().update();
	}

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
        keySetIterator = screen.getNpcList().keySet().iterator();

        while(keySetIterator.hasNext()){
        	npcPointer = keySetIterator.next();
        	screen.getNpcList().get(npcPointer).getCharacter().draw(screen.getBatch(), npcPointer, ((NPC)screen.getNpcList().get(npcPointer)).isFriendly());
            if(screen.getNpc().getBound().overlaps(screen.getNpcList().get(npcPointer).getBound()) ){
            	screen.getNpcController().walkReAdjust(5);
                screen.setHitObject(true);            
            }
        }


        if(screen.getNpc().getBound().overlaps(screen.getMapModel().getEntryDoor())|| screen.getNpc().getBound().overlaps(screen.getMapModel().getExitDoor()) ){
        	screen.getNpcController().walkReAdjust(5);
            screen.setHitObject(true);  
        }

        
        if(screen.getNpcController().findPlayerToAttack() && !screen.getPlayer().getCharacter().isDead()){
        	screen.getPlayer().getCharacter().underAttack();
        } else if(screen.getNpcController().findNPCtoAttack() != null){
        	npcPointer = screen.getNpcController().findNPCtoAttack();
        	GameActor tmp= screen.getNpcList().remove(npcPointer);
        	tmp.getCharacter().underAttack();
        	((NPC)tmp).setFriendly(false);
        	screen.getNpcList().put(tmp.getPosition(), tmp);
        } else{
        	screen.getNpcController().agressivelyWalkTo(screen.getPlayer().getPosition().x, screen.getPlayer().getPosition().y);
        }
	}

	@Override
	public void updateDialogueStage(float delta) {
        screen.getUiStage().act(delta);
        screen.getUiStage().draw();
	}


}