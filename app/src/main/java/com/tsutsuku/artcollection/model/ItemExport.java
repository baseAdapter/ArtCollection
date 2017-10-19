package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author Sun Renwei
 * @Create 2017/2/28
 * @Description 专家bean
 */

public class ItemExport implements Parcelable{


    /**
     * userId : 3
     * nickname : huwb
     * photo : http://yssc.pinnc.com/u/headDefault@3x.png
     * personalSign :
     * jdSpcateId : 12
     * useMoney : 0.00
     */

    private String userId;
    private String nickname;
    private String photo;
    private String personalSign;
    private String jdSpcateId;
    private String useMoney;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPersonalSign() {
        return personalSign;
    }

    public void setPersonalSign(String personalSign) {
        this.personalSign = personalSign;
    }

    public String getJdSpcateId() {
        return jdSpcateId;
    }

    public void setJdSpcateId(String jdSpcateId) {
        this.jdSpcateId = jdSpcateId;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.nickname);
        dest.writeString(this.photo);
        dest.writeString(this.personalSign);
        dest.writeString(this.jdSpcateId);
        dest.writeString(this.useMoney);
    }

    public ItemExport() {
    }

    protected ItemExport(Parcel in) {
        this.userId = in.readString();
        this.nickname = in.readString();
        this.photo = in.readString();
        this.personalSign = in.readString();
        this.jdSpcateId = in.readString();
        this.useMoney = in.readString();
    }

    public static final Creator<ItemExport> CREATOR = new Creator<ItemExport>() {
        @Override
        public ItemExport createFromParcel(Parcel source) {
            return new ItemExport(source);
        }

        @Override
        public ItemExport[] newArray(int size) {
            return new ItemExport[size];
        }
    };
}
