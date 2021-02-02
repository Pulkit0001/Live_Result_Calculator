package com.example.liveCalculator;

public class Boxer {
    int index;
    Boxer(int index)
    {
        this.index=index;
    }
    public void increment(int i){
        index+=i;
    }
    public int getIndex()
    {
        return index;
    }
}
