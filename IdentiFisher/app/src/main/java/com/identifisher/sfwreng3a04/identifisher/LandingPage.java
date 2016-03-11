package com.identifisher.sfwreng3a04.identifisher;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* A bunch of Android stuff begins... */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        /* A bunch of Android stuff end... */

        //All Buttons that belong in LandingPage.xml are created here...
        final Button[] buttons = {(Button) findViewById(R.id.identifyBtn),
                                  (Button) findViewById(R.id.findLakeBtn),
                                  (Button) findViewById(R.id.settingsBtn),
                                  (Button) findViewById(R.id.aboutUsBtn),};

        //Cycle through buttons, assign Switch/Case ButtonListener
        for(Button btn : buttons) {
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int id = v.getId();
                    switch (id) {
                        case(R.id.identifyBtn):
                            //TODO Go to new page
                            break;
                        case(R.id.findLakeBtn):
                            //TODO See above
                            break;
                        case(R.id.settingsBtn):
                            //TODO See above
                            break;
                        case(R.id.aboutUsBtn):
                            //TODO See above
                            break;
                        default:
                            //TODO Throw error?
                            break;
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
