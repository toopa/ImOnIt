package com.toopa.imonit.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.toopa.imonit.database.DatabaseHelper;
import com.toopa.imonit.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toopa on 16/05/2015.
 */
public class FavoritesManager {

    private DatabaseHelper dbHelper;

    private final ContentValues values = new ContentValues();

    public void addFavorite(final Task task){
        dbHelper.saveFavorite(task);
    }

    public List<Task> getFavorites(){
        return dbHelper.getFavorites();
    }
}
