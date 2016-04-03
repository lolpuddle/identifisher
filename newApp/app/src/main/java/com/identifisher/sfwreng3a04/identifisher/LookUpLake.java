package com.identifisher.sfwreng3a04.identifisher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LookUpLake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up_lake);

        Button[] buttons = {
                (Button) findViewById(R.id.geolocationButton),
                (Button) findViewById(R.id.lakeLookUpPageButton)
        };

        for(Button btn : buttons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case (R.id.geolocationButton):
                            //TODO find geolocation, return success/failure
                                //if success, assign it to global variable, fill R.id.geolocationText, let User know
                                //if not, let user it failed
                            break;
                        case (R.id.lakeLookUpPageButton):
                            //TODO double check we have valid location, return fail if we dont
                            //If we do, query database and return information
                                //How to return it? As a group we need to decide between pop up or scrollable text?
                            break;
                    }
                }
            });
        }
    }
}
