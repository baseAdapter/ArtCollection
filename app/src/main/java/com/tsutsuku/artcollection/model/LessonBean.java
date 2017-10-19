package com.tsutsuku.artcollection.model;

import com.tsutsuku.artcollection.model.comment.CommentFoldBean;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/3/6
 * @Description
 */

public class LessonBean {

    /**
     * videoId : 2
     * videoTitle : 对话：意大利绘画艺术（4）
     * lecturer : 12
     * videoLength : 12
     * seeCount : 75
     * videoLogo : http://yssc.pinnc.com/upload/2017/02/18/20170218105110112.jpg
     * videoUrl : http://yssc.pinnc.com/videos/1234567.mp4
     * videoDesc : 《回望美好时代——意大利19世纪末—20世纪初绘画精品展》于7月19日开幕。
     * addTime : 2017.03.06
     * videoKeyWord : 绘画艺术
     * detailUrl : http://yssc.pinnc.com/aweb/videoDetail.php?vid=2
     * isCollection : 0
     * comments : [{"commentId":"2","pId":"0","comcontent":"嗯嗯","comTime":"2017.02.22","userId":"44","nickName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","replay_commnets":[{"commentId":"3","pId":"2","comcontent":"嗯？","comTime":"2017-02-22 21:56:39","userId":"44","displayName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","toUserId":"0","toDisplayName":"","toPhoto":""}]},{"commentId":"4","pId":"0","comcontent":"是吧😁","comTime":"2017.02.22","userId":"44","nickName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","replay_commnets":[]},{"commentId":"5","pId":"0","comcontent":"唉唉唉唉唉","comTime":"2017.02.22","userId":"44","nickName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","replay_commnets":[]},{"commentId":"6","pId":"0","comcontent":"好的啊啊啊啊啊啊啊吧","comTime":"2017.02.22","userId":"44","nickName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","replay_commnets":[]},{"commentId":"7","pId":"0","comcontent":"😔😔😄😥😉😞😉🍰👩🏻👩🏻😳😶🙃🐮😶👩🏻😣😛😘😤😮🤠😮😬😬😱😥🤕👹😿😿😻","comTime":"2017.02.22","userId":"44","nickName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","replay_commnets":[]},{"commentId":"8","pId":"0","comcontent":"很多时候","comTime":"2017.02.23","userId":"6","nickName":"huwb","photo":"http://yssc.pinnc.com/u/7/d746e34ba1a3ea6789b43e72e7e18a20.jpg","replay_commnets":[{"commentId":"9","pId":"8","comcontent":"好了啦😳","comTime":"2017-02-23 09:49:19","userId":"44","displayName":"小禹","photo":"http://yssc.pinnc.com/u/45/8a07516121c705a88ab4995d85f9d530.jpg","toUserId":"0","toDisplayName":"","toPhoto":""}]},{"commentId":"10","pId":"0","comcontent":"Ewes ","comTime":"2017.02.23","userId":"45","nickName":"郁金香","photo":"http://yssc.pinnc.com/u/46/1048e7d464ccf27f617a37cbd358f2cc.jpg","replay_commnets":[]}]
     */

    private String videoId;
    private String videoTitle;
    private String lecturer;
    private String videoLength;
    private String seeCount;
    private String videoLogo;
    private String videoUrl;
    private String videoDesc;
    private String addTime;
    private String videoKeyWord;
    private String detailUrl;
    private int isCollection;
    private List<CommentFoldBean> comments;

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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getVideoKeyWord() {
        return videoKeyWord;
    }

    public void setVideoKeyWord(String videoKeyWord) {
        this.videoKeyWord = videoKeyWord;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public List<CommentFoldBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentFoldBean> comments) {
        this.comments = comments;
    }
}
