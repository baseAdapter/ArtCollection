package com.tsutsuku.artcollection.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.tsutsuku.artcollection.ui.base.BaseApplication;


/**
 * Created by sunrenwei on 2016/5/29.
 */
public class ToastUtils {

    public static void showMessage(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showMessage(int msg) {
        Toast.makeText(BaseApplication.getInstance(), BaseApplication.getInstance().getString(msg), Toast.LENGTH_LONG).show();
    }

}
