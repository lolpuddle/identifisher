package sql;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christopher on 2016-03-30.
 */
public class sqLite {

    public sqLite() {
        String sqlCommand = "CREATE TABLE " + Contract.FeedEntry.TABLE_NAME + " ("
                + "ID INT PRIMARY KEY NOT NULL, " + Contract.FeedEntry.FISH_NAME_TITLE + " TEXT NOT NULL,"
                + Contract.FeedEntry.FISH_COLOR_TITLE + " TEXT NOT NULL,"
                + Contract.FeedEntry.FISH_PATTERN_TITLE + " TEXT NOT NULL,"
                + Contract.FeedEntry.FISH_SHAPE_TITLE + " TEXT NOT NULL)";

        SQLiteDatabase sql = new ;
    }
}
