package com.tsutsuku.artcollection.model.lesson;

/**
 * @Author Tsutsuku
 * @Create 2017/3/5
 * @Description
 */

public class ItemVideo {

    /**
     * videoId : 1
     * videoTitle : 朱岚：莫兰迪最吸引我的是表现事物的自然本质
     * lecturer : 朱岚
     * videoLength : 60
     * seeCount : 27
     * videoLogo : http://yssc.pinnc.com/upload/2017/02/18/20170218103648540.jpg
     * videoKeyWord : 自然本质
     * videoUrl :
     * commentCount : 1
     */

    private String videoId;
    private String videoTitle;
    private String lecturer;
    private String videoLength;
    private String seeCount;
    private String videoLogo;
    private String videoKeyWord;
    private String videoUrl;
    private String commentCount;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public String getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(String seeCount) {
        this.seeCount = seeCount;
    }

    public String getVideoLogo() {
        return videoLogo;
    }

    public void setVideoLogo(String videoLogo) {
        this.videoLogo = videoLogo;
    }

    public String getVideoKeyWord() {
        return videoKeyWord;
    }

    public void setVideoKeyWord(String videoKeyWord) {
        this.videoKeyWord = videoKeyWord;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
