package com.tsutsuku.artcollection.contract;

import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class HomeContract {
    public interface View {
    }

    public interface Presenter extends BasePresenter<View> {
        void initView(RecyclerView rvBase);

        void loadData();
        void refreshData();
    }


}