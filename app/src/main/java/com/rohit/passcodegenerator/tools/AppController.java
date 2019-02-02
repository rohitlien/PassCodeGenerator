package com.rohit.passcodegenerator.tools;

import android.app.Application;

import com.rohit.passcodegenerator.database.RealmHelper;

/**
 * Created by oust on 2/2/19.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmHelper.setDefaultConfig(this);
    }
}
