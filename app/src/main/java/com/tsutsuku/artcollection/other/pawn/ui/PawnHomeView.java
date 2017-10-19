package com.tsutsuku.artcollection.other.pawn.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.presenter.main.ADRepository;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.glideTransform.GlideRoundTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/24
 * @Description 典当页顶部
 */

public class PawnHomeView {

    @BindView(R.id.ibOne)
    ImageView ibOne;
    @BindView(R.id.ibTwo)
    ImageView ibTwo;
    @BindView(R.id.ibPublish)
    ImageView ibPublish;
    @BindView(R.id.ibThree)
    ImageView ibThree;
    @BindView(R.id.ibManager)
    ImageView ibManager;

    private View headerView;
    private Context context;
    private List<HomeBean.ADBean> list;

    public PawnHomeView(Context context) {
        this.context = context;
    }

    public View getView() {
        if (headerView == null) {
            headerView = LayoutInflater.from(context).inflate(R.layout.block_pawn_home, null);
            ButterKnife.bind(this, headerView);


        }
        return headerView;
    }

    public void setData(List<HomeBean.ADBean> list) {
        this.list = list;
        Glide.with(context).load(list.get(0).getPic()).transform(new GlideRoundTransform(context)).into(ibOne);
        Glide.with(context).load(list.get(1).getPic()).transform(new GlideRoundTransform(context)).into(ibTwo);
        Glide.with(context).load(list.get(2).getPic()).transform(new GlideRoundTransform(context)).into(ibThree);
        Glide.with(context).load(R.drawable.icon_want_pawn).transform(new GlideRoundTransform(context)).into(ibPublish);
        Glide.with(context).load(R.drawable.button_art_dealer).transform(new GlideRoundTransform(context)).into(ibManager);
    }

    @OnClick({R.id.ibPublish, R.id.ibManager, R.id.ibOne, R.id.ibTwo, R.id.ibThree})
    public void onViewClicked(View view) {
        if (list == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.ibPublish:
                if (!TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))){
                    PawnPublishActivity.launch(context);
                } else{
                    LoginActivity.launch(context);
                }

                break;
            case R.id.ibManager:
                DealerListActivity.launch(context);
                break;
            case R.id.ibOne:
                ADRepository.parseAD(context, list.get(0));
                break;
            case R.id.ibTwo:
                ADRepository.parseAD(context, list.get(1));
                break;
            case R.id.ibThree:
                ADRepository.parseAD(context, list.get(2));
                break;
        }
    }
}
