package com.identifisher.sfwreng3a04.identifisher;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sql.DBHelper;
import sql.FishReader;

/**
 * Created by Christopher on 2016-04-04.
 */
public class InitialDatabase {

    DBHelper db;

    public InitialDatabase(DBHelper database) {
        db = database;
    }

    public void init() {
        FishReader fr = new FishReader();
        String[][] fish = fr.read();
        for(String[] singleFish : fish) {
            Log.d("Adding Fish",singleFish[0] + " - " + singleFish[1]);
            db.insertFish(singleFish[0],singleFish[1],singleFish[2],singleFish[3]);
        }
        db.insertLake("Lake Ontario");
        db.insertLake("Lake Superior");
        db.insertLake("Lake Erie");
        db.insertLake("Lake Huron");
    }
}
