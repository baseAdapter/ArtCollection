package com.tsutsuku.artcollection.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.MineWalletContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.RecordsInfoBean;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.wallet.RecordAdapterItem;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/02/15
 */

public class MineWalletPresenterImpl implements MineWalletContract.Presenter {

    private MineWalletContract.View view;
    private BaseRvAdapter adapter;
    private RecordsInfoBean info;

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<RecordsInfoBean.RecordsBean>(null) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new RecordAdapterItem();
                }
            };
        }
        return adapter;
    }

    @Override
    public RecordsInfoBean getInfo() {
        return info;
    }

    @Override
    public void attachView(MineWalletContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Balance.getBalanceAndRecords");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    info = GsonUtils.parseJson(data.getString("info"), RecordsInfoBean.class);
                    view.setBalance(info.getCashBalance());
                    adapter.setData(info.getRecords());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}