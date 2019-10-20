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
    VocabularyCategoryAdapter adapter;
    int isAll = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_category);

        getIntent().getExtras().getInt(Config.VOCABULARY_ALL);

        if (getCategoryModelFromMainActivity() == null) {
            isAll = 1;
            arr.addAll(daoSession.getVocabularyDao().loadAll());
        } else {
            categories = getCategoryModelFromMainActivity();
            Log.e("ID", String.valueOf(categories.getId()));
            arr.addAll(getListVocabulary());
        }

        toolbar = this.findViewById(R.id.toolbar_vocabulary_cat);
        gridView = this.findViewById(R.id.grid_vocabulary_cat);
        speedDialView = this.findViewById(R.id.speed_dial_vocabulary_cat);

        speedDialView.setMainFabOpenedBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)));
        speedDialView.setMainFabClosedBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)));
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_all_voc, R.drawable.round_format_list_bulleted_24)
                        .setFabBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                        .setLabel("All Vocabularies")
                        .setLabelBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                        .setLabelColor(Color.WHITE)
                        .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_favorite, R.drawable.round_favorite_24)
                        .setFabBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                        .setLabel("Favorites")
                        .setLabelBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                        .setLabelColor(Color.WHITE)
                        .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_test_vocabulary, R.drawable.round_subject_24)
                .setLabelClickable(false)
                .setLabel("Test Your Vocabulary")
                        .setLabelBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                        .setLabelColor(Color.WHITE)
                .setFabBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                .create());
        // TODO future update 1.2.0
//        speedDialView
//                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_matching_game, R.drawable.round_games_24)
//                        .setFabBackgroundColor(Color.WHITE)
//                        .setLabel("Matching Game")
//                        .setLabelClickable(false)
//                        .create());

        speedDialView.setOnActionSelectedListener(this);

        if (isAll == 1) {
            toolbar.setTitle("All Vocabularies");
            speedDialView.setVisibility(View.GONE);
        } else {
            toolbar.setTitle(categories.getTitle());
        }
        this.setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView.setOnItemClickListener(this);

        adapter = new VocabularyCategoryAdapter(this, arr);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private Categories getCategoryModelFromMainActivity() {
        return getIntent().getParcelableExtra(Config.CATEGORY_KEY) == null ? null : (Categories) getIntent().getParcelableExtra(Config.CATEGORY_KEY);
    }

    public ArrayList<Vocabulary> getListVocabulary() {
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
        Intent intent;
        if (isAll == 1) {
            intent = new Intent(this, VocabularyAll.class);
            intent.putExtra(Config.VOCABULARY_ALL, arr.get(i));
            intent.putExtra("isSearch", false);
            startActivity(intent);
        } else if (isAll == 0){
            intent = new Intent(this, VocabularyActivity.class);
            intent.putExtra(Config.VOCABULARY_CAT_KEY, arr);
            intent.putExtra(Config.VOCABULARY_ID_KEY, i);
            startActivityForResult(intent, Config.VOCABULARY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Config.VOCABULARY_REQUEST_CODE) {
                int position = data.getIntExtra(Config.VOC_ID, arr.size());
                gridView.setSelection(position);
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
            case R.id.action_all_voc:
                Intent intent = new Intent(this, VocabularyCategoryActivity.class);
                intent.putExtra(Config.VOCABULARY_ALL, 1);
                startActivity(intent);
                speedDialView.close(true);
                break;
            case R.id.action_test_vocabulary:
                Intent intent2 = new Intent(this, TestVocabularyActivity.class);
                intent2.putExtra(Config.VOCABULARY_CAT_KEY, arr);
                intent2.putExtra("name_cat", categories.getTitle());
                startActivity(intent2);
                speedDialView.close(true);
                // TODO later bitch
//            case R.id.action_matching_game:
//                Intent intent1 = new Intent(this, MatchingGameActivity.class);
//                intent1.putExtra(Config.VOCABULARY_CAT_KEY, arr);
//                intent1.putExtra("name_cat", categories.getTitle());
//                startActivity(intent1);
        }
        return true;
    }
}
