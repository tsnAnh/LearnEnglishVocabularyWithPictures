package dev.tsnanh.education.englishvocabularywithpictures.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import dev.tsnanh.education.englishvocabularywithpictures.R;
import dev.tsnanh.education.englishvocabularywithpictures.controller.Config;
import dev.tsnanh.education.englishvocabularywithpictures.controller.Utilities;
import dev.tsnanh.education.englishvocabularywithpictures.controller.VocabularyViewPagerAdapter;
import dev.tsnanh.education.englishvocabularywithpictures.model.Vocabulary;

import java.io.IOException;

public class VocabularyAll extends AppCompatActivity implements VocabularyFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Vocabulary vocabulary;
    private SpeedDialView speedDialView;
    private SpeedDialActionItem itemListen;
    private boolean isPLAYING = false;
    private MediaPlayer mp;
    boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        isSearch = getIntent().getExtras().getBoolean("isSearch");
        vocabulary = getIntent().getParcelableExtra(Config.VOCABULARY_ALL);

        // FindView
        toolbar = this.findViewById(R.id.toolbar_vocabulary);
        viewPager = this.findViewById(R.id.view_pager_vocabulary);
        tabLayout = this.findViewById(R.id.tab_layout_vocabulary);
        speedDialView = this.findViewById(R.id.fab_menu_vocabulary);

        speedDialView.setMainFabOpenedBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)));
        speedDialView.setMainFabClosedBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)));
        itemListen = new SpeedDialActionItem.Builder(R.id.action_listen, R.drawable.round_volume_up_24)
                .setFabBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                .setLabelClickable(false)
                .setLabelBackgroundColor(Color.parseColor(getString(R.string.colorPrimary)))
                .setLabelColor(Color.BLACK)
                .setLabel("Listen")
                .create();
        speedDialView
                .addActionItem(itemListen);
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                onRadioClick();
                return false;
            }
        });

        toolbar.setTitle(vocabulary.getEn_us());
        toolbar.setSubtitle(vocabulary.getEn_us_pr());
        this.setSupportActionBar(toolbar);

        assert this.getSupportActionBar() != null;
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setUpViewPager();

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_vocabulary, menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search_vocabulary);
        if (isSearch) {
            itemSearch.setVisible(false);
        } else {
            itemSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    startActivity(new Intent(VocabularyAll.this, VocabularySearchActivity.class));
                    return true;
                }
            });
        }

        MenuItem itemFav = menu.findItem(R.id.action_favorite_vocabulary);
        itemFav.setVisible(false);

        MenuItem itemShare = menu.findItem(R.id.action_share_vocabulary);
        itemShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Utilities.shareApplication(VocabularyAll.this);
                return true;
            }
        });

        return true;
    }

    private void setUpViewPager() {
        VocabularyViewPagerAdapter adapter = new VocabularyViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VocabularyFragment(vocabulary), vocabulary.getEn_us());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
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
                        mp.setDataSource(Config.SERVER_AUDIO_FOLDER + vocabulary.getEn_us_audio());
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
