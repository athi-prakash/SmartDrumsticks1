package com.example.priyagosain.smartdrumsticks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserInputValues extends AppCompatActivity {
    TextView alertValue, responseDelayValue;
    EditText alertInput, delayInput;
    Button startPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_input_values);

        // find widgets
        alertValue = (TextView)findViewById(R.id.alertValue);
        responseDelayValue = (TextView)findViewById(R.id.responseDelayValue);
        alertInput = (EditText) findViewById(R.id.alertInput);
        delayInput = (EditText) findViewById(R.id.delayInput);
        startPlay = (Button) findViewById(R.id.startPlay);

        // When the button is clicked, collect values and pass to the next screen
        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Collect values
                Integer alertThreshold = Integer.parseInt(alertInput.getText().toString());
                Integer responseDelay = Integer.parseInt(delayInput.getText().toString());
                // Validate the values
                if (isValidAlterThreshold(alertThreshold) && isValidResponseDelay(responseDelay)){
                    // If the value is valid, pass it to the next screen
                    // The destination of those values are PlayDeum view
                    Intent intent = new Intent(UserInputValues.this, SelectLevel.class);
                    intent.putExtra("alertThreshold", alertThreshold);
                    intent.putExtra("responseDelay", responseDelay);
                    startActivity(intent);
                }else{// otherwise, give users hints that theri values are not valid
                    alertInput.setText("");
                    alertInput.setHint("Wroing Number");
                    delayInput.setText("");
                    delayInput.setHint("Wrong Number");
                }
            }
        }) ;
    }

    // Validate alert threshold value
    private boolean isValidAlterThreshold(Integer input){
        if (input >= 0 && input <= 100){
            return true;
        }else{
            return false;
        }
    }

    // Validate response delay value
    private boolean isValidResponseDelay(Integer input){
        if (input >= 0 && input <= 500){
            return true;
        }else{
            return false;
        }
    }
}
