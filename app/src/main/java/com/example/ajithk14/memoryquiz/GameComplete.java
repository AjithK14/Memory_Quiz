package com.example.ajithk14.memoryquiz;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameComplete extends AppCompatActivity {
    ConstraintLayout myLayout;
    AnimationDrawable myDraw;
    TextView rightans;
    TextView wrongans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);
        myLayout = (ConstraintLayout)findViewById(R.id.myLayout);
        myDraw=(AnimationDrawable)myLayout.getBackground();
        myDraw.setEnterFadeDuration(4500);
        myDraw.setExitFadeDuration(4500);
        myDraw.start();
        Intent mIntent = getIntent();
        int wrong = mIntent.getIntExtra("wrong", 0);
        int right = mIntent.getIntExtra("right", 0);
        rightans = (TextView)findViewById(R.id.textView8);
        wrongans = (TextView)findViewById(R.id.textView9);
        rightans.setText("Right: "+right);
        wrongans.setText("Wrong: "+wrong);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String input = timeStamp.substring(4,6)+"/"+timeStamp.substring(6,8)+"/"+timeStamp.substring(0,4);
        DATABASEFINAL.dates.add(input + " " + Integer.toString(right));
        DATABASEFINAL.done(getApplicationContext());
    }
    public void Done(View v)
    {
        startActivity(new Intent(getApplicationContext(),StartScreen.class));
    }
    public void playAgain(View v)
    {
        startActivity(new Intent(getApplicationContext(),GameActivity.class));
    }
}
