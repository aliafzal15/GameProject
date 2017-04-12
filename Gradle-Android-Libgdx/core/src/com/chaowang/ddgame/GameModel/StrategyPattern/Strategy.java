package com.chaowang.ddgame.GameModel.StrategyPattern;
/**
 * Strategy interface
 * @author chao wang
 * @version 3.0
 */
public interface Strategy {

	/**
	 * abstract functions for setupCamera, renderInteraction, updateDialogueStage
	 */
	void setupCamera();

	void renderInteraction(float delta);
	
	void updateDialogueStage(float delta);
}
