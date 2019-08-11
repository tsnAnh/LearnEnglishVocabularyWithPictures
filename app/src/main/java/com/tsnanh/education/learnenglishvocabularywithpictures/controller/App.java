package com.tsnanh.education.learnenglishvocabularywithpictures.controller;

import android.app.Application;

import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoMaster;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private static DaoSession daoSession;

    public void init() {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(this, Config.DATABASE_NAME);
        Database database = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
