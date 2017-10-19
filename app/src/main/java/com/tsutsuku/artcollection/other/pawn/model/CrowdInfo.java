package com.tsutsuku.artcollection.other.pawn.model;

import com.tsutsuku.artcollection.model.comment.CommentFoldBean;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/26
 * @Description
 */

public class CrowdInfo {

    /**
     * id : 4
     * title : 34324
     * logo : http://yssc.51urmaker.com/upload/2017/06/21/20170621142444721.jpg
     * totalRaised : 34.00
     * minRaised : 44.00
     * haveNum : 1
     * author : 34
     * authorImg :
     * authorDes : 234
     * beginTime : 2017-06-21 14:25:11
     * endTime : 2017-07-15 14:25:12
     * endIntTime : 1622514
     * status : 1
     * addTime : 2017-06-21 14:24:44
     * haveRaised : 0.01
     * detailUrl : http://yssc.51urmaker.com/aweb/CrowdDetail.php?id=4
     * detailDoUrl : http://yssc.51urmaker.com/aweb/CrowdDo.php?id=4
     * briefPics : ["http://yssc.51urmaker.com/upload/2017/06/21/20170621141934586.jpg","http://yssc.51urmaker.com/upload/2017/06/21/20170621141934586.jpg"]
     */

    private String id;
    private String title;
    private String logo;
    private String totalRaised;
    private String minRaised;
    private String haveNum;
    private String author;
    private String authorImg;
    private String authorDes;
    private String beginTime;
    private String endTime;
    private String endIntTime;
    private String status;
    private String addTime;
    private String haveRaised;
    private String detailUrl;
    private String detailDoUrl;
    private List<String> briefPics;
    private List<CommentFoldBean> comments;

    public List<CommentFoldBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentFoldBean> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTotalRaised() {
        return totalRaised;
    }

    public void setTotalRaised(String totalRaised) {
        this.totalRaised = totalRaised;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getAuthorDes() {
        return authorDes;
    }

    public void setAuthorDes(String authorDes) {
        this.authorDes = authorDes;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndIntTime() {
        return endIntTime;
    }

    public void setEndIntTime(String endIntTime) {
        this.endIntTime = endIntTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getHaveRaised() {
        return haveRaised;
    }

    public void setHaveRaised(String haveRaised) {
        this.haveRaised = haveRaised;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDetailDoUrl() {
        return detailDoUrl;
    }

    public void setDetailDoUrl(String detailDoUrl) {
        this.detailDoUrl = detailDoUrl;
    }

    public List<String> getBriefPics() {
        return briefPics;
    }

    public void setBriefPics(List<String> briefPics) {
        this.briefPics = briefPics;
    }
}
