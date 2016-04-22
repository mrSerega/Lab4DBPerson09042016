package com.example.serega.lab4dbperson09042016;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Serega on 09.04.2016.
 */
public class DB {
    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "personBD";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MARK = "mark";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_MARK = 2;

    private SQLiteDatabase DB;

    public DB(Context context){
        OpenHelper OH = new OpenHelper(context);
        DB = OH.getWritableDatabase();
    }

    public long insert(String name, int mark){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_MARK, mark);
        return DB.insert(TABLE_NAME, null, cv);
    }

    public int update(Person p){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, p.get_name());
        cv.put(COLUMN_MARK, p.get_mark());
        return DB.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(p.get_id())});
    }

    public void deleteAll(){
        DB.delete(TABLE_NAME, null, null);
    }

    public void delete(long id){
        DB.delete(TABLE_NAME,COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Person selectFirst(long id){
        Cursor pCursor = DB.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        pCursor.moveToFirst();
        String name = pCursor.getString(NUM_COLUMN_NAME);
        int mark = pCursor.getInt(NUM_COLUMN_MARK);
        return new Person(id, name, mark);
    }

    public ArrayList<Person> selectAll(){
        Cursor pCursor = DB.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Person> arr = new ArrayList<Person>();
        pCursor.moveToFirst();
        if(!pCursor.isAfterLast()){
            do{
                long id = pCursor.getLong(NUM_COLUMN_ID);
                String name = pCursor.getString(NUM_COLUMN_NAME);
                int mark = pCursor.getInt(NUM_COLUMN_MARK);
                arr.add(new Person(id,name,mark));
            }while(pCursor.moveToNext());
        }
        return arr;
    }
}
