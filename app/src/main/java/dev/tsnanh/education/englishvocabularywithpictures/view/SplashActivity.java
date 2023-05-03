package dev.tsnanh.education.englishvocabularywithpictures.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import dev.tsnanh.education.englishvocabularywithpictures.R;
import dev.tsnanh.education.englishvocabularywithpictures.controller.Config;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isOnline()) {
                    showWarningDialog();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, Config.SPLASH_TIME_OUT);
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setTitle("Warning")
                .setMessage("Please turn on WiFi or mobile data for the best experience. You can " +
                        "still turn off the connection, but we cannot display images and play audio");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
