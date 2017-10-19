package com.tsutsuku.artcollection.other.pawn.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.pawn.model.ItemDealer;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/21
 * @Description
 */

public class DealerListContract {

    public interface View {
        void setData(int total, List<ItemDealer> list);

        void addData(List<ItemDealer> list);
    }

    public interface Presenter {
        void getData(String pageIndex);
    }

    public interface Model {
        void getData(String pageIndex, HttpResponseHandler handler);
    }


}