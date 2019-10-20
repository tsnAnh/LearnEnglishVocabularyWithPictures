package com.tsnanh.education.learnenglishvocabularywithpictures.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Admin on 12/31/2015.
 */
public class DatabaseOpenHelper extends DaoMaster.DevOpenHelper {

    private Context context;


    private static String DB_PATH;

    private static String DB_NAME;

    public DatabaseOpenHelper(Context context, String name) {
        super(context, name);
        this.context = context;
        this.DB_NAME = name;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
       // context.getString(R.string.DB_PATH);
        try {
            createDataBase();
        } catch (Exception ioe) {
            throw new Error("Unable to create database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.v("database_upgrade", "Upgrade from: " + oldVersion + " to " + newVersion);

    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO_upgrade", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by creating new tables");
        if(newVersion == 3){
            Log.i("greenDAO_upgrade", "Create Test Result Table");

         }

    }

    /** Create new database if not present */
    public void createDataBase() {

        if (databaseExists()) {
            /* Check for Upgrade */
        } else {
            /* Database does not exists create blank database */
            Database db = this.getWritableDb();
            db.close();

            copyDataBase();
        }
    }

    /** Check Database if it exists */
    private boolean databaseExists() {
        SQLiteDatabase sqliteDatabase = null;
        try {
            String databasePath = DB_PATH + DB_NAME;
            sqliteDatabase = SQLiteDatabase.openDatabase(databasePath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (sqliteDatabase != null) {
            sqliteDatabase.close();
        }
        return sqliteDatabase != null ? true : false;
    }

    /**
     * Copy existing database file in system
     */
    public void copyDataBase() {

        int length;
        byte[] buffer = new byte[1024];
        String databasePath = DB_PATH + DB_NAME;

        try {
            InputStream databaseInputFile = this.context.getAssets().open("databases/"+DB_NAME);
            OutputStream databaseOutputFile = new FileOutputStream(databasePath);

            while ((length = databaseInputFile.read(buffer)) > 0) {
                databaseOutputFile.write(buffer, 0, length);
                databaseOutputFile.flush();
            }
            databaseInputFile.close();
            databaseOutputFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate(Database db) {

    }
}