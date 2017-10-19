package com.tsutsuku.artcollection.ui.main;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.im.IMConstant;
import com.tsutsuku.artcollection.im.IMHelper;
import com.tsutsuku.artcollection.im.ui.ChatActivity;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.TLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author Sun Renwei
 * @Create 2016/10/10
 * @Description 我的消息界面
 */
public class MineMessageActivity extends BaseActivity {
    @BindView(R.id.eclMessage)
    EaseConversationList eclMessage;

    Observable observable;

    private List<EMConversation> list = new ArrayList<>();
    private Realm realm;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MineMessageActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_message);
    }

    @Override
    public void initViews() {
        initTitle(R.string.mine_message);
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {
        observable = RxBus.getDefault().register("Unread", Boolean.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean o) {
                if (o) {
                    refresh();
                }
            }
        });
    }

    @Override
    public void initData() {
        realm = Realm.getDefaultInstance();

        list.addAll(loadConversationList());
        eclMessage.init(list, new EaseConversationList.EaseConversationListHelper() {
            @Override
            public String onSetItemSecondaryText(EMMessage lastMessage) {
                switch (lastMessage.getType()) {
                    case TXT:
                        return null;
                    case VOICE:
                        return getString(R.string.im_voice);
                    case IMAGE:
                        return getString(R.string.im_image);
                    default:
                        return "";
                }
            }
        });
        eclMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = list.get(position);
                try {
                    switch (conversation.getType()) {
                        case Chat: {
                            startActivity(new Intent(context, ChatActivity.class)
                                    .putExtra("userId", conversation.getUserName()));
                        }
                        break;
                        case GroupChat: {
                            startActivity(new Intent(context, ChatActivity.class)
                                    .putExtra("userId", conversation.getUserName())
                                    .putExtra("chatType", IMConstant.CHATTYPE_GROUP));
                            EMClient.getInstance().groupManager().changeGroupName(conversation.getUserName(), conversation.getLastMessage().getStringAttribute("groupName"));
                            EaseUser groupInfo = IMHelper.getInstance().getGroupProManager().getCurrentUserInfo();
                            groupInfo.setAvatar(conversation.getLastMessage().getStringAttribute("groupUrl"));
                            groupInfo.setNick(conversation.getLastMessage().getStringAttribute("groupName"));
                        }
                        break;
                    }
                } catch (Exception e) {
                    TLog.e("get Type error=" + e);
                }
                refresh();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister("Unread", observable);
        RxBus.getDefault().post("Unread", true);
        realm.close();
    }

    /**
     * 刷新对话列表
     */
    public void refresh() {
        list.clear();
        list.addAll(loadConversationList());
        eclMessage.refresh();
    }

    /**
     * load conversation list[从Easemob IM 中获取推送和会话信息]
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }
}
