package com.toopa.imonit.services;

import com.toopa.imonit.database.DatabaseHelper;
import com.toopa.imonit.model.Task;
import com.toopa.imonit.model.TaskStatus;

import java.util.List;

/**
 * Created by Toopa on 29/11/2015.
 */
public class TasksManager {

    private DatabaseHelper dbHelper;

    public TasksManager(final DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addTask(final Task task) {
        dbHelper.saveTask(task);
    }

    public void deleteTask(Task task) {
        dbHelper.deleteTask(task);
    }

    public void updateTaskStatus(Task task, TaskStatus taskStatus) {
        dbHelper.updateTaskStatus(task, taskStatus);
    }

    public List<Task> getTasks() {
        return dbHelper.getTasks();
    }
}
