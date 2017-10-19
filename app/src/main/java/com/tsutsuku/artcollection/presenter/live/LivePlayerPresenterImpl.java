package com.tsutsuku.artcollection.presenter.live;

import com.tsutsuku.artcollection.contract.live.LivePlayerContract;

/**
 * Created by sunrenwei on 2017/04/04
 */

public class LivePlayerPresenterImpl implements LivePlayerContract.Presenter {

    private LivePlayerContract.View view;

    @Override
    public void attachView(LivePlayerContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}