package com.example.priyagosain.smartdrumsticks;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class PlayDrum extends AppCompatActivity {

    TextView hitText;
    static int hits;
    String type;
    ArrayList<Integer> delay = new ArrayList<Integer>();
    ObjectAnimator bgColor;

    NetworkTask networkTask;

    /*UI EVENT HANDLER*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hits = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.PlayDrum);

        Bundle extras = getIntent().getExtras();
        type = extras.getString("type");
        Log.v("STEP0", type);

        /*Call to start transmission*/
        networkTask = new NetworkTask(PlayDrum.this);
        networkTask.execute();

        hitText = (TextView) findViewById(R.id.hitText);
        hitText.setOnClickListener(new View.OnClickListener() {

            /*Event of hitting the drum*/
            @Override
            public void onClick(View v) {
                /*If the drum hit is not in synchronization with transmission*/
                if (networkTask.i > hits + 1) {
                    Log.v("FAIL1", "Hit=" + hits);
                    Intent intent;
                    intent = new Intent(PlayDrum.this, DisplayDelayResult.class);
                    /*Pass the number of correct hits, delay and total bests*/
                    intent.putIntegerArrayListExtra("delay", delay);
                    intent.putExtra("hits", hits);
                    intent.putExtra("beats", networkTask.rhythm.size());
                    startActivity(intent);
                }
                /*Calculate delay for the hit*/
                delay.add((int) (new Date().getTime() - networkTask.rhythm.get(networkTask.i - 1) - networkTask.start));
                hits++;
                Log.v("Test1", "Hit=" + hits);
            }
        });
    }
}