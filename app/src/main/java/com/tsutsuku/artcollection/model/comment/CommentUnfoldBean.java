package com.tsutsuku.artcollection.model.comment;

/**
 * @Author Sun Renwei
 * @Create 2017/1/17
 * @Description
 */

public class CommentUnfoldBean {

    /**
     * commentId : 1
     * userId : 1000002
     * userName : null
     * userPhoto : http://yssc.pinnc.com/u/
     * toUserId : 0
     * toUserName :
     * content : 好的
     * postTime : 28698381
     */

    private String commentId;
    private String userId;
    private String userName;
    private String userPhoto;
    private String toUserId;
    private String toUserName;
    private String content;
    private String postTime;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
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
}
