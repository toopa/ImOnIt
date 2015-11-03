package com.toopa.imonit.ui;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.toopa.imonit.R;
import com.toopa.imonit.database.DatabaseHelper;
import com.toopa.imonit.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toopa on 31/05/2015.
 */
public class FavoritesDialog extends Dialog {

    final private List<Task> favorites = new ArrayList<Task>();

    private MainPageActivity parentActivity;

    private ArrayAdapter<Task> favoritesListAdapter;

    DatabaseHelper databaseHelper;

    public FavoritesDialog(final MainPageActivity parentActivity) {
        super(parentActivity);
        this.parentActivity = parentActivity;
        this.databaseHelper = parentActivity.getDataBaseHelper();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_favorites);

        List<Task> favorites = databaseHelper.getFavorites();
        favoritesListAdapter = new ArrayAdapter<Task>(parentActivity,android.R.layout.simple_list_item_1,favorites);

        final ListView favoriteListView = (ListView) findViewById(R.id.favorite_list);
        favoriteListView.setAdapter(favoritesListAdapter);

        favoritesListAdapter.notifyDataSetChanged();
    }
}
