package com.toopa.imonit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.toopa.imonit.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Toopa on 05/04/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    private static final String DATABASE_NAME = "ImOnItDB";

    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_FAVORITES = "favorites" ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_FAVORITES+"(id VARCHAR,description VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveFavorite(Task favorite){
        database.insert(TABLE_FAVORITES,null,new ContentValues());
    }

    public List<Task> getFavorites() {
        final List<Task> favorites = new ArrayList<Task>();
        final Cursor cursor = database.query(TABLE_FAVORITES, null, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            favorites.add(cursorToTask(cursor));
        }
        return favorites;
    }

    private Task cursorToTask(Cursor cursor) {
        return new Task(UUID.fromString(cursor.getString(0)), cursor.getString(1));
    }
}
