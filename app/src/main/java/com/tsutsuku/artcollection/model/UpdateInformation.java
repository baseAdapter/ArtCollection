package com.tsutsuku.artcollection.model;

/**
 * Created by Administrator on 2017/10/30.
 */

public class UpdateInformation {

    /**
     * terminal_version : 1.2.1
     * content : 【新增】用户注册或购买商品可获得一定数量的积分
     【新增】积分兑换金币，金币可用于兑换商品或提现
     【新增】金币商城，用金币兑换你喜欢的礼品
     【新增】app推广活动，分享给你的好友赚取金币
     【新增】版本更新提示，让用户第一时间收到最棒更新
     【优化】全新注册，登录，找回密码界面，增强用户体验
     * status : 2
     * address : https://www.2345.com/
     */

    private String terminal_version;
    private String content;
    private String status;
    private String address;

    public String getTerminal_version() {
        return terminal_version;
    }

    public void setTerminal_version(String terminal_version) {
        this.terminal_version = terminal_version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
