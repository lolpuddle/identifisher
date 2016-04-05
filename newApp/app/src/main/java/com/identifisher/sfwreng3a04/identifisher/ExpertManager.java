package com.identifisher.sfwreng3a04.identifisher;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

import sql.DBHelper;

/**
 * Controller Class
 * Manages and uses the Experts to identify a Fish when given the information form the IdentifyFish View Controller
 *
 * TODO Sort the array of Fish in most to least likely
 *
 * Created by Christopher on 2016-04-04.
 */
public class ExpertManager {

    Expert[] experts;
    DBHelper db;

    /**
     * Contructor - Builds a ExpertManager while initializing Experts
     * @param dbHelper - The Database this ExpertManager will use
     */
    public ExpertManager(DBHelper dbHelper) {
        db = dbHelper;
        experts = new Expert[3];
        experts[0] = new ColorExpert(dbHelper);
        experts[1] = new ShapeExpert(dbHelper);
        experts[2] = new PatternExpert(dbHelper);
    }

    /**
     * Provides means of identifying a fish when given the info
     * @param info - Information regarding the Fish as... - [Color, Shape, Pattern]
     * @return String[] of all possible fish that fit the criteria
     */
    public String[] identifyThis(String[] info) {
        ArrayList<String[]> allFish = new ArrayList<String[]>();
        ArrayList<String> allFishInOne = new ArrayList<String>();
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
        Iterator<String[]> i = allFish.iterator();
        while (i.hasNext()) {
            for(String s : i.next()) {
                allFishInOne.add(s);
            }
        }
        String[] toSort = allFishInOne.toArray(new String[allFishInOne.size()]);
        return overlap(allFish.toArray(new String[allFish.size()][maxCol]));
    }

    /**
     * Provides the means of finding any overlapping fish in all of the arrays of allFish
     * @param allFish - list from each Expert on what fish they think it is
     * @return String[] - list of fish that appear in all 3 arrays
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
     * Provides means of returning all shared elements of two arrays
     * @param one - first array
     * @param two - second array
     * @return an array of all shared elements
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
