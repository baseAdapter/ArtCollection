package com.tsutsuku.artcollection.ui.mine;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ShareModel;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class InviteActivity extends BaseActivity {
    @BindView(R.id.inviteBt)
    Button mInviteBt;
    @BindView(R.id.tv_activity_detail)
    TextView mDetailTv;
    @BindView(R.id.personNum)
    TextView mPersonNum;
    @BindView(R.id.coinNum)
    TextView mCoinNum;

    Gson gson;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_invite);
    }

    @Override
    public void initViews() {
        initTitle(R.string.invite_friends);
        ButterKnife.bind(this);
        gson = new Gson();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    private void getShare() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Gold.inviteFriends");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    ShareModel model = gson.fromJson(data.toString(), ShareModel.class);
                    OnekeyShare oks = new OnekeyShare();
                    //关闭sso授权
                    oks.disableSSOWhenAuthorize();

                    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                    oks.setTitle("好友给您送1000现金积分");
                    // text是分享文本，所有平台都需要这个字段
                    oks.setText("好友邀请您加入艺术收藏网，今天的艺术品就是明天的古玩。");

                    // url仅在微信（包括好友和朋友圈）中使用
                    oks.setUrl(model.getList().getInvite_url() + model.getList().getInvite_code());
                    oks.setTitleUrl(model.getList().getInvite_url() + model.getList().getInvite_code());//QQ

                    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                    oks.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509445624236&di=b4e3ed00789dfb21a925a87715555317&imgtype=0&src=http%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2015%2F0710%2F20150710054809607.jpg");//确保SDcard下面存在此张图片

                    // 启动分享GUI
                    oks.show(getApplicationContext());
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @OnClick({R.id.inviteBt, R.id.tv_activity_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inviteBt:
                getShare();
                break;
            case R.id.tv_activity_detail:
                startActivity(new Intent(this, ActivityDetailActivity.class));
                break;
            default:
                break;
        }

    }
}
