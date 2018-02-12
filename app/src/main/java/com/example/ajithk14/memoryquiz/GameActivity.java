package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Button A, B, C, D;
    ImageView faceImg;
    List<FaceItem> list = new ArrayList<>();
    Random r;
    int correct_answer;
    int turn = 1;
    int correct = 0;
    int wrong = 0;
    public Button[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        faceImg = (ImageView) findViewById(R.id.imageView4);
        r=new Random();
        A = (Button)findViewById(R.id.optionA);
        B= (Button)findViewById(R.id.optionB);
        C = (Button)findViewById(R.id.optionC);
        D = (Button)findViewById(R.id.optionD);

        arr = new Button[]{A,B,C,D};

        if (DATABASEFINAL.answers.size()==0)
        {
            DATABASEFINAL.readFromFile(getApplicationContext());
        }

        for (int i = 0; i < DATABASEFINAL.answers.size(); i++)
        {
            list.add(new FaceItem(DATABASEFINAL.answers.get(i),DATABASEFINAL.faces.get(i)));
        }

        Collections.shuffle(list);
        newQuestion(turn);
    }

    @Override
    protected void onPause() {
        DATABASEFINAL.done(getApplicationContext());
        super.onPause();
    }
    protected void onDestroy() {
        super.onDestroy();
        DATABASEFINAL.done(getApplicationContext());
        Log.d("out", "WE OUT");
    }

    public void onClickA(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (A.getText().toString().equalsIgnoreCase(list.get(turn-1).name))
        {
            faceImg.setImageResource(R.drawable.check);
            correct++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }
        if (turn < 10)
        {
            turn++;
            turn = turn%list.size();
            newQuestion(turn);
        }
        else
        {
            gameFinished();
        }
    }
    public void onClickB(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (A.getText().toString().equalsIgnoreCase(list.get(turn-1).name))
        {
            faceImg.setImageResource(R.drawable.check);
            correct++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }
        if (turn < 10)
        {
            turn++;
            turn = turn%list.size();
            newQuestion(turn);
        }
        else
        {
            gameFinished();
        }
    }
    public void onClickC(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (A.getText().toString().equalsIgnoreCase(list.get(turn-1).name))
        {
            faceImg.setImageResource(R.drawable.check);
            correct++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }
        if (turn < 10)
        {
            turn++;
            turn = turn%list.size();
            newQuestion(turn);
        }
        else
        {
            gameFinished();
        }
    }
    public void onClickD(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (A.getText().toString().equalsIgnoreCase(list.get(turn-1).name))
        {
            faceImg.setImageResource(R.drawable.check);
            correct++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }
        if (turn < 10)
        {
            turn++;
            turn = turn%list.size();
            newQuestion(turn);
        }
        else
        {
            gameFinished();
        }
    }
    public void flashRightWrong()
    {
        for (int k = 0; k < 6; k++)
        {
            //will need to test if editing text is good idea
            if (k%2 == 0){
            arr[correct_answer].setBackgroundColor(0xff669900);}
            else
            {
                arr[correct_answer].setBackgroundColor(0xff99cc00);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    public void gameFinished()
    {
        Log.d("right",""+correct);
        Log.d("missed",""+wrong);
        finish();
    }
    private void newQuestion(int num)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File f = new File(path1, list.get(num-1).image);
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        faceImg.setImageBitmap(b);
        correct_answer = r.nextInt(4);
        arr[correct_answer].setText(list.get(num-1).name);
        for (int i = 1; i < 4; i++)
        {
            arr[(correct_answer+i)%4].setText(list.get((num-1+i)%list.size()).name);
        }
    }
}
