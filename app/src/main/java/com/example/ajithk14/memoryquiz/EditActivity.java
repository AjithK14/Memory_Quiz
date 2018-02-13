package com.example.ajithk14.memoryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    ListView lst;
    ArrayList<String> names = DATABASEFINAL.answers;
    ArrayList<String> facePics = DATABASEFINAL.faces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        lst = (ListView)findViewById(R.id.listview);
        CustomListView cust = new CustomListView(this);
        lst.setAdapter(cust);
    }
}
