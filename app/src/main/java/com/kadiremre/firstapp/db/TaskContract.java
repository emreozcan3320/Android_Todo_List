package com.kadiremre.firstapp.db;

import android.provider.BaseColumns;

/**
 * Created by emre on 18.12.2017.
 */

public class TaskContract {
    public static final String DB_NAME = "com.kadiremre.firstapp.db";
    public static final int DB_VERSION = 2;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks_updated";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DATE = "date";


    }
}
