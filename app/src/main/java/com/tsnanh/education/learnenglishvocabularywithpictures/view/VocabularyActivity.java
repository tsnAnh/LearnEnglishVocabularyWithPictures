package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Utilities;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.VocabularyViewPagerAdapter;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoSession;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VocabularyActivity extends AppCompatActivity implements VocabularyFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ArrayList<Vocabulary> arrayList;
    private int vocabularyId;
    private SpeedDialView speedDialView;
    private Vocabulary vocabulary;
    int currentPage = 0;
    boolean isStop = false;
    Handler handler = new Handler();
    Runnable update;
    Timer swipeTimer;
    private SpeedDialActionItem itemPlay, itemListen, itemSettings, itemStop;
    private boolean isPLAYING = false;
    private MediaPlayer mp;
    MenuItem itemFavorite;
    int duration;
    boolean isRepeat;

    private DaoSession daoSession = App.getDaoSession();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        createActionItem();

        speedDialView
                .addActionItem(itemPlay);
        speedDialView
                .addActionItem(itemListen);
        speedDialView
                .addActionItem(itemSettings);

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.action_play:
                        startPagerAutoSwipe();
                        break;
                    case R.id.action_stop:
                        stopPagerAutoSwipe();
                        break;
                    case R.id.action_listen:
                        onRadioClick();
                        break;
                    case R.id.action_settings:
                        if (swipeTimer != null) {
                            stopPagerAutoSwipe();
                        }
                        Intent intent = new Intent(VocabularyActivity.this, VocabularySettingsActivity.class);
                        startActivityForResult(intent, Config.VOCABULARY_SETTINGS_REQUEST_CODE);
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

        sharedPreferences = getSharedPreferences(Config.SHARE_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences == null) {
            duration = 5;
            isRepeat = false;
            editor.putInt(Config.SHARE_PREFERENCES_DURATION, duration);
            editor.putBoolean(Config.SHARE_PREFERENCES_REPEAT, isRepeat);
            editor.apply();
        } else {
            duration = sharedPreferences.getInt(Config.SHARE_PREFERENCES_DURATION, 5);
            isRepeat = sharedPreferences.getBoolean(Config.SHARE_PREFERENCES_REPEAT, false);
        }

        this.setUpViewPager();

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolbar.setTitle(arrayList.get(tab.getPosition()).getEn_us());
                toolbar.setSubtitle(arrayList.get(tab.getPosition()).getEn_us_pr());

                currentPage = vocabularyId = tab.getPosition();
                vocabulary = arrayList.get(vocabularyId);

                if (vocabulary.getLiked() == 1) {
                    itemFavorite.setIcon(R.drawable.round_favorite_24);
                } else {
                    itemFavorite.setIcon(R.drawable.round_favorite_border_24);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // nothing to do yet
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // nothing to do
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                boolean b = data.getBooleanExtra("reload_voc_settings", false);
                if (b) {
                    refreshSettings(data.getBooleanExtra("repeat", false), data.getIntExtra("duration", 5));
                }
            }
        }
    }

    private void refreshSettings(boolean b, int duration) {
        isRepeat = b;
        this.duration = duration;
    }

    public void onRadioClick() {
        speedDialView.close(true);
        if (!isPLAYING) {
            isPLAYING = true;
            mp = new MediaPlayer();
            new Thread() {
                @Override
                public void run() {
                    try {
                        mp.setDataSource(Config.SERVER_AUDIO_FOLDER + arrayList.get(vocabularyId).getEn_us_audio());
                        mp.prepare();
                        handlerAudio.sendEmptyMessage(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            isPLAYING = false;
            mp.release();
            mp = null;
        }
        isPLAYING = false;
    }

    private Handler handlerAudio = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            mp.start();
            return false;
        }
    });

    private void createActionItem() {
        itemPlay = new SpeedDialActionItem.Builder(R.id.action_play, R.drawable.round_play_arrow_24)
                .setFabBackgroundColor(Color.WHITE)
                .setLabel("Play")
                .setLabelClickable(false)
                .create();

        itemSettings = new SpeedDialActionItem.Builder(R.id.action_settings, R.drawable.round_settings_24)
                .setFabBackgroundColor(Color.WHITE)
                .setLabel("Settings")
                .setLabelClickable(false)
                .create();


        itemListen = new SpeedDialActionItem.Builder(R.id.action_listen, R.drawable.round_volume_up_24)
                .setFabBackgroundColor(Color.WHITE)
                .setLabelClickable(false)
                .setLabel("Listen")
                .create();

        itemStop = new SpeedDialActionItem.Builder(R.id.action_stop, R.drawable.round_stop_24)
                .setFabBackgroundColor(Color.WHITE)
                .setLabelClickable(false)
                .setLabel("Stop")
                .create();
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
        if (swipeTimer != null) {
            swipeTimer.cancel();
        }
        Intent intent = new Intent();
        intent.putExtra(Config.VOC_ID, viewPager.getCurrentItem());
        intent.putParcelableArrayListExtra(Config.VOCABULARY_CAT_KEY, arrayList);
        VocabularyActivity.this.setResult(Config.VOCABULARY_REQUEST_CODE, intent);
        VocabularyActivity.this.finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_vocabulary, menu);

        // TODO add listener for actionbar button

        itemFavorite = menu.findItem(R.id.action_favorite_vocabulary);
        if (vocabulary.getLiked() == 1) {
            itemFavorite.setIcon(R.drawable.round_favorite_24);
        } else {
            itemFavorite.setIcon(R.drawable.round_favorite_border_24);
        }
        itemFavorite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (vocabulary.getLiked() == 0) {
                    vocabulary.setLiked(1);
                    daoSession.getVocabularyDao().getDatabase().execSQL("update vocabularies set liked = 1 where id = "+vocabulary.getId()+"");
                    daoSession.getVocabularyDao().refresh(vocabulary);
                    arrayList.set(vocabularyId, vocabulary);
                    itemFavorite.setIcon(R.drawable.round_favorite_24);
                    Toast.makeText(VocabularyActivity.this, "Added to Favorites!", Toast.LENGTH_SHORT).show();
                } else {
                    vocabulary.setLiked(0);
                    daoSession.getVocabularyDao().getDatabase().execSQL("update vocabularies set liked = 0 where id = "+vocabulary.getId()+"");
                    daoSession.getVocabularyDao().refresh(vocabulary);
                    arrayList.set(vocabularyId, vocabulary);
                    itemFavorite.setIcon(R.drawable.round_favorite_border_24);
                    Toast.makeText(VocabularyActivity.this, "Removed from Favorite!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        MenuItem itemShare = menu.findItem(R.id.action_share_vocabulary);
        itemShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Utilities.shareApplication(VocabularyActivity.this);
                return true;
            }
        });

        MenuItem itemSearch = menu.findItem(R.id.action_search_vocabulary);
        itemSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(VocabularyActivity.this, VocabularySearchActivity.class);
                startActivity(intent);
                return false;
            }
        });

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

    private void startPagerAutoSwipe() {
        speedDialView.close(true);
        speedDialView.replaceActionItem(itemPlay, itemStop);
        tabLayout.setKeepScreenOn(true);
        currentPage = viewPager.getCurrentItem();
        isStop = false;
        update = new Runnable() {
            public void run() {
                if(!isStop){
                    if (currentPage == arrayList.size()) {
                        if (isRepeat) {
                            currentPage = 0;
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra(Config.VOC_ID, arrayList.size());
                            VocabularyActivity.this.setResult(Config.VOCABULARY_REQUEST_CODE, intent);
                            VocabularyActivity.this.finish();
                        }
                    }
                    if (currentPage == 0) {
                        viewPager.setCurrentItem(currentPage, true);
                    }
                    viewPager.setCurrentItem(currentPage++, true);
                }
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 0, duration*1000);
    }

    private void stopPagerAutoSwipe() {
        swipeTimer.cancel();
        isStop = true;
        speedDialView.replaceActionItem(itemStop, itemPlay);
        tabLayout.setKeepScreenOn(false);
    }

    @Override
    public void onBackPressed() {
        if (swipeTimer != null) {
            swipeTimer.cancel();
        }
        editor.putBoolean(Config.SHARE_PREFERENCES_REPEAT, isRepeat);
        editor.putInt(Config.SHARE_PREFERENCES_DURATION, duration);
        editor.apply();
        Intent intent = new Intent();
        intent.putExtra(Config.VOC_ID, viewPager.getCurrentItem());
        intent.putParcelableArrayListExtra(Config.VOCABULARY_CAT_KEY, arrayList);
        VocabularyActivity.this.setResult(Config.VOCABULARY_REQUEST_CODE, intent);
        VocabularyActivity.this.finish();
    }
}
