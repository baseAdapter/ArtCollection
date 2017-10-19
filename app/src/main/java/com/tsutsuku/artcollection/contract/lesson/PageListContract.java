package com.tsutsuku.artcollection.contract.lesson;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/5
 * @Description
 */

public class PageListContract {
    
public interface View{
    void hideProgress();
    void showProgress();
}

public interface Presenter extends BasePresenter<View>{
    void getData(boolean refresh);
    BaseRvAdapter getAdapter();
}


}