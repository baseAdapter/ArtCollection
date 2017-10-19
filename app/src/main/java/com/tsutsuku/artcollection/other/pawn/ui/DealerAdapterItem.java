package com.tsutsuku.artcollection.other.pawn.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.im.ui.ChatActivity;
import com.tsutsuku.artcollection.other.pawn.model.ItemDealer;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/21
 * @Description
 */

public class DealerAdapterItem extends BaseAdapterItem<ItemDealer> {
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.llCheck)
    LinearLayout llCheck;
    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.ivFollow)
    ImageView ivFollow;
    @BindView(R.id.tvPoint)
    TextView tvPoint;

    @Override
    public int getLayoutResId() {
        return R.layout.item_dealer;
    }

    @Override
    public void handleData(ItemDealer item, int position) {
        Glide.with(context)
                .load(item.getPhoto())
                .transform(new CircleTransform(context))
                .into(ivAvatar);

        tvName.setText(item.getDisplayName());

        if ("1".equals(item.getIsCheck())) {
            llCheck.setVisibility(View.VISIBLE);
        } else {
            llCheck.setVisibility(View.GONE);
        }

//        ivFollow.setVisibility(item);

        tvPoint.setText("评分" + item.getPoint());
    }

    @OnClick({R.id.ivFollow, R.id.flChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivFollow:
                break;
            case R.id.flChat:
                ChatActivity.launch(context, curItem.getHxAccount(), curItem.getUserId(), curItem.getDisplayName(), curItem.getPhoto());
                break;
        }
    }
}
