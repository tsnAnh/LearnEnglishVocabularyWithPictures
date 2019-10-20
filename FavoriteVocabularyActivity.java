package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.FavoriteAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.GridAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Utilities;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoSession;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.VocabularyDao;

import java.util.ArrayList;

public class FavoriteVocabularyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private Toolbar toolbar;
    private FavoriteAdapter favoriteAdapter;
    private DaoSession daoSession = App.getDaoSession();
    private ArrayList<Vocabulary> arrFavorite = new ArrayList<>();
    private TextView lblNoFav;

    @Override
    protected void onRestart() {
        super.onRestart();
        arrFavorite.clear();
        arrFavorite.addAll(daoSession.getVocabularyDao().queryBuilder().where(VocabularyDao.Properties.Liked.eq(1)).list());

        if (arrFavorite.size() <= 0) {
            gridView.setVisibility(View.GONE);
            lblNoFav.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.VISIBLE);
            lblNoFav.setVisibility(View.GONE);
            favoriteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_vocabulary);

        toolbar = this.findViewById(R.id.toolbar_favorite);
        gridView = this.findViewById(R.id.grid_favorite);
        lblNoFav = this.findViewById(R.id.lbl_no_favorite);

        gridView.setOnItemClickListener(this);
        toolbar.setTitle("Favorite");
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrFavorite.addAll(daoSession.getVocabularyDao().queryBuilder().where(VocabularyDao.Properties.Liked.eq(1)).list());

        if (arrFavorite.size() <= 0) {
            gridView.setVisibility(View.GONE);
            lblNoFav.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.VISIBLE);
            lblNoFav.setVisibility(View.GONE);
            favoriteAdapter = new FavoriteAdapter(this, arrFavorite);
            gridView.setAdapter(favoriteAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_main, menu);

        MenuItem itemListType = menu.findItem(R.id.menu_item_view_list_main);
        itemListType.setVisible(false);
        MenuItem itemSearch = menu.findItem(R.id.menu_item_search_main);
        itemSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(FavoriteVocabularyActivity.this, VocabularySearchActivity.class));
                return true;
            }
        });
        MenuItem itemShare = menu.findItem(R.id.menu_item_share_main);
        itemShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Utilities.shareApplication(FavoriteVocabularyActivity.this);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, VocabularyActivity.class);
        intent.putExtra(Config.VOCABULARY_CAT_KEY, arrFavorite);
        intent.putExtra(Config.VOCABULARY_ID_KEY, i);
        startActivityForResult(intent, Config.VOCABULARY_FAVORITE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Config.VOCABULARY_FAVORITE_REQUEST_CODE) {
                int position = data.getIntExtra(Config.VOC_ID, arrFavorite.size());
                gridView.setSelection(position);
                arrFavorite.clear();
                arrFavorite.addAll(data.<Vocabulary>getParcelableArrayListExtra(Config.VOCABULARY_CAT_KEY));
                for (Vocabulary vocabulary:
                        arrFavorite) {
                    if (vocabulary.getLiked() == 0) {
                        arrFavorite.remove(vocabulary);
                    }
                }
                favoriteAdapter.notifyDataSetChanged();
            }
        }
    }
}
