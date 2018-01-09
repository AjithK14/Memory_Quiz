package com.example.ajithk14.memoryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
public class Main2Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void onClick(View v)
    {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
}
