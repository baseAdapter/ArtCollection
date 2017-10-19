package com.tsutsuku.artcollection.view;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/28
 * @Description 渐变式 Shopping title
 */

public class ShadeDetailTitle extends ShadeNormalTitle {

    @BindView(R.id.ibCollect)
    ImageButton ibCollect;
    private boolean isWCollection;
    private boolean isCollected;
    private String ctype; // 收藏类型
    private String pId; // 收藏物品的Id

    private DetailFunctionListener listener;

    public ShadeDetailTitle(Context context, DetailFunctionListener listener, int totalOffset) {
        super(context, null, totalOffset);
        this.listener = listener;
    }

    public void init(String ctype, String pId) {
        this.ctype = ctype;
        this.pId = pId;
    }

    protected void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.title_shade_detail, null, false);
        ButterKnife.bind(this, rootView);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(45));
        layoutParams.setMargins(0, 0, 0, 0);
        flTitle.setLayoutParams(layoutParams);
    }

    @Override
    protected void shadeOne(int offset) {
        super.shadeOne(offset);
        ibCollect.setImageResource(isCollected ? R.drawable.icon_w_collected : R.drawable.icon_w_collection);
        ibCollect.setAlpha(1 - 1.0f * offset / -DensityUtils.dp2px(totalOffset / 2));
        isWCollection = true;
    }

    @Override
    protected void shadeTwo(int offset) {
        super.shadeTwo(offset);
        ibCollect.setImageResource(isCollected ? R.drawable.icon_b_collected : R.drawable.icon_b_collection);
        ibCollect.setAlpha(1.0f * (offset + DensityUtils.dp2px(totalOffset / 2)) / -DensityUtils.dp2px(totalOffset / 2));
        isWCollection = false;
    }

    public void setCollect(boolean collect) {
        this.isCollected = collect;
        if (isWCollection) {
            ibCollect.setImageResource(isCollected ? R.drawable.icon_w_collected : R.drawable.icon_w_collection);
        } else {
            ibCollect.setImageResource(isCollected ? R.drawable.icon_b_collected : R.drawable.icon_b_collection);
        }
    }

    @OnClick({R.id.ibBack, R.id.ibCollect, R.id.ibShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibBack: {
                listener.back();
            }
            break;
            case R.id.ibCollect: {
                collection();
            }
            break;
            case R.id.ibShare: {
                listener.share();
            }
            break;
        }
    }

    public void collection() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", isCollected ? "Collections.delete" : "Collections.add");
        hashMap.put("pId", pId);
        hashMap.put("ctype", ctype);
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    setCollect(!isCollected);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    public interface DetailFunctionListener extends NormalFunctionListener {
        void collect();
    }
}
