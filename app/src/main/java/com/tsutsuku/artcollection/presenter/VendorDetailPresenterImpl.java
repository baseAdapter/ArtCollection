package com.tsutsuku.artcollection.presenter;

import android.content.Context;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.VendorDetailContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.VendorInfoBean;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/03/01
 */

public class VendorDetailPresenterImpl implements VendorDetailContract.Presenter {

    private VendorDetailContract.View view;
    private String vendorId;
    private Context context;
    private VendorInfoBean bean;

    public VendorDetailPresenterImpl(Context context, String vendorId) {
        this.context = context;
        this.vendorId = vendorId;
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Farm.getFarmInfo");
        hashMap.put("farmId", vendorId);
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bean = GsonUtils.parseJson(data.getString("info"), VendorInfoBean.class);
                    view.setViews(bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void attachView(VendorDetailContract.View view) {
        this.view = view;
        getData();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void follow() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", bean.getIsFollow() == 1 ? "Follow.delete" : "Follow.add");
        hashMap.put("pId", vendorId);
        hashMap.put("ctype", "1");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bean.setIsFollow(bean.getIsFollow() == 1 ? 0 : 1);
                    view.setFollow(bean.getIsFollow() == 1);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });


    }

    @Override
    public void share() {
        ShareUtils.showShare(context, bean.getCoverPic(), bean.getFarmName(), SharedPref.getSysString(Constants.Share.SHOP) + vendorId);
    }
}