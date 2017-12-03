package com.demo.lijao.application;

import zuo.biao.library.base.BaseApplication;

/**
 * Created by admin on 2017/12/3.
 */

public class MyApplication extends BaseApplication{
    private static final String TAG = "MyApplication";

    private static MyApplication context;
    public static MyApplication getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }
}
