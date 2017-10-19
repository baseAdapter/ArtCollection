package com.tsutsuku.artcollection.other.custom.model;

/**
 * @Author Tsutsuku
 * @Create 2017/6/20
 * @Description
 */

public class CustomDetailInfo {


    /**
     * diyId : 13
     * title : 测试
     * logo : http://yssc.51urmaker.com/upload/2017/07/02/20170702111933576.jpg
     * author : 111
     * authorImg : http://yssc.51urmaker.com/
     * authorKeyWord :
     * authorDes :
     * price : 0.01
     * addTime : 2017-07-02 11:19:33
     * serverUId : 0
     * serverName :
     * detailUrl : http://yssc.51urmaker.com/aweb/CustDiyDetail.php?diyid=13
     * hxAccount : {"uuid":"647f18e0-5cb0-11e7-8b83-b3f3dcabfda6","username":"developuser_0","disname":""}
     */

    private String diyId;
    private String title;
    private String logo;
    private String author;
    private String authorImg;
    private String authorKeyWord;
    private String authorDes;
    private String price;
    private String addTime;
    private String serverUId;
    private String serverName;
    private String detailUrl;
    private HxAccountBean hxAccount;

    public String getDiyId() {
        return diyId;
    }

    public void setDiyId(String diyId) {
        this.diyId = diyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getAuthorKeyWord() {
        return authorKeyWord;
    }

    public void setAuthorKeyWord(String authorKeyWord) {
        this.authorKeyWord = authorKeyWord;
    }

    public String getAuthorDes() {
        return authorDes;
    }

    public void setAuthorDes(String authorDes) {
        this.authorDes = authorDes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getServerUId() {
        return serverUId;
    }

    public void setServerUId(String serverUId) {
        this.serverUId = serverUId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public HxAccountBean getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(HxAccountBean hxAccount) {
        this.hxAccount = hxAccount;
    }

    public static class HxAccountBean {
        /**
         * uuid : 647f18e0-5cb0-11e7-8b83-b3f3dcabfda6
         * username : developuser_0
         * disname :
         */

        private String uuid;
        private String username;
        private String disname;
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

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

        public String getDisname() {
            return disname;
        }

        public void setDisname(String disname) {
            this.disname = disname;
        }
    }
}
