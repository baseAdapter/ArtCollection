package com.tsutsuku.artcollection.model;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description Content
 */

public class ItemVendor {

    /**
     * farmId : 10
     * farmName : 琥珀净水金蓝珀宝宝百家锁
     * pic : http://yssc.pinnc.com/upload/2017/02/17/20170217152008595.jpg
     * address :
     * score : 0.0
     * sale : 0
     */

    private String farmId;
    private String farmName;
    private String pic;
    private String address;
    private String score;
    private String sale;

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }
}
