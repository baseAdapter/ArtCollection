package com.tsutsuku.artcollection.model;

/**
 * @Author Sun Renwei
 * @Create 2017/3/2
 * @Description 店铺关注bean
 */

public class ItemFollow {


    /**
     * Id : 7
     * farmName : 未设置
     * personalSign :
     * pic : http://yssc.pinnc.com/u/headDefault@3x.png
     * createTime : 2017-03-02 10:39:15
     */

    private String Id;
    private String farmName;
    private String personalSign;
    private String pic;
    private String createTime;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getPersonalSign() {
        return personalSign;
    }

    public void setPersonalSign(String personalSign) {
        this.personalSign = personalSign;
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
