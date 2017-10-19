package com.tsutsuku.artcollection.contract.circle;

import android.content.Context;
import android.content.Intent;

import com.tsutsuku.artcollection.presenter.BasePresenter;
import com.tsutsuku.artcollection.ui.circleBase.SharePicAdapter;

import java.util.ArrayList;

/**
 * @Author Sun Renwei
 * @Create 2017/1/9
 * @Description Content
 */

public class ShareBaseContract {
    public interface View {
        Context getContext();
        void showPhotoDialog();
        void showProgressDialog();
        void dismissProgressDialog();
        void finish();
    }

    public interface Presenter extends BasePresenter<View>{
        SharePicAdapter getPicAdapter();
        void getPhotoFromGallery();
        void getPhotoFromCamera();
        void setPhotoFromGallery(ArrayList<String> photoPaths);
        void setPhotoFromCamera();
        void deletePhoto(ArrayList<String> photoPaths);

        void parseResult(int requestCode, Intent data);


    }
}