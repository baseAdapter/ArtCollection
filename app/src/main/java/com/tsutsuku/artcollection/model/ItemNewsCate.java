package com.tsutsuku.artcollection.model;

/**
 * @Author Sun Renwei
 * @Create 2017/1/12
 * @Description Content
 */

public class ItemNewsCate {
    private String cateId;
    private String cateName;

    public ItemNewsCate(String cateId, String cateName) {
        this.cateId = cateId;
        this.cateName = cateName;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
