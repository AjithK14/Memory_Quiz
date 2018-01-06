package com.example.ajithk14.memoryquiz;

/**
 * Created by Ajithk14 on 1/6/2018.
 */

public class Faces {
    private int id;
    private int score;
    private String name;
    int[] coors;

    public Faces(int[] coors,int id)
    {
        this.coors=coors;
        this.score=0;
        this.id = id;
    }
    public void callFace(int id) //for when a user calls this
    {
        this.id = id;
    }
    public void increment()
    {
        score++;
    }
    public int getScore()
    {
        return score;
    }
}
