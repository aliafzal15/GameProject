package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.chaowang.ddgame.DialogueSystem.Dialogue;
import com.chaowang.ddgame.DialogueSystem.DialogueNode;
import com.chaowang.ddgame.DialogueSystem.DialogueTraverser;
import com.chaowang.ddgame.GameUI.DialogueBox;
import com.chaowang.ddgame.GameUI.OptionBox;
import com.chaowang.ddgame.DialogueSystem.DialogueNode.NODE_TYPE;

/**
 * Created by Chao on 18/03/2017.
 */

public class DialogueController extends InputAdapter{


    private DialogueTraverser traverser;
    private DialogueBox dialogueBox;
    private DialogueBox messageDialog;
    private OptionBox optionBox;
    private int answerIndex;

    public DialogueController(DialogueBox box, OptionBox optionBox, DialogueBox message) {
        this.dialogueBox = box;
        this.optionBox = optionBox;
        answerIndex = -1;
        messageDialog = message;
    }

    public int getAnswerIndex() {
        return answerIndex;
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
        if (optionBox.isVisible()) {
            if (keycode == Input.Keys.UP) {
                optionBox.moveUp();
                return true;
            } else if (keycode == Input.Keys.DOWN) {
                optionBox.moveDown();
                return true;
            }
        }
        if (traverser != null && keycode == Input.Keys.ENTER && dialogueBox.isFinished()) { // continue through tree
            if (traverser.getType() == NODE_TYPE.END) {
                traverser = null;
                dialogueBox.setVisible(false);
            } else if (traverser.getType() == NODE_TYPE.LINEAR) {
                answerIndex = -1;
                progress(0);
            } else if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
                answerIndex = optionBox.getIndex();
                System.out.println("in root, answer index is " + answerIndex);
                optionBox.setVisible(false);
                dialogueBox.setVisible(false);
                return true;
            }
            return true;
        }
        if (dialogueBox.isVisible()) {
            return true;
        }
        if ( keycode == Input.Keys.ESCAPE && messageDialog.isFinished()) { // continue through tree
            messageDialog.setVisible(false);
            return true;
        }
        return false;
    }

    public void update(float delta) {
        if (dialogueBox.isFinished() && traverser != null) {
            if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE && answerIndex == -1) {
                optionBox.setVisible(true);
            }
        }
    }

    public void startDialogue(Dialogue dialogue) {
        traverser = new DialogueTraverser(dialogue);
        dialogueBox.setVisible(true);
        dialogueBox.animateText(traverser.getText());
        if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
            optionBox.clear();
            for (String s : dialogue.getNode(dialogue.getStart()).getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

    public void animateText(String message) {
        messageDialog.setVisible(true);
        messageDialog.animateText(message);
    }

    private void progress(int index) {
        optionBox.setVisible(false);
        DialogueNode nextNode = traverser.getNextNode(index);
        dialogueBox.animateText(nextNode.getText());
        if (nextNode.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
            optionBox.clearChoices();
            for (String s : nextNode.getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

    public boolean isDialogueShowing() {
        return dialogueBox.isVisible();
    }
}
