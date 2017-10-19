package com.tsutsuku.artcollection.contract.contract;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description
 */

public class AuctionDealContract {
    
public interface View{
    void setPrice(String price);
}

public interface Presenter extends BasePresenter<View> {
    BaseRvAdapter getAdapter();
    void settle();
    void getData(boolean refresh);
}

public interface Model{
}


}