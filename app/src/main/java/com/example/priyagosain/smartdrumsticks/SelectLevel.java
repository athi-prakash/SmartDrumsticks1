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

        // get the button object
        btnBasic = (Button) findViewById(R.id.btnBasic);
        btnInter = (Button) findViewById(R.id.btnInter);
        btnAdv = (Button) findViewById(R.id.btnAdv);

        // Once the user clicks this button, the app will go to play drumsticks screen with "beginner" difficulty
        btnBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //receive user defined values from UserInputValues view.
                //Then, those values will be past to the next view, which is the destination
                Bundle extras = getIntent().getExtras();
                Integer alertThreshold = (Integer)extras.get("alertThreshold");
                Integer responseDelay = (Integer)extras.get("responseDelay");

                //set up the next screen we will go
                Intent intent = new Intent(SelectLevel.this, PlayDrum.class);
                //Pass information the next scree need
                intent.putExtra("type", "Beg");
                intent.putExtra("alertThreshold", alertThreshold);
                intent.putExtra("responseDelay", responseDelay);
                // Jump to the next screen
                startActivity(intent);
            }
        });

        // Once the user clicks this button, the app will go to play drumsticks screen with "middle" difficulty
        btnInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //receive user defined values from UserInputValues view.
                //Then, those values will be past to the next view, which is the destination
                Bundle extras = getIntent().getExtras();
                Integer alertThreshold = (Integer)extras.get("alertThreshold");
                Integer responseDelay = (Integer)extras.get("responseDelay");

                //set up the next screen we will go
                Intent intent = new Intent(SelectLevel.this, PlayDrum.class);
                //Pass information the next scree need
                intent.putExtra("type", "Int");
                intent.putExtra("alertThreshold", alertThreshold);
                intent.putExtra("responseDelay", responseDelay);
                // Jump to the next screen
                startActivity(intent);
            }
        });

        // Once the user clicks this button, the app will go to play drumsticks screen with "middle" difficulty
        btnAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //receive user defined values from UserInputValues view.
                //Then, those values will be past to the next view, which is the destination
                Bundle extras = getIntent().getExtras();
                Integer alertThreshold = (Integer)extras.get("alertThreshold");
                Integer responseDelay = (Integer)extras.get("responseDelay");

                //set up the next screen we will go
                Intent intent = new Intent(SelectLevel.this, PlayDrum.class);
                //Pass information the next scree need
                intent.putExtra("type", "Exp");
                intent.putExtra("alertThreshold", alertThreshold);
                intent.putExtra("responseDelay", responseDelay);
                // Jump to the next screen
                startActivity(intent);
            }
        });

    }

}

