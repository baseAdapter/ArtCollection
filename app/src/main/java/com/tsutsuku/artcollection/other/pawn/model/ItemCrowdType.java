package com.tsutsuku.artcollection.other.pawn.model;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/26
 * @Description
 */

public class ItemCrowdType {

    /**
     * id : 3
     * crowdId : 4
     * title : 34324
     * content :
     * totalNum : 32
     * minRaised : 23.00
     * haveNum : 5
     * haveRaised : 115.00
     * freight : 34.00
     * return_day : 23
     * briefPics : ["http://yssc.51urmaker.com/upload/2017/06/21/20170621141934586.jpg","http://yssc.51urmaker.com/upload/2017/06/21/20170621141934586.jpg"]
     */

    private String id;
    private String crowdId;
    private String title;
    private String content;
    private String totalNum;
    private String minRaised;
    private String haveNum;
    private String haveRaised;
    private String freight;
    private String return_day;
    private List<String> briefPics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrowdId() {
        return crowdId;
    }

    public void setCrowdId(String crowdId) {
        this.crowdId = crowdId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getMinRaised() {
        return minRaised;
    }

    public void setMinRaised(String minRaised) {
        this.minRaised = minRaised;
    }

    public String getHaveNum() {
        return haveNum;
    }

    public void setHaveNum(String haveNum) {
        this.haveNum = haveNum;
    }

    public String getHaveRaised() {
        return haveRaised;
    }

    public void setHaveRaised(String haveRaised) {
        this.haveRaised = haveRaised;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getReturn_day() {
        return return_day;
    }

    public void setReturn_day(String return_day) {
        this.return_day = return_day;
    }

    public List<String> getBriefPics() {
        return briefPics;
    }

    public void setBriefPics(List<String> briefPics) {
        this.briefPics = briefPics;
    }
}
