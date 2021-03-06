package com.example.ajithk14.memoryquiz;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameComplete extends AppCompatActivity {
    ConstraintLayout myLayout;
    AnimationDrawable myDraw;
    TextView rightans;
    TextView wrongans;
    TextView remark;
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
        remark = (TextView)findViewById(R.id.textView4);
        remark.setText(right > wrong ? "Congratulations!" : "Better Luck next time!");
        rightans.setText("Right: "+right);
        wrongans.setText("Wrong: "+wrong);
    }
    public void Done(View v)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    public void playAgain(View v)
    {
        startActivity(new Intent(getApplicationContext(),GameActivity.class));
    }
}
