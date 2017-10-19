package com.tsutsuku.artcollection.model;

import com.tsutsuku.artcollection.model.activity.ItemActivity;
import com.tsutsuku.artcollection.model.lesson.ItemVideo;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/17
 * @Description Content
 */

public class HomeBean {


    private List<ADBean> topbanner;
    private List<ADBean> menubutton;
    private List<ADBean> top3;
    private List<ItemVideo> video_list;
    private List<ItemActivity> activit_list;
    private List<ItemActivity> vcloud_list;
    private List<MenusBean> menus;

    public List<ADBean> getTopbanner() {
        return topbanner;
    }

    public void setTopbanner(List<ADBean> topbanner) {
        this.topbanner = topbanner;
    }

    public List<ADBean> getMenubutton() {
        return menubutton;
    }

    public void setMenubutton(List<ADBean> menubutton) {
        this.menubutton = menubutton;
    }

    public List<ADBean> getTop3() {
        return top3;
    }

    public void setTop3(List<ADBean> top3) {
        this.top3 = top3;
    }

    public List<ItemVideo> getVideo_list() {
        return video_list;
    }

    public void setVideo_list(List<ItemVideo> video_list) {
        this.video_list = video_list;
    }

    public List<ItemActivity> getActivit_list() {
        return activit_list;
    }

    public void setActivit_list(List<ItemActivity> activit_list) {
        this.activit_list = activit_list;
    }

    public List<ItemActivity> getVcloud_list() {
        return vcloud_list;
    }

    public void setVcloud_list(List<ItemActivity> vcloud_list) {
        this.vcloud_list = vcloud_list;
    }

    public List<MenusBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenusBean> menus) {
        this.menus = menus;
    }

    public static class ADBean {
        /**
         * itemType : 1
         * pic : http://yssc.pinnc.com/upload/2017/02/17/20170217145738303.png
         * name : 玉
         * nameColor : #e8800f
         * description :
         * photosWidth : 751
         * photoHeight : 443
         * outLinkOrId : 1
         */

        private String itemType;
        private String pic;
        private String name;
        private String nameColor;
        private String description;
        private String photosWidth;
        private String photoHeight;
        private String outLinkOrId;

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameColor() {
            return nameColor;
        }

        public void setNameColor(String nameColor) {
            this.nameColor = nameColor;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPhotosWidth() {
            return photosWidth;
        }

        public void setPhotosWidth(String photosWidth) {
            this.photosWidth = photosWidth;
        }

        public String getPhotoHeight() {
            return photoHeight;
        }

        public void setPhotoHeight(String photoHeight) {
            this.photoHeight = photoHeight;
        }

        public String getOutLinkOrId() {
            return outLinkOrId;
        }

        public void setOutLinkOrId(String outLinkOrId) {
            this.outLinkOrId = outLinkOrId;
        }
    }

    public static class MenusBean {
        /**
         * name : 拍卖
         * keyWord : 101
         * otherinfo :
         * pic : http://yssc.pinnc.com/upload/system/paimai@3x.png
         * href : auctionmain.php
         */

        private String name;
        private String keyWord;
        private String otherinfo;
        private String pic;
        private String href;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public String getOtherinfo() {
            return otherinfo;
        }

        public void setOtherinfo(String otherinfo) {
            this.otherinfo = otherinfo;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
