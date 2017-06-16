package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    Intent intent;
    int hits,beats;
    EditText delayView;
    /*UI Event handler*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        /*Fetch values passed from previous activity*/
        Intent myIntent = getIntent(); // this is just for example purpose
        Bundle extras = getIntent().getExtras();
        ArrayList<Integer> delay = new ArrayList<Integer>();
        delay=extras.getIntegerArrayList("delay");
        hits=extras.getInt("hits");
        beats=extras.getInt("beats");

        delayView = (EditText) findViewById(R.id.editDelay);

        String delayStr = new String();
        /*Loop on the number of hits*/
        for (int i=0;i<hits;i++)
        {
            /*Prints the delay in log*/
            Log.v("X1", "Delay=" + delay.get(i));
            /*Concatenate all delay to output on the screen*/
            delayStr.concat(Integer.toString(delay.get(i)));
            delayStr.concat("\n");
        }
        delayView.setText(delayStr);
    }
}
