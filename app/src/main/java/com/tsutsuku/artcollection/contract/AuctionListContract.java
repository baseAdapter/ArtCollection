package com.tsutsuku.artcollection.contract;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/19
 * @Description
 */

public class AuctionListContract {

    public interface View {
    }

    public interface Presenter extends BasePresenter<View> {
        BaseRvAdapter getAdapter();
        void getData(boolean refresh);
    }


}