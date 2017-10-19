package com.tsutsuku.artcollection.other.custom.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomType;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/19
 * @Description
 */

public class CustomHomeContract {

    public interface View {
        void setBar(List<HomeBean.ADBean> list);

        void setCustomList(List<ItemCustomType> list);

    }

    public interface Presenter {
        void start();
    }

    public interface Model {
        void getBar(HttpResponseHandler handler);

        void getType(HttpResponseHandler handler);
    }


}