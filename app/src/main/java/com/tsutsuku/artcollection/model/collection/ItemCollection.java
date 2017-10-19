package com.tsutsuku.artcollection.model.collection;

/**
 * @Author Tsutsuku
 * @Create 2017/3/25
 * @Description
 */

public class ItemCollection {


    /**
     * id : 2
     * ctype : 1
     * title : 对话：意大利绘画艺术（4）
     * brief : 12
     * other : 12
     * pic : http://yssc.pinnc.com/upload/2017/02/18/20170218105110112.jpg
     * createTime : 2017-03-07 19:51:47
     */

    private String id;
    private String ctype;
    private String title;
    private String brief;
    private String other;
    private String pic;
    private String createTime;
    private String isAuction;

    public String getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(String isAuction) {
        this.isAuction = isAuction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
