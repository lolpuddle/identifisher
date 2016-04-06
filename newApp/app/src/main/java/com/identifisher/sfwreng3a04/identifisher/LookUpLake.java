package com.identifisher.sfwreng3a04.identifisher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

public class LookUpLake extends AppCompatActivity {


    static String inputText;
    EditText input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up_lake);
        input = (EditText)findViewById(R.id.geolocationText);

        Button[] buttons = {
                (Button) findViewById(R.id.lakeLookUpPageButton)
        };

        for(Button btn : buttons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getId();
                    Intent intent;
                    switch (v.getId()) {
                        case (R.id.lakeLookUpPageButton):
                            inputText = input.getText().toString();
                            intent = new Intent(v.getContext(), MapsActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}
