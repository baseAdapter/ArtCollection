package com.tsutsuku.artcollection.model;

/**
 * @Author Sun Renwei
 * @Create 2017/2/14
 * @Description 简单的Model，用于Recyclerview显示封装
 */

public class ItemRv {
    private String type;
    private Object data;

    public ItemRv(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
