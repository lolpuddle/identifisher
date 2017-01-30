package com.identifisher.sfwreng3a04.identifisher;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

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
    private int secretKey;

    /**
     * Contructor - Builds a ExpertManager while initializing Experts
     * @param dbHelper - The Database this ExpertManager will use
     */
    public ExpertManager(DBHelper dbHelper) {
        Random rand = new Random();
        int key = rand.nextInt(60);
        secretKey = key;
        db = dbHelper;
        experts = new Expert[3];
        experts[0] = new ColorExpert(dbHelper, key);
        experts[1] = new ShapeExpert(dbHelper, key);
        experts[2] = new PatternExpert(dbHelper, key);
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
            hold = CeaserCipher.decode(experts[i].getFish(CeaserCipher.encode(info[i],secretKey)),secretKey);
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
        for(String s : toSort) {
            Log.d("ExpertManager","To Sort: " + s);
        }
        for(String s : sort(toSort)) {
            Log.d("ExpertManager","Sorted: " + s);
        }
        toSort = removeDup(sort(toSort));
        for(String s : toSort) {
            Log.d("ExpertManager","Shrunk: " + s);
        }
        return toSort;
        //return overlap(allFish.toArray(new String[allFish.size()][maxCol]));
    }

    /**sort by frequency. Reference: http://www.java-fries.com/2015/02/sort-elements-frequency/
     * Sort by creating a map with element in the targeted array as keys and the frequency as value
     * Convert the sorted map back to an array.
     * @param arr - list of unordered fish returned by all experts
     * @return String[] - list of fish ordered from most to least frequency of occurrence
     */
    private String[] sort(String[] arr) {
    // map of the elements of array and frequency as its value
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < arr.length; i++) {
            String key = arr[i];
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
        //sort list of elements of map's entries
        List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> s1,
                               Entry<String, Integer> s2) {
                return s2.getValue().compareTo(s1.getValue());
            }
        });
        //rearrange array
        int index = 0;
        for (Map.Entry<String, Integer> entry : entryList) {
            for (int i = 0; i < entry.getValue(); i++) {
                arr[index++] = entry.getKey();
            }
        }
        return arr;
    }

    /**
     * Provides the means of removing duplicated entries in an array
     * Use hash set to eliminate non-unique entries
     * @param arr - list of fish
     * @return String[] - list of fish without duplicates
     */
    private String[] removeDup(String[] arr) {
        List<String> temp = Arrays.asList(arr);
        Set<String> set = new LinkedHashSet<String>(temp);
        String[] newArr = new String[set.size()];
        set.toArray(newArr);
        return newArr;
    }
    /**
     * Provides the means of finding any overlapping fish in all of the arrays of allFish
     * @param allFish - list from each Expert on what fish they think it is
     * @return String[] - list of fish that appear in all 3 arrays
     */
    private String[] overlap(String[][] allFish) {
        if(allFish.length > 1) {
            return allFish[0];
        } else {
            ArrayList<String> possibleFish = new ArrayList<String>();
            String[] start = sharedElement(allFish[0], allFish[1]);
            for (int i = 2; i < allFish.length; i++) {
                start = sharedElement(start, allFish[i]);
            }
            return start;
        }
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
