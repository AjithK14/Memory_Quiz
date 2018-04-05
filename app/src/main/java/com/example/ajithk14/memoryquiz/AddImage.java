
package com.example.ajithk14.memoryquiz;

        import android.content.Context;
        import android.content.ContextWrapper;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.AnimationDrawable;

        import com.soundcloud.android.crop.Crop;

        import android.net.Uri;
        import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.text.SimpleDateFormat;
        import java.util.Arrays;
        import java.util.Date;


public class AddImage extends AppCompatActivity {

    public Bitmap b;
    Button formDone;
    Button discard;
    public boolean complete;
    Button rectsCreated;
    public EditText et;
    ConstraintLayout myLayout;
    AnimationDrawable myDraw;
    public ImageView faceTemp;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addimage);
        Intent receivedIntent = getIntent();
        myLayout = (ConstraintLayout)findViewById(R.id.myLayout);
        myDraw=(AnimationDrawable)myLayout.getBackground();
        myDraw.setEnterFadeDuration(4500);
        myDraw.setExitFadeDuration(4500);
        myDraw.start();


        String select = receivedIntent.getStringExtra("smooth");
        name = receivedIntent.getStringExtra("filename");
        //rectsCreated = (Button)findViewById(R.id.button7);
        complete=false;
        faceTemp = (ImageView) findViewById(R.id.imageView3);
        formDone = (Button)findViewById(R.id.button7);
        et = (EditText)findViewById(R.id.editText2);
        discard = (Button)findViewById(R.id.button8);
        loadImageFromStorage(select,name);

        /*
        faceTemp.setVisibility(View.INVISIBLE);
        formDone.setVisibility(View.INVISIBLE);
        et.setVisibility(View.INVISIBLE);
        discard.setVisibility(View.INVISIBLE);
        */

    }
    public void formComplete(View v)
    {
        String  str=et.getText().toString();

        if(str.equalsIgnoreCase(""))
        {
            et.setHint("please enter name");//it gives user to hint
            et.setError("please enter name");//it gives user to info message //use any one //
            Toast.makeText(AddImage.this,"Name required!", Toast.LENGTH_SHORT).show();
        }
        else {
            complete = true;
            if (DATABASEFINAL.answers.size() == 0) {
                Log.d("debug", "reading...");
                DATABASEFINAL.readFromFile(getApplicationContext());
            }
            DATABASEFINAL.faces.add(name);
            DATABASEFINAL.scores.add(0);
            DATABASEFINAL.favoriteColor.add("");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    AddImage.this);

            // set title
            alertDialogBuilder.setTitle("Done!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Success!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity




                            //Set 'answers' to face name and all other fields to ""
                            DATABASEFINAL.answers.add(et.getText().toString());





                            DATABASEFINAL.done(getApplicationContext());
                            Log.d("stuff", Arrays.toString(DATABASEFINAL.answers.toArray()));
                            Log.d("stuff", Arrays.toString(DATABASEFINAL.faces.toArray()));
                            Intent i = new Intent(AddImage.this, RenameDelete.class);
                            i.putExtra("personName",et.getText().toString().replace("\n",""));
                            i.putExtra("picName", name);
                            startActivity(i);
                            //startActivity(new Intent(getApplicationContext(), RenameDelete.class));
                            dialog.dismiss();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();




            //Intent in=new Intent(getApplicationContext(),second.class);
            //startActivity(in);

        }
    }
    public void deleteFace(View v)
    {
        if (true)
        {
            startActivity(new Intent(getApplicationContext(),StartScreen.class));
            complete = true;
            Log.d("stuff", Arrays.toString(DATABASEFINAL.answers.toArray()));
            //Intent in=new Intent(getApplicationContext(),second.class);
            //startActivity(in);
        }

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
    public void weOut(View v)
    {
        /*
        LinkedList<FaceRectangle> list = imgView.done();
        faceTemp.setVisibility(View.VISIBLE);
        et.setVisibility(View.VISIBLE);
        discard.setVisibility(View.VISIBLE);
        formDone.setVisibility(View.VISIBLE);
        imgView.setVisibility(View.INVISIBLE);
        rectsCreated.setVisibility(View.INVISIBLE);
        findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        for (FaceRectangle tempoRect: list)
        {
            //createBitmap(Bitmap,x,y,width,height)
            int[] posXY = new int[2];
            Rect r = tempoRect.locateView(findViewById(R.id.myImageDraw));
            float xPos=r.right;
            float yPos=(r.bottom);
            //faceTemp.getLocationOnScreen();
            int width = (int) (tempoRect.dimens.right-tempoRect.dimens.left);
            int height = (int) (tempoRect.dimens.bottom-tempoRect.dimens.top);
            Log.d("Parameters", ""+(int)tempoRect.dimens.left + " " + (int)tempoRect.dimens.top);
            Log.d("MoreParameters", ""+(int)tempoRect.dimens.right + " " + (int)tempoRect.dimens.bottom);
            Log.d("Dimensions", ""+(int)xPos + " " + (int)yPos);
            //Log.d("bitmapSizze", ""+b.width + " " + b.getScaledHeight());

            //int x = (int)((((int)tempoRect.dimens.left) + ((int)tempoRect.dimens.right))/2);
            //int y = (int)((((int)tempoRect.dimens.top) + ((int)tempoRect.dimens.bottom))/2);
            int x = ((int)tempoRect.dimens.left);
            int y = ((int)tempoRect.dimens.top);

            Bitmap croppedBitmap = Bitmap.createBitmap(b,x,y, width,height);
            faceTemp.setImageBitmap(croppedBitmap);
            while (!complete) {
            }
            complete=false;



        }
        finish();
        */
    }
    private void loadImageFromStorage(String path,String name) {

        try {

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(path1, name);
            /*Uri tempoUri = Uri.fromFile(f);
            Intent imageDownload = new Intent(Intent.ACTION_PICK,tempoUri);
            imageDownload.putExtra("crop","true");
            imageDownload.putExtra("aspectX",1);
            imageDownload.putExtra("aspectY",1);
            imageDownload.putExtra("outputX",200);
            imageDownload.putExtra("outputY",200);
            imageDownload.putExtra("return-data",true);
            startActivityForResult(imageDownload,2);*/

            //faceTemp.setImageDrawable(null);
            //Crop.pickImage(this);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            faceTemp.setImageBitmap(b);
            /*
            imgView= (DrawImageView) findViewById(R.id.myImageDraw);
            imgView.startStuff(b);
            */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        /*
        int id = item.getItemId();
        if (id == R.id.action_select)
        {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode,resultCode,result);
        if (requestCode==2 && resultCode == RESULT_OK && result != null)
        {
            Bundle extras = result.getExtras();
            Bitmap image = extras.getParcelable("data");
            faceTemp.setImageBitmap(image);
            //beginCrop(result.getData());
        }
        /*
        else if (requestCode == Crop.REQUEST_CROP)
        {
            handleCrop(resultCode,result);
        }
        */
    }

    private void beginCrop(Uri source)
    {
        Uri destination = Uri.fromFile(new File(getCacheDir(),"cropped"));
        Crop.of(source,destination).asSquare().start(this);
    }
    private void handleCrop(int resultCode, Intent result)
    {
        if (resultCode == RESULT_OK)
        {
            faceTemp.setImageURI(Crop.getOutput(result));
        }
        else if (resultCode==Crop.RESULT_ERROR)
        {
            Toast.makeText(this,Crop.getError(result).getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
/*
Changes made:
If u want to go back to rectangle DrawImage view then modify weOut
also modify loadImageFromStorage
only use this class for cropped images
 */