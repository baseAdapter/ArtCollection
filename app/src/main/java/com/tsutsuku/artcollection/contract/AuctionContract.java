package com.tsutsuku.artcollection.contract;

import android.content.Intent;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class AuctionContract {
public interface View{
}

public interface Presenter extends BasePresenter<View>{
    void parseResult(int requestCode, int resultCode, Intent data);
    BaseRvAdapter getAdapter();
}

public interface Model{
}


}