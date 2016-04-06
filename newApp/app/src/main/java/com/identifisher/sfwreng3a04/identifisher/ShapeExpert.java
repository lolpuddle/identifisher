package com.identifisher.sfwreng3a04.identifisher;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import sql.DBHelper;

/**
 * Created by Christopher on 2016-04-04.
 */
public class ShapeExpert extends Expert {

    DBHelper database;
    private int secretKey;

    public ShapeExpert(DBHelper db, int key) {
        database = db;
        secretKey = key;
    }

    @Override
    public String[] getFish(String data) {
        data = CeaserCipher.decode(data, secretKey);
        String[][] info = extractShape(database.getAllFishInformation());
        Log.d("ShapeExpert", "Beginning Shape Analysis... Looking for " + data);
        ArrayList<String> possibleFish = new ArrayList<String>();
        for (String[] row : info) {
            Log.d("ShapeExpert","Comparing..." + row[1] + " to " + data);
            if(row[1].compareTo(data) == 0) {
                Log.d("ShapeExpert",row[0] + " is possible..");
                possibleFish.add(row[0]);
            }
        }
        String[] toReturn = new String[possibleFish.size()];
        return CeaserCipher.encode(possibleFish.toArray(toReturn),secretKey);
    }

    private String[][] extractShape(String[][] allFishInformation) {
        String[][] toReturn = new String[allFishInformation.length][2];
        int i = 0;
        for (String[] row : allFishInformation) {
            toReturn[i][0] = row[0];
            toReturn[i][1] = row[1];
            i++;
        }
        return toReturn;
    }
}
