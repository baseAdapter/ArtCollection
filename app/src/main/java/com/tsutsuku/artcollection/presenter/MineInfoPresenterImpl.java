package com.tsutsuku.artcollection.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.MineInfoContract;
import com.tsutsuku.artcollection.utils.ToastUtils;

/**
 * Created by sunrenwei on 2017/01/14
 */

public class MineInfoPresenterImpl extends MineInfoBasePresenterImpl implements MineInfoContract.Presenter {

    public MineInfoPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void setMobile(String oldMobile) {

    }
    
}