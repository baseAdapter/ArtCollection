package com.tsutsuku.artcollection.model;


/**
 * @Author Sun Renwei
 * @Create 2017/2/21
 * @Description Content
 */

public class CountInfoBean {

    /**
     * userId : 43
     * integrateTotal : 10
     * fansTotal : 0
     * followTotal : 0
     * needPayCount : 0
     * needSendCount : 0
     * needRevCount : 0
     * needEvaluateCount : 0
     */

    private String userId;
    private String integrateTotal;
    private String fansTotal;
    private String followTotal;
    private int goldTotal;

    public int getGoldTotal() {
        return goldTotal;
    }

    public void setGoldTotal(int goldTotal) {
        this.goldTotal = goldTotal;
    }

    private int needPayCount;
    private int needSendCount;
    private int needRevCount;
    private int needEvaluateCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIntegrateTotal() {
        return integrateTotal;
    }

    public void setIntegrateTotal(String integrateTotal) {
        this.integrateTotal = integrateTotal;
    }

    public String getFansTotal() {
        return fansTotal;
    }

    public void setFansTotal(String fansTotal) {
        this.fansTotal = fansTotal;
    }

    public String getFollowTotal() {
        return followTotal;
    }

    public void setFollowTotal(String followTotal) {
        this.followTotal = followTotal;
    }

    public int getNeedPayCount() {
        return needPayCount;
    }

    public void setNeedPayCount(int needPayCount) {
        this.needPayCount = needPayCount;
    }

    public int getNeedSendCount() {
        return needSendCount;
    }

    public void setNeedSendCount(int needSendCount) {
        this.needSendCount = needSendCount;
    }

    public int getNeedRevCount() {
        return needRevCount;
    }

    public void setNeedRevCount(int needRevCount) {
        this.needRevCount = needRevCount;
    }

    public int getNeedEvaluateCount() {
        return needEvaluateCount;
    }

    public void setNeedEvaluateCount(int needEvaluateCount) {
        this.needEvaluateCount = needEvaluateCount;
    }
}
