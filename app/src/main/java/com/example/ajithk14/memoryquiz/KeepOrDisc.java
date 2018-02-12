package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class KeepOrDisc extends AppCompatActivity {
    Bitmap b;
    public EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_or_disc);
        Intent receivedIntent = getIntent();
        et = (EditText)findViewById(R.id.editText);
        String select = receivedIntent.getStringExtra("smooth");
        String name = receivedIntent.getStringExtra("filename");

        Bitmap recievedPic = loadImageFromStorage(select,name);

    }
    public void formDone(View v)
    {
            String  str=et.getText().toString();

            if(str.equalsIgnoreCase(""))
            {
                et.setHint("please enter name");//it gives user to hint
                et.setError("please enter name");//it gives user to info message //use any one //
            }
            else
            {
                //Intent in=new Intent(getApplicationContext(),second.class);
                //startActivity(in);
            }

    }
    public Bitmap loadImageFromStorage(String path,String name) {

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(path1, name);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
