package com.tsutsuku.artcollection.other.rent.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.model.ItemProduct;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/8
 * @Description
 */

public class RentListContract {

    public interface View {
        void setData(int total, List<ItemProduct> list);

        void addData(List<ItemProduct> list);
    }

    public interface Presenter {
        void setTop();

        void getData(String pageIndex);
    }

    public interface Model {
        void getData(String pageIndex, boolean isTop, HttpResponseHandler handler);
    }


}