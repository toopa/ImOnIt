package com.toopa.imonit.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.toopa.imonit.R;
import com.toopa.imonit.model.Task;
import com.toopa.imonit.services.FavoritesManager;
import com.toopa.imonit.services.TasksManager;

/**
 * Created by Toopa on 25/02/2015.
 */
public class NewTaskDialog extends Dialog {

    private MainPageActivity parentActivity;

    private EditText toDoItemDescriptionField;

    private CheckBox addToFavoritesCheckBox;

    private Button favoritesButton;

    private Button createItemButton;

    private final TasksManager tasksManager;
    private final FavoritesManager favoritesManager;

    public NewTaskDialog(final MainPageActivity parentActivity) {
        super(parentActivity);
        this.parentActivity = parentActivity;
        this.tasksManager = parentActivity.getTasksManager();
        this.favoritesManager = parentActivity.getFavoritesManager();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_task);

        toDoItemDescriptionField = (EditText) findViewById(R.id.to_do_item_descriptor);

        addToFavoritesCheckBox = (CheckBox) findViewById(R.id.add_to_favorite_checkbox);

        favoritesButton = (Button) findViewById(R.id.from_favorites_button);
        defineOnClickForFavoritesButton();

        createItemButton = (Button) findViewById(R.id.create_item_button);
        defineOnClickForCreateItemButton();
    }

    private void defineOnClickForFavoritesButton() {
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFavoritesDialog();
            }

            private void displayFavoritesDialog() {
                FavoritesDialog favoritesDialog = new FavoritesDialog(parentActivity);
                favoritesDialog.show();
                dismiss();
            }
        });
    }

    private void defineOnClickForCreateItemButton() {
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String taskDescription = toDoItemDescriptionField.getText().toString().toLowerCase();
                if (!isValidTaskDescription(taskDescription)) {
                    return;
                }
                if (taskAlreadyExists(taskDescription)) {
                    new DuplicatedTaskDialog(parentActivity).show();
                    dismiss();
                    return;
                }
                final Task task = new Task(taskDescription);
                if (addToFavoritesCheckBox.isChecked()) {
                    favoritesManager.addFavorite(task);
                }
                tasksManager.addTask(task);
                parentActivity.addToDoItem(task);
                dismiss();
            }

            private boolean taskAlreadyExists(final String taskDescription) {
                return parentActivity.retrieveToDoItemByDescription(taskDescription) != null;
            }

            private boolean isValidTaskDescription(final String taskDescription) {
                return taskDescription != null && !"".equals(taskDescription);
            }
        });
    }
}
