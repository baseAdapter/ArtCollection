package com.tsutsuku.artcollection.im;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.im.db.DBManager;
import com.tsutsuku.artcollection.im.entity.IMMember;
import com.tsutsuku.artcollection.im.parse.PreferenceManager;
import com.tsutsuku.artcollection.im.parse.UserProfileManager;
import com.tsutsuku.artcollection.im.receiver.CallReceiver;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.TLog;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class IMHelper {
    /**
     * data sync listener
     */
    static public interface DataSyncListener {
        /**
         * sync complete
         *
         * @param success true：data sync successful，false: failed to sync data
         */
        public void onSyncComplete(boolean success);
    }

    protected static final String TAG = "DemoHelper";

    private EaseUI easeUI;

    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;

    private static IMHelper instance = null;

    public boolean isVoiceCalling;
    public boolean isVideoCalling;

    private String username;

    private Context appContext;

    private CallReceiver callReceiver;

    private EMConnectionListener connectionListener;

    private LocalBroadcastManager broadcastManager;

    private boolean isGroupAndContactListenerRegisted;

    private UserProfileManager userProManager;

    private UserProfileManager groupProManager;

    private Map<String, EaseUser> contactList;

    private IMHelper() {
    }

    public synchronized static IMHelper getInstance() {
        if (instance == null) {
            instance = new IMHelper();
        }
        return instance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(Context context) {
        EMOptions options = initChatOptions();
        //use default options if options is null
        if (EaseUI.getInstance().init(context, options)) {

            appContext = context;

            //debug mode, you'd better set it to false, if you want release your App officially.
            EMClient.getInstance().setDebugMode(true);
            //get easeui instance
            easeUI = EaseUI.getInstance();
            //to set user's profile and avatar
            setEaseUIProviders();

            EMClient.getInstance().callManager().getVideoCallHelper().setAdaptiveVideoFlag(true);
            setGlobalListeners();
            broadcastManager = LocalBroadcastManager.getInstance(appContext);
            initDbDao();

            PreferenceManager.init(context);
        }
    }


    private EMOptions initChatOptions() {
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        return options;
    }

    /**
     * 根据用户imId返回相应用户称呼[用户称呼、群组名称或通知名称]
     *
     * @param username
     * @return
     */
    private EaseUser getUserInfo(String username) {
        // To get instance of EaseUser, here we get it from the user list in memory
        // You'd better cache it if you get it from your server
        EaseUser user = null;
        if (username.equals(EMClient.getInstance().getCurrentUser()))
            return getUserProfileManager().getCurrentUserInfo();

        Realm realm = Realm.getDefaultInstance();
        if ("deve".equals(username.substring(0, 4)) || "admin".equals(username.substring(0, 5))) { // 用户称呼
            IMMember friend = DBManager.getIMMember(username);
            if (friend != null) {
                try {
                    user = new EaseUser(username);
                    user.setAvatar(friend.getAvatar());
                    user.setNick(friend.getCall());
                } catch (Exception e) {
                    Log.e("user setAvatar error", e.toString());
                }
            }
        }
        realm.close();

        // if user is not in your contacts, set inital letter for him/her
        if (user == null) {
            user = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(user);
        }
        return user;
    }

    public UserProfileManager getUserProfileManager() {
        if (userProManager == null) {
            userProManager = new UserProfileManager();
        }
        return userProManager;
    }

    public UserProfileManager getGroupProManager() {
        if (groupProManager == null) {
            groupProManager = new UserProfileManager();
        }
        return groupProManager;
    }

    protected void setEaseUIProviders() {
        // set profile provider if you want easeUI to handle avatar and nickname
        easeUI.setUserProfileProvider(new EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    // set options
//

//        //set emoji icon provider
//        easeUI.setEmojiconInfoProvider(new EaseEmojiconInfoProvider() {
//
//            @Override
//            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
//                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
//                for (EaseEmojicon emojicon : data.getEmojiconList()) {
//                    if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
//                        return emojicon;
//                    }
//                }
//                return null;
//            }
//
//            @Override
//            public Map<String, Object> getTextEmojiconMapping() {
//                return null;
//            }
//        });

    //set notification options, will use default if you don't set it
//        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotificationInfoProvider() {
//
//            @Override
//            public String getTitle(EMMessage message) {
//                //you can update title here
//                return null;
//            }
//
//            @Override
//            public int getSmallIcon(EMMessage message) {
//                //you can update icon here
//                return 0;
//            }
//
//            @Override
//            public String getDisplayedText(EMMessage message) {
//                // be used on notification bar, different text according the message type.
//                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
//                if (message.getType() == Type.TXT) {
//                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
//                }
//                EaseUser user = getUserInfo(message.getFrom());
//                if (user != null) {
//                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
//                        return String.format(appContext.getString(R.string.em_at_your_in_group), user.getNick());
//                    }
//                    return user.getNick() + ": " + ticker;
//                } else {
//                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
//                        return String.format(appContext.getString(R.string.em_at_your_in_group), message.getFrom());
//                    }
//                    return message.getFrom() + ": " + ticker;
//                }
//            }
//
//            @Override
//            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
//                // here you can customize the text.
//                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
//                return null;
//            }
//
//            @Override
//            public Intent getLaunchIntent(EMMessage message) {
//                // you can set what activity you want display when user click the notification
//                Intent intent = new Intent(appContext, ChatActivity.class);
//                // open calling activity if there is call
//                if (isVideoCalling) {
//                    intent = new Intent(appContext, VideoCallActivity.class);
//                } else if (isVoiceCalling) {
//                    intent = new Intent(appContext, VoiceCallActivity.class);
//                } else {
//                    ChatType chatType = message.getChatType();
//                    if (chatType == ChatType.Chat) { // single chat message
//                        intent.putExtra("userId", message.getFrom());
//                        intent.putExtra("chatType", IMConstant.CHATTYPE_SINGLE);
//                    } else { // group chat message
//                        // message.getTo() is the group id
//                        intent.putExtra("userId", message.getTo());
//                        if (chatType == ChatType.GroupChat) {
//                            intent.putExtra("chatType", IMConstant.CHATTYPE_GROUP);
//                        } else {
//                            intent.putExtra("chatType", IMConstant.CHATTYPE_CHATROOM);
//                        }
//
//                    }
//                }
//                return intent;
//            }
//        });
//    }

    /**
     * set global listener
     */
    protected void setGlobalListeners() {

        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
//                if (error == EMError.USER_REMOVED) {
//                    onCurrentAccountRemoved();
//                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                    onConnectionConflict();
//                }
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
//                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
//                    EMLog.d(TAG, "group and contact already synced with servre");
//                } else {
//                    if (!isGroupsSyncedWithServer) {
//                        asyncFetchGroupsFromServer(null);
//                    }
//
//                    if (!isContactsSyncedWithServer) {
//                        asyncFetchContactsFromServer(null);
//                    }
//
//                    if (!isBlackListSyncedWithServer) {
//                        asyncFetchBlackListFromServer(null);
//                    }
//                }
            }
        };

        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }

        //register incoming call receiver
        appContext.registerReceiver(callReceiver, callFilter);
        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);
        //register group and contact event listener
        registerGroupAndContactListener();
        //register message event listener
        registerMessageListener();

    }

    private void initDbDao() {
//        inviteMessgeDao = new InviteMessgeDao(appContext);
//        userDao = new UserDao(appContext);
    }

    /**
     * get contact list
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            Realm realm = Realm.getDefaultInstance();
            List<IMMember> list = DBManager.queryFriend();
            Map<String, EaseUser> users = new HashMap<>();
            for (IMMember member : list) {
                EaseUser user = new EaseUser(member.getImId());
                user.setNick(member.getCall());
                user.setAvatar(member.getAvatar());
                EaseCommonUtils.setUserInitialLetter(user);
                users.put(member.getImId(), user);
            }
            contactList = users;
        }

        // return a empty non-null object to avoid app crash
        if (contactList == null) {
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }

    /**
     * register group and contact listener, you need register when login
     */
    public void registerGroupAndContactListener() {
        if (!isGroupAndContactListenerRegisted) {
//            EMClient.getInstance().groupManager().addGroupChangeListener(new MyGroupChangeListener());
//            EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
            isGroupAndContactListenerRegisted = true;
        }

    }

    /**
     * group change listener
     */
