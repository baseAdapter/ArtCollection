package com.tsutsuku.artcollection.ui.exchange;

/**
 * Created by Administrator on 2017/10/8.
 */

public class ExchangeRecord {
    /**
     *
     * recordId:
     * goldAmount: 金币数
     * outOrderId：数量
     * createTime：时间
     * name:产品名称
     * coverPhoto：图片
     *
     **/
    private String goldAmount;
    private int outOrderId;
    private String createTime;
    private String name;
    private String coverPhoto;

    public String getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(String goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(int outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
