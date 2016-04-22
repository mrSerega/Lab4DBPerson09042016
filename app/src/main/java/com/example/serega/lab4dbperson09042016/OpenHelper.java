package com.example.serega.lab4dbperson09042016;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Serega on 09.04.2016.
 */
public class OpenHelper extends SQLiteOpenHelper {

    //TODO: передаавть через конструктор
    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "personBD";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MARK = "mark";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_MARK = 2;

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "create table "+ TABLE_NAME + " ("+
                COLUMN_ID + " integer primary key autoincrement, "+
                COLUMN_NAME+ " text, "+
                COLUMN_MARK+ " int);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("drop table if exist "+TABLE_NAME);
        onCreate(db);
    }
}
