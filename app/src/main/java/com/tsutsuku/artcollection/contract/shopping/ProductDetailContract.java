package com.tsutsuku.artcollection.contract.shopping;

import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Sun Renwei
 * @Create 2017/1/25
 * @Description Content
 */

public class ProductDetailContract {
    
public interface View{
    void setData(ProductInfoBean bean);
    void setFollow(boolean isFollow);
    void setCollection(boolean isCollection);
}

public interface Presenter extends BasePresenter<View>{
    void add();
    void buy();
    void chat();
    void follow();
    void collection();

    void share();
}


}