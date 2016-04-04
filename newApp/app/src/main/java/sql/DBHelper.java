package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.identifisher.sfwreng3a04.identifisher.InitialDatabase;

import java.util.ArrayList;

/**
 * Created by Christopher on 2016-04-02.
 */
public class DBHelper extends SQLiteOpenHelper {

    /* DB Schema
    *       lakeNames - Table to hold solely Lake Info
    *                   Contains: ID - Number
    *                             Name - String
    *       fishNames - Table to hold solely Fish Info
    *                   Contains: ID - Number
    *                             Name - String
    *                             Color - String
    *                             Pattern - String
    *                             Shape - String
    *
    *       lakeAndFish - Table to hold number of Fish per Lake
    *                   Contains: Lake - Number
    *                             Fish - Number
    *                             Number - Int
    * */

    private static final String DATABASE_NAME = "lakeData.db";
    private static final String LAKE_TABLE = "lakeNames";
    private static final String LAKE_COLUMN_ID = "ID";
    private static final String LAKE_COLUMN_NAME = "Name";
    private static final String FISH_TABLE = "fishNames";
    private static final String FISH_COLUMN_ID = "ID";
    private static final String FISH_COLUMN_NAME = "Name";
    private static final String FISH_COLUMN_COLOR = "Color";
    private static final String FISH_COLUMN_SHAPE = "Shape";
    private static final String FISH_COLUMN_PATTERN = "Pattern";
    private static final String FISH_LAKE_TABLE = "lakeAndFish";
    private static final String FISH_LAKE_COLUMN_LAKE = "Lake";
    private static final String FISH_LAKE_COLUMN_FISH = "Fish";
    private static final String FISH_LAKE_COLUMN_NUMBER = "Number";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ LAKE_TABLE +
                        " ("+LAKE_COLUMN_ID+" integer primary key autoincrement, "+LAKE_COLUMN_NAME+" varchar(255))");
        db.execSQL("CREATE TABLE "+ FISH_TABLE+
                " ("+FISH_COLUMN_ID+" integer primary key autoincrement, "+FISH_COLUMN_NAME+" varchar(255), " +
                FISH_COLUMN_COLOR + " varchar(255)," +
                FISH_COLUMN_PATTERN + " varchar(255)," +
                FISH_COLUMN_SHAPE +" varchar(255))");
        db.execSQL("CREATE TABLE " + FISH_LAKE_TABLE +
                " (" + FISH_LAKE_COLUMN_LAKE + " integer, " + FISH_LAKE_COLUMN_FISH + " integer " + LAKE_COLUMN_NAME + " integer," +
                "FOREIGN KEY (" + FISH_LAKE_COLUMN_LAKE + ") REFERENCES " + LAKE_TABLE + "(" + LAKE_COLUMN_ID + ")" +
                "FOREIGN KEY (" + FISH_LAKE_COLUMN_FISH + ") REFERENCES " + FISH_TABLE + "(" + FISH_COLUMN_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LAKE_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + FISH_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + FISH_LAKE_TABLE + "");
        onCreate(db);
    }

    /**
     *
     * @param name
     * @param color
     * @param pattern
     * @param shape
     * @return
     */
    public boolean insertFish  (String name, String color, String pattern, String shape) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Color", color);
        contentValues.put("Pattern", pattern);
        contentValues.put("Shape", shape);
        db.insert(FISH_TABLE, null, contentValues);
        return true;
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean insertLake  (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        db.insert(LAKE_TABLE, null, contentValues);
        return true;
    }

    /**
     *
     * @param lake
     * @param fish
     * @return
     */
    public boolean addFishToLake  (String lake, String fish) {
        return addFishToLake(getLakeID(lake), getFishID(fish));
    }

    /**
     *
     * @param lake
     * @param fish
     * @return
     */
    public boolean addFishToLake  (String lake, int fish) {
        return addFishToLake(getLakeID(lake), fish);
    }

    /**
     *
     * @param lake
     * @param fish
     * @return
     */
    public boolean addFishToLake  (int lake, String fish) {
        return addFishToLake(lake, getFishID(fish));
    }

    /**
     *
     * @param lake
     * @param fish
     * @return
     */
    public boolean addFishToLake  (int lake, int fish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FISH_LAKE_COLUMN_LAKE, lake);
        contentValues.put(FISH_LAKE_COLUMN_FISH, fish);
        contentValues.put(FISH_LAKE_COLUMN_NUMBER, 0);
        db.insert(FISH_LAKE_TABLE, null, contentValues);
        return true;
    }

    /**
     *
     * @param lake
     * @return
     */
    public ArrayList getLakeData(String lake) {
        return getLakeData(getLakeID(lake));
    }

    /**
     *
     * @param lake
     * @return
     */
    public ArrayList getLakeData(int lake) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor l = dbRead.rawQuery("SELECT * FROM " + FISH_LAKE_TABLE + " WHERE " + FISH_LAKE_COLUMN_LAKE + "=" + lake, null);
        l.moveToFirst();

        while(l.isAfterLast() == false){
            array_list.add(l.getString(l.getColumnIndex(FISH_LAKE_COLUMN_FISH)));
            l.moveToNext();
        }
        return array_list;
    }

    /**
     *Used to retrieve Fish Data from the Database
     * @param name - The Name of the Fish being Queried
     * @return A String array with data in the order of {Name, Shape, Pattern, Color}
     */
    public String[] getFishData (String name) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        ArrayList<String[]> array_list = new ArrayList<String[]>();
        Cursor l = dbRead.rawQuery("SELECT * FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_NAME + "='" + name + "'", null);
        l.moveToFirst();
        return getFishData(Integer.valueOf(l.getString(l.getColumnIndex(FISH_COLUMN_ID))));
    }

    /**
     * Used to retrieve Fish Data from the Database
     * @param fish - The ID number of the Fish
     * @return A String array with data in the order of {Name, Shape, Pattern, Color}
     */
    public String[] getFishData(int fish) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor l = dbRead.rawQuery("SELECT * FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_ID + "=" + fish, null);
        l.moveToFirst();
        String[] toReturn = {
                (l.getString(l.getColumnIndex(FISH_COLUMN_NAME))),
                (l.getString(l.getColumnIndex(FISH_COLUMN_SHAPE))),
                (l.getString(l.getColumnIndex(FISH_COLUMN_PATTERN))),
                (l.getString(l.getColumnIndex(FISH_COLUMN_COLOR)))
        };
        return toReturn;
    }

    /**
     * Used to retrieve the name of the Lake which corresponds to the ID set for it.
     * @param id - ID number of the Lake
     * @return A String of the Lake's Name
     */
    public String getLakeName(int id) {
        SQLiteDatabase rd = this.getReadableDatabase();
        Cursor r = rd.rawQuery("SELECT " + LAKE_COLUMN_NAME + " FROM " + LAKE_TABLE + " WHERE " + LAKE_COLUMN_ID + "=" + id,null);
        r.moveToFirst();
        return r.getString(r.getColumnIndex(LAKE_COLUMN_NAME));
    }

    /**
     * Used to retrieve the ID of any Lake in the Database
     * @param name - String of the Name of the Lake
     * @return The ID number of the lake as an integer
     */
    public int getLakeID(String name) {
        SQLiteDatabase rd = this.getReadableDatabase();
        Cursor r = rd.rawQuery("SELECT " + LAKE_COLUMN_ID + " FROM " + LAKE_TABLE + " WHERE " + LAKE_COLUMN_NAME + "='" + name + "'",null);
        r.moveToFirst();
        return Integer.valueOf(r.getString(r.getColumnIndex(LAKE_COLUMN_ID)));
    }

    /**
     *
     * @param id
     * @return
     */
    public String getFishName(int id) {
        SQLiteDatabase rd = this.getReadableDatabase();
        Cursor r = rd.rawQuery("SELECT " + FISH_COLUMN_NAME + " FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_ID + "=" + id,null);
        r.moveToFirst();
        return r.getString(r.getColumnIndex(FISH_COLUMN_NAME));
    }

    /**
     *
     * @param name
     * @return
     */
    public int getFishID(String name) {
        SQLiteDatabase rd = this.getReadableDatabase();
        Cursor r = rd.rawQuery("SELECT " + FISH_COLUMN_ID + " FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_NAME + "='" + name + "'",null);
        r.moveToFirst();
        return Integer.valueOf(r.getString(r.getColumnIndex(FISH_COLUMN_ID)));
    }

    /**
     *
     * @return
     */
    public String[][] getAllFishInformation() {
        SQLiteDatabase rd = this.getReadableDatabase();
        Cursor allFish = rd.rawQuery("SELECT * FROM " + FISH_TABLE, null);
        String[][] toReturn = new String[allFish.getCount()][4];
        allFish.moveToFirst();
        for(int i = 0; i < toReturn.length; i++) {
            toReturn[i] = getFishData(Integer.valueOf(allFish.getString(allFish.getColumnIndex(FISH_COLUMN_ID))));
            allFish.moveToNext();
        }
        return toReturn;
    }
}
