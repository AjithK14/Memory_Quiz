package com.example.ajithk14.memoryquiz;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Ajithk14 on 1/6/2018.
 */

public class MyDBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME= "FacesStorage.db";
    public static final String TABLE_PRODUCTS="Faces";
    public static final String COLUMN_ID="score";
    public static final String COLUMN_PRODUCTNAME="faceName";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE_TABLE" + TABLE_PRODUCTS + "(" + COLUMN_ID + "PRIMARY IMAGE"
                + COLUMN_PRODUCTNAME + "TEXT" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);

    }
    //add row
    public void addFace(Faces face)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME,face.getScore());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS,null,values);
        db.close();
    }
    //delete from database
    public void delFace(String face)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + face + "\";");
    }
}
