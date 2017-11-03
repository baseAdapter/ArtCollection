package com.tsutsuku.artcollection.api;

import android.os.Environment;

import com.tsutsuku.artcollection.BuildConfig;


/**
 * @Author Sun Renwei
 * @Create 2016/12/26
 * @Description Content
 */

public class ApiConstants {
    public static final class Api {
        private static final String DEBUG_HOST = "http://ysscapi.51urmaker.com/v1_1/";

        public static final String HOST_URL =    "http://120.27.220.26/ysscapi.51urmaker.com/Public/v1_1/";

        public static final String HOST_TEST = "http://ysscwx.51urmaker.com/API/Public/v1_1/";

        public static String HOST = DEBUG_HOST;

        public static final String KEY = "yssc7d3c64545245457bfe9693282c75e2ef795d3";
        public static final String SECRET = "SECRET";
    }


    public static final class Paths {
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = BASE_PATH + "/" + BuildConfig.APPLICATION_ID + "/image";
        public static final String IMAGE_UPLOAD_TEMP_PATH = IMAGE_LOADER_CACHE_PATH + "/upload";
        public static final String IMAGE_PHOTO_CACHE_PATH = IMAGE_LOADER_CACHE_PATH + "/photo";
    }


}
