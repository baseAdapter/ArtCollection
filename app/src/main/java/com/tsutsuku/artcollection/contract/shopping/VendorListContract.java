package com.tsutsuku.artcollection.contract.shopping;

import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description 店家列表Contract
 */

public class VendorListContract {

    public interface View {
        void setSales(boolean up);
        void setPraise(boolean up);
        void setAD(List<HomeBean.ADBean> list);
    }

    public interface Presenter extends BasePresenter<View> {
        BaseRvAdapter getAdapter();

        void orderSale();

        void orderPraise();

        void loadData(boolean refresh);
    }

    public interface Model {
    }


}