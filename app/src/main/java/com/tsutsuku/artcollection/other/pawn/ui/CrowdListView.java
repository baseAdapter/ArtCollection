package com.tsutsuku.artcollection.other.pawn.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.BannerHolder.BannerImageHolder;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/1/24
 * @Description 典当页顶部
 */

public class CrowdListView {


    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.tvName)
    TextView tvName;
    private View headerView;
    private Context context;

    public CrowdListView(Context context) {
        this.context = context;
    }

    public View getView() {
        if (headerView == null) {
            headerView = LayoutInflater.from(context).inflate(R.layout.block_crowd_list, null);
            ButterKnife.bind(this, headerView);
        }
        return headerView;
    }

    public void setData(final List<String> list, String num) {
        tvName.setText("选择项目回报(" + num + ")");
        cbBase.setPages(
                new CBViewHolderCreator<BannerImageHolder>() {
                    @Override
                    public BannerImageHolder createHolder() {
                        return new BannerImageHolder();
                    }
                }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.icon_dot, R.drawable.icon_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PhotoViewActivity.launchTypeView(context, position, (ArrayList) list);
                    }
                })
                .startTurning(4000);
    }
}
