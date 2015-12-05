package com.toopa.imonit.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.toopa.imonit.R;
import com.toopa.imonit.model.Task;
import com.toopa.imonit.services.FavoritesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toopa on 31/05/2015.
 */
public class FavoritesDialog extends Dialog {

    private List<Task> favorites;

    private MainPageActivity parentActivity;

    private FavoritesManager favoritesManager;

    private ArrayAdapter<Task> favoritesListAdapter;

    public FavoritesDialog(final MainPageActivity parentActivity) {
        super(parentActivity);
        this.favorites = new ArrayList<>();
        this.parentActivity = parentActivity;
        this.favoritesManager = parentActivity.getFavoritesManager();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_favorites);

        favorites = favoritesManager.getFavorites();
        favoritesListAdapter = new ArrayAdapter<>(parentActivity, android.R.layout.simple_list_item_1, favorites);

        final ListView favoriteListView = (ListView) findViewById(R.id.favorite_list);
        favoriteListView.setAdapter(favoritesListAdapter);
        defineOnClickFavoriteItem(favoriteListView);
        defineOnLongClickFavoriteItem(favoriteListView);

        favoritesListAdapter.notifyDataSetChanged();
    }

    private void defineOnClickFavoriteItem(final ListView favoriteListView) {
        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Task task = favorites.get(position);
                if (taskAlreadyExists(task.getDescription())) {
                    new DuplicatedTaskDialog(parentActivity).show();
                } else {
                    parentActivity.addToDoItem(task);
                }
                dismiss();
            }

            private boolean taskAlreadyExists(final String taskDescription) {
                return parentActivity.retrieveToDoItemByDescription(taskDescription) != null;
            }
        });
    }

    private void defineOnLongClickFavoriteItem(final ListView favoriteListView) {
        favoriteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                favoritesManager.deleteFavorite(favorites.get(position));
                dismiss();
                return true;
            }
        });
    }
}
