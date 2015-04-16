package com.russellborja.saladbowl.data;

import android.provider.BaseColumns;

/**
 * Created by russellborja on 15-04-10.
 */
public class Contract {

    public static final int COL_NAME = 2;
    public static final int COL_TEAM = 2;
    public static final int COL_WORDS= 3;

    public static final class NameEntry implements BaseColumns{

        public static final String TABLE_NAME = "saladbowl";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_TEAM = "team";

        public static final String COLUMN_WORDS = "words";

    }
}
