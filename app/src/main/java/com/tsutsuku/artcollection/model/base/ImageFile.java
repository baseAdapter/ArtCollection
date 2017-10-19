package com.tsutsuku.artcollection.model.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @Author Sun Renwei
 * @Create 2016/12/27
 * @Description 图片文件夹
 */

public class ImageFile implements Parcelable {
    private String path;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeList(list);
    }

    public static final Creator<ImageFile> CREATOR = new Creator<ImageFile>() {
        @Override
        public ImageFile createFromParcel(Parcel source) {
            ImageFile file = new ImageFile();
            file.setPath(source.readString());
            file.setList(source.readArrayList(String.class.getClassLoader()));
            return file;
        }

        @Override
        public ImageFile[] newArray(int size) {
            return new ImageFile[size];
        }
    };

    public String getPath() {
        return path;
    }

    public String getName() {
        String[] str = path.split("/");
        return str[str.length - 1];
    }

    public void setPath(String name) {
        this.path = name;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
