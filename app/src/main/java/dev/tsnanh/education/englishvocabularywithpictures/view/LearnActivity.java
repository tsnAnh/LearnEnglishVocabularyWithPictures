package dev.tsnanh.education.englishvocabularywithpictures.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import dev.tsnanh.education.englishvocabularywithpictures.R;
import dev.tsnanh.education.englishvocabularywithpictures.controller.Config;
import dev.tsnanh.education.englishvocabularywithpictures.controller.Utilities;
import dev.tsnanh.education.englishvocabularywithpictures.model.Vocabulary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LearnActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RoundedImageView imgLearn;
    private EditText edtLearn;
    private LinearLayout linearLayout, linearLayout2, linearLayout3;
    private Button btnShowMean;
    private TextView lblMeanTitle, lblMean, lblCorrect;
    private Vocabulary vocabulary;
    int position = 0;
    private boolean isPLAYING = false;
    private MediaPlayer mp;
    private ImageView btnSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        vocabulary = (Vocabulary) getIntent().getExtras().get("vocabulary");

        toolbar = this.findViewById(R.id.toolbar_learn);
        imgLearn = this.findViewById(R.id.img_learn);
        edtLearn = this.findViewById(R.id.edt_learn);
        linearLayout = this.findViewById(R.id.button_group_learn);
        linearLayout2 = this.findViewById(R.id.button_group_learn2);
        linearLayout3 = this.findViewById(R.id.button_group_learn3);
        btnShowMean = this.findViewById(R.id.btn_show_mean);
        lblMeanTitle = this.findViewById(R.id.lbl_mean_title_learn);
        lblMean = this.findViewById(R.id.lbl_mean_learn);
        lblCorrect = this.findViewById(R.id.lbl_correct);
        btnSound = this.findViewById(R.id.btn_sound_learn);

        btnSound.setOnClickListener(this);
        btnShowMean.setOnClickListener(this);

        toolbar.setTitle("Learn");
        toolbar.setNavigationIcon(R.drawable.round_clear_24);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtLearn.setEnabled(false);

        Glide.with(this)
                .load(Config.SERVER_IMAGE_FOLDER + vocabulary.getImage())
                .placeholder(R.drawable.progress)
                .centerCrop()
                .into(imgLearn);

        final String[] arr = vocabulary.getEn_us().split("");
        List<String> list = new ArrayList<String>(Arrays.asList(arr));
        list.removeAll(Arrays.asList("", null));
        List<String> list2 = list;
        Collections.shuffle(list2);

        int i = 1;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMarginStart(8);
        params.setMarginEnd(8);
        params.leftMargin = 8;
        params.rightMargin = 8;
        for (String character : list2) {
            if (!character.equals("")) {
                if (i <= 7) {
                    Button myButton = new Button(this);
                    myButton.setLayoutParams(params);
                    myButton.setBackgroundResource(R.drawable.state_button_learn);
                    myButton.setText(character);
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String str = ((Button)view).getText().toString();
                            if (str.equals(arr[position])) {
                                position++;
                                edtLearn.append(str);
                                view.setEnabled(false);
                                if (edtLearn.getText().toString().equals(vocabulary.getEn_us())) {
                                    showMean();
                                    lblCorrect.setText("Correct!");
                                    toolbar.setTitle(vocabulary.getEn_us());
                                    toolbar.setSubtitle(vocabulary.getEn_us_pr());
                                    btnSound.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.GONE);
                                    linearLayout2.setVisibility(View.GONE);
                                    linearLayout3.setVisibility(View.GONE);
                                    onRadioClick();
                                }
                            }
                        }
                    });

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    lp.setMargins(4, 0, 4, 0);
                    linearLayout.addView(myButton, lp);
                    i++;
                } else if (i <= 14) {
                    Button myButton = new Button(this);
                    myButton.setLayoutParams(params);
                    myButton.setBackgroundResource(R.drawable.state_button_learn);
                    myButton.setText(character);
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String str = ((Button)view).getText().toString();
                            if (str.equals(arr[position])) {
                                position++;
                                edtLearn.append(str);
                                view.setEnabled(false);
                                if (edtLearn.getText().toString().equals(vocabulary.getEn_us())) {
                                    showMean();
                                    lblCorrect.setText("Correct!");
                                    toolbar.setTitle(vocabulary.getEn_us());
                                    toolbar.setSubtitle(vocabulary.getEn_us_pr());
                                    btnSound.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.GONE);
                                    linearLayout2.setVisibility(View.GONE);
                                    linearLayout3.setVisibility(View.GONE);
                                    onRadioClick();
                                }
                            }
                        }
                    });

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    lp.setMargins(4, 0, 4, 0);
                    linearLayout2.addView(myButton, lp);
                    i++;
                } else {
                    Button myButton = new Button(this);
                    myButton.setLayoutParams(params);
                    myButton.setBackgroundResource(R.drawable.state_button_learn);
                    myButton.setText(character);
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String str = ((Button)view).getText().toString();
                            if (str.equals(arr[position])) {
                                position++;
                                edtLearn.append(str);
                                view.setEnabled(false);
                                if (edtLearn.getText().toString().equals(vocabulary.getEn_us())) {
                                    showMean();
                                    lblCorrect.setText("Correct!");
                                    toolbar.setTitle(vocabulary.getEn_us());
                                    toolbar.setSubtitle(vocabulary.getEn_us_pr());
                                    btnSound.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.GONE);
                                    linearLayout2.setVisibility(View.GONE);
                                    linearLayout3.setVisibility(View.GONE);
                                    onRadioClick();
                                }
                            }
                        }
                    });

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    lp.setMargins(4, 0, 4, 0);
                    linearLayout3.addView(myButton, lp);
                    i++;
                }
            }
        }

        lblMean.setText(vocabulary.getEn_us_mean());
    }

    public void onRadioClick() {
        btnSound.setEnabled(false);
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
            btnSound.setEnabled(true);
            return false;
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_vocabulary, menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search_vocabulary);
        itemSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(LearnActivity.this, VocabularySearchActivity.class));
                return true;
            }
        });

        MenuItem itemFav = menu.findItem(R.id.action_favorite_vocabulary);
        itemFav.setVisible(false);

        MenuItem itemShare = menu.findItem(R.id.action_share_vocabulary);
        itemShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Utilities.shareApplication(LearnActivity.this);
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

    private void showMean() {
        btnShowMean.setVisibility(View.GONE);
        lblMean.setVisibility(View.VISIBLE);
        lblMeanTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_show_mean) {
            showMean();
        }
        if (id == R.id.btn_sound_learn) {
            onRadioClick();
        }
    }
}
