package com.tsutsuku.artcollection.im.entity;

import android.text.TextUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Author Sun Renwei
 * @Create 2016/7/29
 * @Description IM用户实例
 */
public class IMMember extends RealmObject {
    public static final String SELF = "-1"; // 自己
    public static final String NORMAL = "0"; // 非好友
    public static final String FRIEND = "1"; // 好友
    public static final String BE_ADDED = "2"; // 被添加好友


    @PrimaryKey
    private String userId; // 用户id
    private String imId;   // 环信IM id
    private String userName; // 姓名[用户自定义的昵称]
    private String avatar; // 头像
    private String status; // 好友类型[是否与当前用户是好友]
    private String initial; // 称呼首字母
    private String mobile; // 电话号码
    private String nickName; // 备注昵称
    private String sex; // 性别
    private String sign; // 签名
    private String title = ""; // 头衔

    public IMMember() {
    }

    public IMMember(String imId, String userId, String userName, String avatar, String status, String mobile, String nickName, String sex, String sign) {
        this.imId = imId;
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
        this.status = status;
        this.mobile = mobile;
        this.nickName = nickName;
        this.sex = sex;
        this.sign = sign;
    }

    // 称呼[根据备注昵称、自定义昵称和电话号码生成]
    public String getCall() {
        if (!TextUtils.isEmpty(nickName)) {
            return nickName;
        } else if (!TextUtils.isEmpty(userName)) {
            return userName;
        } else {
            return mobile;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


}
