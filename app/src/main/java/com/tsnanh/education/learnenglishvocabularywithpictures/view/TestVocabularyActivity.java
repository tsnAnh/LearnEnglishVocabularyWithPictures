package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestVocabularyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgVocTest;
    private TextView lblQuestionCount, lblAnswer, lblMeanTitle, lblMean;
    private FloatingActionButton fabNextTest;
    private RadioGroup radioGroup;
    private ArrayList<Vocabulary> arr;
    int i = 0, answered = 0;
    private MediaPlayer mp;
    private boolean isPLAYING = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_vocabulary);

        arr = getIntent().getParcelableArrayListExtra(Config.VOCABULARY_CAT_KEY);
        String s = getIntent().getStringExtra("name_cat");
        Collections.shuffle(arr);

        findView();

        toolbar.setNavigationIcon(R.drawable.round_clear_24);
        toolbar.setTitle("Test Your Vocabulary");
        toolbar.setSubtitle(s);
        toolbar.setSubtitleTextColor(Color.LTGRAY);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lblAnswer.setText("Your result is " + answered + "/" + arr.size());
        fabNextTest.hide();
        fabNextTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.removeAllViews();
                if (i < arr.size() - 1) {
                    fabNextTest.hide();
                    loadTest(++i);
                } else {
                    Toast.makeText(TestVocabularyActivity.this, "Your Score: " + answered + "/" + arr.size(), Toast.LENGTH_LONG).show();
                    TestVocabularyActivity.this.finish();
                }
            }
        });

        loadTest(i);
    }

    private void loadTest(int location) {
        lblQuestionCount.setText(String.format("Question %s", String.valueOf(location + 1)));
        ArrayList<Vocabulary> temp = new ArrayList<>();
        final Vocabulary vocabulary = arr.get(location);
        lblMean.setText(vocabulary.getEn_us_mean());
        lblMean.setVisibility(View.GONE);
        lblMeanTitle.setVisibility(View.GONE);
        temp.add(vocabulary);
        Glide.with(this).load(Config.SERVER_IMAGE_FOLDER + vocabulary.getImage()).centerCrop().placeholder(R.drawable.progress).into(imgVocTest);
        ArrayList<Integer> integers = new ArrayList<>();
//        for (int j = 0; j < 3; j++) {
//            int random = new Random().nextInt(arr.size());
//            if (random != i || !integers.contains(random)) {
//                integers.add(random);
//                temp.add(arr.get(random));
//            }
//        }
        for (int j = 0; j < arr.size(); j++) {
            integers.add(j);
        }
        Collections.shuffle(integers);
        int count = 1;
        for (int j = 0; j < integers.size(); j++) {
            int num = integers.get(j);
            if (num != location) {
                temp.add(arr.get(num));
            } else {
                continue;
            }
            if (count == 3) {
                break;
            }
            count++;
        }
        Collections.shuffle(temp);
        Toast.makeText(this, String.valueOf(temp.size()), Toast.LENGTH_SHORT).show();
        for (int j = 0; j < temp.size(); j++) {
            final int position = j;
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(temp.get(j).getEn_us());
            radioButton.setTextColor(Color.WHITE);
            radioButton.setTypeface(null, Typeface.BOLD);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    String s = compoundButton.getText().toString();
                    String ss = vocabulary.getEn_us();
                    if (s.equals(ss)) {
                        lblMean.setVisibility(View.VISIBLE);
                        lblMeanTitle.setVisibility(View.VISIBLE);
                        compoundButton.setTextColor(Color.GREEN);
                        disableAllRadio(position);
                        fabNextTest.show();
                        lblAnswer.setText("Your result is " + ++answered + "/" + arr.size());
                        onRadioClick(vocabulary);
                    } else {
                        disableAllRadio();
                        compoundButton.setTextColor(Color.RED);
                        fabNextTest.show();
                    }
                }
            });
            radioGroup.addView(radioButton);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onRadioClick(final Vocabulary vocabulary) {
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

    private void disableAllRadio(int position) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            if (position != i) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
        }
    }

    private void disableAllRadio() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    private void findView() {
        toolbar = this.findViewById(R.id.toolbar_test);
        imgVocTest = this.findViewById(R.id.img_test);
        lblQuestionCount = this.findViewById(R.id.lbl_question_count);
        lblAnswer = this.findViewById(R.id.lbl_answer);
        lblMeanTitle = this.findViewById(R.id.lbl_mean_title_test);
        lblMean = this.findViewById(R.id.lbl_mean_test);
        fabNextTest = this.findViewById(R.id.fab_next_test);
        radioGroup = this.findViewById(R.id.radio_answer_group);
    }
}