//    class MyGroupChangeListener implements EMGroupChangeListener {
//
//        @Override
//        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
//
//            new InviteMessgeDao(appContext).deleteMessage(groupId);
//
//            // user invite you to join group
//            InviteMessage msg = new InviteMessage();
//            msg.setFrom(groupId);
//            msg.setTime(System.currentTimeMillis());
//            msg.setGroupId(groupId);
//            msg.setGroupName(groupName);
//            msg.setReason(reason);
//            msg.setGroupInviter(inviter);
//            Log.d(TAG, "receive invitation to join the group：" + groupName);
//            msg.setStatus(InviteMesageStatus.GROUPINVITATION);
//            notifyNewInviteMessage(msg);
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//
//        @Override
//        public void onInvitationAccpted(String groupId, String invitee, String reason) {
//
//            new InviteMessgeDao(appContext).deleteMessage(groupId);
//
//            //user accept your invitation
//            boolean hasGroup = false;
//            EMGroup _group = null;
//            for (EMGroup group : EMClient.getInstance().groupManager().getAllGroups()) {
//                if (group.getGroupId().equals(groupId)) {
//                    hasGroup = true;
//                    _group = group;
//                    break;
//                }
//            }
//            if (!hasGroup)
//                return;
//
//            InviteMessage msg = new InviteMessage();
//            msg.setFrom(groupId);
//            msg.setTime(System.currentTimeMillis());
//            msg.setGroupId(groupId);
//            msg.setGroupName(_group == null ? groupId : _group.getGroupName());
//            msg.setReason(reason);
//            msg.setGroupInviter(invitee);
//            Log.d(TAG, invitee + "Accept to join the group：" + _group == null ? groupId : _group.getGroupName());
//            msg.setStatus(InviteMesageStatus.GROUPINVITATION_ACCEPTED);
//            notifyNewInviteMessage(msg);
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//
//        @Override
//        public void onInvitationDeclined(String groupId, String invitee, String reason) {
//
//            new InviteMessgeDao(appContext).deleteMessage(groupId);
//
//            //user declined your invitation
//            boolean hasGroup = false;
//            EMGroup group = null;
//            for (EMGroup _group : EMClient.getInstance().groupManager().getAllGroups()) {
//                if (_group.getGroupId().equals(groupId)) {
//                    group = _group;
//                    hasGroup = true;
//                    break;
//                }
//            }
//            if (!hasGroup)
//                return;
//
//            InviteMessage msg = new InviteMessage();
//            msg.setFrom(groupId);
//            msg.setTime(System.currentTimeMillis());
//            msg.setGroupId(groupId);
//            msg.setGroupName(group == null ? groupId : group.getGroupName());
//            msg.setReason(reason);
//            msg.setGroupInviter(invitee);
//            Log.d(TAG, invitee + "Declined to join the group：" + group == null ? groupId : group.getGroupName());
//            msg.setStatus(InviteMesageStatus.GROUPINVITATION_DECLINED);
//            notifyNewInviteMessage(msg);
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//
//        @Override
//        public void onUserRemoved(String groupId, String groupName) {
//            //user is removed from group
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//
//        @Override
//        public void onGroupDestroy(String groupId, String groupName) {
//            // group is dismissed,
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//
//        @Override
//        public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {
//
//            // user apply to join group
//            InviteMessage msg = new InviteMessage();
//            msg.setFrom(applyer);
//            msg.setTime(System.currentTimeMillis());
//            msg.setGroupId(groupId);
//            msg.setGroupName(groupName);
//            msg.setReason(reason);
//            Log.d(TAG, applyer + " Apply to join group：" + groupName);
//            msg.setStatus(InviteMesageStatus.BEAPPLYED);
//            notifyNewInviteMessage(msg);
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//
//        @Override
//        public void onApplicationAccept(String groupId, String groupName, String accepter) {
//
//            String st4 = appContext.getString(R.string.Agreed_to_your_group_chat_application);
//            // your application was accepted
//            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
//            msg.setChatType(ChatType.GroupChat);
//            msg.setFrom(accepter);
//            msg.setTo(groupId);
//            msg.setMsgId(UUID.randomUUID().toString());
//            msg.addBody(new EMTextMessageBody(accepter + " " + st4));
//            msg.setStatus(Status.SUCCESS);
//            // save accept message
//            EMClient.getInstance().chatManager().saveMessage(msg);
//            // notify the accept message
//            getNotifier().vibrateAndPlayTone(msg);
//
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//
//        @Override
//        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
//            // your application was declined, we do nothing here in demo
//        }
//
//        @Override
//        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
//            // got an invitation
//            String st3 = appContext.getString(R.string.Invite_you_to_join_a_group_chat);
//            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
//            msg.setChatType(ChatType.GroupChat);
//            msg.setFrom(inviter);
//            msg.setTo(groupId);
//            msg.setMsgId(UUID.randomUUID().toString());
//            msg.addBody(new EMTextMessageBody(inviter + " " + st3));
//            msg.setStatus(EMMessage.Status.SUCCESS);
//            // save invitation as messages
//            EMClient.getInstance().chatManager().saveMessage(msg);
//            // notify invitation message
//            getNotifier().vibrateAndPlayTone(msg);
//            EMLog.d(TAG, "onAutoAcceptInvitationFromGroup groupId:" + groupId);
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_GROUP_CHANAGED));
//        }
//    }

    /***
     * 好友变化listener
     */
