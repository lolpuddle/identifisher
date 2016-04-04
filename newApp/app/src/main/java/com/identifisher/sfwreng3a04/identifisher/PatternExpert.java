package com.identifisher.sfwreng3a04.identifisher;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sql.DBHelper;

/**
 * Created by Christopher on 2016-04-04.
 */
public class PatternExpert extends Expert {

    DBHelper database;

    public PatternExpert(DBHelper db) {
        database = db;
    }

    @Override
    public String[] getFish(String data) {
        String[][] info = extractPattern(database.getAllFishInformation());
        ArrayList<String> possibleFish = new ArrayList<String>();
        for (String[] row : info) {
            if(row[1].equals(data)) {
                possibleFish.add(row[0]);
            }
        }
        String[] toReturn = new String[possibleFish.size()];
        return possibleFish.toArray(toReturn);
    }

    private String[][] extractPattern(String[][] allFishInformation) {
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
