package com.tsutsuku.artcollection.live.player;

import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/5/6
 * @Description 回放列表
 */

public class VideoPlayListActivity extends BaseActivity {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_fans);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle("");
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

}
