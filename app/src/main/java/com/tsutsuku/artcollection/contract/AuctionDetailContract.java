package com.tsutsuku.artcollection.contract;

import android.app.Activity;
import android.content.Intent;

import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
 * @Author Sun Renwei
 * @Create 2017/2/20
 * @Description 拍卖详情Contract
 */

public class AuctionDetailContract {

    public interface View {
        void setView(ProductInfoBean bean);
        void setRecordNum(int count);
        void setDepositPayed();

        void setCollection(boolean collection);
    }

    public interface Presenter extends BasePresenter<View> {
        void getData(String productId);
        void exitRoom();
        BaseRvAdapter getAdapter();
        void addRecord(ProductInfoBean.AuctionRecodeBean bean);
        void payDeposit(Activity activity);
        void parseResult(Intent intent);

        void share();
        void chat();
        void collection();
    }

    public interface Model {
    }


}