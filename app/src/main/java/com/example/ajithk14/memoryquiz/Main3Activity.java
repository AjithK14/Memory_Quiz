package com.example.ajithk14.memoryquiz;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.crop.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    final int REQUEST_CODE_GALLERY=999;
    private Context TheThis;
    byte[] byteArray;
    ByteArrayOutputStream stream;
    Intent CamIntent,GalIntent,CropIntent;
    public Uri imageUri;
    File file;

    private ProgressBar progressBar;
    private TextView loadingText;
    private int progStatus=0;
    private Handler mhand = new Handler();

    final int CAMERA_CAPTURE = 1;
    Uri uri;
    Bitmap bitmap;
    public Uri picUri;
    private Bitmap takenImage;
    public String cameraFileName;
    public Bitmap THEbitmap;
    final int RequestPermissionCode=1;
    static final int REQUEST_IMAGE_CAPTURE=1;
    static final int REQUEST_TAKE_PHOTO = 1;
    final int REQUEST_EXTERNAL_STORAGE=4;
    ImageView imageView;
    final int CROP_PIC=2;
    CropImageView mCropImageView;
    public static SQLiteHelper sqlitehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCropImageView = new CropImageView(getApplicationContext());
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        loadingText=(TextView)findViewById(R.id.loadingComplete);
        progressBar.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);
        imageView = (ImageView) findViewById(R.id.imageView);
        THEbitmap=null;
        //int permis = ContextCompat.checkSelfPermission(Main3Activity.this,
          //      Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Main3Activity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Main3Activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

        }
        int permissionCheck = ContextCompat.checkSelfPermission(Main3Activity.this,Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
        {
            RequestRuntimePermission();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getDrawable()==null)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Main3Activity.this);

                    // set title
                    alertDialogBuilder.setTitle("SORRY!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("You need to enter an image first!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);

                    showProgress();
                //changeActivities();
                }
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //sqlitehelper = new SQLiteHelper(this, "FacesDB.sqlite",null,1);
        //sqlitehelper.queryData("CREATE TABLE IF NOT EXISTS FACE (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOG)");

    }
    private void GalleryOpen() {
        GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),2);
    }

    private void CameraOpen() {
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*file = new File(Environment.getExternalStorageDirectory(),
                "file"+String.valueOf(System.currentTimeMillis())+".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        CamIntent.putExtra("return-data",true);*/
        startActivityForResult(CamIntent,CAMERA_CAPTURE);
    }

    public void showProgress()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progStatus < 100)
                {
                    progStatus++;
                    android.os.SystemClock.sleep(50);
                    mhand.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progStatus);
                        }
                    });
                }
                mhand.post(new Runnable() {
                    @Override
                    public void run() {
                        loadingText.setVisibility(View.VISIBLE);
                        changeActivities();
                    }
                });
            }
        }).start();
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

            startActivityForResult(CropIntent,CROP_PIC);
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
        String[] x = saveToInternalStorage(((BitmapDrawable)imageView.getDrawable()).getBitmap());
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraFileName = Environment.getExternalStorageDirectory() +""+ System.currentTimeMillis();
        File file = new File(""+Environment.getExternalStorageDirectory());
        if(!file.exists()){
            file.mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraFileName)));
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CAMERA_CAPTURE);

    }
    public void onClick(View view)
    {
        Crop.pickImage(this);
        //GalleryOpen();
    }

    private Intent getCropIntent(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        return intent;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        {
            if (requestCode==Crop.REQUEST_PICK)
            {
                Uri src_uri = data.getData();
                Uri dest = Uri.fromFile(new File(getCacheDir(),"cropped"));
                Crop.of(src_uri,dest).asSquare().start(this);
                imageView.setImageURI(Crop.getOutput(data));
            }
            else if (requestCode==82)
            {
                THEbitmap = getBitmapFromData(data);
                imageView.setImageBitmap(THEbitmap);

            }
            else if (requestCode == CAMERA_CAPTURE) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                Uri uri = Uri.fromFile(new File(cameraFileName));
                intent.setDataAndType(uri, "image/*");
                startActivityForResult(getCropIntent(intent), 82);
                // get the Uri for the captured image
                //picUri = data.getData();
                //Log.d("gg","WE ARE USING THE CAMERA WOOT WOOT");
                /*

                //mCropImageView.setImageURI
                mCropImageView.setImageURI(getPickImageResultUri(data));
                Bitmap cropped =  mCropImageView.getCroppedImage(500, 500);
                if (cropped != null)
                    imageView.setImageBitmap(cropped);*/
                //performCrop();
            }
            else if (requestCode==Crop.REQUEST_CROP)
            {
                handle_crop(resultCode,data);
            }
            else if (data != null && (requestCode == CROP_PIC || requestCode==79)) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap

                THEbitmap = extras.getParcelable("data");
                imageView.setImageBitmap(THEbitmap);
            }
        }
    }
    public static Bitmap getBitmapFromData(Intent data) {
        Bitmap photo = null;
        Uri photoUri = data.getData();
        if (photoUri != null) {
            photo = BitmapFactory.decodeFile(photoUri.getPath());
        }
        if (photo == null) {
            Bundle extra = data.getExtras();
            if (extra != null) {
                photo = (Bitmap) extra.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }
        }

        return photo;
    }
    public void handle_crop(int code, Intent result)
    {
        if (code==RESULT_OK)
        {
            imageView.setImageURI(Crop.getOutput(result));
            THEbitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        }
        else if (code==Crop.RESULT_ERROR)
        {

        }
    }

        /*
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
    */
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
    private void performCrop() {
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
            startActivityForResult(cropIntent,79);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
            //return 0;
        }

    }
    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }
    public Uri getPickImageResultUri(Intent  data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null  && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ?  getCaptureImageOutputUri() : data.getData();
    }


}
