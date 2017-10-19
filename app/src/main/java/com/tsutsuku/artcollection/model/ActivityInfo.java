package com.tsutsuku.artcollection.model;

import com.tsutsuku.artcollection.model.comment.CommentFoldBean;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/3/18
 * @Description
 */

public class ActivityInfo {

    /**
     * activityId : 5
     * ctype : 3 1:普通活动 2:直播活动
     * activityName : 直播对接测试了
     * activityTime : 2017-03-14
     * deadline_time : 2017-03-30 11:00:32
     * capNumber : 10
     * address : 234234
     * brief : 随着综合国力和人民生活水平的提升，作为中国人精神文
     * coverPhoto : http://yssc.pinnc.com/upload/2017/02/22/20170222144452715.jpg
     * status : 0
     * useMoney : 0.01
     * farmId : 7
     * pushUserId : 6
     * detailUrl : http://yssc.pinnc.com/aweb/ActivityDetail.php?aid=5
     * userList : [{"createTime":"2017-03-02 18:27:53","userId":"6","nickName":"huwb","pic":"http://yssc.pinnc.com/7/d746e34ba1a3ea6789b43e72e7e18a20.jpg"},{"createTime":"2017-03-09 11:05:44","userId":"44","nickName":"小禹","pic":"http://yssc.pinnc.com/45/8a07516121c705a88ab4995d85f9d530.jpg"}]
     * vcloud : {"httpPullUrl":"http://flv89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9.flv?netease=flv89271069.live.126.net","hlsPullUrl":"http://pullhls89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9/playlist.m3u8","rtmpPullUrl":"rtmp://v89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9","name":"直播对接测试了","pushUrl":"rtmp://p89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9?wsSecret=917a9332497712b3b0d146c95bef2f8e&wsTime=1489460333","cid":"24181ebafa704b21ab973dfd77e2d3a9"}
     * productInfo : {"7":{"couponId":0,"deliveryType":1,"deliveryFee":0,"userNote":"","items":[{"productId":5,"buyAmount":1}]}}
     * isJoin : 0
     * canPush : 0u
     * chatRoom : {"groupId":"8753833377793","groupName":"直播对接测试了","photo":""}
     * farmInfo : {"farmName":"百饰工艺","coverPic":"http://yssc.pinnc.com/upload/2017/02/17/20170217141824986.jpg","brief":"琥珀净水金蓝珀宝宝百家锁","isFollow":0,"hxAccount":{"uuid":"02904a30-f4d9-11e6-83d4-63ae95ea7b0d","username":"developfarm_7"}}
     * comments : [{"commentId":"1","pId":"0","comcontent":"23345435","comTime":"2017.02.22","userId":"6","nickName":"huwb","photo":"http://yssc.pinnc.com/u/7/d746e34ba1a3ea6789b43e72e7e18a20.jpg","replay_commnets":[]},{"commentId":"16","pId":"0","comcontent":" Dffffdt","comTime":"2017.03.15","userId":"45","nickName":"郁金香","photo":"http://yssc.pinnc.com/u/46/1048e7d464ccf27f617a37cbd358f2cc.jpg","replay_commnets":[]}]
     */

