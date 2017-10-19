package com.tsutsuku.artcollection.contract.shopping;

import android.content.Intent;

import com.tsutsuku.artcollection.presenter.BasePresenter;

/**
 * @Author Tsutsuku
 * @Create 2017/3/8
 * @Description
 */

public class VendorApplyContract {
    
public interface View{
    String getNameHint();
    String getCodeHint();
    String getHint();
    void finish();
    void showDialog();
    void setPic(String path);

    void showProgress();
    void hideProgress();
}

public interface Presenter extends BasePresenter<View>{
    void getPhotoFromGallery(int curImage);
    void getPhotoFromCamera(int curImage);
    void selectPic(int curImage);
    void apply(String name, String code, String mobile, String address);
    void parseResult(int requestCode, Intent data);
}


}