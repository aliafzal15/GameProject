package com.chaowang.ddgame.GameModel.StrategyPattern;

public interface Strategy {

	
	void setupCamera();

	void renderInteraction();
	
	void updateDialogueStage(float delta);
}
