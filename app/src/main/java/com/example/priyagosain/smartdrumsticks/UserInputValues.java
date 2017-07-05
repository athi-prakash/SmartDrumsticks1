package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserInputValues extends AppCompatActivity {
    TextView alertValue, responseDelayValue;
    EditText alertInput, delayInput;
    Button startPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_input_values);

        alertValue = (TextView)findViewById(R.id.alertValue);
        responseDelayValue = (TextView)findViewById(R.id.responseDelayValue);
        alertInput = (EditText) findViewById(R.id.alertInput);
        delayInput = (EditText) findViewById(R.id.delayInput);
        startPlay = (Button) findViewById(R.id.startPlay);

        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Integer alertThreshold = Integer.parseInt(alertInput.getText().toString());
                Integer responseDelay = Integer.parseInt(delayInput.getText().toString());
                if (isValidAlterThreshold(alertThreshold) && isValidResponseDelay(responseDelay)){
                    Intent intent = new Intent(UserInputValues.this, SelectLevel.class);
                    intent.putExtra("alertThreshold", alertThreshold);
                    intent.putExtra("responseDelay", responseDelay);
                    startActivity(intent);
                }else{
                    alertInput.setText("");
                    alertInput.setHint("Wroing Number");
                    delayInput.setText("");
                    delayInput.setHint("Wrong Number");
                }
            }
        }) ;
    }

    private boolean isValidAlterThreshold(Integer input){
        if (input >= 0 && input <= 100){
            return true;
        }else{
            return false;
        }
    }

    private boolean isValidResponseDelay(Integer input){
        if (input >= 0 && input <= 500){
            return true;
        }else{
            return false;
        }
    }
}
