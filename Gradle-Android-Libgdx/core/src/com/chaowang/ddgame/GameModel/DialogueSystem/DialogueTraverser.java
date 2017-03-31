package com.chaowang.ddgame.GameModel.DialogueSystem;

import java.util.List;

/**
 * @author hydrozoa
 */
public class DialogueTraverser {

    private Dialogue dialogue;
    private com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode currentNode;
    /**
     * set dialogue values
     * @param dialogue
     */
    public DialogueTraverser(Dialogue dialogue) {
        this.dialogue = dialogue;
        currentNode = dialogue.getNode(dialogue.getStart());
    }
    /**
     * get next node
     * @param pointerIndex
     * @return
     */
    public com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode getNextNode(int pointerIndex) {
        com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode nextNode = dialogue.getNode(currentNode.getPointers().get(pointerIndex));
        currentNode = nextNode;
        return nextNode;
    }
    /**
     * get option
     * @return
     */
    public List<String> getOptions() {
        return currentNode.getLabels();
    }
    /**
     * get text
     * @return
     */
    public String getText() {
        return currentNode.getText();
    }
    /**
     * 
     * get type
     * @return
     */
    public com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode.NODE_TYPE getType() {
        return currentNode.getType();
    }
    /**
     * 
     * set type
     * @param type
     */
    public void  setType (com.chaowang.ddgame.GameModel.DialogueSystem.DialogueNode.NODE_TYPE type) {
        currentNode.setType(type);
    }
}
