package com.tsutsuku.artcollection.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/2/8
 * @Description
 */

public class GsonUtils {
    private static Gson gson = new Gson();

    public static <T> T parseJson(String jsonData, Class<T> type) {
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    public static <T> List<T> parseJsonArray(String jsonData, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (!TextUtils.isEmpty(jsonData)) {
            try {
                JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
                for (JsonElement elem : array) {
                    result.add(gson.fromJson(elem, clazz));
                }
            } catch (JsonSyntaxException e) {
                Log.e("GsonUtils", "parseJsonArray error" + e);
            }
        }

        return result;
    }
}
