package com.identifisher.sfwreng3a04.identifisher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class LookUpLake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up_lake);

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
                            intent = new Intent(v.getContext(), MapsActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}
