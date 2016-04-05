package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/**
 * Database Controller Class
 * All functions regarding changing the Database SHOULD be through here, for high cohesion/low coupling.
 *
 * Author: Christopher McDonald
 *
 * Created by Christopher on 2016-04-02.
 */
public class DBHelper extends SQLiteOpenHelper {

    /* DB Schema
    *       lakeNames - Table to hold solely Lake Info
    *                   Contains: ID - Number PRIMARY
    *                             Name - String
    *       fishNames - Table to hold solely Fish Info
    *                   Contains: ID - Number PRIMARY
    *                             Name - String
    *                             Color - String
    *                             Pattern - String
    *                             Shape - String
    *
    *       lakeAndFish - Table to hold number of Fish per Lake
    *                   Contains: Lake - Number
    *                             Fish - Number
    *                             Number - Int
    *
    * TODO Changing Primary key for lakeNames and fishNames to include the name while maintaing autoincrement
    * */

    /* ----------------- Database Naming ----------------- */
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

    /**
     * This method is ran when a Database is CREATED, ie. when the User has none installed on the Device
     * @param db - SQLiteDatabase, the Database created
     */
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

    /**
     * This method is ran when a Database is UPGRADED, ie. when the User has one installed on the Device, and it is replaced
     * @param db - SQLiteDatabase, the Database created
     * @param oldVersion - int, number to represent the old database
     * @param newVersion - int, number to represent the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LAKE_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + FISH_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + FISH_LAKE_TABLE + "");
        onCreate(db);
    }

    /**
     * Provides the means for Adding a New Fish into the Database
     * @param name - Name of the Fish, can be scientific or normal
     * @param color - Color of the Fish, should be kept simple (ex. Brown, Silver, Yellow Silver... etc.)
     * @param pattern - Pattern of the Fish, should be kept simple (ex. Elongated, Fusiform... etc.)
     * @param shape - Shape of the Fish, should be kept simple (ex. See others)
     * @return Boolean - True for successful entries, False for a failed entry
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
     * Provides the means for adding a Lake to the database
     *
     * TODO Test for names including special characters such as apos.
     * @param name - Name of the Lake being added, can include spaces
     * @return Boolean - True is successful entry, False for failed
     */
    public boolean insertLake  (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        db.insert(LAKE_TABLE, null, contentValues);
        return true;
    }

    /**
     * Provides means for adding an instance of a fish to a Lake in the Database
     *
     * @param lake - String, name of the Lake. Must exist in lakeTables
     * @param fish - String, name of the Fish. Must exist in fishTables
     * @return Boolean - True is successful entry, False for failed
     */
    public boolean addFishToLake  (String lake, String fish) {
        return addFishToLake(getLakeID(lake), getFishID(fish));
    }

    /**
     * Provides means for adding an instance of a fish to a Lake in the Database
     *
     * @param lake String, name of the Lake. Must exist in lakeTables
     * @param fish Integer, ID of the Fish. Must exist in fishTables
     * @return Boolean - True is successful entry, False for failed
     */
    public boolean addFishToLake  (String lake, int fish) {
        return addFishToLake(getLakeID(lake), fish);
    }

    /**
     * Provides means for adding an instance of a fish to a Lake in the Database
     *
     * @param lake - Integer, ID of the Lake. Must exist in lakeTables
     * @param fish - String, name of the Fish. Must exist in fishTables
     * @return Boolean - True is successful entry, False for failed
     */
    public boolean addFishToLake  (int lake, String fish) {
        return addFishToLake(lake, getFishID(fish));
    }

    /**
     * Provides means for adding an instance of a fish to a Lake in the Database
     *
     * @param lake - Integer, ID of the Lake. Must exist in lakeTables
     * @param fish - Integer, ID of the Fish. Must exist in fishTables
     * @return Boolean - True is successful entry, False for failed
     *
     * TODO Query the database, get the current number and update it with a +1
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
     * Provides the means of getting a List of Fish names and how many have been caught there
     *
     * @param lake - Name of the lake being queried
     * @return String[][] - List of 2-length arrays with [Name,Number]
     */
    public String[][] getLakeData(String lake) {
        return getLakeData(getLakeID(lake));
    }

    /**
     * Provides the means of getting a List of Fish names and how many have been caught there
     *
     * @param lake - Name of the lake being queried
     * @return String[][] - List of 2-length arrays with [Name,Number]
     */
    public String[][] getLakeData(int lake) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        ArrayList<String[]> array_list = new ArrayList<String[]>();
        Cursor l = dbRead.rawQuery("SELECT * FROM " + FISH_LAKE_TABLE + " WHERE " + FISH_LAKE_COLUMN_LAKE + "=" + lake, null);
        l.moveToFirst();
        String[] fishNumber = new String[2];

        while(l.isAfterLast() == false){
            fishNumber[0] = l.getString(l.getColumnIndex(FISH_LAKE_COLUMN_FISH));
            fishNumber[1] = l.getString(l.getColumnIndex(FISH_LAKE_COLUMN_NUMBER));
            array_list.add(fishNumber);
            l.moveToNext();
        }
        return array_list.toArray(new String[array_list.size()][2]);
    }

    /**
     * Used to retrieve Fish Data from the Database
     * @param name - The Name of the Fish being Queried
     * @return A String array with data in the order of {Name, Shape, Pattern, Color}
     */
    public String[] getFishData (String name) {
        return getFishData(getFishID(name));
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
     * Provides the means of getting a name of the Fish from a number ID
     *
     * @param id - ID of the fish wished to received the name of
     * @return String - name of the fish
     */
    public String getFishName(int id) {
        SQLiteDatabase rd = this.getReadableDatabase();
        Cursor r = rd.rawQuery("SELECT " + FISH_COLUMN_NAME + " FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_ID + "=" + id,null);
        r.moveToFirst();
        return r.getString(r.getColumnIndex(FISH_COLUMN_NAME));
    }

    /**
     * Provides means of getting the ID of a fish using the name
     *
     * @param name - Name of the fish wished to received the name of
     * @return int - ID of the fish
     */
    public int getFishID(String name) {
        SQLiteDatabase rd = this.getReadableDatabase();
        Cursor r = rd.rawQuery("SELECT " + FISH_COLUMN_ID + " FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_NAME + "='" + name + "'",null);
        r.moveToFirst();
        return Integer.valueOf(r.getString(r.getColumnIndex(FISH_COLUMN_ID)));
    }

    /**
     * Provides the means of getting all the Fish Data from the database, to be used by experts
     *
     * @return String[][] list of Fish information in the format {Name, Color, Shape, Pattern}
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
