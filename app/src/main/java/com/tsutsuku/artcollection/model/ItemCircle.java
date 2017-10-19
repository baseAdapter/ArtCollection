package com.tsutsuku.artcollection.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/6
 * @Description Content
 */

public class ItemCircle implements Parcelable{


    /**
     * msgId : 23
     * userId : 44
     * userName : 小禹
     * userPhoto : http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg
     * title : 2222
     * ctype : 0
     * useMoney : 0.00
     * content : 33333
     * pics : ["http://yssc.pinnc.com/circle/2017/03/12/37693cfc748049e45d87b8c7d8b9aacd1_280_421_thumb.jpg"]
     * postTime : 375988
     * address :
     * longitude : 0
     * latitude : 0
     * checkState : 0
     * isFollow : 0
     * isFree : 0
     * spcateId : 0
     * expertUserId : 0
     * commentCount : 7
     * thumbs : [{"userId":"6","userName":"huwb","userPhoto":"http://yssc.pinnc.com/u/7/d746e34ba1a3ea6789b43e72e7e18a20.jpg"}]
     * spacateName :
     */

    private String msgId;
    private String userId;
    private String userName;
    private String userPhoto;
    private String title;
    private String ctype;
    private String useMoney;
    private String content;
    private String postTime;
    private String address;
    private String longitude;
    private String latitude;
    private String checkState;
    private String isFollow;
    private String isFree;
    private String spcateId;
    private String expertUserId;
    private int commentCount;
    private String spacateName;
    private List<String> pics;
    private List<ThumbsBean> thumbs;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getSpcateId() {
        return spcateId;
    }

    public void setSpcateId(String spcateId) {
        this.spcateId = spcateId;
    }

    public String getExpertUserId() {
        return expertUserId;
    }

    public void setExpertUserId(String expertUserId) {
        this.expertUserId = expertUserId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getSpacateName() {
        return spacateName;
    }

    public void setSpacateName(String spacateName) {
        this.spacateName = spacateName;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<ThumbsBean> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<ThumbsBean> thumbs) {
        this.thumbs = thumbs;
    }

    public static class ThumbsBean implements Parcelable{
        /**
         * userId : 6
         * userName : huwb
         * userPhoto : http://yssc.pinnc.com/u/7/d746e34ba1a3ea6789b43e72e7e18a20.jpg
         */

        private String userId;
        private String userName;
        private String userPhoto;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userId);
            dest.writeString(this.userName);
            dest.writeString(this.userPhoto);
        }

        public ThumbsBean() {
        }

        public ThumbsBean(String userId) {
            this.userId = userId;
        }

        protected ThumbsBean(Parcel in) {
            this.userId = in.readString();
            this.userName = in.readString();
            this.userPhoto = in.readString();
        }

        public static final Creator<ThumbsBean> CREATOR = new Creator<ThumbsBean>() {
            @Override
            public ThumbsBean createFromParcel(Parcel source) {
                return new ThumbsBean(source);
            }

            @Override
            public ThumbsBean[] newArray(int size) {
                return new ThumbsBean[size];
            }
        };

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ThumbsBean){
                if (this.getUserId().equals(((ThumbsBean)obj).getUserId())){
                    return true;
                }
            }
            return false;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msgId);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userPhoto);
        dest.writeString(this.title);
        dest.writeString(this.ctype);
        dest.writeString(this.useMoney);
        dest.writeString(this.content);
        dest.writeString(this.postTime);
        dest.writeString(this.address);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.checkState);
        dest.writeString(this.isFollow);
        dest.writeString(this.isFree);
        dest.writeString(this.spcateId);
        dest.writeString(this.expertUserId);
        dest.writeInt(this.commentCount);
        dest.writeStringList(this.pics);
        dest.writeTypedList(this.thumbs);
        dest.writeString(this.spacateName);
    }

    public ItemCircle() {
    }

    protected ItemCircle(Parcel in) {
        this.msgId = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.userPhoto = in.readString();
        this.title = in.readString();
        this.ctype = in.readString();
        this.useMoney = in.readString();
        this.content = in.readString();
        this.postTime = in.readString();
        this.address = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.checkState = in.readString();
        this.isFollow = in.readString();
        this.isFree = in.readString();
        this.spcateId = in.readString();
        this.expertUserId = in.readString();
        this.commentCount = in.readInt();
        this.pics = in.createStringArrayList();
        this.thumbs = in.createTypedArrayList(ThumbsBean.CREATOR);
        this.spacateName = in.readString();
    }

    public static final Creator<ItemCircle> CREATOR = new Creator<ItemCircle>() {
        @Override
        public ItemCircle createFromParcel(Parcel source) {
            return new ItemCircle(source);
        }

        @Override
        public ItemCircle[] newArray(int size) {
            return new ItemCircle[size];
        }
    };
}
