package com.example.rtauro.gesturerecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readFromFile("/storage/emulated/0/data.json");
    }

    public void readFromFile(String filename)
    {
        String samples[]={"five","four","three","two","one"};
        File file =new File(filename);
        StringBuilder text=new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        for(int i=0;i<samples.length;i++)
            parseJson(text.toString(),samples[i]);
        Log.d("Riana text",text+"");



    }

    public void parseJson(String text,String number){

        try {
            JSONObject reader = new JSONObject(text);
            JSONObject gesture_five  = reader.getJSONObject("gesture_five");
            JSONObject sample_five=gesture_five.getJSONObject("sample_"+number);
            JSONArray array=sample_five.getJSONObject("accelerometer").getJSONArray("data");
            List<Integer> timestamp=new ArrayList<>();
            List<Double> x_values=new ArrayList<>();
            List<Double> y_values=new ArrayList<>();
            List<Double> z_values=new ArrayList<>();
            int count=0;
            for(int i=0;i<array.length();i++)
            {
                JSONObject obj=array.getJSONObject(i);
                x_values.add(obj.getDouble("x"));
                y_values.add(obj.getDouble("y"));
                z_values.add(obj.getDouble("z"));
                timestamp.add(obj.getInt("timestamp"));
                count++;

            }
            Log.d("riana","X:"+ x_values+" ---Y : "+y_values+"-- Z "+z_values);
            Log.d("riana",count+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
