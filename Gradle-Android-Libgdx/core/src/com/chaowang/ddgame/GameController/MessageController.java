package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.chaowang.ddgame.GameUI.DialogueBox;

/**
 * Created by Chao on 18/03/2017.
 */

public class MessageController extends InputAdapter{

    private DialogueBox dialogueBox;

    public MessageController(DialogueBox box){
        this.dialogueBox = box;

    }

    @Override
    public boolean keyDown(int keycode) {
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if ( keycode == Input.Keys.ENTER && dialogueBox.isFinished()) { // continue through tree
            dialogueBox.setVisible(false);
            return true;
        }
        return false;
    }

    public void startDialogue(String message) {
        dialogueBox.setVisible(true);
        dialogueBox.animateText(message);
    }

}
