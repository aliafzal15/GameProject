package com.chaowang.ddgame.GameModel.StrategyPattern;

import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.chaowang.ddgame.GameModel.GameActor;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameView.GameItemExchangeScreen;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuModel.MapModel.Wall;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
/**
 * strategy pattern for Computer Player
 * @author chao wang
 * @version 3.0
 */
public class ComputerPlayerStrategy implements Strategy{

	private GameScreen screen;
    private Iterator<Vector2> keySetIterator, enemyIterator ;
    private Iterator<Map.Entry<Vector2,GameActor>> entrySetIterator;
    private Vector2 enemyPointer, cur;
    private Map.Entry<Vector2,GameActor> entry;
    /**
     * construct
     * @param gameScreen
     */
	public ComputerPlayerStrategy(GameScreen gameScreen){
		this.screen = gameScreen;
        enemyIterator = screen.getNpcList().keySet().iterator();
        enemyPointer = enemyIterator.next();
        screen.getPlayerEditorBtn().setVisible(false);
    }
	/**
	 * setup Camera
	 */
	@Override
	public void setupCamera() {
		screen.getCam().position.set(screen.getPlayer().getPosition().x + (screen.getPlayer().getCurrentFrame().getRegionWidth() / 2), 
				screen.getPlayer().getPosition().y + screen.getPlayer().getCurrentFrame().getRegionHeight() / 2, 0);
		screen.getBatch().setProjectionMatrix(screen.getCam().combined);
		screen.getCam().update();
	}
	/**
	 * render Interaction
	 */
	@Override
	public void renderInteraction() {

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

        // draw npc on screen
//        entrySetIterator = screen.getNpcList().entrySet().iterator();
//        while(entrySetIterator.hasNext()){
//            entry = entrySetIterator.next();
//            entry.getValue().getCharacter().draw(screen.getBatch(), entry.getKey(), ((NPC)entry.getValue()).isFriendly());
//            if(screen.getPlayer().getBound().overlaps(entry.getValue().getBound()) ){
//                screen.getPlayerController().reAdjust(5);
//                screen.setHitObject(true);
//                if(!((NPC)entry.getValue()).isFriendly() && entry.getValue().getCharacter().isDead()){
//                    screen.getPlayerController().teleport(0, -8);
//                }
//            }
//        }

        keySetIterator = screen.getNpcList().keySet().iterator();
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            screen.getNpcList().get(cur).getCharacter().draw(screen.getBatch(), cur, ((NPC)screen.getNpcList().get(cur)).isFriendly());
            if(screen.getPlayer().getBound().overlaps(screen.getNpcList().get(cur).getBound()) ){
            	screen.getPlayerController().reAdjust(5);
                screen.setHitObject(true);
                if(!((NPC)screen.getNpcList().get(cur)).isFriendly() && screen.getNpcList().get(cur).getCharacter().isDead()){
                        screen.getPlayerController().teleport(0, -8);
                }
            }
        }

        //draw exit door on screen, exit mechanism
        if(screen.getPlayer().getBound().overlaps(screen.getMapModel().getExitDoor()) ) {
        	if(screen.getPlayerController().isEnemyAllDead()){
                if (screen.getCampaign().getMapPack().size == GameScreen.getCount() + 1) {
                    screen.getPlayerController().setStartToMove(false);  // does not allow next npc play, in fade out 3 seconds
                    screen.getPlayer().setPosition(new Vector2(-1000,-1000));
                	screen.getUiStage().addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            MainMenuScreen.logArea.clear();
                            screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));
                        }
                    })));
                } else {
                	screen.getPlayer().getCharacter().promoteUp();
                    GameScreen.setCount(GameScreen.getCount()+1);
                    screen.getPlayerController().setStartToMove(false);  // does not allow next npc play, in fade out 3 seconds
                    screen.getPlayer().setPosition(new Vector2(-1000,-1000));
                    screen.getUiStage().addAction(Actions.sequence(Actions.fadeOut(3), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("loading map number "+GameScreen.getCount());
                            MainMenuScreen.logArea.setText("");
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

//        if( !((NPC)screen.getNpcList().get(enemyPointer)).isFriendly()
//                &&enemyPointer != null && ! screen.isHitObject() && ! screen.getNpcList().get(enemyPointer).getCharacter().isDead() ){
//        	screen.getPlayerController().walkTo(screen.getNpcList().get(enemyPointer).getPosition().x,screen.getNpcList().get(enemyPointer).getPosition().y );
//        } else{
//            if(enemyIterator.hasNext()
//                    && (((NPC)screen.getNpcList().get(enemyPointer)).isFriendly() || screen.getNpcList().get(enemyPointer).getCharacter().isDead())){
//                enemyPointer = enemyIterator.next();
//            }
//            if(!enemyIterator.hasNext()){
//            	screen.getPlayerController().walkTo(screen.getMapModel().getExitDoor().x + screen.getPlayer().getBound().width, screen.getMapModel().getExitDoor().y - (screen.getPlayer().getBound().height * 3 /4));
//            }
//        }

        if(screen.getPlayerController().findEnemyInAttackRange()!=null && screen.getPlayerController().isAbleToAttack()){
            enemyPointer = screen.getPlayerController().findEnemyInAttackRange();
            GameActor tmp= screen.getNpcList().remove(enemyPointer);
            MainMenuScreen.logArea.appendText("you are attacking "+ tmp.getCharacter().getName()+"\n");
            tmp.getCharacter().underAttack(screen.getPlayer().getCharacter());
            screen.getNpcList().put(tmp.getPosition(), tmp);
            screen.startNextRound();
        } else{
            if(screen.getPlayerController().findEnemyToAttack()!=null && ! screen.isHitObject()){
                screen.getPlayerController().walkTo(screen.getNpcList().get(enemyPointer).getPosition().x,screen.getNpcList().get(enemyPointer).getPosition().y );
            }
            if(screen.getPlayerController().findEnemyToAttack()== null){
                screen.getPlayerController().walkTo(screen.getMapModel().getExitDoor().x + screen.getPlayer().getBound().width, screen.getMapModel().getExitDoor().y - (screen.getPlayer().getBound().height * 3 /4));
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
