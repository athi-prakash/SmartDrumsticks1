package com.example.priyagosain.smartdrumsticks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    Button btnBasic;
    Button btnInter;
    Button btnAdv;

    /*UI EVENT HANDLER*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the buttton object
        btnBasic = (Button) findViewById(R.id.btnBasic);
        btnInter = (Button) findViewById(R.id.btnInter);
        btnAdv = (Button) findViewById(R.id.btnAdv);
        // going to play drumsticks screen
        btnBasic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(i);
        }
        });
        // going to play drumsticks screen
        btnInter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(i);
           }
        });
        // going to play drumsticks screen
        btnAdv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

    }

    }

