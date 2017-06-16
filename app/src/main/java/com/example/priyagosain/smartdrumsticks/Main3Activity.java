package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    Intent intent;
    int hits,beats;
    TextView delayView;
    /*UI Event handler*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        /*Fetch values passed from previous activity*/
        Bundle extras = getIntent().getExtras();
        ArrayList<Integer> delay = new ArrayList<Integer>();
        delay=extras.getIntegerArrayList("delay");
        hits=extras.getInt("hits");
        beats=extras.getInt("beats");

        delayView = (TextView) findViewById(R.id.editDelay);

        String delayStr = new String();
        /*Loop on the number of hits*/
        for (int i=0;i<hits;i++)
        {
            /*Prints the delay in log*/
            Log.v("X1", "Delay=" + delay.get(i));
            /*Concatenate all delay to output on the screen*/
            delayView.append(Integer.toString(delay.get(i))+'\n');
        }
    }
}
