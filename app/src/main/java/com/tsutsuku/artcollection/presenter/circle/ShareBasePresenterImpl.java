package com.tsutsuku.artcollection.presenter.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.circle.ShareBaseContract;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.ui.circleBase.ImageFilesActivity;
import com.tsutsuku.artcollection.ui.circleBase.SharePicAdapter;
import com.tsutsuku.artcollection.utils.FileUtils;
import com.tsutsuku.artcollection.utils.TLog;
import com.tsutsuku.artcollection.utils.luban.Luban;
import com.tsutsuku.artcollection.utils.luban.OnCompressListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/10
 * @Description Content
 */

public class ShareBasePresenterImpl implements ShareBaseContract.Presenter {

    private static final int PHOTO_REQUEST_CAMERA = 1; // 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2; // 从相册中选择
    private static final int PHOTO_REQUEST_DELETE = 3; // 删除已选中照片

    protected ShareBaseContract.View view;
    private int leftCount = Constants.MAX_SHARE_PIC;

    protected int type;

    protected RecyclerView.Adapter photoAdapter;
    protected ArrayList<String> imageList = new ArrayList<>(); // 原始图片
    protected ArrayList<String> uploadList = new ArrayList<>(); // 压缩过的带上传图片，与原始图片一一对应
    protected Uri imageUri;
    protected String imagePath;
    protected Context context;

    @Override
    public void attachView(ShareBaseContract.View view) {
        this.view = view;
        context = view.getContext();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public SharePicAdapter getPicAdapter() {
        if (photoAdapter == null) {
            photoAdapter = new SharePicAdapter(view.getContext(), imageList) {
                @Override
                public void showSelectDialog() {
                    view.showPhotoDialog();
                }

                @Override
                public void showPicView(int position) {
                    PhotoViewActivity.launchTypeDelete((Activity) context, position, imageList, PHOTO_REQUEST_DELETE);
                }
            };
        }
        return (SharePicAdapter) photoAdapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter, List<String> list, int leftCount) {
        photoAdapter = adapter;
        this.leftCount = leftCount;
        imageList = (ArrayList) list;
    }

    @Override
    public void getPhotoFromGallery() {
        ImageFilesActivity.launch((Activity) context, leftCount - imageList.size(), PHOTO_REQUEST_GALLERY);
    }

    @Override
    public void getPhotoFromCamera() {
        File file = FileUtils.createLocalFile(System.currentTimeMillis() + ".jpg", ApiConstants.Paths.IMAGE_UPLOAD_TEMP_PATH);
        imagePath = file.getAbsolutePath();
        ((Activity) context).startActivityForResult(new Intent().setAction(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)), PHOTO_REQUEST_CAMERA);
    }

    @Override
    public void setPhotoFromGallery(ArrayList<String> photoPaths) {
        for (final String photoPath : photoPaths) {
            Luban.get(context, ApiConstants.Paths.IMAGE_UPLOAD_TEMP_PATH)
                    .load(new File(photoPath))
                    .putGear(Luban.THIRD_GEAR)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            TLog.e("luban start" + photoPath);
                        }

                        @Override
                        public void onSuccess(File file) {
                            uploadList.add(file.getAbsolutePath());
                            TLog.e("luban finish" + file.getAbsolutePath());
                        }

                        @Override
                        public void onError(Throwable e) {
                            TLog.e("luban parse" + photoPath + "error" + e);
                        }
                    }).launch();
            imageList.add(photoPath);
        }
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPhotoFromCamera() {
        Luban.get(context, ApiConstants.Paths.IMAGE_UPLOAD_TEMP_PATH)
                .load(new File(imagePath))
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        uploadList.add(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
        imageList.add(imagePath);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void deletePhoto(ArrayList<String> photoPaths) {
        for (int i = imageList.size() - 1; i > 0; i--) {
            if (!photoPaths.contains(imageList.get(i))) {
                uploadList.remove(i);
            }
        }
        imageList.clear();
        imageList.addAll(photoPaths);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void parseResult(int requestCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY: {// 从相册选取图片
                setPhotoFromGallery(data.getStringArrayListExtra(Intents.IMAGE_PATHS));
            }
            break;
            case PHOTO_REQUEST_CAMERA: {// 拍照获取图片
                setPhotoFromCamera();
            }
            break;
            case PHOTO_REQUEST_DELETE: {// 删除照片
                deletePhoto(data.getStringArrayListExtra(Intents.IMAGE_PATHS));
            }
            break;
            default:
                break;
        }
    }
}
