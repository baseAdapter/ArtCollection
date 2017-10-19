package com.tsutsuku.artcollection.model.shopping;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/3/1
 * @Description
 */

public class VendorInfoBean {


    /**
     * farmId : 12
     * farmName : 名人字画
     * userId : 6
     * coverPic : http://yssc.pinnc.com/upload/2017/04/19/20170419092437129.jpg
     * brief : 当代名人字画
     * address : 浙江省杭州市
     * phone : 15632487952
     * score : 0.00
     * sale : 0
     * briefPics : ["http://yssc.pinnc.com/upload/2017/04/18/20170418113322402.jpg","http://yssc.pinnc.com/upload/2017/04/19/20170419092453597.jpg"]
     * isFollow : 0
     * FollowCount : 2
     * hxAccount : {"uuid":"4e810ab0-23d7-11e7-9950-9d4bb13a9d30","username":"developfarm_12"}
     */

    private String farmId;
    private String farmName;
    private String userId;
    private String coverPic;
    private String brief;
    private String address;
    private String phone;
    private String score;
    private String sale;
    private int isFollow;
    private int FollowCount;
    private HxAccountBean hxAccount;
    private List<String> briefPics;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public int getFollowCount() {
        return FollowCount;
    }

    public void setFollowCount(int FollowCount) {
        this.FollowCount = FollowCount;
    }

    public HxAccountBean getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(HxAccountBean hxAccount) {
        this.hxAccount = hxAccount;
    }

    public List<String> getBriefPics() {
        return briefPics;
    }

    public void setBriefPics(List<String> briefPics) {
        this.briefPics = briefPics;
    }

    public static class HxAccountBean {
        /**
         * uuid : 4e810ab0-23d7-11e7-9950-9d4bb13a9d30
         * username : developfarm_12
         */

        private String uuid;
        private String username;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
