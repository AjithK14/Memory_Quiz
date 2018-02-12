package com.example.ajithk14.memoryquiz;

import android.Manifest;
import android.content.ActivityNotFoundException;
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

import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    Intent CamIntent,GalIntent,CropIntent;
    public Uri imageUri;
    File file;
    Uri uri;
    Bitmap bitmap;
    public Uri picUri;
    private Bitmap takenImage;
    public Bitmap THEbitmap;
    final int RequestPermissionCode=1;
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
        THEbitmap=null;
        int permissionCheck = ContextCompat.checkSelfPermission(Main3Activity.this,Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
        {
            RequestRuntimePermission();
        }


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
        //sqlitehelper = new SQLiteHelper(this, "FacesDB.sqlite",null,1);
        //sqlitehelper.queryData("CREATE TABLE IF NOT EXISTS FACE (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOG)");

    }
    @Override
    protected void onPause() {
        DATABASEFINAL.done(getApplicationContext());
        super.onPause();
    }
    private void GalleryOpen() {
        GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),2);
    }

    private void CameraOpen() {
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(),
                "file"+String.valueOf(System.currentTimeMillis())+".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        CamIntent.putExtra("return-data",true);
        startActivityForResult(CamIntent,0);
    }


    private void CropImage() {

        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri,"image/*");

            CropIntent.putExtra("crop","true");
            CropIntent.putExtra("outputX",180);
            CropIntent.putExtra("outputY",180);
            CropIntent.putExtra("aspectX",3);
            CropIntent.putExtra("aspectY",4);
            CropIntent.putExtra("scaleUpIfNeeded",true);
            CropIntent.putExtra("return-data",true);

            startActivityForResult(CropIntent,1);
        }
        catch (ActivityNotFoundException ex)
        {

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case RequestPermissionCode:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Permission Canceled",Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
    private void RequestRuntimePermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(Main3Activity.this,Manifest.permission.CAMERA))
        {
            Toast.makeText(this,"suhhh",Toast.LENGTH_SHORT).show();

        }
        else
        {
            ActivityCompat.requestPermissions(Main3Activity.this,new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_CAPTURE);
        }
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
        String[] x = saveToInternalStorage(THEbitmap);
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("smooth", x[0]);
        i.putExtra("filename", x[1]);
        startActivity(i);


    }
    private String[] saveToInternalStorage(Bitmap bitmapImage) {
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
        CameraOpen();
    }
    public void onClick(View view)
    {
        GalleryOpen();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK)
            CropImage();
        else if(requestCode == 2)
        {
            if(data != null)
            {
                uri = data.getData();
                CropImage();
            }
        }
        else if (requestCode == 1)
        {
            if(data != null)
            {
                Bundle bundle = data.getExtras();
                THEbitmap = bundle.getParcelable("data");
                imageView.setImageBitmap(THEbitmap);
                //String[] THEarr = saveToInternalStorage(THEbitmap);
            }
        }
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data != null)
        {
            //Uri uri = data.getData();
            if (true){
                Bundle extras = data.getExtras();
                takenImage = extras.getParcelable("data");
                imageView.setImageBitmap(takenImage);
                /*
                InputStream inputStream = getContentResolver().openInputStream(uri);
                takenImage = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(takenImage);
                stream = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byteArray = stream.toByteArray();
                */
    /*
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
                    /*
                takenImage = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                String imageurl = getRealPathFromURI(imageUri);
                imageView.setImageBitmap(takenImage);
                stream = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byteArray = stream.toByteArray();*/
    /*
                    picUri = data.getData();
                    int done = performCrop();
                    if (done == 1)
                    {
                        Bundle extras = data.getExtras();
                        // get the cropped bitmap
                        takenImage = extras.getParcelable("data");
                        imageView.setImageBitmap(takenImage);
                    }
                }

                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

    }*/
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
    private int performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 2);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            return 1;
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

    }
    protected void onDestroy() {
        super.onDestroy();
        DATABASEFINAL.done(getApplicationContext());
        Log.d("out", "WE OUT");
    }

}
