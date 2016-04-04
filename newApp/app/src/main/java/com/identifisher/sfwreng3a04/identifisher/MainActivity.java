package com.identifisher.sfwreng3a04.identifisher;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import sql.DBHelper;

public class MainActivity extends AppCompatActivity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Testing Begins!
        mydb = new DBHelper(this);
        ExpertManager expertManager = new ExpertManager(mydb);
        String[] info = {" \"silver\""," \"elongated\""," \"striped\""};
        String[] fishes = expertManager.identifyThis(info);
        Log.d("Test","Begin...");
        for(String a : fishes) {
            Log.d("Test",a);
        }
        Log.d("Test", "End...");
        //  Testing Ends.. :(

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
