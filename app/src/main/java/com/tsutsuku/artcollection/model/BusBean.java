package com.tsutsuku.artcollection.model;

/**
 * @Author Tsutsuku
 * @Create 2017/2/23
 * @Description
 */

public class BusBean {
    private int type;
    private String data;
    private String ext;

    public BusBean(int type) {
        this.type = type;
    }

    public BusBean(int type, String data) {
        this.data = data;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
