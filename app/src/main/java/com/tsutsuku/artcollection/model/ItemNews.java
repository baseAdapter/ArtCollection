package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author Sun Renwei
 * @Create 2017/1/11
 * @Description Content
 */

public class ItemNews implements Parcelable {


    /**
     * newsId : 19
     * newName : 高官们的学霸往事:王毅高考语文成绩全班第一
     * brief : 中共中央政治局委员、国务院副总理马凯对军转安置工作进行动员部署。他指出，各地区各部门要正确把握深化国防和军队改革期间军转安置工作面临的新形
     * newTime : 2016-06-07
     * coverPhoto : http://yssc.pinnc.com/
     * detailUrl : http://yssc.pinnc.com?nid=19
     */

    private String newsId;
    private String newName;
    private String brief;
    private String newTime;
    private String coverPhoto;
    private String detailUrl;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getNewTime() {
        return newTime;
    }

    public void setNewTime(String newTime) {
        this.newTime = newTime;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newsId);
        dest.writeString(this.newName);
        dest.writeString(this.brief);
        dest.writeString(this.newTime);
        dest.writeString(this.coverPhoto);
        dest.writeString(this.detailUrl);
    }

    public ItemNews() {
    }

    protected ItemNews(Parcel in) {
        this.newsId = in.readString();
        this.newName = in.readString();
        this.brief = in.readString();
        this.newTime = in.readString();
        this.coverPhoto = in.readString();
        this.detailUrl = in.readString();
    }

    public static final Creator<ItemNews> CREATOR = new Creator<ItemNews>() {
        @Override
        public ItemNews createFromParcel(Parcel source) {
            return new ItemNews(source);
        }

        @Override
        public ItemNews[] newArray(int size) {
            return new ItemNews[size];
        }
    };
}
