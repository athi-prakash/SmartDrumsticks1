package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DisplayDelayResult extends AppCompatActivity {

    Intent intent;
    int hits, beats;
    TextView delayView;

    /*UI Event handler*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_delay_result);

        /*Fetch values passed from previous activity*/
        Bundle extras = getIntent().getExtras();
        ArrayList<Integer> delay = new ArrayList<Integer>();
        delay = extras.getIntegerArrayList("delay");
        hits = extras.getInt("hits");
        beats = extras.getInt("beats");

        double maxDelay = Collections.max(delay);
        double minDelay = Collections.min(delay);
        double sumDelay = 0, avgDelay;
        for (int i = 0; i < delay.size(); i++){
            sumDelay += delay.get(i);
        }
        avgDelay = sumDelay/delay.size();

        delayView = (TextView) findViewById(R.id.editDelay);
        delayView.setText("Delays\n");
        delayView.append("Max Delay:" + (Double.toString(maxDelay)) + "\n");
        delayView.append("Min Delay:" + (Double.toString(minDelay)) + "\n");
        delayView.append("Average Delay:" + (Double.toString(avgDelay)) + "\n");
        delayView.append("Details:\n");
        String delayStr = new String();
        /*Loop on the number of hits*/
//        for (int i = 0; i < hits; i++) {
            /*Prints the delay in log*/
//            Log.v("X1", "Delay=" + delay.get(i));
            /*Concatenate all delay to output on the screen*/
//            delayView.append(Integer.toString(delay.get(i)) + "\n");
//        }
    }
}
