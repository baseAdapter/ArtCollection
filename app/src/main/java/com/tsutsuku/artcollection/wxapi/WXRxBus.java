package com.tsutsuku.artcollection.wxapi;


import com.tsutsuku.artcollection.utils.RxBus;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by sunrenwei on 2016/8/8.
 */
public abstract class WXRxBus {
    public static final int PAY_CANCEL = 0;
    public static final int PAY_FAILED = 1;
    public static final int PAY_SUCCESS = 2;

    private Observable<WXItem> observable;


    public WXRxBus() {
        observable = RxBus.getDefault().register("wxpay", WXItem.class);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WXItem>() {
                    @Override
                    public void call(WXItem wxItem) {
                        switch (wxItem.getId()) {
                            case PAY_CANCEL: {
                                cancel();
                            }
                            break;
                            case PAY_FAILED: {
                                fail();
                            }
                            break;
                            case PAY_SUCCESS: {
                                success();
                            }
                            break;
                            default:
                                break;
                        }
                        RxBus.getDefault().unregister("wxpay", observable);
                        finish();
                    }
                });
    }

    public abstract void cancel();

    public abstract void fail();

    public abstract void success();

    protected void finish(){};
}
