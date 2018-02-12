package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Ajithk14 on 2/11/2018.
 */

public class DATABASEFINAL {

    static ArrayList<String> faces = new ArrayList<String>();
    static ArrayList<String> answers = new ArrayList<String>();
    FileInputStream fis;
    final StringBuffer storedString = new StringBuffer();
    public static void done(Context context)
    {
        try {
            FileOutputStream output = new FileOutputStream("facesNames.txt", false);
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("facesNames.txt", Context.MODE_PRIVATE));
            for (String x: faces) {
                output.write((x + '\n').getBytes());
            }
            //outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        try {
            FileOutputStream output2 = new FileOutputStream("actualNames.txt", false);
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("actualNames.txt", Context.MODE_PRIVATE));
            for (String x: answers) {
                output2.write((x + '\n').getBytes());
            }
            output2.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static void readFromFile(Context context) {

        //String ret = "";

        try {
            FileInputStream inputStream = new FileInputStream("facesNames.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //System.out.println("Reading File line by line using BufferedReader");

            String line = reader.readLine();
            while(line != null){
                faces.add(line);
                Log.d("reading..", line);
                line = reader.readLine();
            }
            reader.close();
            inputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        try {
            /*
            InputStream inputStream = context.openFileInput("actualNames.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    answers.add(receiveString);
                }

                inputStream.close();
                //ret = stringBuilder.toString();
            }*/
            FileInputStream inputStream = new FileInputStream("actualNames.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //System.out.println("Reading File line by line using BufferedReader");

            String line = reader.readLine();
            while(line != null){
                answers.add(line);
                Log.d("reading..", line);
                line = reader.readLine();
            }
            reader.close();
            inputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        //return ret;
    }
}
