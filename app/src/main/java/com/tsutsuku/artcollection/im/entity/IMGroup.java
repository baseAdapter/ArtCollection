package com.tsutsuku.artcollection.im.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Author ChenYingYing
 * @Create 2016/8/28
 * @Description $todo<IM中创建群组的实例>
 */
public class IMGroup extends RealmObject {
    @PrimaryKey
    public String imId;             //环信IM id[即环信IM name,环信以IM name为唯一标示, 格式为"user_ +　userId"]
    public String groupId;          //群组id
    public String groupName;        //群组名称
    public String groupAvatar;      //群组头像
    public String groupOwner;       //群组创建者
    public String groupType;        //群组类型 最大用户数(默认200)
    public String groupDeclared;    //群组简介
    public String groupCreateDate;  //群组创建时间
    public String groupMemberCount; //群组成员数
    public String groupPermission;  //群组加入权限
    public String groupJoined;      //群组是否加入
    public String groupNotice;      //群组通知

    public IMGroup() {
    }

    public IMGroup(String imId, String groupId, String groupName, String groupAvatar, String groupOwner, String groupType, String groupDeclared, String groupCreateDate, String groupMemberCount, String groupPermission, String groupJoined, String groupNotice) {
        this.imId = imId;
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAvatar = groupAvatar;
        this.groupOwner = groupOwner;
        this.groupType = groupType;
        this.groupDeclared = groupDeclared;
        this.groupCreateDate = groupCreateDate;
        this.groupMemberCount = groupMemberCount;
        this.groupPermission = groupPermission;
        this.groupJoined = groupJoined;
        this.groupNotice = groupNotice;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
    }

    public String getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupDeclared() {
        return groupDeclared;
    }

    public void setGroupDeclared(String groupDeclared) {
        this.groupDeclared = groupDeclared;
    }

    public String getGroupCreateDate() {
        return groupCreateDate;
    }

    public void setGroupCreateDate(String groupCreateDate) {
        this.groupCreateDate = groupCreateDate;
    }

    public String getGroupMemberCount() {
        return groupMemberCount;
    }

    public void setGroupMemberCount(String groupMemberCount) {
        this.groupMemberCount = groupMemberCount;
    }

    public String getGroupPermission() {
        return groupPermission;
    }

    public void setGroupPermission(String groupPermission) {
        this.groupPermission = groupPermission;
    }

    public String getGroupJoined() {
        return groupJoined;
    }

    public void setGroupJoined(String groupJoined) {
        this.groupJoined = groupJoined;
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice;
    }
}
