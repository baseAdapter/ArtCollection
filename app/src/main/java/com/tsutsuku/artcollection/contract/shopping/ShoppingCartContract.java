package com.tsutsuku.artcollection.contract.shopping;

import android.content.Context;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/2/6
 * @Description Content
 */

public class ShoppingCartContract {
public interface View{
    void setTotalPrice(String totalPrice);
    Context getContext();
}

public interface Presenter extends BasePresenter<View>{
    BaseAdapter getAdapter();
    void refresh();
    void settle();
}


}