package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    Intent intent;
    int hits;
    int beats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent myIntent = getIntent(); // this is just for example purpose
        Bundle extras = getIntent().getExtras();
        ArrayList<Integer> myList1 = new ArrayList<Integer>();
        myList1=extras.getIntegerArrayList("myList");
        hits=extras.getInt("hits");
        beats=extras.getInt("beats");
        for (int i=0;i<hits;i++)
        {
            Log.v("X1", "Delay=" + myList1.get(i));
        }
    }
}
