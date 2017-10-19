package com.tsutsuku.artcollection.utils;

import android.os.Environment;


import com.tsutsuku.artcollection.BuildConfig;

import java.io.File;

/**
 * Created by YUNBO on 2015/11/25.
 */
public class FileUtils {
    public static String mSDCardPath = null;
    public static final String APP_FOLDER_NAME = BuildConfig.APPLICATION_ID;

    private static String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    public static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建本地文件
     */
    public static File createCacheLocalFile(String filename, String path) {
        if (filename.isEmpty() || path.isEmpty()) {
            return null;
        }
        File dir = new File(Environment.getExternalStorageDirectory(), path);
        if (!dir.exists()) {
            dir.mkdirs(); // 加了个s
        }
        File file = new File(dir, filename);
        return file;
    }

    public static File createLocalFile(String fileName, String parentPath) {
        if (parentPath.isEmpty()){
            return null;
        }

        File dir = new File(parentPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        return file;
    }


}
