package com.tsnanh.education.learnenglishvocabularywithpictures.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class Utilities {
//    public static void saveBitmapToData
    public static Bitmap getBitmapFromURL(String src) {
        try {
            InputStream inputStream = new URL(src).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static void saveImage(Context context, Bitmap bitmap, String name) {
        File dir = new File(context.getFilesDir(), "image/categories");
        File f = new File(dir, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
