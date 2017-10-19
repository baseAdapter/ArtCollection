package com.tsutsuku.artcollection.other.custom.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomBean;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/8
 * @Description 定制列表契约类
 */

public class CustomListContract {

    public interface View {
        void addData(List<ItemCustomBean> list);

        void finishLoading();

        void setTotal(int total);
    }

    public interface Presenter {
        void setId(String id);

        void getData(String pageIndex);
    }

    public interface Model {
        void getData(String cateId, String pageIndex, HttpResponseHandler httpResponseHandler);
    }


}