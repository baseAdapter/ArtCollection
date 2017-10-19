package com.tsutsuku.artcollection.model;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description 搜索结果Item
 */

public class ItemSearch {

    /**
     * type : 1
     * itemId : 7
     * name : 百饰工艺
     * pic : http://yssc.pinnc.com/upload/2017/02/17/20170217141824986.jpg
     * brief : 琥珀净水金蓝珀宝宝百家锁
     * otherinfo : 0
     */

    private String type;
    private String itemId;
    private String name;
    private String pic;
    private String brief;
    private String otherinfo;
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }
}
