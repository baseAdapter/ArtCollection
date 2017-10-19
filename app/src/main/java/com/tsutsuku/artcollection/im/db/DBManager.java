package com.tsutsuku.artcollection.im.db;




import com.tsutsuku.artcollection.im.entity.IMGroup;
import com.tsutsuku.artcollection.im.entity.IMMember;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @Author Sun Renwei
 * @Create 2016/7/29
 * @Description IM数据库管理类[好友和群组]
 */
public class DBManager {

    /**
     * 添加同窗
     *
     * @param member
     */
    public static void addMember(final IMMember member) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(member);
            }
        });
        realm.close();
    }

    /**
     * 修改好友关系
     *
     * @param imId   好友imId
     * @param status 修改后的状态
     */
    public static void changeFriendStatus(final String imId, final String status) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(IMMember.class).equalTo("imId", imId).findFirst().setStatus(status);
            }
        });
        realm.close();
    }

    /**
     * 添加或更新同窗信息
     *
     * @param member
     */
    public static void addOrUpdateMember(final IMMember member) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(member);
            }
        });
    }

    /**
     * 查询全部好友，返回查询结果[按首字母升序排序]
     *
     * @return RealmResults<IMFriend>
     */
    public static ArrayList<IMMember> queryFriend() {
        ArrayList<IMMember> friends = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<IMMember> results = realm.where(IMMember.class).equalTo("status", IMMember.FRIEND).findAll();
        friends.addAll(results);
        realm.close();
        return friends;
    }

    /**
     * 根据关键词查询好友
     * @param key
     * @return
     */
    public static ArrayList<IMMember> queryFriend(String key) {
        ArrayList<IMMember> friends = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<IMMember> results = realm.where(IMMember.class).equalTo("status", IMMember.FRIEND).findAll();
        friends.addAll(results);
        for (IMMember friend : friends){
            if (!friend.getCall().contains(key)){
                friends.remove(friend);
            }
        }
        realm.close();
        return friends;
    }

    /**
     * 清空好友数据库
     */
    public static void clearFriends() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<IMMember> results = realm.where(IMMember.class).equalTo("status", IMMember.FRIEND).findAll();
        realm.beginTransaction();
        for (IMMember member : results) {
            member.setStatus(IMMember.NORMAL);
        }
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 清空用户清单数据库
     */
    public static void clearIMMember() {
        Realm realm = Realm.getDefaultInstance();
        realm.where(IMMember.class).findAll().deleteAllFromRealm();
        realm.close();
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    public static IMMember getMember(String userId) {
        Realm realm = Realm.getDefaultInstance();
        IMMember friend = realm.where(IMMember.class).equalTo("userId", userId).findFirst();
        realm.close();
        return friend;
    }

    public static IMMember getIMMember(String hxAccount) {
        Realm realm = Realm.getDefaultInstance();
        IMMember friend = realm.where(IMMember.class).equalTo("imId", hxAccount).findFirst();
        realm.close();
        return friend;
    }

    public static String getFriendStatus(String userId) {
        Realm realm = Realm.getDefaultInstance();
        String status = realm.where(IMMember.class).equalTo("userId", userId).findFirst().getStatus();
        realm.close();
        return status;
    }

    /**
     * 根据环信账号查看是否已存储用户信息，否则创建新用户
     * @param imId
     */
    public static void addMember(String imId, String userId, String nick, String avatar) {
        Realm realm = Realm.getDefaultInstance();
        IMMember member = realm.where(IMMember.class).equalTo("imId", imId).findFirst();
        realm.beginTransaction();
        if (member == null){
            member = new IMMember();
            member.setImId(imId);
        }
        member.setUserId(userId);
        member.setNickName(nick);
        member.setAvatar(avatar);
        realm.copyToRealmOrUpdate(member);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 更新群组信息
     *
     * @param group
     */
    public static void addOrUpdateGroup(final IMGroup group) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(group);
            }
        });
        realm.close();

    }


    /**
     * 查询全部群组
     *
     * @return ArrayList<IMGroup>
     */
    public static ArrayList<IMGroup> queryGroup() {
        ArrayList<IMGroup> groups = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<IMGroup> results = realm.where(IMGroup.class).findAll();
        groups.addAll(results);
        realm.close();
        return groups;
    }


    public static String getFriendCall(String userId) {
        Realm realm = Realm.getDefaultInstance();
        String call = realm.where(IMMember.class).equalTo("userId", userId).findFirst().getCall();
        realm.close();
        return call;
    }

    public static void setFriendNick(String userId, String nick) {
        Realm realm = Realm.getDefaultInstance();
        IMMember friend = realm.where(IMMember.class).equalTo("userId", userId).findFirst();
        realm.beginTransaction();
        friend.setNickName(nick);
        realm.commitTransaction();
    }
}
