package com.identifisher.sfwreng3a04.identifisher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import sql.DBHelper;

/**
 * View Controller Class
 * This View provides the means of identifying fish for the user using drop down menus and buttons.
 *
 * TODO Make Dropdowns populate using AllFishInformation
 * TODO Add Button to Add A Possible Fish to Lake
 * TODO Add Dropdown to populate with possible fish
 *
 * Author: Christopher McDonald
 */
public class IdentifyFish extends AppCompatActivity {

    DBHelper db;
    ExpertManager expertManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_fish);


        Button identify = (Button) findViewById(R.id.identifyIdentifyPageButton);           //Button to fire identifying process
        final TextView setForFish = (TextView) findViewById(R.id.fishIdentifiedTextView);   //A Display to give information for the User, see TODO's
        final Spinner[] spinners = {                                                        //All Dropdowns with Information filled in R.String, see TODO's
                (Spinner) findViewById(R.id.colorSpinner),
                (Spinner) findViewById(R.id.shapeSpinner),
                (Spinner) findViewById(R.id.patternSpinner)
        };
        db = new DBHelper(this);                                                            //Database to be used
        expertManager = new ExpertManager(db);                                              //ExpertManager (Forum) to handle indentifying

        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] choices = {                                                        //Get all information from Dropdowns
                        spinners[0].getSelectedItem().toString(),
                        spinners[1].getSelectedItem().toString(),
                        spinners[2].getSelectedItem().toString()
                };
                //Give String array to ExpertManager, await response.
                String[] response = expertManager.identifyThis(choices);
                String options = "";
                for(String fish : response) {
                    options += fish + ",";
                }
                options.substring(0,options.length()-2);                                    //Current eway of Displaying to User, TODO change
                options += " are all possible Fish.";
                setForFish.setText(options);
            }
        });
    }
}
