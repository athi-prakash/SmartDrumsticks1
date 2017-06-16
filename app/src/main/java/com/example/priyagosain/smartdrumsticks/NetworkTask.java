package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import android.os.Bundle;

/**
 * Created by sstto on 16-06-2017.
 */

public class NetworkTask extends AsyncTask<Void, byte[], Boolean> {
    static int i;
    static double start = new Date().getTime();
    double[] myList = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,11000,12000,13000,14000,15000,16000,17000,18000,19000,20000};
    Main2Activity main2Activity;

    NetworkTask (Main2Activity main2Activity1)
    {main2Activity=main2Activity1;}

    @Override
    protected Boolean doInBackground(Void... params) { //This runs on a different thread
        boolean result = false;
        try {
            i=0;
            //IP & Port is hard coded
//            Socket socket = new Socket("134.190.187.18", 8081);
            Socket socket = new Socket("192.168.43.10", 8080);
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            //printStream.print("1");
            while (i<10)
            {
                while (new Date().getTime()-start == myList[i]) {
                    printStream.print("1");
                    i++;
                    break;
                }
                if (i>main2Activity.hit_count+1)
                {
                    break;
                }
            }
            printStream.close();
            socket.close();
            while (new Date().getTime()-10000 == myList[i-1]) {
            }
            Intent intent;
            intent = new Intent(main2Activity, Main3Activity.class);
            intent.putIntegerArrayListExtra("myList", main2Activity.myList1);
            intent.putExtra("hits", main2Activity.hit_count);
            intent.putExtra("beats", 20);
            main2Activity.startActivity(intent);
        }catch(UnknownHostException e){
            System.out.println(e.toString());
        }catch(IOException e){
            System.out.println(e.toString());
        }
        return result;
    }
}