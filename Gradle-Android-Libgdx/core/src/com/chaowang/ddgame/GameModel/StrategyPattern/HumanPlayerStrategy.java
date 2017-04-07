package com.chaowang.ddgame.GameModel.StrategyPattern;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameView.GameItemExchangeScreen;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuModel.MapModel.Wall;
import com.chaowang.ddgame.MenuView.MainMenuScreen;

public class HumanPlayerStrategy implements Strategy{
	
	private GameScreen screen;
    private Iterator<Vector2> keySetIterator ;
    
	public HumanPlayerStrategy(GameScreen gameScreen){
		this.screen = gameScreen;
		screen.getDialogueController().startDialogue(screen.getDialogue());
	}

	@Override
	public void setupCamera() {
		screen.getCam().position.set(screen.getPlayer().getPosition().x + (screen.getPlayer().getCurrentFrame().getRegionWidth() / 2), 
				screen.getPlayer().getPosition().y + screen.getPlayer().getCurrentFrame().getRegionHeight() / 2, 0);
		screen.getBatch().setProjectionMatrix(screen.getCam().combined);
		screen.getCam().update();
	}
	
	public void renderInteraction(){
//        if(!screen.getDialogBox().isVisible()){
//        }

        //draw walls on screen
        for(Wall cur : screen.getMapModel().getWallLocationList() ){
            cur.draw(screen.getBatch());
            if(screen.getPlayer().getBound().overlaps(cur)){
                screen.getPlayerController().reAdjust(0);
                screen.setHitObject(true);
            }
        }

        // draw items on screen
        keySetIterator = screen.getMapModel().getItemLocationList().keySet().iterator();
        Vector2 cur;

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            screen.getMapModel().getItemLocationList().get(cur).draw(screen.getBatch(), cur);
            if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getItemLocationList().get(cur)) ){
                screen.getPlayerController().pickupItem(screen.getMapModel().getItemLocationList().get(cur));
                screen.getDialogueController().animateText(screen.getMapModel().getItemLocationList().get(cur).toString()+"  found!");
                keySetIterator.remove();
            }
        }

        // draw enemy on screen
        keySetIterator = screen.getNpcList().keySet().iterator();

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            screen.getNpcList().get(cur).getCharacter().draw(screen.getBatch(), cur, ((NPC)screen.getNpcList().get(cur)).isFriendly());
            if(screen.getPlayer().getBound().overlaps(screen.getNpcList().get(cur).getBound()) ){
            	screen.getPlayerController().reAdjust(5);
                screen.setHitObject(true);
                if(!((NPC)screen.getNpcList().get(cur)).isFriendly()){
                    if(screen.getNpcList().get(cur).getCharacter().isDead()){
                        screen.getGame().setScreen(new GameItemExchangeScreen(screen.getGame(),screen.getPlayer(),screen.getMapModel(),screen.getCampaign(), cur, screen.getNpcList(), screen.isUserPlay()));
                    }
                }


            }
        }

        //draw exit door on screen, exit mechanism
        if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getExitDoor()) ) {
            if (screen.getPlayer().getPosition().y + screen.getPlayer().getBound().getHeight() <= screen.getMapModel().getExitDoor().y + 1f) {
            	if(screen.getScreenController().isEnemyAllDead()){
                    if (screen.getCampaign().getMapPack().size == GameScreen.getCount() + 1) {
                    	screen.getPlayer().setPosition(new Vector2(-1000,-1000));
                    	screen.getUiStage().addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                MainMenuScreen.logArea.clear();
                                screen.getGame().setScreen(new com.chaowang.ddgame.MenuView.MainMenuScreen(screen.getGame()));
                            }
                        })));
                    } else {
                    	screen.getPlayer().getCharacter().promoteUp();
                        GameScreen.setCount(GameScreen.getCount()+1);
                        screen.getPlayer().setPosition(new Vector2(-1000,-1000));
                        screen.getUiStage().addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                //campaign.getMapPack().removeIndex(0);
                                System.out.println("loading map number "+GameScreen.getCount());
                                MainMenuScreen.logArea.clear();
                                screen.getGame().setScreen(new GameScreen(screen.getGame(), screen.getPlayer().getCharacter(), screen.getCampaign().getMapPack().get(GameScreen.getCount()), screen.getCampaign(), screen.isUserPlay()));
                            }
                        })));
                    }
            	}else{
                    screen.getDialogueController().animateText("There are still hostile monsters survive on the map");
                    screen.getPlayerController().reAdjust(5);
                    screen.setHitObject(true);
            	}
            } else {
            	screen.getPlayerController().reAdjust(0);
                screen.setHitObject(true);
            }
        }


        if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getEntryDoor()) ){
        	screen.getPlayerController().reAdjust(0);
            screen.setHitObject(true);
        }

        // if index flag is false, which means the dialog is displaying, don't allow player do any action untill dialog is finished
        if(!screen.getDialogueController().isDialogStopRolling()) {
            //System.out.println("dialog is still displaying");
        } else {
            if (screen.getDialogueController().getAnswerIndex() == 0) {
                //playerController.setStartToMove(false);   //not move yet
            	screen.getPlayerController().movePlayer();
            }
            else if (screen.getDialogueController().getAnswerIndex() == 1) {
            	screen.getScreenController().attackEnemy() ; // if cannot find enemy in range to attack
            }
            else if (screen.getDialogueController().getAnswerIndex() == 2) {
                Vector2 friendLocation = screen.getScreenController().tradeWithFriend();
                if(friendLocation !=null){
                	screen.getGame().setScreen(new GameItemExchangeScreen(screen.getGame(),screen.getPlayer(),screen.getMapModel(),screen.getCampaign(), friendLocation, screen.getNpcList(), screen.isUserPlay()));
                } else{
                	screen.getDialogueController().setAnswerIndex(0);
                	screen.getDialogueController().animateText("Cannot find friendly NPC to trade, change to move!");
                }
                //game.setScreen(new GameItemExchangeScreen(game,player,mapModel,campaign, cur, true));
            }
        }

	}
	
	public void updateDialogueStage(float delta){
		screen.getDialogueController().update(delta);
		screen.getDialogueController().keyUp();
        screen.getUiStage().act(delta);
        screen.getUiStage().draw();
        screen.getScreenController().onClickListen();
	}



}
