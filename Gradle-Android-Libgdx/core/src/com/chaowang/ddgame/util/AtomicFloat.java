package com.chaowang.ddgame.util;

/**
 * class for Atomic Float
 * @author chao wang
 * @version 2.0
 */
public class AtomicFloat {
    private float atomic;
    /**
     * constructor
     * @param atomic
     */
    public AtomicFloat(float atomic) {
        this.atomic = atomic;
    }
    /**
     * set value for atomic
     */
    public AtomicFloat(){
        atomic = 1;
    }
    /**
     * get atomic value 
     * @return
     */
    public float get() {
        return atomic;
    }
    /**
     * set atomic value 
     * @param atomic
     */
    public void set(float atomic) {
        this.atomic = atomic;
    }
}
