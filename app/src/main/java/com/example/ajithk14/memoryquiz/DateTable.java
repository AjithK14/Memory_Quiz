package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DateTable extends AppCompatActivity {

    ListView lst;
    private String[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetable);
        DATABASEFINAL.readFromFile(getApplicationContext());
        lst = (ListView)findViewById(R.id.listview);
        DateTable.CustomAdapter cust = new DateTable.CustomAdapter();
        lst.setAdapter(cust);
    }
    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return DATABASEFINAL.dates.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
        public int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and width
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);

                // Choose the smallest ratio as inSampleSize value, this will guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }

            return inSampleSize;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listview_layout,null);
            TextView tvw1;
            TextView tvw2;

            /*
            for every row in the edit activity list get the image through facePics array
             */
            ImageView imw;
            tvw1=(TextView)view.findViewById(R.id.textView);
            tvw2=(TextView)view.findViewById(R.id.textView2);
            TextView tvw3=(TextView)view.findViewById(R.id.textView3);
            tvw3.setVisibility(View.VISIBLE);
            imw = (ImageView) view.findViewById(R.id.imageView6);
            imw.setVisibility(View.GONE);
            tvw2.setVisibility(View.GONE);



            String[] first = DATABASEFINAL.dates.get(i).split(" ");
            tvw1.setText(first[0]);
            tvw3.setText(first[1]);
            //final String tv = (String) tvw1.getText();
            Button bt9 = (Button)view.findViewById(R.id.button9);
            bt9.setVisibility(View.GONE);
            /*
            bt9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(StatsActivity.this, RenameDelete.class);
                    i.putExtra("personName",first.replace("\n",""));
                    i.putExtra("picName", pickName);
                    startActivity(i);
                }
            });
            */
            return view;
        }
    }
}
