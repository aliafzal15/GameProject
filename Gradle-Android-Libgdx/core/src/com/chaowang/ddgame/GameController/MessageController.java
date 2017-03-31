package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.chaowang.ddgame.GameUtl.DialogueBox;

/**
 * controller for message delivering
 * @author chao wang
 * @version 2.0
 */

public class MessageController extends InputAdapter{

    private DialogueBox dialogueBox;
    /**
     * construct
     * @param box
     */
    public MessageController(DialogueBox box){
        this.dialogueBox = box;

    }
    /**
     * key down to switch message
     */
    @Override
    public boolean keyDown(int keycode) {
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }
    /**
     * key up to switch message
     */
    @Override
    public boolean keyUp(int keycode) {
        if ( keycode == Input.Keys.ENTER && dialogueBox.isFinished()) { // continue through tree
            dialogueBox.setVisible(false);
            return true;
        }
        return false;
    }
    /**
     * start dialogue
     * @param message
     */
    public void startDialogue(String message) {
        dialogueBox.setVisible(true);
        dialogueBox.animateText(message);
    }

}
