package com.example.swp1sec;

import android.provider.BaseColumns;

public final class Databases {

    public static final class CreateDB implements BaseColumns {

        public static final String TITLE = "title";
        public static final String DATE = "date";

        public static final String _TABLENAME0 = "academic_calendar";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +TITLE+" text not null , "
                +DATE+" text not null);";
    }
}
