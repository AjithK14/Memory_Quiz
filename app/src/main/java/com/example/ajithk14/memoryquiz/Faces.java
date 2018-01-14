package com.example.ajithk14.memoryquiz;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ajithk14 on 1/6/2018.
 */

public class Faces {
    private int _id;
    private int score;
    private String currDt;
    private String name;
    Bitmap coors;

    public Faces(Bitmap coors, String name)
    {
        this.coors=coors;
        this.score=0;
        this.name=name;
        this.currDt = DateFormat.getDateTimeInstance().format(new Date());
    }
    public String getCurrDt()
    {
        return currDt;
    }
    public void set_id(int x)
    {
        _id = x;
    }
    public int getid()
    {
        return _id;
    }
    public void increment()
    {
        score++;
    }
    public int getScore()
    {
        return score;
    }
    public String getName(){return name;}
}
