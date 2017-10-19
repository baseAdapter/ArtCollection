package com.tsutsuku.artcollection.contract;

import android.content.Intent;

import com.tsutsuku.artcollection.model.point.ItemPoint;
import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description 积分兑换详情页Contract
 */

public class PointDetailContract {

    public interface View {
        void setView(ItemPoint item);
        void finish();
    }

    public interface Presenter extends BasePresenter<View> {
        void parseItem(ItemPoint item);
    }

    public interface Model {
    }


}