package com.tsutsuku.artcollection.model.activity;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description 活动model
 */

public class ItemActivity {

    /**
     * activityId : 20
     * activityName : 那些年我们读过的经典
     * activityTime : 2017-04-21 08:58:07
     * useMoney : 0.00
     * brief : 【展览推荐】之：国家典籍博物馆
     * coverPhoto : http://yssc.pinnc.com/upload/2017/04/22/20170422221723411.png
     * viewCount : 0
     * commentCount : 0
     * status : 1
     * liveStatus : 0
     * pushUserId : 0
     * farmName :
     * joinCount : 0
     */

    private String activityId;
    private String activityName;
    private String activityTime;
    private String useMoney;
    private String brief;
    private String coverPhoto;
    private String viewCount;
    private String commentCount;
    private String status;
    private String liveStatus;
    private String pushUserId;
    private String farmName;
    private String joinCount;
    private boolean isLive;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getPushUserId() {
        return pushUserId;
    }

    public void setPushUserId(String pushUserId) {
        this.pushUserId = pushUserId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(String joinCount) {
        this.joinCount = joinCount;
    }
}
