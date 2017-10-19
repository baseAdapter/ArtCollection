package com.tsutsuku.artcollection.contract.shopping;

import android.content.Context;

import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Tsutsuku
 * @Create 2017/2/9
 * @Description
 */

public class ShoppingSettleContract {

    public interface View {
        void setPrice(String price);
        void setAddress(ItemAddress address);
        void showAddressEdit(boolean show);
        Context getContext();
        void dealFinish();
    }

    public interface Presenter extends BasePresenter<View> {
        String getAddressId();
        void setAddress(ItemAddress address);
        void submit(String payType);
        BaseRvAdapter getAdapter();
    }

    public interface Model {
    }


}