package com.example.ajithk14.memoryquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ButtonStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_stats);
    }
    public void onClickStatsActivity(View v) {
        startActivity(new Intent(getApplicationContext(),StatsActivity.class));
    }
    public void onClickGraph(View v)
    {
        DATABASEFINAL.readFromFile(getApplicationContext());
        if (DATABASEFINAL.dates.size()==0){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ButtonStatsActivity.this);

            // set title
            alertDialogBuilder.setTitle("SORRY!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("You need to play some games first!")
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
            startActivity(new Intent(getApplicationContext(),GraphActivity.class));}
    }
    public void onClickDataTable(View v)
    {
        DATABASEFINAL.readFromFile(getApplicationContext());
        if (DATABASEFINAL.dates.size()==0){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ButtonStatsActivity.this);

            // set title
            alertDialogBuilder.setTitle("SORRY!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("You need to play some games first!")
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
            startActivity(new Intent(getApplicationContext(),DateTableActivity.class));}
    }
}
