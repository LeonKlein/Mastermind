package com.master.mind.Actors;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Leon on 07.10.2017.
 */

public class PermutableArray<T> extends Array<T> {
    public PermutableArray (boolean ordered, int capactiy){
        super(ordered, capactiy);
    }

    public void permuteLeft(){
        T first = this.first();
        for (int i = 0; i < this.size - 1 ; i++) {
            this.set(i, this.get(i+1));
        }
        this.set(this.size - 1, first);
    }

    public void permuteRight(){
        T last = this.peek();
        for (int i = this.size - 1; i > 0 ; i--) {
            this.set(i, this.get(i-1));
        }
        this.set(0, last);
    }
}
