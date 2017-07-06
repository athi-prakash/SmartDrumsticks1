package com.example.priyagosain.smartdrumsticks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class SelectLevel extends AppCompatActivity {
    Button btnBasic;
    Button btnInter;
    Button btnAdv;

    /*UI EVENT HANDLER*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level);
        // get the buttton object
        btnBasic = (Button) findViewById(R.id.btnBasic);
        btnInter = (Button) findViewById(R.id.btnInter);
        btnAdv = (Button) findViewById(R.id.btnAdv);
        // going to play drumsticks screen
        btnBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //receive user defined values from UserInputValues view.
                Bundle extras = getIntent().getExtras();
                Integer alertThreshold = (Integer)extras.get("alertThreshold");
                Integer responseDelay = (Integer)extras.get("responseDelay");
                Intent intent = new Intent(SelectLevel.this, PlayDrum.class);
                intent.putExtra("type", "Beg");
                intent.putExtra("alertThreshold", alertThreshold);
                intent.putExtra("responseDelay", responseDelay);
                startActivity(intent);
            }
        });
        // going to play drumsticks screen
        btnInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //receive user defined values from UserInputValues view.
                Bundle extras = getIntent().getExtras();
                Integer alertThreshold = (Integer)extras.get("alertThreshold");
                Integer responseDelay = (Integer)extras.get("responseDelay");
                Intent intent = new Intent(SelectLevel.this, PlayDrum.class);
                intent.putExtra("type", "Int");
                intent.putExtra("alertThreshold", alertThreshold);
                intent.putExtra("responseDelay", responseDelay);
                startActivity(intent);
            }
        });
        // going to play drumsticks screen
        btnAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //receive user defined values from UserInputValues view.
                Bundle extras = getIntent().getExtras();
                Integer alertThreshold = (Integer)extras.get("alertThreshold");
                Integer responseDelay = (Integer)extras.get("responseDelay");
                Intent intent = new Intent(SelectLevel.this, PlayDrum.class);
                intent.putExtra("type", "Exp");
                intent.putExtra("alertThreshold", alertThreshold);
                intent.putExtra("responseDelay", responseDelay);
                startActivity(intent);
            }
        });

    }

}

