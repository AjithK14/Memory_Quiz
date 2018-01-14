
package com.example.ajithk14.memoryquiz;

        import android.content.Context;
        import android.content.ContextWrapper;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.widget.ImageView;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;


public class Main2Activity extends AppCompatActivity {

    DrawImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent receivedIntent = getIntent();
        String select = receivedIntent.getStringExtra("smooth");
        String name = receivedIntent.getStringExtra("filename");
        loadImageFromStorage(select,name);


    }

    private void loadImageFromStorage(String path,String name) {

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(path1, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            imgView= (DrawImageView) findViewById(R.id.myImageDraw);
            imgView.startStuff(b);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