//    public class MyContactListener implements EMContactListener {
//
//        @Override
//        public void onContactAdded(String username) {
//            // save contact
//            Map<String, EaseUser> localUsers = getContactList();
//            Map<String, EaseUser> toAddUsers = new HashMap<String, EaseUser>();
//            EaseUser user = new EaseUser(username);
//
//            if (!localUsers.containsKey(username)) {
//                userDao.saveContact(user);
//            }
//            toAddUsers.put(username, user);
//            localUsers.putAll(toAddUsers);
//
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactDeleted(String username) {
//            Map<String, EaseUser> localUsers = IMHelper.getInstance().getContactList();
//            localUsers.remove(username);
//            userDao.deleteContact(username);
//            inviteMessgeDao.deleteMessage(username);
//
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactInvited(String username, String reason) {
//            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
//
//            for (InviteMessage inviteMessage : msgs) {
//                if (inviteMessage.getGroupId() == null && inviteMessage.getFrom().equals(username)) {
//                    inviteMessgeDao.deleteMessage(username);
//                }
//            }
//            // save invitation as message
//            InviteMessage msg = new InviteMessage();
//            msg.setFrom(username);
//            msg.setTime(System.currentTimeMillis());
//            msg.setReason(reason);
//            Log.d(TAG, username + "apply to be your friend,reason: " + reason);
//            // set invitation status
//            msg.setStatus(InviteMesageStatus.BEINVITEED);
//            notifyNewInviteMessage(msg);
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactAgreed(String username) {
//            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
//            for (InviteMessage inviteMessage : msgs) {
//                if (inviteMessage.getFrom().equals(username)) {
//                    return;
//                }
//            }
//            // save invitation as message
//            InviteMessage msg = new InviteMessage();
//            msg.setFrom(username);
//            msg.setTime(System.currentTimeMillis());
//            Log.d(TAG, username + "accept your request");
//            msg.setStatus(InviteMesageStatus.BEAGREED);
//            notifyNewInviteMessage(msg);
//            broadcastManager.sendBroadcast(new Intent(IMConstant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactRefused(String username) {
//            // your request was refused
//            Log.d(username, username + " refused to your request");
//        }
//    }

    /**
     * save and notify invitation message
     *
     * @param msg
     */
//    private void notifyNewInviteMessage(InviteMessage msg) {
//        if (inviteMessgeDao == null) {
//            inviteMessgeDao = new InviteMessgeDao(appContext);
//        }
//        inviteMessgeDao.saveMessage(msg);
//        //increase the unread message count
//        inviteMessgeDao.saveUnreadMessageCount(1);
//        // notify there is new message
//        getNotifier().vibrateAndPlayTone(null);
//    }

    /**
     * user has logged into another device
     */
//    protected void onConnectionConflict() {
//        Intent intent = new Intent(appContext, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(IMConstant.ACCOUNT_CONFLICT, true);
//        appContext.startActivity(intent);
//    }

    /**
     * account is removed
     */
//    protected void onCurrentAccountRemoved() {
//        Intent intent = new Intent(appContext, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(IMConstant.ACCOUNT_REMOVED, true);
//        appContext.startActivity(intent);
//    }
//
//    private EaseUser getUserInfo(String username) {
//        // To get instance of EaseUser, here we get it from the user list in memory
//        // You'd better cache it if you get it from your server
//        EaseUser user = null;
//        if (username.equals(EMClient.getInstance().getCurrentUser()))
//            return getUserProfileManager().getCurrentUserInfo();
//        user = getContactList().get(username);
//        if (user == null && getRobotList() != null) {
//            user = getRobotList().get(username);
//        }
//
//        // if user is not in your contacts, set inital letter for him/her
//        if (user == null) {
//            user = new EaseUser(username);
//            EaseCommonUtils.setUserInitialLetter(user);
//        }
//        return user;
//    }

    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().onNewMsg(message);
                    }

                    Log.d("IM", "IMHelper:" + message.toString());
                    // 缓存信息
                    try {
                        DBManager.addMember(message.getUserName(), "0", message.getStringAttribute("fromName"), message.getStringAttribute("fromPhoto"));
                    } catch (Exception e) {
                        Log.d("IM", "IMHelper catch user info error=" + e);
                    }

                    RxBus.getDefault().post("FriendStatus", message.getFrom());
                }
                RxBus.getDefault().post("Unread", true);

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                     EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //red packet code : 处理红包回执透传消息
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));

                    ProductInfoBean.AuctionRecodeBean bean = new ProductInfoBean.AuctionRecodeBean();
                    try {
                        bean.setNickName(message.getStringAttribute("nickName"));
                        bean.setUserId(message.getStringAttribute("userId"));
                        bean.setPhoto(message.getStringAttribute("photo"));
                        bean.setPrice(message.getStringAttribute("price"));
                        bean.setAddTime(message.getStringAttribute("addTime"));
                    } catch (Exception e) {
                        TLog.e("get cmd message error=" + e);
                    }
                    RxBus.getDefault().post(BusEvent.BID, bean);
                }
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * logout
     *
     * @param unbindDeviceToken whether you need unbind your device token
     * @param callback          callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * get instance of EaseNotifier
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void reset() {
//        isSyncingGroupsWithServer = false;
//        isSyncingContactsWithServer = false;
//        isSyncingBlackListWithServer = false;
//
//        demoModel.setGroupsSynced(false);
//        demoModel.setContactSynced(false);
//        demoModel.setBlacklistSynced(false);
//
//        isGroupsSyncedWithServer = false;
//        isContactsSyncedWithServer = false;
//        isBlackListSyncedWithServer = false;
//
//        isGroupAndContactListenerRegisted = false;
//
//        setContactList(null);
//        setRobotList(null);
//        getUserProfileManager().reset();
//        DemoDBManager.getInstance().closeDB();
    }

    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }

}
