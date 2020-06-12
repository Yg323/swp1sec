package com.example.swp1sec;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        //생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override//테이블 생성. 변수명을 달리하면 여리 테이블 생성 가능.
        public void onCreate(SQLiteDatabase db){
            db.execSQL(Databases.CreateDB._CREATE0);
        }

        @Override//버전 업그레이드 시 사용
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+Databases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }
    //해당 데이터베이스를 열어서 사용할 수 있게 해줌.
    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }
    //해당 데이터베이스를 닫는다.
    public void close(){
        mDB.close();
    }

    //INSERT
    public long insertColumn(String title, String date){
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.TITLE, title);
        values.put(Databases.CreateDB.DATE, date);
        return mDB.insert(Databases.CreateDB._TABLENAME0, null, values);
    }



}
