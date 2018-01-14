package com.example.ajithk14.memoryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Faces> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


    }
}
