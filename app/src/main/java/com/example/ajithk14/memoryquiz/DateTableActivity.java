package com.example.ajithk14.memoryquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class DateTableActivity extends AppCompatActivity {

    String[] spaceProbeHeaders={"Date","Score"};
    String[][] spaceProbes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_table);

        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(2);
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));

        //POPULATE
        populateData();

        //ADAPTERS
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbes));

        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(DateTableActivity.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void populateData()
    {
        DATABASEFINAL.readFromFile(getApplicationContext());
        // spaceProbes= new String[][]{{}};
        spaceProbes= new String[DATABASEFINAL.dates.size()][2];

        for (int i=0;i<DATABASEFINAL.dates.size();i++) {

            String[] s=DATABASEFINAL.dates.get(i).split(" ");

            spaceProbes[i][0]=s[0];
            spaceProbes[i][1]=s[1];

        }
    }

}

