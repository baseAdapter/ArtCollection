package com.tsutsuku.artcollection.ui.activity;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.ActivityInfo;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

/**
 * @Author Tsutsuku
 * @Create 2017/3/18
 * @Description
 */

public class ActivityUserView {
    public static FrameLayout getView(Context context, ActivityInfo.UserListBean bean) {
        FrameLayout layout = new FrameLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(DensityUtils.dp2px(60), ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setPadding(DensityUtils.dp2px(15), 0, 0, 0);

        ImageView pic = new ImageView(context);
        FrameLayout.LayoutParams picParams = new FrameLayout.LayoutParams(DensityUtils.dp2px(45), DensityUtils.dp2px(45));
        picParams.setMargins(0, DensityUtils.dp2px(10), 0, 0);
        pic.setLayoutParams(picParams);
        Glide.with(context).load(bean.getPic()).transform(new CircleTransform(context)).into(pic);
        layout.addView(pic);

        TextView tvName = new TextView(context);
        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.gravity = Gravity.CENTER_HORIZONTAL;
        tvParams.setMargins(0, DensityUtils.dp2px(60), 0, 0);
        tvName.setLayoutParams(tvParams);
        tvName.setGravity(Gravity.CENTER_HORIZONTAL);

        tvName.setSingleLine(true);
        tvName.setTextSize(12);
        tvName.setTextColor(context.getResources().getColor(R.color.d));
        tvName.setText(bean.getNickName());
        layout.addView(tvName);

        return layout;
    }
}
