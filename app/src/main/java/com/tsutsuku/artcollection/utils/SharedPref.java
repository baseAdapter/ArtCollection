package com.tsutsuku.artcollection.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tsutsuku.artcollection.ui.base.BaseApplication;


/**
 * @Author Sun Renwei
 * @Create 2017/1/3
 * @Description Content
 */

public class SharedPref {
    private static final String SYSTEM_PRE = "systemPre";
    private static final String USER_PRE = "userPre";

    private static Context context = BaseApplication.getInstance();
    private static SharedPreferences sysPref = context.getSharedPreferences(SYSTEM_PRE, Context.MODE_PRIVATE);
    private static SharedPreferences userPref = context.getSharedPreferences(USER_PRE, Context.MODE_PRIVATE);
    private static SharedPreferences.Editor sysEditor = sysPref.edit();
    private static SharedPreferences.Editor userEditor = userPref.edit();

    public static void clear() {
        userEditor.clear();
        userEditor.apply();
    }

    // Int
    public static int getInt(String key, int def){
        return userPref.getInt(key, def);
    }

    public static int getInt(String key){
        return getInt(key, 0);
    }

    public static void putInt(String key, int value){
        userEditor.putInt(key, value);
        userEditor.apply();
    }

    public static int getSysInt(String key, int def){
        return sysPref.getInt(key, def);
    }

    public static int getSysInt(String key){
        return getSysInt(key, 0);
    }

    public static void putSysInt(String key, int value){
        sysEditor.putInt(key, value);
        sysEditor.apply();
    }

    // String
    public static String getString(String key, String def){
        return userPref.getString(key, def);
    }

    public static String getString(String key){
        return getString(key, "");
    }

    public static void putString(String key, String value){
        userEditor.putString(key, value);
        userEditor.apply();
    }

    public static String getSysString(String key, String def){
        return sysPref.getString(key, def);
    }

    public static String getSysString(String key){
        return getSysString(key, "");
    }

    public static void putSysString(String key, String value){
        sysEditor.putString(key, value);
        sysEditor.apply();
    }
}
