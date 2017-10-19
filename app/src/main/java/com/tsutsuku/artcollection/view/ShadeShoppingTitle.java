package com.tsutsuku.artcollection.view;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.WindowsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/28
 * @Description 渐变式 Shopping title
 */

public class ShadeShoppingTitle extends ShadeNormalTitle {

    @BindView(R.id.ibSCart)
    ImageButton ibSCart;

    private ShoppingFunctionListener listener;
    private int statusBarHeight = 0;

    public ShadeShoppingTitle(Context context, ShoppingFunctionListener listener, int totalOffset) {
        super(context, null, totalOffset);
        this.listener = listener;
    }


    protected void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.title_shade_shopping, null, false);
        ButterKnife.bind(this, rootView);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(45));
        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        flTitle.setLayoutParams(layoutParams);
    }

    @Override
    protected void shadeOne(int offset) {
        super.shadeOne(offset);
        ibSCart.setImageResource(R.drawable.icon_w_cart);
        ibSCart.setAlpha(1 - 1.0f * offset / -DensityUtils.dp2px(totalOffset / 2));
    }

    @Override
    protected void shadeTwo(int offset) {
        super.shadeTwo(offset);
        ibSCart.setImageResource(R.drawable.icon_b_cart);
        ibSCart.setAlpha(1.0f * (offset + DensityUtils.dp2px(totalOffset / 2)) / -DensityUtils.dp2px(totalOffset / 2));
    }

    @OnClick({R.id.ibBack, R.id.ibSCart, R.id.ibShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibBack: {
                listener.back();
            }
            break;
            case R.id.ibSCart: {
                listener.gotoCart();
            }
            break;
            case R.id.ibShare: {
                listener.share();
            }
            break;
        }
    }

    public interface ShoppingFunctionListener extends NormalFunctionListener {
        void gotoCart();
    }
}
