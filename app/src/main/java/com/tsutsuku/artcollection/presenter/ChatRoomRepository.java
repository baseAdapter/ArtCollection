package com.tsutsuku.artcollection.presenter;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TLog;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Tsutsuku
 * @Create 2017/3/29
 * @Description
 */

public class ChatRoomRepository {

    private String roomId;
    private boolean hasEnter;
    private CallBack callBack;

    public ChatRoomRepository(String roomId, CallBack callBack) {
        this.roomId = roomId;
        this.callBack = callBack;
    }

    public void enterRoom(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ChatRoom.addMember");
        hashMap.put("roomId", roomId);
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    hasEnter = true;
                    if (callBack != null){
                        callBack.enterRoom();
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

        EMClient.getInstance().chatroomManager().joinChatRoom(roomId, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(EMChatRoom value) {
                //加入聊天室成功
                TLog.e("in chatRoom");
            }

            @Override
            public void onError(final int error, String errorMsg) {
                //加入聊天室失败
            }
        });
    }

    public void exitRoom(){
        if (hasEnter){
            final HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("service", "ChatRoom.deleteMember");
            hashMap.put("roomId", roomId);
            hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
            HttpsClient client = new HttpsClient();
            client.post(hashMap, new HttpResponseHandler() {
                @Override
                protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                    if (data.getInt("code") == 0) {
                        hasEnter = false;
                        callBack.exitRoom();
                    }
                }

                @Override
                protected void onFailed(int statusCode, Exception e) {

                }
            });
        }

        EMClient.getInstance().chatroomManager().leaveChatRoom(roomId);
    }

    public interface CallBack{
        void enterRoom();
        void exitRoom();
    }
}
