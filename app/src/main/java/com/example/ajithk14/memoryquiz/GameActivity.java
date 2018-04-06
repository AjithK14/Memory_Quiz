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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    /*
    READ IN ALL THE FACES AND THEIR NAMES
    SHUFFLE THE FACES
    FOR THE FUTURE WE NEED TO INCREASE THE FREQUENCY OF THE MOST MISSED FACES
     */

    /*
    THE ONCLICK METHOD FOR EACH BUTTON ESSENTIALLY SEES IF THE RIGHT ANSWER HAS BEEN SELECTED
    THEN IT PERFORMS AN ANIMATION BASED ON THE CORRECTNESS OF THE ANSWER
    THEN IT SEES IF 10 QUESTIONS HAVE BEEN ASKED AND ENDS THE GAME ACCORDINGLY
     */

    Button A, B, C, D;
    ImageView faceImg;
    TextView quesField;
    List<FaceItem> list = new ArrayList<>();
    Random r;
    int correct_answer;
    int turn = 1;
    int tempB;
    int correct = 0;
    int wrong = 0;
    public Button[] arr;
    public String THERIGHTANSWER;
    public int THERIGHTINDEX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        faceImg = (ImageView) findViewById(R.id.imageView4);
        r=new Random();
        int permis = ContextCompat.checkSelfPermission(GameActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        //EACH OPTION
        A = (Button)findViewById(R.id.optionA);
        B = (Button)findViewById(R.id.optionB);
        C = (Button)findViewById(R.id.optionC);
        D = (Button)findViewById(R.id.optionD);

        quesField = (TextView) findViewById((R.id.questionField));

        arr = new Button[]{A,B,C,D};

        //FILL UP CONTENTS OF ARRAY

        if (DATABASEFINAL.answers.size()==0){
            //Log.d("apparently this is zero", "FE");

        DATABASEFINAL.readFromFile(getApplicationContext());}
        Log.d("Fav Color List",DATABASEFINAL.favoriteColor.toString());

        for (int i = 0; i < DATABASEFINAL.answers.size(); i++)
        {
            list.add(new FaceItem("What is his/her name?",DATABASEFINAL.answers.get(i), DATABASEFINAL.faces.get(i), i));
            list.add(new FaceItem("What is his/her favorite color?",DATABASEFINAL.favoriteColor.get(i), DATABASEFINAL.faces.get(i), i));
        }

        //FOR NOW WE ARE SHUFFLING BUT LATER WE NEED TO ASK THE MOST MISSED MORE OFTEN
        Collections.shuffle(list);

        //GIVE THE USER A QUESTION
        newQuestion(turn%list.size());
    }

    protected void onDestroy() {
        super.onDestroy();
        DATABASEFINAL.done(getApplicationContext());
        Log.d("out", "WE OUT");
    }
    public void showRightDialog()
    {
        Log.e("answers", (TextUtils.join(", ",DATABASEFINAL.answers)));
        Log.e("scores", (TextUtils.join(", ",DATABASEFINAL.scores)));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                GameActivity.this);

        // set title
        alertDialogBuilder.setTitle("Correct!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Nice job!")
                .setCancelable(false)
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
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
            DATABASEFINAL.scores.set(THERIGHTINDEX,DATABASEFINAL.scores.get(THERIGHTINDEX)+1);
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
            DATABASEFINAL.scores.set(THERIGHTINDEX,DATABASEFINAL.scores.get(THERIGHTINDEX)+1);
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
            DATABASEFINAL.scores.set(THERIGHTINDEX,DATABASEFINAL.scores.get(THERIGHTINDEX)+1);
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
            DATABASEFINAL.scores.set(THERIGHTINDEX,DATABASEFINAL.scores.get(THERIGHTINDEX)+1);
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
                alertDialogBuilder.setTitle("Not quite...");
                Log.d("suh",""+turn);
                // set dialog message
                alertDialogBuilder
                        .setMessage("The correct answer is "+arr[correct_answer].getText())
                        .setCancelable(false)
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
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
        /*
        FIND THE IMAGE IN THE DATABASE ARRAY  AND
         */

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
        ArrayList<Integer> poses = new ArrayList<>(4);
        poses.add(num);

        faceImg.setImageBitmap(b);
        quesField.setText(list.get(num).questionText);
        correct_answer = r.nextInt(4);
        arr[correct_answer].setText(list.get(num).answer);
        for (int i = 1; i < 4; i++)
        {
            int pos = (int)((double)(Math.random()*list.size()));


            /* NOTE: change list.size()/x so that x = the number of answer options (name, fav color, etc.....) */

            if(list.get(pos).questionText.equals(list.get(num).questionText) && (!poses.contains(pos) || poses.size()>=list.size()/2)) {
                arr[(correct_answer + i) % 4].setText(list.get(pos).answer);
                poses.add(pos);
            }
            else
                i--;
        }
        Log.d("right answer", list.get(num).answer);
        THERIGHTANSWER=list.get(num).answer;
        THERIGHTINDEX = list.get(num).index;
    }
}
