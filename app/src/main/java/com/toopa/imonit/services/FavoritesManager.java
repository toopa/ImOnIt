package com.toopa.imonit.services;

import com.toopa.imonit.database.DatabaseHelper;
import com.toopa.imonit.model.Task;

import java.util.List;

/**
 * Created by Toopa on 16/05/2015.
 */
public class FavoritesManager {

    private DatabaseHelper dbHelper;

    public FavoritesManager(final DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addFavorite(final Task task) {
        dbHelper.saveFavorite(task);
    }

    public void deleteFavorite(Task task) {
        dbHelper.deleteFavorite(task);
    }

    public List<Task> getFavorites() {
        return dbHelper.getFavorites();
    }
}
