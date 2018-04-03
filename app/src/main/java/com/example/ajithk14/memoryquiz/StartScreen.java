package com.example.ajithk14.memoryquiz;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.content.*;

public class StartScreen extends AppCompatActivity{
    ConstraintLayout myLayout;
    AnimationDrawable myDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);
        myLayout = (ConstraintLayout)findViewById(R.id.myLayout);
        myDraw=(AnimationDrawable)myLayout.getBackground();
        myDraw.setEnterFadeDuration(4500);
        myDraw.setExitFadeDuration(4500);
        myDraw.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DATABASEFINAL.done(getApplicationContext());
        Log.d("out", "WE OUT");
    }
    public void info(View v)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                StartScreen.this);

        // set title
        alertDialogBuilder.setTitle("Instructions");

        // set dialog message
        alertDialogBuilder
                .setMessage("Welcome! This app is designed for you to be able to make quizzes for potential dementia" +
                        " patients who you care for and would like to be able to remember information about all" +
                        " of their loved ones. " + "\n" +  "\n" + "Usage of this app is simple: " + "\n" + "\n" +"Take pictures of faces you would like" +
                " for the quizzes to be on with the camera app in this phone." + "\n" + "Then click the add button to add the faces one by one."
                + "\n" + "You can crop these images to capture only the face. " + "\n" + "Then enter information about the face. Repeatedly do this "
                + "until all of the faces you want are in the database. " + "\n"+"You can delete and edit information as necessary with the edit button and "
                + "click play to take quizzes.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    public void onClick(View v)
    {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(),ChooseImage.class));

    }
    public void onClickEdit(View v)
    {
        startActivity(new Intent(getApplicationContext(),EditActivity.class));
    }
    public void onClickStats(View v)
    {
        startActivity(new Intent(getApplicationContext(),StatsActivity.class));
    }
    public void onClickPlay(View v)
    {
        DATABASEFINAL.readFromFile(getApplicationContext());
        if (DATABASEFINAL.answers.size()==0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    StartScreen.this);

            // set title
            alertDialogBuilder.setTitle("SORRY!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("You need to enter some names first!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        else{
        startActivity(new Intent(getApplicationContext(),GameActivity.class));}
    }
}