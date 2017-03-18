package com.chaowang.ddgame.util;

/**
 * Created by Chao on 16/03/2017.
 */

public class AtomicFloat {
    private float atomic;

    public AtomicFloat(float atomic) {
        this.atomic = atomic;
    }

    public AtomicFloat(){
        atomic = 1;
    }

    public float get() {
        return atomic;
    }

    public void set(float atomic) {
        this.atomic = atomic;
    }
}
