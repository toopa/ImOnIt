package com.toopa.imonit.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.toopa.imonit.R;
import com.toopa.imonit.model.Task;

/**
 * Created by Toopa on 25/02/2015.
 */
public class NewTaskDialog extends Dialog {

    private MainPageActivity parentActivity;

    private EditText toDoItemDescriptionField;

    private Button favoritesButton;
    
    private Button createItemButton;

    public NewTaskDialog(final MainPageActivity parentActivity) {
        super(parentActivity);
        this.parentActivity = parentActivity;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_task);

        toDoItemDescriptionField = (EditText) findViewById(R.id.to_do_item_descriptor);

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
            }
        });
    }

    private void defineOnClickForCreateItemButton() {
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String taskDescription = toDoItemDescriptionField.getText().toString().toLowerCase();
                if(!isValidTaskDescription(taskDescription)) {
                    return;
                }
                if(taskAlreadyExists(taskDescription)) {
                    final DuplicatedTaskDialog duplicatedTaskDialog = new DuplicatedTaskDialog(parentActivity);
                    duplicatedTaskDialog.show();
                    dismiss();
                    return;
                }
                parentActivity.addToDoItem(new Task(taskDescription));
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