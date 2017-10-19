package com.tsutsuku.artcollection.utils;

import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by sunrenwei on 2016/5/29.
 */
public class EncryptUtils {
    private static final String TAG = "EncryptUtils";

    public static String md5(String string) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (Exception e) {
            Log.e(TAG, "Error=" + e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
