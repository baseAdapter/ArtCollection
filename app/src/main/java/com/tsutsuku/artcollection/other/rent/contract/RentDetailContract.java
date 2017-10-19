package com.tsutsuku.artcollection.other.rent.contract;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;

/**
 * @Author Tsutsuku
 * @Create 2017/6/25
 * @Description
 */

public class RentDetailContract {
    
public interface View{
    void setData(ProductInfoBean bean);
    void setCollection(boolean collection);
}

public interface Presenter{
    void start();
    void share();
    void chat();
    void buy(String num);
    void add();
    void collection();
}

public interface Model{
    void getData(String productId, HttpResponseHandler handler);
    void collection(boolean collection, String productId, HttpResponseHandler handler);
}


}