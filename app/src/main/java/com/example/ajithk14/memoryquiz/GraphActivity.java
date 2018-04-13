package com.example.ajithk14.memoryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        double x, y;
        x=0;
        GraphView graph = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Change over time");
        series1=new LineGraphSeries<>();

        DATABASEFINAL.readFromFile(getApplicationContext());
        for (int i = 0; i < DATABASEFINAL.dates.size(); i++)
        {
            String[] s=DATABASEFINAL.dates.get(i).split(" ");
            x+=1.0;
            series1.appendData(new DataPoint(x,Integer.parseInt(s[1])),true, 100);
        }
        graph.addSeries(series1);
    }
}
