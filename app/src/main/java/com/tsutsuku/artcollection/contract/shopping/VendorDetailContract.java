package com.tsutsuku.artcollection.contract.shopping;

import com.tsutsuku.artcollection.model.shopping.VendorInfoBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/1
 * @Description
 */

public class VendorDetailContract {

public interface View{
    void setViews(VendorInfoBean bean);
    void setFollow(boolean isFollow);
}

public interface Presenter extends BasePresenter<View>{
    void follow();
    void share();
}

public interface Model{
}


}