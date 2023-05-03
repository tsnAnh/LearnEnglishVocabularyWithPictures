package dev.tsnanh.education.englishvocabularywithpictures.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;
import dev.tsnanh.education.englishvocabularywithpictures.R;
import dev.tsnanh.education.englishvocabularywithpictures.controller.Config;

public class VocabularySettingsActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private FloatingActionButton fabSaveSettings;
    private Toolbar toolbar;
    private SeekBar seekBarDuration;
    private Switch aSwitchRepeat;
    private TextView lblDuration;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int duration;
    boolean isRepeat, isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_settings);

        // FindView
        fabSaveSettings = this.findViewById(R.id.fab_save_settings);
        lblDuration = this.findViewById(R.id.lbl_duration_settings);
        toolbar = this.findViewById(R.id.toolbar_vocabulary_settings);
        seekBarDuration = this.findViewById(R.id.seekbar_duration);
        aSwitchRepeat = this.findViewById(R.id.switch_repeat_settings);

        fabSaveSettings.setOnClickListener(this);
        seekBarDuration.setOnSeekBarChangeListener(this);
        aSwitchRepeat.setOnCheckedChangeListener(this);

        sharedPreferences = getSharedPreferences(Config.SHARE_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences == null) {
            duration = 5;
            isRepeat = false;
            aSwitchRepeat.setChecked(false);
        } else {
            duration = sharedPreferences.getInt(Config.SHARE_PREFERENCES_DURATION, 5);
            isRepeat = sharedPreferences.getBoolean(Config.SHARE_PREFERENCES_REPEAT, false);
            aSwitchRepeat.setChecked(isRepeat);
        }

        seekBarDuration.setProgress(duration);
        lblDuration.setText(String.valueOf(duration));
        toolbar.setTitle("Vocabulary Play Settings");
        this.setSupportActionBar(toolbar);

        assert this.getSupportActionBar() != null;
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isChanged = false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        exitSettings();
        return true;
    }

    @Override
    public void onBackPressed() {
        exitSettings();
    }

    private void exitSettings() {
        if (isChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
            builder.setMessage("Do you want to save your changes?")
                    .setTitle("Save?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putBoolean(Config.SHARE_PREFERENCES_REPEAT, isRepeat);
                            editor.putInt(Config.SHARE_PREFERENCES_DURATION, duration);
                            editor.apply();
                            StyleableToast.makeText(VocabularySettingsActivity.this, "Saved!", Toast.LENGTH_SHORT, R.style.mytoast).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    })
                    .setNeutralButton("Don't Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.fab_save_settings) {
            editor.putBoolean(Config.SHARE_PREFERENCES_REPEAT, isRepeat);
            editor.putInt(Config.SHARE_PREFERENCES_DURATION, duration);
            editor.apply();
            setResult(RESULT_OK);
            StyleableToast.makeText(VocabularySettingsActivity.this, "Saved!", Toast.LENGTH_SHORT, R.style.mytoast).show();
            finish();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int min = 1;
        if (i < min) {
            lblDuration.setText("1");
            duration = 1;
        } else {
            duration = i;
            lblDuration.setText(String.valueOf(i));
        }
        isChanged = true;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isRepeat = b;
        isChanged = true;
    }
}
