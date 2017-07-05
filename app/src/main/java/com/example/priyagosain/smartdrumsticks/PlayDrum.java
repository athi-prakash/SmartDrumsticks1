package com.example.priyagosain.smartdrumsticks;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.BoolRes;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class PlayDrum extends AppCompatActivity {

    TextView hitText;
    int hits;
    String type;
    ArrayList<Integer> delay = new ArrayList<Integer>();
    ObjectAnimator bgColor;

    Network network;

    /*UI EVENT HANDLER*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hits = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_drum);

        Bundle extras = getIntent().getExtras();
        type = extras.getString("type");
        Log.v("STEP0", type);
        hitText = (TextView) findViewById(R.id.hitText);
        bgColor = ObjectAnimator.ofObject(hitText, "backgroundColor", new ArgbEvaluator(), Color.argb(255, 255, 255, 255), 0xffff0000);
        bgColor.setDuration(3000);
//        bgColor.start();

        /*Call to start transmission*/
        network = new Network(PlayDrum.this);
        network.execute();

        hitText.setOnClickListener(new View.OnClickListener() {

            /*Event of hitting the drum*/
            @Override
            public void onClick(View v) {
                /*If the drum hit is not in synchronization with transmission*/
//                if (network.i > hits + 1) {
//                    Log.v("FAIL1", "Hit=" + hits);
//                    Intent intent;
//                    intent = new Intent(PlayDrum.this, DisplayDelayResult.class);
//                    /*Pass the number of correct hits, delay and total bests*/
//                    intent.putIntegerArrayListExtra("delay", delay);
//                    intent.putExtra("hits", hits);
//                    intent.putExtra("beats", network.rhythm.size());
//                    startActivity(intent);
//                }
                /*Calculate delay for the hit*/
                bgColor.cancel();
//                delay.add((int) (new Date().getTime() - network.rhythm.get(network.i - 1) - network.start));
//                hits++;
//                Log.v("Test1", "Hit=" + hits);
            }
        });
    }

    private class Network extends AsyncTask<PlayDrum, Integer, Boolean> {
        int i;
        double start;
        /*Rhythm is represented as a sequence of number values indicating the millisec of the hit*/
        double[] begginer = {1000, 3000, 5000, 7000, 9000, 12000};
        double[] intermidiate = {1000, 2000, 2500, 3500, 4500, 5000, 6000, 7000, 7500, 8500, 9500, 10000, 11000, 12000, 12500, 13500, 14500, 15000, 16000, 17000};
        double[] expert = {1000, 1500, 2500, 2800, 3100, 3500, 4500, 5000, 6000, 6300, 6600, 7000, 8000, 8500, 9500, 9800, 10100, 10500, 11500, 12000};
        ArrayList<Double> rhythm = new ArrayList<Double>();


        Network(PlayDrum playDrum1) {
            double[] level;
            switch (playDrum1.type) {
                case "Beg": level = begginer; break;
                case "Int": level = intermidiate; break;
                case "Exp": level = expert; break;
                default: level = begginer; break;
            }
            for (int i = 0; i < level.length; i++)
                rhythm.add(level[i]);
            start = new Date().getTime();
        }

        @Override
        protected Boolean doInBackground(PlayDrum... playDrums) { //This runs on a different thread
            boolean result = false;
//        try {
            i = 0;
            //IP & Port is hard coded to create socket client
//            Socket socket = new Socket("134.190.187.18", 8080);
            //Socket socket = new Socket("192.168.43.10", 8080);
            /*Client is set as transmitter*/
//            OutputStream outputStream = socket.getOutputStream();
//            PrintStream printStream = new PrintStream(outputStream);

            /*Loop on the rhythm*/
            while (i < rhythm.size()) {
                /*Transmit a hit when the time matches with the rhythm*/
                while (new Date().getTime() - start == rhythm.get(i)) {
                    publishProgress(i);
//                    printStream.print("1");
                    i++;
                    break;
                }
                /*Check of asynchronization or missed hits*/
//                if (i > hits + 1) {
//                    Log.v("FAIL2", "Hit=" + i + "-" + hits);
//                    /*Terminate in case of async*/
//                    break;
//                }
            }
            /*Close transmission after end of transmission*/
//            printStream.close();
//            socket.close();
            /*Wait for 10 secs after transmission for user to end the last beat*/
            while (new Date().getTime()-10000 == rhythm.get(i-1)) {
            }
            /*Transfer control and pass data to next screen(final)*/
            Intent intent;
            intent = new Intent(playDrums[0], DisplayDelayResult.class);
//            intent.putIntegerArrayListExtra("delay", playDrums[0].delay);
//            intent.putExtra("hits", playDrums[0].hits);
//            intent.putExtra("beats", rhythm.size());
//            playDrums[0].startActivity(intent);

//        }catch(UnknownHostException e){
//            System.out.println(e.toString());
//        }catch(IOException e){
//            System.out.println(e.toString());
//        }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
                hitText.setText(progress[0].toString());
                bgColor.start();
        }

        @Override
        protected void onPostExecute(Boolean result){
        }
    }
}