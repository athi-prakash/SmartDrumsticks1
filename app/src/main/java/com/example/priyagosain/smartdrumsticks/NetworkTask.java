package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by sstto on 16-06-2017.
 */

public class NetworkTask extends AsyncTask<Void, byte[], Boolean> {
    int i;
    double start;
    /*Rhythm is represented as a sequence of number values indicating the millisec of the hit*/
    double[] begginer = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000, 15000, 16000, 17000, 18000, 19000, 20000};
    double[] intermidiate = {1000, 2000, 2500, 3500, 4500, 5000, 6000, 7000, 7500, 8500, 9500, 10000, 11000, 12000, 12500, 13500, 14500, 15000, 16000, 17000};
    double[] expert = {1000, 1500, 2500, 2800, 3100, 3500, 4500, 5000, 6000, 6300, 6600, 7000, 8000, 8500, 9500, 9800, 10100, 10500, 11500, 12000};
    ArrayList<Double> rhythm=new ArrayList<Double>();
    Main2Activity main2Activity;

    NetworkTask (Main2Activity main2Activity1)
    {
        main2Activity=main2Activity1;
        if (main2Activity.type.equals("Beg")) {
            for (int i = 0; i < begginer.length; i++)
                rhythm.add(begginer[i]);
        }
        else if (main2Activity.type.equals("Int"))
        {
            for (int i = 0; i < intermidiate.length; i++)
                rhythm.add(intermidiate[i]);
        }
        else if (main2Activity.type.equals("Exp"))
        {
            for (int i = 0; i < expert.length; i++)
                rhythm.add(expert[i]);
        }
        start = new Date().getTime();
    }

    @Override
    protected Boolean doInBackground(Void... params) { //This runs on a different thread
        boolean result = false;
        try {
            i=0;
            //IP & Port is hard coded to create socket client
            Socket socket = new Socket("134.190.187.18", 8080);
            //Socket socket = new Socket("192.168.43.10", 8080);
            /*Client is set as transmitter*/
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            /*Loop on the rhythm*/
            while (i<rhythm.size())
            {
                /*Transmit a hit when the time matches with the rhythm*/
                while (new Date().getTime()-start == rhythm.get(i)) {
                    printStream.print("1");
                    i++;
                    break;
                }
                /*Check of asynchronization or missed hits*/
                if (i>main2Activity.hits+1)
                {
                    Log.v("FAIL2", "Hit=" + i+"-"+main2Activity.hits);
                    /*Terminate in case of async*/
                    break;
                }
            }
            /*Close transmission after end of transmission*/
            printStream.close();
            socket.close();
            /*Wait for 10 secs after transmission for user to end the last beat*/
            while (new Date().getTime()-10000 == rhythm.get(i-1)) {
            }
            /*Transfer control and pass data to next screen(final)*/
            Intent intent;
            intent = new Intent(main2Activity, Main3Activity.class);
            intent.putIntegerArrayListExtra("delay", main2Activity.delay);
            intent.putExtra("hits", main2Activity.hits);
            intent.putExtra("beats", rhythm.size());
            main2Activity.startActivity(intent);

        }catch(UnknownHostException e){
            System.out.println(e.toString());
        }catch(IOException e){
            System.out.println(e.toString());
        }
        return result;
    }
}