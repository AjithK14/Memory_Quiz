package com.example.ajithk14.memoryquiz;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ajithk14 on 2/12/2018.
 */

public class CustomListView extends ArrayAdapter<String>{
    private ArrayList<String> names;
    private ArrayList<String> facePics;
    private Activity context;
    public CustomListView(Activity context)
    {
        super(context,R.layout.listview_layout);
        this.context=context;
        DATABASEFINAL.readFromFile(getContext());
         names= DATABASEFINAL.answers;
         facePics= DATABASEFINAL.faces;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder=null;
        if (r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout,parent,false);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) r.getTag();
        }
        ContextWrapper cw = new ContextWrapper(this.context);
        File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File f = new File(path1, facePics.get((position)));
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        viewHolder.imw.setImageBitmap(b);
        viewHolder.tvw1.setText(names.get(position));
        return r;
    }
    class ViewHolder
    {
        TextView tvw1;
        ImageView imw;
        ViewHolder(View v)
        {
            tvw1=(TextView)v.findViewById(R.id.textView);
            imw = (ImageView) v.findViewById(R.id.imageView6);
        }
    }
}
