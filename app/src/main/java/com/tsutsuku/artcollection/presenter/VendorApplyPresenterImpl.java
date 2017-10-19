package com.tsutsuku.artcollection.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.SparseArray;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.shopping.VendorApplyContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.circleBase.ImageFilesActivity;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.utils.FileUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;
import com.tsutsuku.artcollection.utils.luban.Luban;
import com.tsutsuku.artcollection.utils.luban.OnCompressListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/03/08
 */

public class VendorApplyPresenterImpl implements VendorApplyContract.Presenter {
    private static final int PHOTO_REQUEST_CAMERA = 1; // 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2; // 从相册中选择
    private static final int PHOTO_REQUEST_DELETE = 3; // 删除已选中照片

    private Context context;
    private VendorApplyContract.View view;
    private String imageOne;
    private String imageTwo;
    private String imagePath;
    private int curImage;
    private String type;
    private String atype;
    private SparseArray<String> array;


    public VendorApplyPresenterImpl(Context context, String type, String atype) {
        this.context = context;
        this.type = type;
        this.atype = atype;
        array = new SparseArray<>();
    }

    @Override
    public void attachView(VendorApplyContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getPhotoFromGallery(int curImage) {
        this.curImage = curImage;
        ImageFilesActivity.launch((Activity) context, 1, PHOTO_REQUEST_GALLERY);
    }

    @Override
    public void getPhotoFromCamera(int curImage) {
        this.curImage = curImage;
        File file = FileUtils.createLocalFile(System.currentTimeMillis() + ".jpg", ApiConstants.Paths.IMAGE_UPLOAD_TEMP_PATH);
        imagePath = file.getAbsolutePath();
        ((Activity) context).startActivityForResult(new Intent().setAction(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)), PHOTO_REQUEST_CAMERA);
    }

    @Override
    public void selectPic(int curImage) {
        this.curImage = curImage;
        if (curImage == 0 && imageOne != null) {
            PhotoViewActivity.launchTypeDelete((Activity) context, imageOne, PHOTO_REQUEST_DELETE);
        } else if (curImage == 1 && imageTwo != null) {
            PhotoViewActivity.launchTypeDelete((Activity) context, imageTwo, PHOTO_REQUEST_DELETE);
        } else {
            view.showDialog();
        }
    }

    @Override
    public void apply(String name, String code, String mobile, String address) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showMessage(view.getNameHint());
        } else if (TextUtils.isEmpty(code)) {
            ToastUtils.showMessage(view.getCodeHint());
        } else if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showMessage(R.string.phone_hint);
        } else if (TextUtils.isEmpty(address)) {
            ToastUtils.showMessage(R.string.address_hint);
        } else if (TextUtils.isEmpty(imageOne) || TextUtils.isEmpty(imageTwo)) {
            ToastUtils.showMessage(view.getHint());
        } else if (array.get(0) == null || array.get(1) == null) {
            ToastUtils.showMessage("申请图片处理中...请稍后提交申请");
        } else {
            applyImpl(name, code, mobile, address);
        }
    }

    @Override
    public void parseResult(int requestCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY: {// 从相册选取图片
                if (curImage == 0) {
                    imageOne = data.getStringArrayListExtra(Intents.IMAGE_PATHS).get(0);
                } else {
                    imageTwo = data.getStringArrayListExtra(Intents.IMAGE_PATHS).get(0);
                }
                compress(curImage, data.getStringArrayListExtra(Intents.IMAGE_PATHS).get(0));
                view.setPic(data.getStringArrayListExtra(Intents.IMAGE_PATHS).get(0));
            }
            break;
            case PHOTO_REQUEST_CAMERA: {// 拍照获取图片
                if (curImage == 0) {
                    imageOne = imagePath;
                } else {
                    imageTwo = imagePath;
                }
                compress(curImage, imagePath);
                view.setPic(imagePath);
            }
            break;
            case PHOTO_REQUEST_DELETE: {// 删除照片
                if (curImage == 0) {
                    imageOne = null;
                    array.setValueAt(0, null);
                } else {
                    imageTwo = null;
                    array.setValueAt(0, null);
                }
                view.setPic(null);
            }
            break;
            default:
                break;
        }
    }

    private void compress(final int curImage, String path) {
        Luban.get(context, ApiConstants.Paths.IMAGE_UPLOAD_TEMP_PATH)
                .load(new File(path))
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        array.put(curImage, file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    private void applyImpl(String name, String code, String mobile, String address) {
        view.showProgress();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "FarmApply.add");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("ctype", type);
        hashMap.put("appctype", atype);
        hashMap.put("name", name);
        hashMap.put("code", code);
        hashMap.put("tel", mobile);
        hashMap.put("address", address);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new ArrayList<String>() {{
            add(array.get(0));
            add(array.get(1));
        }}, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.finish();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override
            protected void onFinish() {
                view.hideProgress();
            }
        });
    }
}