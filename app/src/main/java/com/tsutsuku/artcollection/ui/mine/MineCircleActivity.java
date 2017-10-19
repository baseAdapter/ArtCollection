package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description 我的动态基础Activity[本app中为我的宝贝和我的鉴定]
 */

public class MineCircleActivity extends BaseFragmentActivity {
    public static final String CTYPE = "ctype";
    public static final String TITLE = "title";

    public static void launch(Context context, String title, String ctype) {
        context.startActivity(new Intent(context, MineCircleActivity.class)
                .putExtra(TITLE, title)
                .putExtra(CTYPE, ctype));
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_base_container);
    }

    @Override
    public void initViews() {
        initTitle(getIntent().getStringExtra(TITLE));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flBase, MineCircleFragment.newInstance(getIntent().getStringExtra(CTYPE)));
        transaction.commit();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
