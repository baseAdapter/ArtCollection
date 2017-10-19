package com.tsutsuku.artcollection.other.pawn.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowd;

import java.util.List;

/**
 * @Author Tsutsuku
 * @Create 2017/6/21
 * @Description
 */

public class PawnHomeContract {
    
public interface View{
    void setData(int total, List<ItemCrowd> list);
    void addData(List<ItemCrowd> list);
    void setTop(List<HomeBean.ADBean> list);
}

public interface Presenter{
    void getData(String pageIndex);

    void getTop();
}

public interface Model{
    void getData(String pageIndex, HttpResponseHandler handler);

    void getTop(HttpResponseHandler handler);
}


}