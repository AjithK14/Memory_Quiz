package com.example.ajithk14.memoryquiz;

import android.support.annotation.NonNull;

/**
 * Created by Ajithk14 on 2/11/2018.
 */

public class MyItem implements Comparable<MyItem> {
    String name;
    String image;
    int index;
    int score=0;
    public MyItem(String name1, String image1, int ind)
    {
        name=name1;
        image=image1;
        index = ind;
        score=0;
    }
    public MyItem(String name1, String image1, int ind, int tscore)
    {
        name=name1;
        image=image1;
        index = ind;
        score=tscore;
    }


    @Override
    public int compareTo(@NonNull MyItem o) {
        return o.score-score;
    }
}
