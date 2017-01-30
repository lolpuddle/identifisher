package com.identifisher.sfwreng3a04.identifisher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

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


        final Button identify = (Button) findViewById(R.id.identifyIdentifyPageButton);           //Button to fire identifying process
        final Button addFishToLake = (Button) findViewById(R.id.addFishToLakeButton);             //Button to fire adding a fish to lake
        //final TextView setForFish = (TextView) findViewById(R.id.fishIdentifiedTextView);   //A Display to give information for the User, see TODO's
        final Spinner[] spinners = {                                                        //All Dropdowns with Information filled in R.String, see TODO's
                (Spinner) findViewById(R.id.colorSpinner),
                (Spinner) findViewById(R.id.shapeSpinner),
                (Spinner) findViewById(R.id.patternSpinner)
        };
        final Spinner fishChoice = (Spinner) findViewById(R.id.fishChoiceSpinner);
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
                if(response.length > 0) {
                    editSpinner(fishChoice, response);
                    addFishToLake.setClickable(true);
                } else {
                    //TODO tell user to try again
                    String[] error = {"Fish Not found"};
                    editSpinner(fishChoice,error);
                    addFishToLake.setClickable(false);
                    Log.d("IdentifyFish", "No Fish Found");
                }
            }
        });
    }

    private void editSpinner(Spinner fishChoice, String[] response) {
        ArrayList<String> options=new ArrayList<String>();

        for(String s : response) {
            options.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        fishChoice.setAdapter(adapter);
    }
}
