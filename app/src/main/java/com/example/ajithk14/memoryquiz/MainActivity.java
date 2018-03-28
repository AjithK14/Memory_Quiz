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

import java.util.Arrays;

public class MainActivity extends AppCompatActivity{
    ConstraintLayout myLayout;
    AnimationDrawable myDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onClick(View v)
    {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(),Main3Activity.class));

    }
    public void onClickEdit(View v)
    {
        startActivity(new Intent(getApplicationContext(),EditActivity.class));
    }
    public void onClickPlay(View v)
    {
        DATABASEFINAL.readFromFile(getApplicationContext());
        if (DATABASEFINAL.answers.size()==0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

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
/*
package com.example.ajithk14.memoryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v)
    {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(),Main3Activity.class));

    }
}
*/