    private String activityId;
    private String ctype;
    private String activityName;
    private String activityTime;
    private String deadline_time;
    private String capNumber;
    private String address;
    private String brief;
    private String coverPhoto;
    private String status;
    private String useMoney;
    private String farmId;
    private String pushUserId;
    private String detailUrl;
    private VcloudBean vcloud;
    private String productInfo;
    private int isJoin;
    private int canPush;
    private ChatRoomBean chatRoom;
    private FarmInfoBean farmInfo;
    private List<UserListBean> userList;
    private List<CommentFoldBean> comments;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getDeadline_time() {
        return deadline_time;
    }

    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }

    public String getCapNumber() {
        return capNumber;
    }

    public void setCapNumber(String capNumber) {
        this.capNumber = capNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getPushUserId() {
        return pushUserId;
    }

    public void setPushUserId(String pushUserId) {
        this.pushUserId = pushUserId;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public VcloudBean getVcloud() {
        return vcloud;
    }

    public void setVcloud(VcloudBean vcloud) {
        this.vcloud = vcloud;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public int getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(int isJoin) {
        this.isJoin = isJoin;
    }

    public int getCanPush() {
        return canPush;
    }

    public void setCanPush(int canPush) {
        this.canPush = canPush;
    }

    public ChatRoomBean getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoomBean chatRoom) {
        this.chatRoom = chatRoom;
    }

    public FarmInfoBean getFarmInfo() {
        return farmInfo;
    }

    public void setFarmInfo(FarmInfoBean farmInfo) {
        this.farmInfo = farmInfo;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public List<CommentFoldBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentFoldBean> comments) {
        this.comments = comments;
    }

    public static class VcloudBean {
        /**
         * httpPullUrl : http://flv89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9.flv?netease=flv89271069.live.126.net
         * hlsPullUrl : http://pullhls89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9/playlist.m3u8
         * rtmpPullUrl : rtmp://v89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9
         * name : 直播对接测试了
         * pushUrl : rtmp://p89271069.live.126.net/live/24181ebafa704b21ab973dfd77e2d3a9?wsSecret=917a9332497712b3b0d146c95bef2f8e&wsTime=1489460333
         * cid : 24181ebafa704b21ab973dfd77e2d3a9
         */

        private String httpPullUrl;
        private String hlsPullUrl;
        private String rtmpPullUrl;
        private String name;
        private String pushUrl;
        private String cid;

        public String getHttpPullUrl() {
            return httpPullUrl;
        }

        public void setHttpPullUrl(String httpPullUrl) {
            this.httpPullUrl = httpPullUrl;
        }

        public String getHlsPullUrl() {
            return hlsPullUrl;
        }

        public void setHlsPullUrl(String hlsPullUrl) {
            this.hlsPullUrl = hlsPullUrl;
        }

        public String getRtmpPullUrl() {
            return rtmpPullUrl;
        }

        public void setRtmpPullUrl(String rtmpPullUrl) {
            this.rtmpPullUrl = rtmpPullUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }

    public static class ChatRoomBean {
        /**
         * groupId : 8753833377793
         * groupName : 直播对接测试了
         * photo :
         */

        private String groupId;
        private String groupName;
        private String photo;

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

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

    public static class FarmInfoBean {
        /**
         * farmName : 百饰工艺
         * coverPic : http://yssc.pinnc.com/upload/2017/02/17/20170217141824986.jpg
         * brief : 琥珀净水金蓝珀宝宝百家锁
         * isFollow : 0
         * hxAccount : {"uuid":"02904a30-f4d9-11e6-83d4-63ae95ea7b0d","username":"developfarm_7"}
         */

        private String farmName;
        private String coverPic;
        private String brief;
        private int isFollow;
        private HxAccountBean hxAccount;

        public String getFarmName() {
            return farmName;
        }

        public void setFarmName(String farmName) {
            this.farmName = farmName;
        }

        public String getCoverPic() {
            return coverPic;
        }

        public void setCoverPic(String coverPic) {
            this.coverPic = coverPic;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public HxAccountBean getHxAccount() {
            return hxAccount;
        }

        public void setHxAccount(HxAccountBean hxAccount) {
            this.hxAccount = hxAccount;
        }

        public static class HxAccountBean {
            /**
             * uuid : 02904a30-f4d9-11e6-83d4-63ae95ea7b0d
             * username : developfarm_7
             */

            private String uuid;
            private String username;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }

    public static class UserListBean {
        /**
         * createTime : 2017-03-02 18:27:53
         * userId : 6
         * nickName : huwb
         * pic : http://yssc.pinnc.com/7/d746e34ba1a3ea6789b43e72e7e18a20.jpg
         */

        private String createTime;
        private String userId;
        private String nickName;
        private String pic;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
