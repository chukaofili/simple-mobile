package com.chuka.simplemobileapp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Oladeji on 25/03/2017.
 * simple-mobile
 */

public class SimpleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric fabric = new Fabric.Builder(this).kits(new Crashlytics()).debuggable(true).build();
        Fabric.with(fabric);
    }

}
