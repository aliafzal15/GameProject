package com.chaowang.ddgame.GameModel.StrategyPattern;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.chaowang.ddgame.GameView.GameItemExchangeScreen;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuModel.MapModel.Wall;
import com.chaowang.ddgame.MenuView.MainMenuScreen;

public class ComputerPlayerStrategy implements Strategy{

	private GameScreen screen;
    private Iterator<Vector2> keySetIterator, enemyIterator ;
    private Vector2 enemyPointer;
    
	public ComputerPlayerStrategy(GameScreen gameScreen){
		this.screen = gameScreen;
        enemyIterator = screen.getMapModel().getEnemyLocationList().keySet().iterator();
        enemyPointer = enemyIterator.next();
	}
	
	@Override
	public void setupCamera() {
		screen.getCam().position.set(screen.getPlayer().getPosition().x + (screen.getPlayer().getCurrentFrame().getRegionWidth() / 2), 
				screen.getPlayer().getPosition().y + screen.getPlayer().getCurrentFrame().getRegionHeight() / 2, 0);
		screen.getBatch().setProjectionMatrix(screen.getCam().combined);
		screen.getCam().update();
	}

	@Override
	public void renderInteraction() {

        screen.getBatch().draw(screen.getPlayer().getCurrentFrame(), screen.getPlayer().getPosition().x, screen.getPlayer().getPosition().y );
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
        if(screen.getMessageDialog().isFinished()){
        	screen.getMessageDialog().setVisible(false);
        }
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            screen.getMapModel().getItemLocationList().get(cur).draw(screen.getBatch(), cur);
            if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getItemLocationList().get(cur)) ){
                screen.getPlayerController().pickupItem(screen.getMapModel().getItemLocationList().get(cur));
                screen.getMessageDialog().setVisible(true);
                screen.getDialogueController().animateText(screen.getMapModel().getItemLocationList().get(cur).toString()+"  found!");
                keySetIterator.remove();
            }
        }

        // draw enemy on screen
        keySetIterator = screen.getMapModel().getEnemyLocationList().keySet().iterator();

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            screen.getMapModel().getEnemyLocationList().get(cur).draw(screen.getBatch(), cur, false);
            if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getEnemyLocationList().get(cur).getBound()) ){
            	screen.getPlayerController().reAdjust(5);
                screen.setHitObject(true);
                if(! screen.getMapModel().getEnemyLocationList().get(cur).isDead()){
                	screen.getMapModel().getEnemyLocationList().get(cur).underAttack();
                } else{
                	screen.getPlayerController().teleport(0, -8);
                }
            }
        }


        // draw NPC on screen
        keySetIterator = screen.getMapModel().getFriendLocationList().keySet().iterator();

        while(keySetIterator.hasNext()){
             cur = keySetIterator.next();
             screen.getMapModel().getFriendLocationList().get(cur).draw(screen.getBatch(), cur, true);
            if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getFriendLocationList().get(cur).getBound()) ){
            	screen.getPlayerController().reAdjust(5);
                screen.setHitObject(true);
            }
        }

        //draw exit door on screen, exit mechanism
        if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getExitDoor()) ) {
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
        }


        if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getEntryDoor()) ){
        	screen.getPlayerController().teleport(0, -5);
        	screen.getPlayerController().reAdjust(0);
            screen.setHitObject(true);
        }

        if(enemyPointer != null && ! screen.isHitObject() && ! screen.getMapModel().getEnemyLocationList().get(enemyPointer).isDead() ){
        	screen.getPlayerController().walkTo(screen.getMapModel().getEnemyLocationList().get(enemyPointer).getBound().x, screen.getMapModel().getEnemyLocationList().get(enemyPointer).getBound().y);
        } else{
            if(enemyIterator.hasNext() && screen.getMapModel().getEnemyLocationList().get(enemyPointer).isDead()){
                enemyPointer = enemyIterator.next();
            }
            if(!enemyIterator.hasNext()){
            	screen.getPlayerController().walkTo(screen.getMapModel().getExitDoor().x + screen.getPlayer().getBound().x, screen.getMapModel().getExitDoor().y - (screen.getPlayer().getBound().y * 3 /4));
            }
        }

		
	}

	@Override
	public void updateDialogueStage(float delta) {

        screen.getUiStage().act(delta);
        screen.getUiStage().draw();
	}

}
