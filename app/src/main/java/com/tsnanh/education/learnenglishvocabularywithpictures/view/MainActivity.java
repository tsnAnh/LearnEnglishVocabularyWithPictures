package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.GridAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.ICategory;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.ListAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Categories;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoSession;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, ICategory, AdapterView.OnItemClickListener {

    private DaoSession daoSession = App.getDaoSession();
    private GridView gridView;
    private ListView listView;
    private SpeedDialView speedDialView;
    private ArrayList<Categories> arr = new ArrayList<>();
    private boolean listOrGrid = true; // Grid == true
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr.addAll(daoSession.getCategoriesDao().queryRaw("where intro_img IS NOT NULL order by title asc"));

        loadViewSettings();

        toolbar = this.findViewById(R.id.toolbar_main);
        speedDialView = this.findViewById(R.id.speedDial);
        gridView = this.findViewById(R.id.grid_main);
        listView = this.findViewById(R.id.list_main);

        toolbar.setTitle("Vocabulary Categories");
        toolbar.setTitleTextColor(Color.BLACK);
        this.setSupportActionBar(toolbar);

        speedDialView.setMainFabOpenedBackgroundColor(Color.WHITE);
        speedDialView.setMainFabClosedBackgroundColor(Color.WHITE);
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_favorite, R.drawable.round_favorite_24)
                .setFabBackgroundColor(Color.WHITE)
                .setLabel("Favorites")
                .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_recent, R.drawable.round_timer_24)
                .setFabBackgroundColor(Color.WHITE)
                .setLabel("Recent")
                .create());

        listView.setOnItemClickListener(this);
        displayList(listOrGrid);
    }

    @Override
    public void loadViewSettings() {
        sharedPreferences = getSharedPreferences("LearnEnglishVocabularyWithPictures",Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            listOrGrid = true;
        } else {
            listOrGrid = sharedPreferences.getBoolean("LIST_TYPE", true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LIST_TYPE", listOrGrid);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_main, menu);
        MenuItem itemViewList = menu.findItem(R.id.menu_item_view_list);
        if (listOrGrid) {
            itemViewList.setIcon(R.drawable.round_view_list_24);
        } else {
            itemViewList.setIcon(R.drawable.round_view_module_24);
        }
        itemViewList.setOnMenuItemClickListener(this);

        MenuItem itemSearch = menu.findItem(R.id.menu_item_search_main);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setElevation(0);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.menu_item_view_list:
                displayList(!listOrGrid);

                if (!listOrGrid) {
                    listOrGrid = true;
                    menuItem.setIcon(R.drawable.round_view_list_24);
                } else {
                    listOrGrid = false;
                    menuItem.setIcon(R.drawable.round_view_module_24);
                }
                break;
        }
        return false;
    }

    @Override
    public void displayList(boolean listOrGrid) {
        if (listOrGrid) {
            GridAdapter gridAdapter = new GridAdapter(this, arr);
            gridView.setAdapter(gridAdapter);
            gridView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            ListAdapter listAdapter = new ListAdapter(this, arr);
            listView.setAdapter(listAdapter);
            listView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Categories categories = arr.get(i);

        Bundle bundle = new Bundle();
        bundle.putLong(Config.CATEGORY_ID_KEY, categories.getId());
        startActivity(new Intent(this, VocabularyCategoryActivity.class), bundle);
    }
}
