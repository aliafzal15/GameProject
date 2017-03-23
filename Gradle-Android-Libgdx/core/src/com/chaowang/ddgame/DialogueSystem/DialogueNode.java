package com.chaowang.ddgame.DialogueSystem;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hydrozoa
 */
public class DialogueNode {

    private ArrayList<Integer> pointers = new ArrayList<Integer>();
    private ArrayList<String> labels = new ArrayList<String>();

    private String text;
    private int id;

    private NODE_TYPE type;
    /**
     * enum type to set value
     */
    public enum NODE_TYPE {
        MULTIPLE_CHOICE, // text, pointers with labels
        LINEAR,			 // text, single unlabeled pointer
        END,			 // text, no pointer
        ;
    }
    /**
     * node for dialogue
     * @param text
     * @param id
     */
    public DialogueNode(String text, int id) {
        this.text = text;
        this.id = id;
        type = NODE_TYPE.END;
    }
    /**
     * add nodes
     * @param option
     * @param nodeId
     */
    public void addChoice(String option, int nodeId) {
        if (type == NODE_TYPE.LINEAR) {
            pointers.clear();
        }
        labels.add(option);
        pointers.add(nodeId);
        type = NODE_TYPE.MULTIPLE_CHOICE;
    }
    /**
     * make linear
     * @param nodeId
     */
    public void makeLinear(int nodeId) {
        pointers.clear();
        labels.clear();
        pointers.add(nodeId);
        type = NODE_TYPE.LINEAR;
    }
    /**
     * get pointers
     * @return
     */
    public List<Integer> getPointers() {
        return pointers;
    }
    /**
     * get labels
     * @return
     */
    public List<String> getLabels() {
        return labels;
    }
    /**
     * get type
     * @return
     */
    public NODE_TYPE getType() {
        return type;
    }
    /**
     * set type
     * @param type
     */
    public void setType(NODE_TYPE type) {
        this.type = type;
    }
    /**
     * get text
     * @return
     */
    public String getText() {
        return text;
    }
    /**
     * get ID
     * @return
     */
    public int getID() {
        return id;
    }
}
