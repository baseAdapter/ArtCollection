package com.tsutsuku.artcollection.ui.base;

import android.app.Application;

import com.mob.MobApplication;
import com.tsutsuku.artcollection.im.IMHelper;
import com.yolanda.nohttp.NoHttp;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sunrenwei on 2016/5/28.
 */
public class BaseApplication extends MobApplication {
    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        initRealm();
        initEasemob();

        //ShareSDK.initSDK(this);
    }

    private void init() {
        NoHttp.initialize(this);
    }

    //初始化Realm数据库
    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded() // 正式时需删除，替换为数据库升级[如果有RealmObject更改]
                .build();
        Realm.setDefaultConfiguration(config);
    }

    // 环信初始化
    private void initEasemob() {
        IMHelper.getInstance().init(instance);
    }
}
