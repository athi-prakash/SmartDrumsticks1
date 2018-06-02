package com.example.priyagosain.smartdrumsticks;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;


public class PlayDrum extends AppCompatActivity {

    TextView hitText;
    int hits;
    String type;
    ArrayList<Integer> delay = new ArrayList<Integer>();
    ObjectAnimator bgColor;
    Integer alertThreshold, responseDelay;

    NetworkTask networkTask;

    /*UI EVENT HANDLER*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hits = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_drum);

        // set up variables
        Bundle extras = getIntent().getExtras();
        type = extras.getString("type");
        alertThreshold = extras.getInt("alertThreshold");
        responseDelay = extras.getInt("responseDelay");
        Log.v("STEP0", type);
        hitText = (TextView) findViewById(R.id.hitText);

        // Set the animation
        // Change the color of background of the "hitText" widget from white to red
        bgColor = ObjectAnimator.ofObject(hitText, "backgroundColor", new ArgbEvaluator(), Color.argb(255, 255, 255, 255), 0xffff0000);
        // The changing process will last 10 * alert threshold ms
        bgColor.setDuration(10 * alertThreshold);
        // The changing will start until the user-defined response delay
        bgColor.setStartDelay(responseDelay);

        /*Call to start transmission*/
        networkTask = new NetworkTask(PlayDrum.this);
        networkTask.execute(PlayDrum.this);

        hitText.setOnClickListener(new View.OnClickListener() {

            /*Event of hitting the drum*/
            @Override
            public void onClick(View v) {
                /*If the drum hit is not in synchronization with transmission*/
                if(new Date().getTime() > networkTask.rhythm.get(networkTask.signals) + networkTask.start + responseDelay){
//                if (networkTask.signals > hits + 1) {
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
                bgColor.cancel();
                delay.add(calculateDelay(networkTask.rhythm.get(networkTask.signals - 1), networkTask.start));
                hits++;
                Log.v("Test1", "Hit=" + hits);
            }
        });
    }

    private int calculateDelay(double rhythmTime, double startTime) {
        return (int) (new Date().getTime() - rhythmTime - startTime - responseDelay);
    }

    private class NetworkTask extends AsyncTask<PlayDrum, Integer, PlayDrum> {
        int signals;
        double start;
        /*Rhythm is represented as a sequence of number values indicating the millisec of the hit*/
        double[] begginer = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000, 15000, 16000, 17000, 18000, 19000, 20000};
        double[] intermidiate = {1000, 2000, 2500, 3500, 4500, 5000, 6000, 7000, 7500, 8500, 9500, 10000, 11000, 12000, 12500, 13500, 14500, 15000, 16000, 17000};
        double[] expert = {1000, 1500, 2500, 2800, 3100, 3500, 4500, 5000, 6000, 6300, 6600, 7000, 8000, 8500, 9500, 9800, 10100, 10500, 11500, 12000};
        ArrayList<Double> rhythm = new ArrayList<Double>();


        NetworkTask(PlayDrum playDrum1) {
            double[] level;
            switch (playDrum1.type) {
                case "Beg":
                    level = begginer;
                    break;
                case "Int":
                    level = intermidiate;
                    break;
                case "Exp":
                    level = expert;
                    break;
                default:
                    level = begginer;
                    break;
            }
            for (int i = 0; i < level.length; i++) rhythm.add(level[i]);
            start = new Date().getTime();
        }

        // This function will be executed once an instance of NetworkTask calls "execute" method.
        // This method will run on another thread apart from the UI thread, i.e. it will run in background.
        // This method will pick a list of beginner, intermidiate, and expert, and then send signals
        //     on the time shown in the list in order. Once it find that the user missed more than one
        //     singnal, this stop will stop and call onPostExecute to terminate this thread.
        @Override
        protected PlayDrum doInBackground(PlayDrum... playDrums) { //This runs on a different thread
            try {
                signals = 0;
                //IP & Port is hard coded to create socket client
//            Socket socket = new Socket("134.190.187.18", 8080);
                Socket socket = new Socket("192.168.43.10", 8080);
            /*Client is set as transmitter*/
                OutputStream outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);

            /*Loop on the rhythm*/
                while (signals < rhythm.size()) {
                /*Transmit a hit when the time matches with the rhythm*/
                    while (new Date().getTime() - start == rhythm.get(signals)) {
                /*Check of asynchronization or missed hits*/
                        if (signals > hits + 1) {// if user have missed more that one signal
                            Log.v("FAIL2", "Hit=" + signals + "-" + hits);
                    /*Terminate in case of async*/
                            return playDrums[0];//terminate the networkTask task and call onPostExecute to jump to other jobs
                        }
                        publishProgress(signals);
                        signals++;
                        printStream.print("1");
                        break;
                    }
                }
            /*Close transmission after end of transmission*/
                printStream.close();
                socket.close();
            /*Wait for 10 secs after transmission for user to end the last beat*/
//            while (new Date().getTime() - 10000 == rhythm.get(signals - 1)) {
//            }

            } catch (UnknownHostException e) {
                System.out.println(e.toString());
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            return playDrums[0];
        }

        // Everytime send a signal, start to change the color of the screen
        @Override
        protected void onProgressUpdate(Integer... progress) {
            hitText.setText(progress[0].toString());
            bgColor.start();
        }

        // This method will be called when this background will be terminated.
        // This method will collect data of delays and jump to the next screen to
        //     display it.
        @Override
        protected void onPostExecute(PlayDrum result) {
            Log.v("FAIL2", "POST");
            /*Transfer control and pass data to next screen(final)*/
            Intent intent;
            intent = new Intent(result, DisplayDelayResult.class);
            intent.putIntegerArrayListExtra("delay", result.delay);
            intent.putExtra("hits", result.hits);
            intent.putExtra("beats", rhythm.size());
            result.startActivity(intent);
        }
    }
}