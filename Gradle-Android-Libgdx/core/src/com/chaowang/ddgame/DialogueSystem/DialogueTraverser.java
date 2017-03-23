package com.chaowang.ddgame.DialogueSystem;

import java.util.List;
import com.chaowang.ddgame.DialogueSystem.DialogueNode.NODE_TYPE;

/**
 * @author hydrozoa
 */
public class DialogueTraverser {

    private Dialogue dialogue;
    private DialogueNode currentNode;
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
    public DialogueNode getNextNode(int pointerIndex) {
        DialogueNode nextNode = dialogue.getNode(currentNode.getPointers().get(pointerIndex));
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
    public NODE_TYPE getType() {
        return currentNode.getType();
    }
    /**
     * 
     * set type
     * @param type
     */
    public void  setType (NODE_TYPE type) {
        currentNode.setType(type);
    }
}
