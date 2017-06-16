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
    static int hit_count;
    ArrayList<Integer> myList1 = new ArrayList<Integer>();
    NetworkTask networkTask= new NetworkTask(Main2Activity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hit_count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        networkTask.execute();
        hitText = (TextView) findViewById(R.id.hitText);
        hitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkTask.i>hit_count+1)
                {
                    Intent intent;
                    intent = new Intent(Main2Activity.this, Main3Activity.class);
                    intent.putIntegerArrayListExtra("myList", myList1);
                    intent.putExtra("hits", hit_count);
                    intent.putExtra("beats", 10);
                    startActivity(intent);
                }
                myList1.add((int)(new Date().getTime() - networkTask.myList[networkTask.i - 1] - networkTask.start));
                hit_count++;
            }
        });
    }
}