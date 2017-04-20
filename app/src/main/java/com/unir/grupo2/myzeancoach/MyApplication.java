package com.unir.grupo2.myzeancoach;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by Cesar on 20/04/2017.
 */
public class MyApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        Foreground.init(this);
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}