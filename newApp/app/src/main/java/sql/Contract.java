package sql;

import android.provider.BaseColumns;

/**
 * Created by Christopher on 2016-03-30.
 */
public class Contract {

    public Contract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Fish";

        public static final String FISH_NAME_TITLE = "Name";
        public static final String FISH_NAME_SUBTITLE = "Fish's Name";

        public static final String FISH_COLOR_TITLE = "Color";
        public static final String FISH_COLOR_SUBTITLE = "Fish's Color";

        public static final String FISH_SHAPE_TITLE = "Shape";
        public static final String FISH_SHAPE_SUBTITLE = "Fish's Shape";

        public static final String FISH_PATTERN_TITLE = "Pattern";
        public static final String FISH_PATTERN_SUBTITLE = "Fish's Pattern";
    }
}
