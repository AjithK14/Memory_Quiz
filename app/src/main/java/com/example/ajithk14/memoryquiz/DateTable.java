package com.example.ajithk14.memoryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DateTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetable);
        init();
    }
    public void init(){
        TableLayout ll = (TableLayout) findViewById(R.id.displayLinear);


        for (int i = 0; i <2; i++) {
            /*
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            checkBox = new CheckBox(this);
            tv = new TextView(this);
            addBtn = new ImageButton(this);
            addBtn.setImageResource(R.drawable.add);
            minusBtn = new ImageButton(this);
            minusBtn.setImageResource(R.drawable.minus);
            qty = new TextView(this);
            checkBox.setText("hello");
            qty.setText("10");
            row.addView(checkBox);
            row.addView(minusBtn);
            row.addView(qty);
            row.addView(addBtn);
            ll.addView(row,i);
            */
        }
    }
}
