package dev.tsnanh.education.englishvocabularywithpictures.controller;

import android.content.Context;
import android.content.Intent;

public final class Utilities {

    public static void shareApplication(Context context) {
        String message = "Learn English Vocabulary With Me Via This App https://play.google.com/store/apps/details?id=com.tsnanh.education.learnenglishvocabularywithpictures";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        share.putExtra(Intent.EXTRA_EMAIL, message);

        context.startActivity(Intent.createChooser(share, "Share This App"));
    }
}
