package com.identifisher.sfwreng3a04.identifisher;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import sql.DBHelper;

/**
 * Created by Christopher on 2016-04-04.
 */
public class ColorExpert extends Expert {

    DBHelper database;

    public ColorExpert(DBHelper db) {
        database = db;
    }

    @Override
    public String[] getFish(String data) {
        String[][] info = extractColor(database.getAllFishInformation());

        ArrayList<String> possibleFish = new ArrayList<String>();
        for (String[] row : info) {
            Log.d("ColorExpert","Comparing..." + row[1] + " to " + data);
            if(row[1].compareTo(data) == 0) {
                Log.d("ColorExpert",row[0] + " is possible..");
                possibleFish.add(row[0]);
            }
        }
        String[] toReturn = new String[possibleFish.size()];
        return possibleFish.toArray(toReturn);
    }

    private String[][] extractColor(String[][] allFishInformation) {
        String[][] toReturn = new String[allFishInformation.length][2];
        int i = 0;
        for (String[] row : allFishInformation) {
            toReturn[i][0] = row[0];
            toReturn[i][1] = row[3];
            i++;
        }
        return toReturn;
    }
}
