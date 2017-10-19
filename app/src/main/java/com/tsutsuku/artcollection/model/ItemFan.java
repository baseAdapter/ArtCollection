package com.tsutsuku.artcollection.model;

/**
 * @Author Tsutsuku
 * @Create 2017/4/2
 * @Description
 */

public class ItemFan {

    /**
     * Id : 8
     * nickname : 郁金香
     * personalSign :
     * pic : http://yssc.pinnc.com/u/46/1048e7d464ccf27f617a37cbd358f2cc.jpg
     * createTime : 2017-02-21 16:46:15
     */

    private String Id;
    private String nickname;
    private String personalSign;
    private String pic;
    private String createTime;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPersonalSign() {
        return personalSign;
    }

    public void setPersonalSign(String personalSign) {
        this.personalSign = personalSign;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
