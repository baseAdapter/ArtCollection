package com.tsutsuku.artcollection.contract.shopping;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Tsutsuku
 * @Create 2017/2/12
 * @Description
 */

public class OrdersListContract {


    public interface View {
        Context getContext();
        Activity getActivity();
        void hideBlank();
        void showBlank();
    }

    public interface Presenter extends BasePresenter<View> {
        BaseRvAdapter getAdapter();
        void refreshData();
        void initViews(RecyclerView rvBase);

        void parseBusEvent(OrderBean bean);
    }

    public interface Model {
    }


}