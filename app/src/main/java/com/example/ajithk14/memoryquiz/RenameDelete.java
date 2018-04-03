package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RenameDelete extends AppCompatActivity {

    EditText myName;
    Button delete;
    ImageView person;
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_delete);
        Intent receivedIntent = getIntent();
        delete = (Button) findViewById(R.id.button10);
        myName = (EditText) findViewById(R.id.editText3);
        String select = receivedIntent.getStringExtra("personName");
        String name = receivedIntent.getStringExtra("picName");
        filename=name;
        person = (ImageView) findViewById(R.id.imageView5);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File f = new File(path1, name);
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        person.setImageBitmap(b);
        myName.setText(select);
    }
    public void delete(View v)
    {
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setCancelable(true);
        builder.setTitle("Title");
        builder.setMessage("Message");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DATABASEFINAL.readFromFile(getApplicationContext());
                        int tempo = DATABASEFINAL.faces.indexOf(filename);
                        DATABASEFINAL.answers.remove(tempo);
                        DATABASEFINAL.faces.remove(tempo);
                        DATABASEFINAL.done(getApplicationContext());
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();*/


    }
    public void onDone(View view)
    {
        DATABASEFINAL.readFromFile(getApplicationContext());
        int tempo = DATABASEFINAL.faces.indexOf(filename);
        DATABASEFINAL.answers.set(tempo,myName.getText().toString());
        DATABASEFINAL.done(getApplicationContext());
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    public void onDelete(View view)
    {
        DATABASEFINAL.readFromFile(getApplicationContext());
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deleting Image")
                .setMessage("Are you sure you want to delete this image? Quizzes will no longer contain this image")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int tempo = DATABASEFINAL.faces.indexOf(filename);
                        DATABASEFINAL.answers.remove(tempo);
                        DATABASEFINAL.missed.remove(tempo);
                        DATABASEFINAL.faces.remove(tempo);
                        DATABASEFINAL.done(getApplicationContext());
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }

                })
                .setNegativeButton("No", null)
                .show();
        /*
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        int tempo = DATABASEFINAL.faces.indexOf(filename);
                        DATABASEFINAL.answers.remove(tempo);
                        DATABASEFINAL.faces.remove(tempo);
                        DATABASEFINAL.done(getApplicationContext());
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
        */

    }
}
