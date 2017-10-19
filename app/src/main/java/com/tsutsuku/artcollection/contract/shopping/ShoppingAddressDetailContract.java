package com.tsutsuku.artcollection.contract.shopping;

import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.model.shopping.ProvinceBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/20
 * @Description Content
 */

public class ShoppingAddressDetailContract {
    
public interface View{
    void finishView(ItemAddress item);
}

public interface Presenter extends BasePresenter<View>{
    List<ProvinceBean.CitysBean> getCitys();
    void setCitys(List<ProvinceBean.CitysBean> citys);
    List<ProvinceBean.CitysBean.AreaBean> getAreas();
    void setAreas(List<ProvinceBean.CitysBean.AreaBean> areas);

    void setProvince(String province);
    void setCity(String city);
    void setArea(String area);
    void setAddressId(String addressId);
    void setAreaId(String areaId);

    String getProvince();
    String getCity();
    String getArea();

    void addAddress(String name, String mobile, String detail, boolean isDefault);
}


}