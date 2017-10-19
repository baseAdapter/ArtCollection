package com.tsutsuku.artcollection.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.utils.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/28
 * @Description 渐变式title
 */

public class ShadeBlankTitle {
    @BindView(R.id.vBg)
    View vBg;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.vDivider)
    View vDivider;
    @BindView(R.id.flTitle)
    FrameLayout flTitle;

    protected Context context;
    protected View rootView;
    protected int totalOffset;
    protected int statusBarHeight = 0;

    private BlankFunctionListener listener;

    public ShadeBlankTitle(Context context, @Nullable BlankFunctionListener listener, int totalOffset) {
        this.context = context;
        this.listener = listener;
        this.totalOffset = totalOffset;
//        statusBarHeight = WindowsUtils.getStatusBarHeight();
        initView();
    }

    protected void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.title_shade_blank, null, false);
        ButterKnife.bind(this, rootView);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(45));
        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        flTitle.setLayoutParams(layoutParams);
    }

    public View getRootView() {
        return rootView;
    }

    /**
     * title 渐变
     *
     * @param offset
     */
    private void shade(int offset) {
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(45));
        layoutParams.setMargins(0, -offset + statusBarHeight, 0, 0);
        flTitle.setLayoutParams(layoutParams);

        if (offset > -DensityUtils.dp2px(totalOffset)) {
            if (offset > -DensityUtils.dp2px(totalOffset / 2)) {
                shadeOne(offset);
            } else {
                shadeTwo(offset);
            }

            vDivider.setVisibility(View.GONE);
            vBg.setAlpha(1.0f * offset / -DensityUtils.dp2px(totalOffset));
        } else {
            vBg.setAlpha(1.0f);
            vDivider.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 前半段title渐变
     *
     * @param offset
     */
    protected void shadeOne(int offset) {
        ibBack.setImageResource(R.drawable.icon_w_back);

        ibBack.setAlpha(1 - 1.0f * offset / -DensityUtils.dp2px(totalOffset / 2));
    }

    /**
     * 后半段title渐变
     *
     * @param offset
     */
    protected void shadeTwo(int offset) {
        ibBack.setImageResource(R.drawable.icon_b_back);

        ibBack.setAlpha(1.0f * (offset + DensityUtils.dp2px(totalOffset / 2)) / -DensityUtils.dp2px(totalOffset / 2));
    }

    public AppBarLayout.OnOffsetChangedListener getOffsetChangedListener() {
        return new AppBarLayout.OnOffsetChangedListener() {
            int offset;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset != offset) {
                    offset = verticalOffset;
                    shade(verticalOffset);
                }
            }
        };
    }

    @OnClick({R.id.ibBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibBack: {
                listener.back();
            }
            break;
        }
    }

    public interface BlankFunctionListener {
        void back();
    }
}
