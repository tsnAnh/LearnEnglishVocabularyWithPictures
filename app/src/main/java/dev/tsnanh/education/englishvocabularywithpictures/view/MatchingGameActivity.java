
package dev.tsnanh.education.englishvocabularywithpictures.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import dev.tsnanh.education.englishvocabularywithpictures.R;
import dev.tsnanh.education.englishvocabularywithpictures.controller.Config;
import dev.tsnanh.education.englishvocabularywithpictures.model.Vocabulary;

import java.util.ArrayList;

public class MatchingGameActivity extends AppCompatActivity {

    private ArrayList<Vocabulary> arr;
    private LinearLayout layoutText, layoutImage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_game);

        arr = getIntent().getParcelableArrayListExtra(Config.VOCABULARY_CAT_KEY);
        String nameCat = getIntent().getStringExtra("name_cat");

        toolbar = this.findViewById(R.id.toolbar_matching_game);
        layoutImage = this.findViewById(R.id.layout_game_image);
        layoutText = this.findViewById(R.id.layout_game_text);

        toolbar.setTitle("Matching Game");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setSubtitle(nameCat);
        toolbar.setSubtitleTextColor(Color.LTGRAY);
        toolbar.setNavigationIcon(R.drawable.round_clear_24);
        this.setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startGame();
    }

    private void startGame() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
