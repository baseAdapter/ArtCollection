package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author Sun Renwei
 * @Create 2017/1/18
 * @Description Content
 */

public class IdentifyInfoBean implements Parcelable {

    /**
     * msgId : 58
     * expertId : 6
     * userName : huwb
     * userPhoto : http://yssc.pinnc.com/u/7/1199340e5287afb6b9f85494936a0135.jpg
     * checkYear : 20世纪30年代
     * checkMoney : 1W元左右
     * checkMeno :
     * checkTime : 2017-01-17 00:00:00
     * checkState : 0
     */

    private String msgId;
    private String expertId;
    private String userName;
    private String userPhoto;
    private String checkYear;
    private String checkMoney;
    private String checkMeno;
    private String checkTime;
    private String checkState;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }

    public String getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(String checkMoney) {
        this.checkMoney = checkMoney;
    }

    public String getCheckMeno() {
        return checkMeno;
    }

    public void setCheckMeno(String checkMeno) {
        this.checkMeno = checkMeno;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public IdentifyInfoBean(String checkMeno, String checkMoney, String checkState, String checkYear, String expertId, String msgId, String userName, String userPhoto) {
        this.checkMeno = checkMeno;
        this.checkMoney = checkMoney;
        this.checkState = checkState;
        this.checkYear = checkYear;
        this.expertId = expertId;
        this.msgId = msgId;
        this.userName = userName;
        this.userPhoto = userPhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msgId);
        dest.writeString(this.expertId);
        dest.writeString(this.userName);
        dest.writeString(this.userPhoto);
        dest.writeString(this.checkYear);
        dest.writeString(this.checkMoney);
        dest.writeString(this.checkMeno);
        dest.writeString(this.checkTime);
        dest.writeString(this.checkState);
    }

    public IdentifyInfoBean() {
    }

    protected IdentifyInfoBean(Parcel in) {
        this.msgId = in.readString();
        this.expertId = in.readString();
        this.userName = in.readString();
        this.userPhoto = in.readString();
        this.checkYear = in.readString();
        this.checkMoney = in.readString();
        this.checkMeno = in.readString();
        this.checkTime = in.readString();
        this.checkState = in.readString();
    }

    public static final Parcelable.Creator<IdentifyInfoBean> CREATOR = new Parcelable.Creator<IdentifyInfoBean>() {
        @Override
        public IdentifyInfoBean createFromParcel(Parcel source) {
            return new IdentifyInfoBean(source);
        }

        @Override
        public IdentifyInfoBean[] newArray(int size) {
            return new IdentifyInfoBean[size];
        }
    };
}
