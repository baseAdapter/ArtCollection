package com.tsutsuku.artcollection.contract.shopping;

import android.content.Context;
import android.os.Bundle;

import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/18
 * @Description Content
 */

public class ShoppingAddressContract {
    
public interface View{
    Context getContext();
    void finishView(ItemAddress item);
}

public interface Presenter extends BasePresenter<View>{


    BaseAdapter getAdapter();
    void loadData();
    void parseResult(int requestCode, Bundle data);
}

}