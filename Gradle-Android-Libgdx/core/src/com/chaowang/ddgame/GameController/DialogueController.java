package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.chaowang.ddgame.GameModel.DialogueSystem.Dialogue;
import com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode;
import com.chaowang.ddgame.GameModel.DialogueSystem.DialogueTraverser;
import com.chaowang.ddgame.GameUtl.DialogueBox;
import com.chaowang.ddgame.GameUtl.OptionBox;
import com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode.NODE_TYPE;

/**
 * controller for dialogue table
 * @author chao wang
 * @version 2.0
 */

public class DialogueController extends InputAdapter{


    private DialogueTraverser traverser;
    private DialogueBox dialogueBox;
    private DialogueBox messageDialog;
    private OptionBox optionBox;
    private int answerIndex;
    private boolean indexFlag;
    /**
     * construct
     * @param box
     * @param optionBox
     * @param message
     */
    public DialogueController(DialogueBox box, OptionBox optionBox, DialogueBox message) {
        this.dialogueBox = box;
        this.optionBox = optionBox;
        answerIndex = -1;
        messageDialog = message;
        indexFlag = false;
    }
    
    /**
     * setter AnswerIndex
     * @return
     */
    public void setAnswerIndex(int index) {
        answerIndex = index;
    }
    /**
     * getter AnswerIndex
     * @return
     */
    public int getAnswerIndex() {
        return answerIndex;
    }
    /**
     * choose answer options
     */
    @Override
    public boolean keyDown(int keycode) {
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }
    /**
     * index flag
     * @return
     */
    public boolean isIndexFlag() {
        return indexFlag;
    }
    /**
     * set index flag
     * @param indexFlag
     */
    public void setIndexFlag(boolean indexFlag) {
        this.indexFlag = indexFlag;
    }
    /**
     * choose answer option
     * @return
     */
    public boolean keyUp() {
        if (optionBox.isVisible()) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP )) {
                optionBox.moveUp();
                return true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN )) {
                optionBox.moveDown();
                return true;
            }
        }
        if (traverser != null && Gdx.input.isKeyPressed(Input.Keys.ENTER ) && dialogueBox.isFinished()) { // continue through tree
            if (traverser.getType() == NODE_TYPE.END) {
                traverser = null;
                dialogueBox.setVisible(false);
            } else if (traverser.getType() == NODE_TYPE.LINEAR) {
                answerIndex = -1;
                progress(0);
            } else if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
                answerIndex = optionBox.getIndex();
                if( answerIndex == 0){
                    indexFlag = true;
                    optionBox.setVisible(false);
                    dialogueBox.setVisible(false);
                    System.out.println("in root, answer index is " + answerIndex+ " which means trade");
                }
                else if(answerIndex ==1){
                    indexFlag = true;
                    optionBox.setVisible(false);
                    dialogueBox.setVisible(false);
                    System.out.println("in root, answer index is " + answerIndex+ "which means move");
                }
                else if(answerIndex ==2){
                    indexFlag = true;
                    optionBox.setVisible(false);
                    dialogueBox.setVisible(false);
                    System.out.println("in root, answer index is " + answerIndex+ "which means trade");
                }
                return true;
            }
            return true;
        }
        if (dialogueBox.isVisible()) {
            return true;
        }
        if ( Gdx.input.isKeyPressed(Input.Keys.ESCAPE ) && messageDialog.isFinished()) { // continue through tree
            messageDialog.setVisible(false);
            return true;
        }
        return false;
    }
    /**
     * update answer
     * @param delta
     */
    public void update(float delta) {
        if (dialogueBox.isFinished() && traverser != null) {
            if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE && answerIndex == -1 ) {
                optionBox.setVisible(true);
            }
        }
    }
    /**
     * start dialogue
     * @param dialogue
     */
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
    /**
     * animate text
     * @param message
     */
    public void animateText(String message) {
        messageDialog.setVisible(true);
        messageDialog.animateText(message);
    }
    /**
     * process text
     * @param index
     */
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
    /**
     * show dialogue
     * @return
     */
    public boolean isDialogueShowing() {
        return dialogueBox.isVisible();
    }
}
