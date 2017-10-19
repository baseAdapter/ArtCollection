package com.tsutsuku.artcollection.contract;

import com.tsutsuku.artcollection.model.RecordsInfoBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description 我的钱包
 */

public class MineWalletContract {

    public interface View {
        void setBalance(String balance);
    }

    public interface Presenter extends BasePresenter<View> {
        BaseRvAdapter getAdapter();
        RecordsInfoBean getInfo();
    }
}