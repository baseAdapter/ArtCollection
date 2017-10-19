package com.tsutsuku.artcollection.wxapi;

/**
 * Created by sunrenwei on 2016/8/8.
 */
public class WXItem {
    int id;
    String content;

    public WXItem(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
