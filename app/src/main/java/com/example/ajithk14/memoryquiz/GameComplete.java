package com.example.ajithk14.memoryquiz;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        try {
            File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct.exists())
            {
                direct.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","gamesPlayed.txt");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());
            FileOutputStream output = new FileOutputStream(file, false);

            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("facesNames.txt", Context.MODE_PRIVATE));
            output.write((currentDateandTime + ' ' + Integer.toString(right) + '\n').getBytes());
            //outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
