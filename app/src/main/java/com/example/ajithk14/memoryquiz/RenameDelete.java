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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RenameDelete extends AppCompatActivity {

    EditText myName, myFavoriteColor, myFavoriteFood;
    Button delete;
    ImageView person;
    String filename;
    String editing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_delete);
        DATABASEFINAL.readFromFile(getApplicationContext());
        Intent receivedIntent = getIntent();
        delete = (Button) findViewById(R.id.button10);
        myName = (EditText) findViewById(R.id.editText3);
        myFavoriteColor = (EditText) findViewById(R.id.colorField);
        myFavoriteFood = (EditText) findViewById(R.id.foodField);
        String name = receivedIntent.getStringExtra("filename");
        editing = receivedIntent.getStringExtra("editing");
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
        DATABASEFINAL.readFromFile(getApplicationContext());
        if (editing.equals("EDIT"))
        {
            int tempo = DATABASEFINAL.faces.indexOf(name);
            myName.setText(DATABASEFINAL.answers.get(tempo));
            myFavoriteColor.setText(DATABASEFINAL.favoriteColor.get(tempo));
            myFavoriteFood.setText(DATABASEFINAL.favoriteFood.get(tempo));
        }

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
                        startActivity(new Intent(getApplicationContext(),StartScreen.class));

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
    public void onCancel(View view)
    {
        startActivity(new Intent(getApplicationContext(),StartScreen.class));
    }
    public void onDone(View view)
    {
        String  str=myName.getText().toString();

        if(str.equalsIgnoreCase(""))
        {
            myName.setHint("please enter name");//it gives user to hint
            myName.setError("please enter name");//it gives user to info message //use any one //
            Toast.makeText(RenameDelete.this,"Name required!", Toast.LENGTH_SHORT).show();
        }
        else if (editing.equals("EDIT"))
        {
            DATABASEFINAL.readFromFile(getApplicationContext());
            int tempo = DATABASEFINAL.faces.indexOf(filename);
            DATABASEFINAL.answers.set(tempo,myName.getText().toString());
            DATABASEFINAL.favoriteColor.set(tempo,myFavoriteColor.getText().toString());
            DATABASEFINAL.favoriteFood.set(tempo,myFavoriteFood.getText().toString());
            DATABASEFINAL.done(getApplicationContext());
            startActivity(new Intent(getApplicationContext(),StartScreen.class));
        }
        else{
        DATABASEFINAL.readFromFile(getApplicationContext());
        DATABASEFINAL.faces.add(filename);
        DATABASEFINAL.answers.add(myName.getText().toString().equals("") ? "_" : myName.getText().toString());
        DATABASEFINAL.favoriteColor.add(myFavoriteColor.getText().toString().equals("") ? "_" : myFavoriteColor.getText().toString());
        DATABASEFINAL.missed.add(0);
        DATABASEFINAL.scores.add(0);
        DATABASEFINAL.favoriteFood.add(myFavoriteFood.getText().toString().equals("") ? "_" : myFavoriteFood.getText().toString());
        DATABASEFINAL.done(getApplicationContext());
        startActivity(new Intent(getApplicationContext(),StartScreen.class));}
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
                        DATABASEFINAL.scores.remove(tempo);
                        DATABASEFINAL.faces.remove(tempo);
                        DATABASEFINAL.missed.remove(tempo);
                        DATABASEFINAL.favoriteColor.remove(tempo);
                        DATABASEFINAL.favoriteFood.remove(tempo);
                        DATABASEFINAL.done(getApplicationContext());
                        startActivity(new Intent(getApplicationContext(),StartScreen.class));
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
                        startActivity(new Intent(getApplicationContext(),StartScreen.class));
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
