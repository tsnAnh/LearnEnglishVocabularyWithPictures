package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.VocabularyViewPagerAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

public class VocabularyActivity extends AppCompatActivity implements VocabularyFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ArrayList<Vocabulary> arrayList;
    private int vocabularyId;
    private SpeedDialView speedDialView;
    private Vocabulary vocabulary;
    private boolean isPLAYING = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        arrayList = getVocabularyModel();
        setVocabularyId();

        vocabulary = arrayList.get(vocabularyId);

        // FindView
        toolbar = this.findViewById(R.id.toolbar_vocabulary);
        viewPager = this.findViewById(R.id.view_pager_vocabulary);
        tabLayout = this.findViewById(R.id.tab_layout_vocabulary);
        speedDialView = this.findViewById(R.id.fab_menu_vocabulary);

        speedDialView.setBackgroundColor(Color.TRANSPARENT);

        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_play, R.drawable.round_play_arrow_24)
                        .setFabBackgroundColor(Color.WHITE)
                        .setLabel("Play")
                        .setLabelClickable(false)
                        .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_listen, R.drawable.round_volume_up_24)
                        .setFabBackgroundColor(Color.WHITE)
                        .setLabelClickable(false)
                        .setLabel("Listen")
                        .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_vocabulary_favorite, R.drawable.round_favorite_24)
                        .setFabBackgroundColor(Color.WHITE)
                        .setLabelClickable(false)
                        .setLabel("Favorite")
                        .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_share_via_facebook, R.drawable.round_share_24)
                        .setFabBackgroundColor(Color.WHITE)
                        .setLabel("Share Via Facebook")
                        .setLabelClickable(false)
                        .create());
        speedDialView
                .addActionItem(new SpeedDialActionItem.Builder(R.id.action_settings, R.drawable.round_settings_24)
                        .setFabBackgroundColor(Color.WHITE)
                        .setLabel("Settings")
                        .setLabelClickable(false)
                        .create());
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.action_play:
                        VocabularyActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = vocabularyId ; i < arrayList.size() ; i++) {
                                    viewPager.setCurrentItem(i);
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                VocabularyActivity.this.finish();
                            }
                        });
                        break;
                }
                return false;
            }
        });

        toolbar.setTitle(vocabulary.getEn_us());
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setSubtitle(vocabulary.getEn_us_pr());
        toolbar.setSubtitleTextColor(Color.LTGRAY);
        this.setSupportActionBar(toolbar);

        assert this.getSupportActionBar() != null;
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setUpViewPager();

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolbar.setTitle(arrayList.get(tab.getPosition()).getEn_us());
                toolbar.setSubtitle(arrayList.get(tab.getPosition()).getEn_us_pr());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // nothing to do
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // nothing to do
            }
        });
    }

    private void setUpViewPager() {
        VocabularyViewPagerAdapter adapter = new VocabularyViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0 ; i < arrayList.size() ; i++) {
            adapter.addFragment(new VocabularyFragment(arrayList.get(i)), arrayList.get(i).getEn_us());
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(vocabularyId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_main, menu);

        // TODO add listener for actionbar button

        return true;
    }

    private ArrayList<Vocabulary> getVocabularyModel() {
        return getIntent().getExtras().getParcelableArrayList(Config.VOCABULARY_CAT_KEY);
    }
    private void setVocabularyId() {
        vocabularyId = getIntent().getExtras().getInt(Config.VOCABULARY_ID_KEY);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // nothing to do
    }

    public void onRadioClick(View v) {

        if (!isPLAYING) {
            isPLAYING = true;
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(Config.SERVER_IMAGE_FOLDER + vocabulary.getEn_us_audio());
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                Log.e("err", "prepare() failed");
            }
        } else {
            isPLAYING = false;
//            stopPlaying();
        }
    }

//    private void stopPlaying() {
//        mp.release();
//        mp = null;
//    }
}
