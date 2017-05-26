package com.project.nat.backgrounddownloading;

import android.app.Application;

/**
 * Created by csuthar on 25/05/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initApp();
    }


    private void initApp() {
        AppJobManager.getJobManager(this);

    }

}
