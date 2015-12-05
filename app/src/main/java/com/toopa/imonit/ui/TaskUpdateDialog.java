package com.toopa.imonit.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toopa.imonit.R;
import com.toopa.imonit.model.Task;
import com.toopa.imonit.model.TaskStatus;
import com.toopa.imonit.services.TasksManager;

/**
 * Created by Toopa on 24/02/2015.
 */
public class TaskUpdateDialog extends Dialog {

    private MainPageActivity parentActivity;

    private TextView dialogText;

    private Button iAmOnItButton;

    private Button iCanNotButton;

    private Button giveUpItemButton;

    private Button doneItemButton;

    private Button deleteItemButton;

    private int toDoItemIndexInList;

    private Task toDoItem;

    private final TasksManager tasksManager;

    public TaskUpdateDialog(final MainPageActivity parentActivity, final int itemIndexInToDoList, final Task toDoItem) {
        super(parentActivity);
        this.parentActivity = parentActivity;
        this.toDoItemIndexInList = itemIndexInToDoList;
        this.toDoItem = toDoItem;
        this.tasksManager = parentActivity.getTasksManager();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Creates a specific dialog, depending on the task status
        switch (toDoItem.getStatus()) {
            case POSTED:
                createPostedTaskDialog();
                break;
            case ASSIGNED:
                createAssignedTaskDialog();
                break;
            case COMPLETED:
                createCompletedTaskDialog();
                break;
        }

    }

    private void createPostedTaskDialog() {
        setContentView(R.layout.dialog_posted_task);
        setDialogText();
        configureIAmOnItButton();
        configureICanNotButton();
        configureDeleteItemButton();
    }

    private void createAssignedTaskDialog() {
        setContentView(R.layout.dialog_assigned_task);
        setDialogText();
        configureDoneItemButton();
        configureGiveUpItemButton();
        configureDeleteItemButton();
    }

    private void createCompletedTaskDialog() {
        setContentView(R.layout.dialog_completed_task);
        setDialogText();
        configureDeleteItemButton();
    }

    private void setDialogText() {
        dialogText = (TextView) findViewById(R.id.to_do_item_descriptor);
        dialogText.setText(toDoItem.getDescription());
    }

    private void configureIAmOnItButton() {
        iAmOnItButton = (Button) findViewById(R.id.i_am_on_it_button);
        defineOnClickForIAmOnItButton(iAmOnItButton);
    }

    private void configureICanNotButton() {
        iCanNotButton = (Button) findViewById(R.id.i_can_not_button);
        defineOnClickForICanNotButton(iCanNotButton);
    }

    private void configureGiveUpItemButton() {
        giveUpItemButton = (Button) findViewById(R.id.give_up_item_button);
        defineOnClickForICanNotButton(giveUpItemButton);
    }

    private void configureDoneItemButton() {
        doneItemButton = (Button) findViewById(R.id.done_item_button);
        defineOnClickForDoneItemButton(doneItemButton);
    }

    private void configureDeleteItemButton() {
        deleteItemButton = (Button) findViewById(R.id.delete_item_button);
        defineOnClickForDeleteItemButton(deleteItemButton);
    }

    private void defineOnClickForDoneItemButton(final Button doneItemButton) {
        doneItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksManager.updateTaskStatus(toDoItem, TaskStatus.COMPLETED);
                parentActivity.changeToDoItemStatus(toDoItemIndexInList, TaskStatus.COMPLETED);
                dismiss();
            }
        });
    }

    private void defineOnClickForIAmOnItButton(final Button iAmOnItButton) {
        iAmOnItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksManager.updateTaskStatus(toDoItem, TaskStatus.ASSIGNED);
                parentActivity.changeToDoItemStatus(toDoItemIndexInList, TaskStatus.ASSIGNED);
                dismiss();
            }
        });
    }

    private void defineOnClickForICanNotButton(final Button iCanNotButton) {
        iCanNotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksManager.updateTaskStatus(toDoItem, TaskStatus.POSTED);
                parentActivity.changeToDoItemStatus(toDoItemIndexInList, TaskStatus.POSTED);
                dismiss();
            }
        });
    }

    private void defineOnClickForDeleteItemButton(final Button deleteButton) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksManager.deleteTask(toDoItem);
                parentActivity.deleteToDoItem(toDoItemIndexInList);
                dismiss();
            }
        });
    }
}
