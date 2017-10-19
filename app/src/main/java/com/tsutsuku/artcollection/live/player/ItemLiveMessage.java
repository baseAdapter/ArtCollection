package com.tsutsuku.artcollection.live.player;

/**
 * @Author Tsutsuku
 * @Create 2017/5/7
 * @Description
 */

public class ItemLiveMessage {
    private String name;
    private String avatar;
    private String content;

    public ItemLiveMessage(String name, String avatar, String content) {
        this.name = name;
        this.avatar = avatar;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
