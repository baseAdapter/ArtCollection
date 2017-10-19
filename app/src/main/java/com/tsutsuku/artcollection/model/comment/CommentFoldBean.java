package com.tsutsuku.artcollection.model.comment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CommentFoldBean implements Parcelable{
    /**
     * commentId : 2
     * pId : 0
     * comcontent : 嗯嗯
     * comTime : 2017.02.22
     * userId : 44
     * nickName : 小禹
     * photo : http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg
     * replay_commnets : [{"commentId":"3","pId":"2","comcontent":"嗯？","comTime":"2017-02-22 21:56:39","userId":"44","displayName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","toUserId":"0","toDisplayName":"","toPhoto":""}]
     */

    private String commentId;
    private String pId;
    private String comcontent;
    private String comTime;
    private String userId;
    private String nickName;
    private String photo;
    private List<ReplayCommnetsBean> replay_commnets;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<ReplayCommnetsBean> getReplay_commnets() {
        return replay_commnets;
    }

    public void setReplay_commnets(List<ReplayCommnetsBean> replay_commnets) {
        this.replay_commnets = replay_commnets;
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
        dest.writeString(this.nickName);
        dest.writeString(this.photo);
        dest.writeList(this.replay_commnets);
    }

    public CommentFoldBean() {
    }

    protected CommentFoldBean(Parcel in) {
        this.commentId = in.readString();
        this.pId = in.readString();
        this.comcontent = in.readString();
        this.comTime = in.readString();
        this.userId = in.readString();
        this.nickName = in.readString();
        this.photo = in.readString();
        this.replay_commnets = new ArrayList<ReplayCommnetsBean>();
        in.readList(this.replay_commnets, ReplayCommnetsBean.class.getClassLoader());
    }

    public static final Creator<CommentFoldBean> CREATOR = new Creator<CommentFoldBean>() {
        @Override
        public CommentFoldBean createFromParcel(Parcel source) {
            return new CommentFoldBean(source);
        }

        @Override
        public CommentFoldBean[] newArray(int size) {
            return new CommentFoldBean[size];
        }
    };
}