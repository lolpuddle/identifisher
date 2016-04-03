package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                " (" + FISH_LAKE_COLUMN_LAKE + " integer, " + FISH_LAKE_COLUMN_FISH + " integer " + LAKE_COLUMN_NAME + " integer)");
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
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor l = dbRead.rawQuery("SELECT " + LAKE_COLUMN_ID +" FROM " + LAKE_TABLE + " WHERE " + LAKE_COLUMN_NAME + "=" + lake, null);
        Cursor f = dbRead.rawQuery("SELECT " + LAKE_COLUMN_ID +" FROM " + LAKE_TABLE + " WHERE " + LAKE_COLUMN_NAME + "=" + lake, null);
        return addFishToLake(Integer.valueOf(l.getString(0)), Integer.valueOf(f.getString(0)));
    }

    /**
     *
     * @param lake
     * @param fish
     * @return
     */
    public boolean addFishToLake  (String lake, int fish) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor l = dbRead.rawQuery("SELECT " + LAKE_COLUMN_ID +" FROM " + LAKE_TABLE + "WHERE " + LAKE_COLUMN_NAME + "=" + lake, null);
        return addFishToLake(Integer.valueOf(l.getString(0)), fish);
    }

    /**
     *
     * @param lake
     * @param fish
     * @return
     */
    public boolean addFishToLake  (int lake, String fish) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor f = dbRead.rawQuery("SELECT " + LAKE_COLUMN_ID +" FROM " + LAKE_TABLE + "WHERE " + LAKE_COLUMN_NAME + "=" + lake, null);
        return addFishToLake(lake, Integer.valueOf(f.getString(0)));
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
        db.insert(FISH_LAKE_TABLE, null, contentValues);
        return true;
    }

    /**
     *
     * @param lake
     * @return
     */
    public ArrayList getLakeData(String lake) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor lakeName = dbRead.rawQuery("SELECT " + LAKE_COLUMN_ID +" FROM " + LAKE_TABLE + " WHERE " + LAKE_COLUMN_NAME + "='" +  lake + "'", null);
        lakeName.moveToFirst();
        return getLakeData(Integer.valueOf(lakeName.getString(lakeName.getColumnIndex(LAKE_COLUMN_ID))));
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
            array_list.add(l.getString(l.getColumnIndex(FISH_LAKE_COLUMN_NUMBER)));
            l.moveToNext();
        }
        return array_list;
    }

    /**
     *
     * @param name
     * @return
     */
    public ArrayList getFishData (String name) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        ArrayList<String[]> array_list = new ArrayList<String[]>();
        Cursor l = dbRead.rawQuery("SELECT * FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_NAME + "='" + name + "'", null);
        l.moveToFirst();
        return getFishData(Integer.valueOf(l.getString(l.getColumnIndex(FISH_COLUMN_ID))));
    }

    /**
     *
     * @param fish
     * @return
     */
    public ArrayList getFishData(int fish) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        ArrayList<String[]> array_list = new ArrayList<String[]>();
        Cursor l = dbRead.rawQuery("SELECT * FROM " + FISH_TABLE + " WHERE " + FISH_COLUMN_ID + "=" + fish, null);
        l.moveToFirst();
        String[] temp = new String[4];
        while(l.isAfterLast() == false) {
            temp[0] = (l.getString(l.getColumnIndex(FISH_COLUMN_NAME)));
            temp[1] = (l.getString(l.getColumnIndex(FISH_COLUMN_SHAPE)));
            temp[2] = (l.getString(l.getColumnIndex(FISH_COLUMN_PATTERN)));
            temp[3] = (l.getString(l.getColumnIndex(FISH_COLUMN_COLOR)));
            array_list.add(temp);
            l.moveToNext();
        }
        return array_list;
    }


}
