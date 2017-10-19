package com.tsutsuku.artcollection.contract.shopping;

import com.tsutsuku.artcollection.model.shopping.OrderInfo;
import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/16
 * @Description
 */

public class OrderDetailContract {

    public interface View {
        void setViews(OrderInfo orderInfo);
    }

    public interface Presenter extends BasePresenter<View> {
        void getData();
        void orderFunction(int ResId);
    }

}