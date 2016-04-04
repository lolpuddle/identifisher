package com.identifisher.sfwreng3a04.identifisher;

import android.util.Log;

import java.util.ArrayList;

import sql.DBHelper;

/**
 * Created by Christopher on 2016-04-04.
 */
public class ExpertManager {

    Expert[] experts;
    DBHelper db;

    public ExpertManager(DBHelper dbHelper) {
        db = dbHelper;
        experts = new Expert[3];
        experts[0] = new ColorExpert(dbHelper);
        experts[1] = new ShapeExpert(dbHelper);
        experts[2] = new PatternExpert(dbHelper);
    }

    /**
     *
     * @param info - Information regarding the Fish as... - [Color, Shape, Pattern]
     * @return
     */
    public String[] identifyThis(String[] info) {
        ArrayList<String[]> allFish = new ArrayList<String[]>();
        String[] hold;
        int maxCol = 0;
        for (int i = 0; i < experts.length; i++) {
            hold = experts[i].getFish(info[i]);
            for(String s : hold) {
                Log.d("ExpertManager",s + " was returned by Expert #" + i);
            }
            allFish.add(hold);
            if(hold.length > maxCol) maxCol = hold.length;
        }
        return overlap(allFish.toArray(new String[allFish.size()][maxCol]));
    }

    /**
     *
     * @param allFish
     * @return
     */
    private String[] overlap(String[][] allFish) {
        ArrayList<String> possibleFish = new ArrayList<String>();
        String[] start = sharedElement(allFish[0],allFish[1]);
        for (int i = 2; i < allFish.length; i++) {
            start = sharedElement(start,allFish[i]);
        }
        return start;
    }

    /**
     *
     * @param one
     * @param two
     * @return
     */
    private String[] sharedElement(String[] one, String[] two) {
        ArrayList<String> toReturn = new ArrayList<String>();
        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < two.length; j++) {
                if(one[i].equals(two[j])) {
                    toReturn.add(one[i]);
                }
            }
        }
        return toReturn.toArray(new String[toReturn.size()]);
    }
}
