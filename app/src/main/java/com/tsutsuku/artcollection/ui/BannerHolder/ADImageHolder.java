package com.tsutsuku.artcollection.ui.BannerHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.model.HomeBean;

/**
 * @Author Sun Renwei
 * @Create 2017/1/24
 * @Description Content
 */

public class ADImageHolder implements Holder<HomeBean.ADBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, HomeBean.ADBean data) {
        Glide.with(context).load(data.getPic()).into(imageView);
    }
}
