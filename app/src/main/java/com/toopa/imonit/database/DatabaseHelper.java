package com.toopa.imonit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.toopa.imonit.model.Task;
import com.toopa.imonit.model.TaskStatus;

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

    public static final String TABLE_FAVORITES = "favorites";
    public static final String TABLE_TASKS = "tasks";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
        onCreate(database);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "(id VARCHAR,description VARCHAR,status VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITES + "(description VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveTask(Task task) {
        final ContentValues values = new ContentValues();
        values.put("id", task.getId().toString());
        values.put("description", task.getDescription());
        values.put("status", task.getStatus().toString());
        database.insert(TABLE_TASKS, null, values);
    }

    public void updateTaskStatus(Task task, TaskStatus taskStatus) {
        deleteTask(task);
        saveTask(new Task(task.getId(), task.getDescription(), taskStatus));
    }

    public void deleteTask(Task task) {
        database.delete(TABLE_TASKS, "description = ?", new String[]{task.getDescription()});
    }

    public List<Task> getTasks() {
        final List<Task> tasks = new ArrayList<>();
        final Cursor cursor = database.query(TABLE_TASKS, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tasks.add(taskCursorToTask(cursor));
            cursor.moveToNext();
        }
        return tasks;
    }

    private Task taskCursorToTask(Cursor cursor) {
        final Task task = new Task(UUID.fromString(cursor.getString(0)), cursor.getString(1));
        task.setStatus(TaskStatus.valueOf(cursor.getString(2)));
        return task;
    }

    public void saveFavorite(Task favorite) {
        final ContentValues values = new ContentValues();
        values.put("description", favorite.getDescription());
        database.insert(TABLE_FAVORITES, null, values);
    }

    public void deleteFavorite(Task task) {
        database.delete(TABLE_FAVORITES, "description = ?", new String[]{task.getDescription()});
    }

    public List<Task> getFavorites() {
        final List<Task> favorites = new ArrayList<>();
        final Cursor cursor = database.query(TABLE_FAVORITES, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favorites.add(favoriteCursorToTask(cursor));
            cursor.moveToNext();
        }
        return favorites;
    }

    private Task favoriteCursorToTask(Cursor cursor) {
        return new Task(cursor.getString(0));
    }
}
