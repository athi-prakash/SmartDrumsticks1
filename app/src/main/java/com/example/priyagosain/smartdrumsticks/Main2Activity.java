package com.example.priyagosain.smartdrumsticks;

import android.os.AsyncTask;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;



public class Main2Activity extends AppCompatActivity {

    TextView hitText;
    static int hits;
    String type;
    ArrayList<Integer> delay = new ArrayList<Integer>();

    NetworkTask networkTask= new NetworkTask(Main2Activity.this);

    /*UI EVENT HANDLER*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hits = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();
        type=extras.getString("type");

        /*Call to start transmission*/
        networkTask.execute();

        hitText = (TextView) findViewById(R.id.hitText);
        hitText.setOnClickListener(new View.OnClickListener() {

            /*Event of hitting the drum*/
            @Override
            public void onClick(View v) {
                /*If the drum hit is not in synchronization with transmission*/
                if (networkTask.i>hits+1)
                {
                    Log.v("FAIL1", "Hit=" + hits);
                    Intent intent;
                    intent = new Intent(Main2Activity.this, Main3Activity.class);
                    /*Pass the number of correct hits, delay and total bests*/
                    intent.putIntegerArrayListExtra("delay", delay);
                    intent.putExtra("hits", hits);
                    intent.putExtra("beats", 20);
                    startActivity(intent);
                }
                /*Calculate delay for the hit*/
                delay.add((int)(new Date().getTime() - networkTask.myList[networkTask.i - 1] - networkTask.start));
                hits++;
                Log.v("Test1", "Hit=" + hits);
            }
        });
    }
}