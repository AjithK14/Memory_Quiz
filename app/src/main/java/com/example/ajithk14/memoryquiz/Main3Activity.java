package com.example.ajithk14.memoryquiz;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class Main3Activity extends AppCompatActivity {

    EditText ename;
    Button btnChoose;
    ImageView imgView;
    final int REQUEST_CODE_GALLERY=999;

    public static SQLiteHelper sqlitehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqlitehelper = new SQLiteHelper(this, "FacesDB.sqlite",null,1);
        sqlitehelper.queryData("CREATE TABLE IF NOT EXISTS FACE (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOG)");

    }
    public void onClick(View view)
    {
        ActivityCompat.requestPermissions(Main3Activity.this,
                new String[]{READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
    }

}
