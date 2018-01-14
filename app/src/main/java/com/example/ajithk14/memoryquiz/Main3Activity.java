package com.example.ajithk14.memoryquiz;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    final int REQUEST_CODE_GALLERY=999;
    private Context TheThis;
    byte[] byteArray;
    ByteArrayOutputStream stream;
    public Uri imageUri;
    Bitmap bitmap;
    private Bitmap takenImage;
    static final int REQUEST_IMAGE_CAPTURE=1;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView imageView;
    public static SQLiteHelper sqlitehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeActivities();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqlitehelper = new SQLiteHelper(this, "FacesDB.sqlite",null,1);
        sqlitehelper.queryData("CREATE TABLE IF NOT EXISTS FACE (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOG)");

    }
    public void changeActivities()
    {
        /*
        FileOutputStream out = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String NameOfFile = "JPEG_" + timeStamp + "_";
        try {
            out = openFileOutput(NameOfFile,Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            out.close();
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        String[] x = saveToInternalSorage(takenImage);
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("smooth", x[0]);
        i.putExtra("filename", x[1]);
        startActivity(i);


    }
    private String[] saveToInternalSorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String NameOfFile = "JPEG_" + timeStamp + ".jpg";
        File mypath = new File(directory, NameOfFile);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to
            // the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arr = new String[2];
        arr[0]= directory.getAbsolutePath();arr[1]=NameOfFile;
        return arr;
    }

    public void onClickTakeImage(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);}
    }
    public void onClick(View view)
    {
        ActivityCompat.requestPermissions(Main3Activity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "No permission to access folder", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                takenImage = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(takenImage);
                stream = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byteArray = stream.toByteArray();

            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        else if (requestCode==REQUEST_IMAGE_CAPTURE)
        {
            if (resultCode== RESULT_CANCELED)
            {

                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                finish();
            }
            else{
                try{
                takenImage = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                String imageurl = getRealPathFromURI(imageUri);
                imageView.setImageBitmap(takenImage);
                stream = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byteArray = stream.toByteArray();}
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Storage");
            builder.setMessage("Do you want this picture to be written to your device's storage? (" +
                    "this message can be turned off in settings)");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    MediaStore.Images.Media.insertImage(getContentResolver(), takenImage, "Img1" , "Taken");

                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();}
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
