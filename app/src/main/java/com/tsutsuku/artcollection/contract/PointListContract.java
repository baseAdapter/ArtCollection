package com.tsutsuku.artcollection.contract;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description 积分兑换列表Contract
 */

public class PointListContract {

    public interface View {
    }

    public interface Presenter extends BasePresenter<View> {
        BaseRvAdapter getAdapter();
        void getData();
    }

    public interface Model {
    }
}