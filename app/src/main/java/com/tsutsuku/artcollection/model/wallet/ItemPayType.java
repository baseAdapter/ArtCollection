package com.tsutsuku.artcollection.model.wallet;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description Content
 */

public class ItemPayType {
    private String name;
    private int picResId;
    private String code;

    public ItemPayType(String name, int picResId, String code) {
        this.name = name;
        this.picResId = picResId;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicResId() {
        return picResId;
    }

    public void setPicResId(int picResId) {
        this.picResId = picResId;
    }
}
