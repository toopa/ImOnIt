package com.toopa.imonit.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.toopa.imonit.R;

/**
 * Created by Toopa on 26/02/2015.
 */
public class DuplicatedTaskDialog extends Dialog {

    private Button okButton;

    public DuplicatedTaskDialog(final MainPageActivity parentActivity) {
        super(parentActivity);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_duplicated_task);

        okButton = (Button) findViewById(R.id.duplicated_task_dialog_ok_button);
        defineOnClickForOkButton();
    }

    private void defineOnClickForOkButton() {
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
