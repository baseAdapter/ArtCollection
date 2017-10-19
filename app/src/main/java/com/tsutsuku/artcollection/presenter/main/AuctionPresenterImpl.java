package com.tsutsuku.artcollection.presenter.main;
import android.content.Intent;

import com.tsutsuku.artcollection.contract.AuctionContract;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

/**
* Created by sunrenwei on 2017/01/04
*/

public class AuctionPresenterImpl implements AuctionContract.Presenter{

    private AuctionContract.View view;
    @Override
    public void attachView(AuctionContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void parseResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public BaseRvAdapter getAdapter() {
        return null;
    }
}