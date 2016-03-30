package com.identifisher.sfwreng3a04.identifisher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class IdentifyFish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_fish);

        Button identify = (Button) findViewById(R.id.identifyIdentifyPageButton);
        final Spinner[] spinners = {
                (Spinner) findViewById(R.id.colorSpinner),
                (Spinner) findViewById(R.id.shapeSpinner),
                (Spinner) findViewById(R.id.patternSpinner)
        };

        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] choices = {
                        spinners[0].getSelectedItem().toString(),
                        spinners[1].getSelectedItem().toString(),
                        spinners[2].getSelectedItem().toString()
                };
                //Give String array to ExpertManager, await response.
            }
        });
    }
}
