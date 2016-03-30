package com.identifisher.sfwreng3a04.identifisher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button[] buttons = {
                (Button) findViewById(R.id.identifyButton),
                (Button) findViewById(R.id.lakeLookUpButton),
                (Button) findViewById(R.id.addFishButton)
        };

        for (Button btn : buttons) {
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    v.getId();
                    Intent intent;
                    switch (v.getId()) {
                        case (R.id.identifyButton):
                            intent = new Intent(v.getContext(), IdentifyFish.class);
                            startActivity(intent);
                            break;
                        case (R.id.lakeLookUpButton):
                            intent = new Intent(v.getContext(), LookUpLake.class);
                            startActivity(intent);
                            break;
                        case (R.id.addFishButton):
                            intent = new Intent(v.getContext(), AddFish.class);
                            startActivity(intent);
                            break;
                        default:
                            //Throw error?
                    }
                }
            });
        }
    }
}
