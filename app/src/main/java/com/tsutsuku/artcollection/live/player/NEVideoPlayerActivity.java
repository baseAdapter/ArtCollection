package com.tsutsuku.artcollection.live.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.netease.neliveplayer.NEMediaPlayer;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.im.IMHelper;
import com.tsutsuku.artcollection.presenter.ChatRoomRepository;
import com.tsutsuku.artcollection.ui.activity.PayFeeDialog;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.KeyBoardUtils;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TLog;
import com.tsutsuku.artcollection.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class NEVideoPlayerActivity extends Activity {
    public final static String TAG = "NELivePlayer/NEVideoPlayerActivity";
    private static final String MEDIA_TYPE = "mediaType";
    private static final String DECODE_TYPE = "decodeType";
    private static final String VIDEO_PATH = "videoPath";
    private static final String CHAT_ROOM = "chatRoom";
    private static final String ID = "id";

    public NEVideoView mVideoView;  //用于画面显示
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.etSend)
    EditText etSend;
    @BindView(R.id.ibPay)
    ImageButton ibPay;
    @BindView(R.id.ibSend)
    ImageButton ibSend;
    private View mLoadingView; //用于指示缓冲状态
//    private NEMediaController mMediaController; //用于控制播放

    public static final int NELP_LOG_UNKNOWN = 0; //!< log输出模式：输出详细
    public static final int NELP_LOG_DEFAULT = 1; //!< log输出模式：输出详细
    public static final int NELP_LOG_VERBOSE = 2; //!< log输出模式：输出详细
    public static final int NELP_LOG_DEBUG = 3; //!< log输出模式：输出调试信息
    public static final int NELP_LOG_INFO = 4; //!< log输出模式：输出标准信息
    public static final int NELP_LOG_WARN = 5; //!< log输出模式：输出警告
    public static final int NELP_LOG_ERROR = 6; //!< log输出模式：输出错误
    public static final int NELP_LOG_FATAL = 7; //!< log输出模式：一些错误信息，如头文件找不到，非法参数使用
    public static final int NELP_LOG_SILENT = 8; //!< log输出模式：不输出

    private String mVideoPath; //文件路径
    private String mDecodeType;//解码类型，硬解或软解
    private String mMediaType; //媒体类型
    private boolean mHardware = true;
    private boolean pauseInBackgroud = false;

    private BaseRvAdapter<ItemLiveMessage> adapter;
    private List<ItemLiveMessage> list;
    private Observable<ItemLiveMessage> observable;

    private Context context;
    NEMediaPlayer mMediaPlayer = new NEMediaPlayer();
    private ChatRoomRepository repository;
    private PayFeeDialog dialog;
    private Handler handler = new Handler();

    /**
     * 默认直播播放配置，媒体类型[网络直播]，解码方式[软解]
     *
     * @param context
     * @param chatRoom  聊天室Id
     * @param Id        活动Id
     * @param videoPath 视频地址
     */
    public static void launch(Context context, String chatRoom, String Id, String videoPath) {
        context.startActivity(new Intent(context, NEVideoPlayerActivity.class)
                .putExtra(MEDIA_TYPE, "livestream")
                .putExtra(DECODE_TYPE, "software")
                .putExtra(CHAT_ROOM, chatRoom)
                .putExtra(ID, Id)
                .putExtra(VIDEO_PATH, videoPath));
    }

    /**
     * @param context
     * @param chatRoom   聊天室Id
     * @param Id         活动Id
     * @param mediaType  媒体类型
     * @param decodeType 解码类型
     * @param videoPath  视频地址
     */
    public static void launch(Context context, String chatRoom, String Id, String mediaType, String decodeType, String videoPath) {
        context.startActivity(new Intent(context, NEVideoPlayerActivity.class)
                .putExtra(MEDIA_TYPE, mediaType)
                .putExtra(DECODE_TYPE, decodeType)
                .putExtra(CHAT_ROOM, chatRoom)
                .putExtra(ID, Id)
                .putExtra(VIDEO_PATH, videoPath));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_player);
        ButterKnife.bind(this);

        context = this;

        //接收MainActivity传过来的参数
        mMediaType = getIntent().getStringExtra(MEDIA_TYPE);
        mDecodeType = getIntent().getStringExtra(DECODE_TYPE);
        mVideoPath = getIntent().getStringExtra(VIDEO_PATH);

        Intent intent = getIntent();
        String intentAction = intent.getAction();
        if (!TextUtils.isEmpty(intentAction) && intentAction.equals(Intent.ACTION_VIEW)) {
            mVideoPath = intent.getDataString();
        }

        if (mDecodeType.equals("hardware")) {
            mHardware = true;
        } else if (mDecodeType.equals("software")) {
            mHardware = false;
        }

        findViewById(R.id.ivBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLoadingView = findViewById(R.id.buffering_prompt);
//        mMediaController = new NEMediaController(this);

        mVideoView = (NEVideoView) findViewById(R.id.nvvBase);

        if (mMediaType.equals("livestream")) {
            mVideoView.setBufferStrategy(0); //直播低延时
        } else {
            mVideoView.setBufferStrategy(2); //点播抗抖动
        }
        mVideoView.setBufferingIndicator(mLoadingView);
        mVideoView.setMediaType(mMediaType);
        mVideoView.setHardwareDecoder(mHardware);
        mVideoView.setPauseInBackground(pauseInBackgroud);
        mVideoView.setVideoPath(mVideoPath);
        mMediaPlayer.setLogLevel(NELP_LOG_SILENT); //设置log级别
        mVideoView.requestFocus();
        mVideoView.start();

        repository = new ChatRoomRepository(getIntent().getStringExtra(CHAT_ROOM), null);
        repository.enterRoom();

        dialog = new PayFeeDialog(this, getIntent().getStringExtra(ID));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBase.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        adapter = new BaseRvAdapter<ItemLiveMessage>(list) {
            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new LiveMessageAdapterItem();
            }
        };
        rvBase.setAdapter(adapter);

        observable = RxBus.getDefault().register(BusEvent.LIVE_MESSAGE, ItemLiveMessage.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ItemLiveMessage>() {
            @Override
            public void call(ItemLiveMessage itemLiveMessage) {
                list.add(0, itemLiveMessage);
                adapter.notifyDataSetChanged();
            }
        });
    }

    NEMediaController.OnShownListener mOnShowListener = new NEMediaController.OnShownListener() {

        @Override
        public void onShown() {
            mVideoView.invalidate();
        }
    };

    NEMediaController.OnHiddenListener mOnHiddenListener = new NEMediaController.OnHiddenListener() {

        @Override
        public void onHidden() {
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (pauseInBackgroud)
            mVideoView.pause(); //锁屏时暂停
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        repository.exitRoom();
        mVideoView.release_resource();
        RxBus.getDefault().unregister(BusEvent.LIVE_MESSAGE, observable);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (pauseInBackgroud && !mVideoView.isPaused()) {
            mVideoView.start(); //锁屏打开后恢复播放
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @OnClick({R.id.ivBack, R.id.ibPay, R.id.ibSend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                finish();
            }
            break;
            case R.id.ibPay: {
                dialog.show();
            }
            break;
            case R.id.ibSend: {
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(etSend.getText().toString(), getIntent().getStringExtra(CHAT_ROOM));
                message.setChatType(EMMessage.ChatType.ChatRoom);
                message.setAttribute("fromId", SharedPref.getString(Constants.USER_ID));
                message.setAttribute("fromName", IMHelper.getInstance().getUserProfileManager().getCurrentUserInfo().getNick());
                message.setAttribute("fromPhoto", IMHelper.getInstance().getUserProfileManager().getCurrentUserInfo().getAvatar());
                //发送消息
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
//                        ToastUtils.showMessage("发送成功");
                        new Thread() {
                            public void run() {
                                RxBus.getDefault().post(BusEvent.LIVE_MESSAGE, new ItemLiveMessage(SharedPref.getString(Constants.NICK), SharedPref.getString(Constants.AVATAR), etSend.getText().toString()));

                                handler.post(runnable);
                            }
                        }.start();
                    }

                    @Override
                    public void onError(int i, String s) {
                        TLog.e("error");
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                EMClient.getInstance().chatManager().sendMessage(message);

            }
            break;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            KeyBoardUtils.closeKeyboard((Activity) context);
            etSend.setText("");
        }
    };
}