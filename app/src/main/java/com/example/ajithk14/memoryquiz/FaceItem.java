package com.example.ajithk14.memoryquiz;

import android.support.annotation.NonNull;

/**
 * Created by Ajithk14 on 2/11/2018.
 */

public class FaceItem implements Comparable<FaceItem> {
    String name;
    String image;
    int index;
    int score=0;
    public FaceItem(String name1, String image1, int ind)
    {
        name=name1;
        image=image1;
        index = ind;
        score=0;
    }
    public FaceItem(String name1, String image1, int ind, int tscore)
    {
        name=name1;
        image=image1;
        index = ind;
        score=tscore;
    }


    @Override
    public int compareTo(@NonNull FaceItem o) {
        return o.score-score;
    }
}
