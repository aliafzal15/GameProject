package com.chaowang.ddgame.DialogueSystem;

import java.util.List;
import com.chaowang.ddgame.DialogueSystem.DialogueNode.NODE_TYPE;

/**
 * @author hydrozoa
 */
public class DialogueTraverser {

    private Dialogue dialogue;
    private DialogueNode currentNode;

    public DialogueTraverser(Dialogue dialogue) {
        this.dialogue = dialogue;
        currentNode = dialogue.getNode(dialogue.getStart());
    }

    public DialogueNode getNextNode(int pointerIndex) {
        DialogueNode nextNode = dialogue.getNode(currentNode.getPointers().get(pointerIndex));
        currentNode = nextNode;
        return nextNode;
    }

    public List<String> getOptions() {
        return currentNode.getLabels();
    }

    public String getText() {
        return currentNode.getText();
    }

    public NODE_TYPE getType() {
        return currentNode.getType();
    }

    public void  setType (NODE_TYPE type) {
        currentNode.setType(type);
    }
}
