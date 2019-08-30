package com.tsnanh.education.learnenglishvocabularywithpictures.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class Utilities {

    public static void shareApplication(Context context) {
        String message = "Learn English Vocabulary With Me Via This App";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        share.putExtra(Intent.EXTRA_EMAIL, message);

        context.startActivity(Intent.createChooser(share, "Share This App"));
    }
}
