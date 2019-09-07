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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.GridAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.ICategory;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.ListAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Utilities;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Categories;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoSession;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, ICategory, AdapterView.OnItemClickListener, SpeedDialView.OnActionSelectedListener {

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_all_voc, R.drawable.round_format_list_bulleted_24)
                        .setFabBackgroundColor(Color.WHITE)
                        .setLabel("All Vocabularies")
                        .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_favorite, R.drawable.round_favorite_24)
                .setFabBackgroundColor(Color.WHITE)
                .setLabel("Favorites")
                .create());

        speedDialView.setOnActionSelectedListener(this);

        listView.setOnItemClickListener(this);
        gridView.setOnItemClickListener(this);
        displayList(listOrGrid);
    }



    @Override
    public void loadViewSettings() {
        sharedPreferences = getSharedPreferences(Config.SHARE_PREFERENCES_KEY, Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            listOrGrid = true;
        } else {
            listOrGrid = sharedPreferences.getBoolean(Config.SHARE_PREFERENCES_LIST_TYPE, true);
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
        MenuItem itemViewList = menu.findItem(R.id.menu_item_view_list_main);
        if (listOrGrid) {
            itemViewList.setIcon(R.drawable.round_view_list_24);
        } else {
            itemViewList.setIcon(R.drawable.round_view_module_24);
        }
        itemViewList.setOnMenuItemClickListener(this);

        MenuItem itemSearch = menu.findItem(R.id.menu_item_search_main);
        itemSearch.setOnMenuItemClickListener(this);

        MenuItem itemShare = menu.findItem(R.id.menu_item_share_main);
        itemShare.setOnMenuItemClickListener(this);

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.menu_item_view_list_main:
                displayList(!listOrGrid);

                if (!listOrGrid) {
                    listOrGrid = true;
                    menuItem.setIcon(R.drawable.round_view_list_24);
                } else {
                    listOrGrid = false;
                    menuItem.setIcon(R.drawable.round_view_module_24);
                }
                break;
            case R.id.menu_item_share_main:
                Utilities.shareApplication(MainActivity.this);
                break;
            case R.id.menu_item_search_main:
                startActivity(new Intent(MainActivity.this, VocabularySearchActivity.class));
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

        Intent intent = new Intent(this, VocabularyCategoryActivity.class);
        intent.putExtra(Config.CATEGORY_KEY, categories);
        intent.putExtra(Config.VOCABULARY_ALL, 0);
        startActivity(intent);
    }

    @Override
    public boolean onActionSelected(SpeedDialActionItem actionItem) {
        int id = actionItem.getId();

        switch (id) {
            case R.id.action_favorite:
                startActivity(new Intent(this, FavoriteVocabularyActivity.class));
                speedDialView.close(true);
                break;
            case R.id.action_all_voc:
                Intent intent = new Intent(this, VocabularyCategoryActivity.class);
                intent.putExtra(Config.VOCABULARY_ALL, 1);
                startActivity(intent);
                speedDialView.close(true);
                break;
        }
        return true;
    }
}
