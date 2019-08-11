package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.GridAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.ICategory;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.ListAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoSession;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;

import java.util.ArrayList;

public class VocabularyCategoryActivity extends AppCompatActivity {

    private DaoSession daoSession = App.getDaoSession();
    private ArrayList<Vocabulary> arr = new ArrayList<>();
    private Toolbar toolbar;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_category);

        Bundle bundle = getIntent().getExtras();
        long categoryId = bundle.getLong(Config.CATEGORY_ID_KEY);

        arr.addAll(daoSession.getVocabularyDao().loadAll());

        toolbar = this.findViewById(R.id.toolbar_vocabulary_cat);
        listView = this.findViewById(R.id.list_vocabulary_cat);


    }
}
