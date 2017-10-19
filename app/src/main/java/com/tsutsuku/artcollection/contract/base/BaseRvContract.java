package com.tsutsuku.artcollection.contract.base;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/9
 * @Description
 */

public class BaseRvContract  {
public interface View{
    void showProgress();
    void hideProgress();
}

public interface Presenter extends BasePresenter<View>{
    void loadCircle(boolean refresh);
    BaseRvAdapter getAdapter();

}

public interface Model{
}


}