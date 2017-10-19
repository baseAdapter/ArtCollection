package com.tsutsuku.artcollection.other.rent.ui;

import android.content.Context;
import android.content.Intent;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.utils.UIUtils.ActivityUtils;

/**
 * @Author Tsutsuku
 * @Create 2017/6/26
 * @Description
 */

public class RentListActivity extends BaseFragmentActivity {
    public static void launch(Context context) {
        context.startActivity(new Intent(context, RentListActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_base_container);
    }

    @Override
    public void initViews() {
        initTitle("艺术租赁");
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), RentListFragment.newInstance(false), R.id.flBase);
    }
}
