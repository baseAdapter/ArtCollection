package com.tsutsuku.artcollection.presenter.address;

import android.text.TextUtils;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.ShoppingAddressDetailContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.model.shopping.ProvinceBean;
import com.tsutsuku.artcollection.utils.PhoneFormatCheckUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;



public class ShoppingAddressDetailPresenterImpl implements ShoppingAddressDetailContract.Presenter {


    private ShoppingAddressDetailContract.View view;

    private List<ProvinceBean.CitysBean> cityList;
    private List<ProvinceBean.CitysBean.AreaBean> areaList;
    private String province;
    private String city;
    private String area;
    private String addressId;
    private String areaId;

    @Override
    public List<ProvinceBean.CitysBean> getCitys() {
        return cityList;
    }

    @Override
    public void setCitys(List<ProvinceBean.CitysBean> citys) {
        this.cityList = citys;
    }

    @Override
    public List<ProvinceBean.CitysBean.AreaBean> getAreas() {
        return areaList;
    }

    @Override
    public void setAreas(List<ProvinceBean.CitysBean.AreaBean> areas) {
        this.areaList = areas;
    }

    @Override
    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @Override
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String getProvince() {
        return province;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getArea() {
        return area;
    }

    @Override
    public void addAddress(String name, String mobile, String detail, boolean isDefault) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showMessage(R.string.address_name_blank);
        } else if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showMessage(R.string.address_mobile_blank);
        } else if (!PhoneFormatCheckUtils.isPhoneLegal(mobile)) {
            ToastUtils.showMessage(R.string.phone_format_error);
        } else if (TextUtils.isEmpty(detail)) {
            ToastUtils.showMessage(R.string.address_detail_blank);
        } else if (areaId == null) {
            ToastUtils.showMessage(R.string.address_region_blank);
        } else {
            addAddressImpl(name, mobile, detail, isDefault);
        }
    }

    private void addAddressImpl(final String name, final String mobile, final String detail, final boolean isDefault) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ReceiptAddress.add");
        hashMap.put("addressId", addressId == null ? "0" : addressId);
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("province", province);
        hashMap.put("city", city);
        hashMap.put("county", area);
        hashMap.put("consigneeName", name);
        hashMap.put("contactNumber", mobile);
        hashMap.put("detailAddr", detail);
        hashMap.put("isDefault", isDefault ? "1" : "0");
        hashMap.put("areaId", areaId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    ItemAddress item = new ItemAddress();
                    item.setAddressId(addressId == null ? data.getString("info") : addressId);
                    item.setIsDefault(isDefault ? "1" : "0");
                    item.setConsigneeName(name);
                    item.setContactNumber(mobile);
                    item.setProvince(province);
                    item.setCity(city);
                    item.setCounty(area);
                    item.setDetailAddress(detail);
                    view.finishView(item);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    @Override
    public void attachView(ShoppingAddressDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}