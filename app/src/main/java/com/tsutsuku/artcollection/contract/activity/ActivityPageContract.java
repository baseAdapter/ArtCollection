package com.tsutsuku.artcollection.contract.activity;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description
 */

public class ActivityPageContract {
    
public interface View{
    void showProgress();
    void hideProgress();
}

public interface Presenter extends BasePresenter<View> {
    BaseRvAdapter getAdapter();
    void getData(boolean refresh);
}


}