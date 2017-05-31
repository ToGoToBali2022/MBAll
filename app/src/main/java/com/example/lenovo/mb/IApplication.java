package com.example.lenovo.mb;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lenovo on 2017/5/28.
 */

public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
    }
}
