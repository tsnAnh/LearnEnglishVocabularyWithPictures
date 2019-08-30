package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Utilities;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.VocabularyCategoryAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Categories;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoSession;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.VocabularyCategories;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.VocabularyCategoriesDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

public class VocabularyCategoryActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, AdapterView.OnItemClickListener, SpeedDialView.OnActionSelectedListener {

    private static DaoSession daoSession = App.getDaoSession();
    private ArrayList<Vocabulary> arr = new ArrayList<>();
    private Toolbar toolbar;
    private GridView gridView;
    private SpeedDialView speedDialView;
    static Categories categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_category);

        categories = getCategoryModelFromMainActivity();
        Log.e("ID", String.valueOf(categories.getId()));

        arr.addAll(getListVocabulary());

        toolbar = this.findViewById(R.id.toolbar_vocabulary_cat);
        gridView = this.findViewById(R.id.grid_vocabulary_cat);
        speedDialView = this.findViewById(R.id.speed_dial_vocabulary_cat);

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
        speedDialView.setOnActionSelectedListener(this);

        toolbar.setTitle("Vocabulary Categories");
        toolbar.setTitleTextColor(Color.BLACK);
        this.setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle(categories.getTitle());
        assert getSupportActionBar() != null;
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView.setOnItemClickListener(this);

        VocabularyCategoryAdapter adapter = new VocabularyCategoryAdapter(this, arr);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private Categories getCategoryModelFromMainActivity() {
        return getIntent().getParcelableExtra(Config.CATEGORY_KEY);
    }

    public static  ArrayList<Vocabulary> getListVocabulary() {
        QueryBuilder<Vocabulary> builder = daoSession.getVocabularyDao().queryBuilder();
        builder.join(VocabularyCategories.class, VocabularyCategoriesDao.Properties.VocabularyId)
                .where(VocabularyCategoriesDao.Properties.CategoryId.eq(categories.getId()));
        ArrayList<Vocabulary> arr = new ArrayList<>(builder.list());
        return arr;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_main, menu);
        MenuItem itemSearch = menu.findItem(R.id.menu_item_search_main);
        itemSearch.setOnMenuItemClickListener(this);
        MenuItem itemShare = menu.findItem(R.id.menu_item_share_main);
        itemShare.setOnMenuItemClickListener(this);
        MenuItem itemList = menu.findItem(R.id.menu_item_view_list_main);
        itemList.setVisible(false);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_share_main:
                Utilities.shareApplication(VocabularyCategoryActivity.this);
                break;
            case R.id.menu_item_search_main:
                startActivity(new Intent(VocabularyCategoryActivity.this, VocabularySearchActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, VocabularyActivity.class);
        intent.putExtra(Config.VOCABULARY_CAT_KEY, arr);
        intent.putExtra(Config.VOCABULARY_ID_KEY, i);
        startActivityForResult(intent, Config.VOCABULARY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Config.VOCABULARY_REQUEST_CODE) {
                int position = data.getIntExtra(Config.VOC_ID, arr.size());
                gridView.setSelection(position);
                arr.clear();
                arr.addAll(data.<Vocabulary>getParcelableArrayListExtra(Config.VOCABULARY_CAT_KEY));
            }
        }
    }

    @Override
    public boolean onActionSelected(SpeedDialActionItem actionItem) {
        int id = actionItem.getId();

        switch (id) {
            case R.id.action_favorite:
                startActivity(new Intent(this, FavoriteVocabularyActivity.class));
                speedDialView.close(true);
                break;
            case R.id.action_recent:
                break;
        }
        return true;
    }
}
