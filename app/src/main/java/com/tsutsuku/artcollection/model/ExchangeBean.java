package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Observable;


/**
 * Created by Administrator on 2017/10/7.
 */

public class ExchangeBean implements Serializable{
    /**
     *
     * id : 34
     * name : 测拼
     * inventory: 金币
     * need_gold: 60
     * integrate: "60"
     * coverPhoto:http://yssc.51urmaker.com/upload/2017/10/07/20171007101534668.jpg
     * detailUrl:http://yssc.51urmaker.com/aweb/ExchangeDetail.php?id=6
     *
     */

    private String id;
    private String name;
    private String inventory;
    private String need_gold;
    private String integrate;
    private String coverPhoto;
    private String detailUrl;
    private int is_collect;

    public int getIsCollection() {
        return is_collect;
    }

    public void setIsCollection(int isCollection) {
        this.is_collect = isCollection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getNeed_gold() {
        return need_gold;
    }

    public void setNeed_gold(String need_gold) {
        this.need_gold = need_gold;
    }

    public String getIntegrate() {
        return integrate;
    }

    public void setIntegrate(String integrate) {
        this.integrate = integrate;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }




}
