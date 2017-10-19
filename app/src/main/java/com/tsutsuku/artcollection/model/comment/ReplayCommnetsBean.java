package com.tsutsuku.artcollection.model.comment;

import android.os.Parcel;
import android.os.Parcelable;

public class ReplayCommnetsBean implements Parcelable{
    /**
     * commentId : 3
     * pId : 2
     * comcontent : 嗯？
     * comTime : 2017-02-22 21:56:39
     * userId : 44
     * displayName : 小禹
     * photo : http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg
     * toUserId : 0
     * toDisplayName :
     * toPhoto :
     */

    private String commentId;
    private String pId;
    private String comcontent;
    private String comTime;
    private String userId;
    private String displayName;
    private String photo;
    private String toUserId;
    private String toDisplayName;
    private String toPhoto;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getComcontent() {
        return comcontent;
    }

    public void setComcontent(String comcontent) {
        this.comcontent = comcontent;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToDisplayName() {
        return toDisplayName;
    }

    public void setToDisplayName(String toDisplayName) {
        this.toDisplayName = toDisplayName;
    }

    public String getToPhoto() {
        return toPhoto;
    }

    public void setToPhoto(String toPhoto) {
        this.toPhoto = toPhoto;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commentId);
        dest.writeString(this.pId);
        dest.writeString(this.comcontent);
        dest.writeString(this.comTime);
        dest.writeString(this.userId);
        dest.writeString(this.displayName);
        dest.writeString(this.photo);
        dest.writeString(this.toUserId);
        dest.writeString(this.toDisplayName);
        dest.writeString(this.toPhoto);
    }

    public ReplayCommnetsBean() {
    }

    protected ReplayCommnetsBean(Parcel in) {
        this.commentId = in.readString();
        this.pId = in.readString();
        this.comcontent = in.readString();
        this.comTime = in.readString();
        this.userId = in.readString();
        this.displayName = in.readString();
        this.photo = in.readString();
        this.toUserId = in.readString();
        this.toDisplayName = in.readString();
        this.toPhoto = in.readString();
    }

    public static final Creator<ReplayCommnetsBean> CREATOR = new Creator<ReplayCommnetsBean>() {
        @Override
        public ReplayCommnetsBean createFromParcel(Parcel source) {
            return new ReplayCommnetsBean(source);
        }

        @Override
        public ReplayCommnetsBean[] newArray(int size) {
            return new ReplayCommnetsBean[size];
        }
    };
}