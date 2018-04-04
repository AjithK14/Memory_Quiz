package com.example.ajithk14.memoryquiz;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
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
import java.util.Arrays;

/**
 * Created by Ajithk14 on 2/11/2018.
 */

public class DATABASEFINAL {

    static ArrayList<String> faces = new ArrayList<String>();
    static ArrayList<String> answers = new ArrayList<String>();
    static ArrayList<Integer> scores = new ArrayList<>();
    static ArrayList<Integer> missed = new ArrayList<>();
    /*

    MOST IMPORTANT FILE
    CREATES TEXT FILES TO STORE INFORMATION FOR EACH OF THESE ARRAYS
    EVERY TIME THE USER PERFORMS AN ACTION THE VALUES IN THESE ARRAYLISTS AND TEXT FILES ARE UPDATED

    THE DONE METHOD WILL WRITE THE CONTENTS OF THESE ARRAYS TO THEIR RESPECTIVE TEXT FILES AND
    THE READFROMFILEMETHOD WILL FILL UP THESE ARRAYLISTS WITH THE CORRECT CONTENT

     */
    FileInputStream fis;
    final StringBuffer storedString = new StringBuffer();
    public static void done(Context context)
    {

        try {
            File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct.exists())
            {
                direct.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","facesNames.txt");

            FileOutputStream output = new FileOutputStream(file, false);

            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("facesNames.txt", Context.MODE_PRIVATE));
            for (String x: faces) {
                Log.d("face",x);
                output.write((x + '\n').getBytes());
            }
            //outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        try {
            File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct.exists())
            {
                direct.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","actualNames.txt");


            FileOutputStream output2 = new FileOutputStream(file, false);
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("actualNames.txt", Context.MODE_PRIVATE));
            for (String x: answers) {
                Log.d("name",x);
                output2.write((x + '\n').getBytes());
            }
            output2.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        try {
            File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct.exists())
            {
                direct.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","actualScores.txt");

            FileOutputStream output = new FileOutputStream(file, false);

            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("facesNames.txt", Context.MODE_PRIVATE));
            for (int x: scores) {
                //Log.d("face",x);
                output.write((Integer.toString(x) + '\n').getBytes());
            }
            //outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        try {
            File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct.exists())
            {
                direct.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","peopleMissed.txt");

            FileOutputStream output = new FileOutputStream(file, false);

            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("facesNames.txt", Context.MODE_PRIVATE));
            for (int x: missed) {
                //Log.d("face",x);
                output.write((Integer.toString(x) + '\n').getBytes());
            }
            //outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static void readFromFile(Context context) {

        //String ret = "";
        faces = new ArrayList<>();
        answers = new ArrayList<>();
        scores = new ArrayList<>();
        missed = new ArrayList<>();
        try {
            File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct.exists())
            {
                direct.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","facesNames.txt");

            FileInputStream inputStream = new FileInputStream(file);

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

            File direct2 = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct2.exists())
            {
                direct2.mkdirs();
            }
            File file2 = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","actualNames.txt");

            FileInputStream inputStream2 = new FileInputStream(file2);

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2));

            //System.out.println("Reading File line by line using BufferedReader");

            String line2 = reader2.readLine();
            while(line2 != null){
                answers.add(line2);
                Log.d("reading..", line2);
                line2 = reader2.readLine();
            }
            reader2.close();
            inputStream2.close();

            File direct3 = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct3.exists())
            {
                direct3.mkdirs();
            }
            File file3 = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","actualScores.txt");

            FileInputStream inputStream3 = new FileInputStream(file3);

            BufferedReader reader3 = new BufferedReader(new InputStreamReader(inputStream3));

            //System.out.println("Reading File line by line using BufferedReader");

            String line3 = reader3.readLine();
            while(line3 != null){
                scores.add(Integer.parseInt(line3.replace("\n","")));
                Log.d("reading..", line3);
                line3 = reader3.readLine();
            }
            reader3.close();
            inputStream3.close();

            File direct4 = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo");
            if (!direct4.exists())
            {
                direct4.mkdirs();
            }
            File file4 = new File(Environment.getExternalStorageDirectory()+File.separator+"MemoryQuizInfo","peopleMissed.txt");

            FileInputStream inputStream4 = new FileInputStream(file4);

            BufferedReader reader4 = new BufferedReader(new InputStreamReader(inputStream4));

            //System.out.println("Reading File line by line using BufferedReader");

            String line4 = reader4.readLine();
            while(line4 != null){
                missed.add(Integer.parseInt(line4.replace("\n","")));
                Log.d("reading..", line4);
                line4 = reader4.readLine();
            }
            reader4.close();
            inputStream4.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        Log.d("stuff", Arrays.toString(DATABASEFINAL.faces.toArray()));
        Log.d("stuff", Arrays.toString(DATABASEFINAL.answers.toArray()));

        //return ret;
    }
}
