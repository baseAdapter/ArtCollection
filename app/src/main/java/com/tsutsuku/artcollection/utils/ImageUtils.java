package com.tsutsuku.artcollection.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.tsutsuku.artcollection.model.base.ImageFile;
import com.tsutsuku.artcollection.ui.base.BaseApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author Tsutsuku
 * @Create 2016/12/26
 * @Description 图片相关工具类
 */

public class ImageUtils {

    private volatile static ImageUtils instance;
    private static Context context;

    public static ImageUtils getInstance() {
        if (instance == null) {
            synchronized (ImageUtils.class) {
                if (instance == null) {
                    context = BaseApplication.getInstance();
                    instance = new ImageUtils();
                }
            }
        }
        return instance;
    }

    protected ImageUtils() {

    }


    /*****  get all image  *****/

    /**
     * get all image paths
     * @return
     */
    private List<String> getAllImagePaths() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Uri uri = intent.getData();
        ArrayList<String> list = new ArrayList<String>();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            list.add(new File(path).getAbsolutePath());
        }
        return list;
    }

    private String getParentFileName(String path) {
        String[] str = path.split("/");
        return str[str.length - 2];
    }

    /**
     * get all files those has image
     * @return
     */
    public List<ImageFile> getAllImageFiles() {
        List<ImageFile> files = new ArrayList<>();
        List<String> pathList = getAllImagePaths();
        if (pathList != null) {
            Set<String> set = new TreeSet();
            int pathNum = pathList.size();

            for (String path : pathList) {
                set.add(getParentFileName(path));
            }

            for (String path : set) {
                ImageFile file = new ImageFile();
                file.setPath(path);
                files.add(file);
            }

            for (String path : pathList) {
                String parentFileName = getParentFileName(path);
                for (ImageFile file : files) {
                    if (file.getName().equals(parentFileName)) {
                        file.getList().add(path);
                    }
                }
            }
        }
        return files;
    }

}
