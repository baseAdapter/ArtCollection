package com.tsutsuku.artcollection.contract;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description 我的积分Contract
 */

public class MinePointContract {

    public interface View {
        void setPoint(String point);
    }

    public interface Presenter extends BasePresenter<View> {
        BaseRvAdapter getAdapter();
        void getData();
    }

    public interface Model {

    }


}