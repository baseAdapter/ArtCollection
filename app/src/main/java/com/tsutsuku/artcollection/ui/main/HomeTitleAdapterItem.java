package com.tsutsuku.artcollection.ui.main;

import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.activity.ActivityListActivity;
import com.tsutsuku.artcollection.ui.activity.LiveListActivity;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.ui.lesson.LessonActivity;
import com.tsutsuku.artcollection.ui.product.ProductActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/5/3
 * @Description
 */

public class HomeTitleAdapterItem extends BaseAdapterItem<String> {
    @BindView(R.id.tvName)
    TextView tvName;


    @Override
    public int getLayoutResId() {
        return R.layout.item_home_title;
    }

    @Override
    public void handleData(String item, int position) {
        tvName.setText(item);
    }

    @OnClick(R.id.tvMore)
    public void onViewClicked() {
        switch (curItem) {
            case "人气讲堂": {
                LessonActivity.launch(context);
            }
            break;
            case "热门活动": {
                ActivityListActivity.launch(context);
            }
            break;
            case "精彩直播": {
                LiveListActivity.launch(context);
            }
            break;
            case "最新推荐": {
                ProductActivity.launch(context);
            }
            break;
        }
    }
}
