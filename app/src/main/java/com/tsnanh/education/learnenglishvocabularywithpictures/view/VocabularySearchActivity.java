package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.SearchAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.VocabularyDao;

import java.util.ArrayList;

public class VocabularySearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ListView listView;
    SearchAdapter searchAdapter;
    private ArrayList<Vocabulary> arr = new ArrayList<>(Config.SEARCH_RESULT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_search);

        toolbar = this.findViewById(R.id.toolbar_search);
        listView = this.findViewById(R.id.lst_search);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView.setOnItemClickListener(this);

        searchAdapter = new SearchAdapter(VocabularySearchActivity.this, arr);
        listView.setAdapter(searchAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem itemSearch = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setQueryHint("Search Vocabulary..");
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                if (s.equals("")) {
                    arr.clear();
                    handler.sendEmptyMessage(0);
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            arr.clear();
                            arr.addAll(App.getDaoSession().getVocabularyDao().queryBuilder().where(VocabularyDao.Properties.En_us.like("%" + s + "%")).list());
                            handler.sendEmptyMessage(0);
                        }
                    }.start();
                }
                return true;
            }
        });
        searchView.setIconifiedByDefault(false);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        itemSearch.expandActionView();
        return true;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            searchAdapter.notifyDataSetChanged();
            return true;
        }
    });

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, VocabularyActivity.class);
        intent.putExtra(Config.VOCABULARY_CAT_KEY, arr);
        intent.putExtra(Config.VOCABULARY_ID_KEY, i);
        startActivityForResult(intent, Config.VOCABULARY_REQUEST_CODE);
    }
}
