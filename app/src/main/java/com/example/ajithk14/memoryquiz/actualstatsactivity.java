package com.example.ajithk14.memoryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Collections;

/*
most missed

Least missed

Log of all actions??

Table with each person and the percentage
their percentage

//Graph of performance
with percentages corre'
ct overall for each quiz
--only display graph if you have at least 2 quizzes

Text that says to view scores of specific pics
go to edit page

 */
public class actualstatsactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualstatsactivity);
        DATABASEFINAL.readFromFile(getApplicationContext());
        //int minIndex = DATABASEFINAL.scores.indexOf(Collections.min(DATABASEFINAL.scores));

        /*
        https://www.youtube.com/watch?v=i-35fEBiYYg
         */

    }
}
