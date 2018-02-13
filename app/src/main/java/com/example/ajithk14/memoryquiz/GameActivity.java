package com.example.ajithk14.memoryquiz;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
    int tempB;
    int correct = 0;
    int wrong = 0;
    public Button[] arr;
    public String THERIGHTANSWER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        faceImg = (ImageView) findViewById(R.id.imageView4);
        r=new Random();
        int permis = ContextCompat.checkSelfPermission(GameActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        A = (Button)findViewById(R.id.optionA);
        B= (Button)findViewById(R.id.optionB);
        C = (Button)findViewById(R.id.optionC);
        D = (Button)findViewById(R.id.optionD);

        arr = new Button[]{A,B,C,D};

        if (DATABASEFINAL.answers.size()==0){
            //Log.d("apparently this is zero", "FE");

        DATABASEFINAL.readFromFile(getApplicationContext());}

        for (int i = 0; i < DATABASEFINAL.answers.size(); i++)
        {
            list.add(new FaceItem(DATABASEFINAL.answers.get(i),DATABASEFINAL.faces.get(i)));
        }

        Collections.shuffle(list);
        newQuestion(turn%list.size());
    }

    protected void onDestroy() {
        super.onDestroy();
        DATABASEFINAL.done(getApplicationContext());
        Log.d("out", "WE OUT");
    }
    public void showRightDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                GameActivity.this);

        // set title
        alertDialogBuilder.setTitle("CORRECT!");

        // set dialog message
        alertDialogBuilder
                .setMessage("You are right!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (turn < 10)
                        {
                            turn++;
                            newQuestion(turn%list.size());
                        }
                        else
                        {
                            gameFinished();
                        }
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    public void correctProtocol()
    {
        faceImg.setImageResource(R.drawable.check);

    }
    public void onClickA(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (A.getText().toString().equals(THERIGHTANSWER))
        {
            correctProtocol();
            showRightDialog();
            correct++;


        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }
    }
    public void onClickB(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (B.getText().toString().equals(THERIGHTANSWER))
        {
            faceImg.setImageResource(R.drawable.check);
            showRightDialog();
            correct++;
        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }

    }
    public void onClickC(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (C.getText().toString().equals(THERIGHTANSWER))
        {
            faceImg.setImageResource(R.drawable.check);
            showRightDialog();
            correct++;

        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }
    }
    public void onClickD(View v)
    { //if right put check on img view and move on
        //if wrong put x on img view make all other buttons red and right one green, have timers to move on
        if (D.getText().toString().equals(THERIGHTANSWER))
        {
            faceImg.setImageResource(R.drawable.check);
            showRightDialog();
            correct++;

        }
        else
        {
            wrong++;
            faceImg.setImageResource(R.drawable.wrong);
            flashRightWrong();

        }
    }
    public void flashRightWrong()
    {
        Log.d("you thought!", ""+correct_answer);
        tempB = ((ColorDrawable) arr[correct_answer].getBackground()).getColor();

        arr[correct_answer].setBackgroundColor(Color.GREEN);
        int tmp = (correct_answer+1)%4;
        arr[correct_answer].setEnabled(false);
        while (tmp!=correct_answer)
        {
            arr[tmp].setEnabled(false);
            tmp=(tmp+1)%4;
        }
        ObjectAnimator anim = ObjectAnimator.ofInt(arr[correct_answer],"backgroundColor",Color.WHITE, Color.GREEN,Color.WHITE);
        anim.setDuration(500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatCount(2);
        anim.start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        GameActivity.this);

                // set title
                alertDialogBuilder.setTitle("WRONG!");
                Log.d("suh",""+turn);
                // set dialog message
                alertDialogBuilder
                        .setMessage("The correct answer is actually "+arr[correct_answer].getText())
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                arr[correct_answer].setBackgroundColor(tempB);
                                if (turn < 10)
                                {
                                    turn++;
                                    int tmp = correct_answer+1;
                                    tmp=tmp%4;
                                    arr[correct_answer].setEnabled(true);
                                    while (tmp!=correct_answer)
                                    {
                                        arr[tmp].setEnabled(true);
                                        tmp=(tmp+1)%4;
                                    }
                                    newQuestion(turn%list.size());
                                }
                                else
                                {
                                    gameFinished();
                                }
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        }, 1000);

        /*
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
        */

    }
    public void gameFinished()
    {
        Log.d("right",""+correct);
        Log.d("missed",""+wrong);
        Intent myIntent = new Intent(GameActivity.this, GameComplete.class);
        myIntent.putExtra("right", correct);
        myIntent.putExtra("wrong",wrong);
        startActivity(myIntent);
        //finish();
    }
    private void newQuestion(int num)
    {
        Log.d("whereweat",""+turn);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File f = new File(path1, list.get((num)).image);
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        faceImg.setImageBitmap(b);
        correct_answer = r.nextInt(4);
        arr[correct_answer].setText(list.get(num).name);
        for (int i = 1; i < 4; i++)
        {
            arr[(correct_answer+i)%4].setText(list.get((num+i)%list.size()).name);
        }
        Log.d("right answer", list.get(num).name);
        THERIGHTANSWER=list.get(num).name;
    }
}
