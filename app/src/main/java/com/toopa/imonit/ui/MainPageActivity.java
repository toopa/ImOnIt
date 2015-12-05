package com.toopa.imonit.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.toopa.imonit.R;
import com.toopa.imonit.database.DatabaseHelper;
import com.toopa.imonit.model.Task;
import com.toopa.imonit.model.TaskStatus;
import com.toopa.imonit.services.FavoritesManager;
import com.toopa.imonit.services.TasksManager;

import java.util.List;


public class MainPageActivity extends ListActivity {

    private List<Task> toDoList;

    private ArrayAdapter<Task> toDoListAdapter;

    private DatabaseHelper dataBaseHelper;

    // Services
    private TasksManager tasksManager;
    private FavoritesManager favoritesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DB Initialization
        initializeDataBase();

        // Services Initialization
        initializeServices();

        // UI Initialization
        initializeUserInterface();
    }

    private void initializeDataBase() {
        dataBaseHelper = new DatabaseHelper(getApplication());
    }

    private void initializeServices() {
        tasksManager = new TasksManager(dataBaseHelper);
        favoritesManager = new FavoritesManager(dataBaseHelper);
    }

    private void initializeUserInterface() {
        setContentView(R.layout.activity_main);

        toDoList = tasksManager.getTasks();

        toDoListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoList);
        setListAdapter(toDoListAdapter);

        final ListView toDoListView = (ListView) findViewById(android.R.id.list);
        defineOnClickToDoListItems(toDoListView);

        final Button addItemButton = (Button) findViewById(R.id.main_activity_add_item_btn);
        defineOnClickForAddItemButton(addItemButton);
    }

    private void defineOnClickToDoListItems(final ListView toDoListView) {
        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayTaskUpdateDialog(position);
            }

            private void displayTaskUpdateDialog(final int position) {
                TaskUpdateDialog taskUpdateDialog = new TaskUpdateDialog(MainPageActivity.this, position, toDoList.get(position));
                taskUpdateDialog.show();
            }
        });
    }

    private void defineOnClickForAddItemButton(final Button addItemButton) {
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNewTaskDialog();
            }

            private void displayNewTaskDialog() {
                final NewTaskDialog newTaskDialog = new NewTaskDialog(MainPageActivity.this);
                newTaskDialog.show();
            }
        });
    }

    public void addToDoItem(final Task newToDoItem) {
        //dataBaseHelper.saveTask(newToDoItem);
        toDoList.add(newToDoItem);
        toDoListAdapter.notifyDataSetChanged();
    }

    public void changeToDoItemStatus(final int toDoItemIndex, final TaskStatus newStatus) {
        // TODO : Implement this
        toDoList.get(toDoItemIndex).setStatus(newStatus);
        toDoListAdapter.notifyDataSetChanged();
    }

    public void deleteToDoItem(final int toDoItemIndex) {
        toDoList.remove(toDoItemIndex);
        toDoListAdapter.notifyDataSetChanged();
    }

    public Task retrieveToDoItemByDescription(final String taskDescription) {
        Task result = null;
        for (Task toDoItem : this.toDoList) {
            if (toDoItem.getDescription().equals(taskDescription)) {
                result = toDoItem;
                break;
            }
        }
        return result;
    }

    public TasksManager getTasksManager() {
        return tasksManager;
    }

    public FavoritesManager getFavoritesManager() {
        return favoritesManager;
    }
}
