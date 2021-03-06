package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    ListView lst;
    private String[] names;
    private String[] facePics;
    Bitmap b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        DATABASEFINAL.readFromFile(getApplicationContext());
        names= DATABASEFINAL.answers.toArray(new String[0]);
        facePics= DATABASEFINAL.faces.toArray(new String[0]);
        lst = (ListView)findViewById(R.id.listview);
        CustomAdapter cust = new CustomAdapter();
        lst.setAdapter(cust);
    }
    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return names.length;
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
            ImageView imw;
            tvw1=(TextView)view.findViewById(R.id.textView);

            imw = (ImageView) view.findViewById(R.id.imageView6);
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(path1, facePics[i]);
            final String pickName = facePics[i];
            Bitmap b = null;
            try {
                b = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
// original measurements
            int origWidth = b.getWidth();
            int origHeight = b.getHeight();

            final int destWidth = 350;//or the width you need
            boolean diff = false;
            if(origWidth > destWidth){
                diff=true;
                // picture is wider than we want it, we calculate its target height
                int destHeight = destWidth;//origHeight/( origWidth / destWidth ) ;
                // we create an scaled bitmap so it reduces the image, not just trim it
                b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);}
            if (diff){
                imw.setImageBitmap(b2);}
                else
            {
                imw.setImageBitmap(b);
            }
            //imw.setImageBitmap(b);
            tvw1.setText(names[i]);
            final String tv = (String) tvw1.getText();
            Button bt9 = (Button)view.findViewById(R.id.button9);
            bt9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(StatsActivity.this, RenameDelete.class);
                    i.putExtra("personName",tv);
                    i.putExtra("picName", pickName);
                    startActivity(i);
                }
            });
            return view;
        }
    }
}
