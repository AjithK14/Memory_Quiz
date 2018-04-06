package com.example.ajithk14.memoryquiz;

/**
 * Created by Ajithk14 on 2/11/2018.
 */

public class FaceItem {
    String questionText;
    String answer;
    String image;
    int index;
    public FaceItem(String questionText1, String answer1, String image1, int ind)
    {
        questionText=questionText1;
        answer=answer1;
        image=image1;
        index = ind;
    }
}
