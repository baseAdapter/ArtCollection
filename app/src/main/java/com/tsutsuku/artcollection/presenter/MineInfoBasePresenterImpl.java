package com.tsutsuku.artcollection.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.MineInfoBaseContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.utils.FileUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;
import com.tsutsuku.artcollection.view.SelectAdapter;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/01/14
 */

public class MineInfoBasePresenterImpl implements MineInfoBaseContract.Presenter {
    private static final int PHOTO_REQUEST_GALLERY = 0;
    private static final int PHOTO_REQUEST_CAMERA = 1;
    private static final int PHOTO_REQUEST_CUT = 2;

    protected Context context;
    private Uri picUri;
    private String cropPath;
    protected MineInfoBaseContract.View view;

    public MineInfoBasePresenterImpl(Context context) {
        this.context = context;
    }


    @Override
    public void setAvatar() {
        DialogPlus.newDialog(context)
                .setAdapter(new SelectAdapter(context, new ArrayList() {{
                    add("相册");
                    add("拍照");
                }}))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                        switch (position) {
                            case 0: {// 选择本地图片
                                ((Activity) context).startActivityForResult(new Intent(Intent.ACTION_PICK, null)
                                                .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                                        , PHOTO_REQUEST_GALLERY);
                            }
                            break;
                            case 1: {// 拍照
                                File file = FileUtils.createLocalFile(System.currentTimeMillis() + ".jpg", ApiConstants.Paths.IMAGE_PHOTO_CACHE_PATH);
                                picUri = Uri.fromFile(file);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                                ((Activity) context).startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                        .putExtra(MediaStore.EXTRA_OUTPUT, picUri), PHOTO_REQUEST_CAMERA);
                            }
                            break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void setNick(String oldNick) {
        new MaterialDialog.Builder(context)
                .title("修改用户名")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(context.getString(R.string.input_nick_hint), oldNick, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                })
                .positiveText(context.getString(R.string.ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String nick = dialog.getInputEditText().getText().toString();
                        if (TextUtils.isEmpty(nick)) {
                            ToastUtils.showMessage(R.string.input_nick_hint);
                        } else {
                            setNickImpl(nick);
                            dialog.dismiss();
                        }
                    }
                })
                .negativeText(context.getString(R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void setSex(String oldSex) {
        new MaterialDialog.Builder(context)
                .title(R.string.sex)
                .items("男", "女")
                .itemsCallback(new MaterialDialog.ListCallback(){
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        setSexImpl(position == 0 ? "男" : "女");
                    }
                })
                .show();
    }

    @Override
    public void parseResult(int requestCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAMERA: {
                crop(picUri);
            }
            break;
            case PHOTO_REQUEST_GALLERY: {
                crop(data.getData());
            }
            break;
            case PHOTO_REQUEST_CUT: {
                setAvatarImpl(cropPath);
            }
            break;
        }
    }

    private void setAvatarImpl(String avatar) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.setAvatar");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, avatar, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    String avatarUrl = data.getString("url");
                    SharedPref.putString(Constants.AVATAR, avatarUrl);
                    view.setAvatar(avatarUrl);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void setNickImpl(final String nick) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.setNickname");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("nickname", nick);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    SharedPref.putString(Constants.NICK, nick);
                    view.setNick(nick);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void setSexImpl(final String sex) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.setSex");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("sex", sex);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    SharedPref.putString(Constants.SEX, sex);
                    view.setSex(sex);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    /**
     * 剪切图片
     *
     * @param uri 原图uri
     * @function:
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);
        // 避免部分手机打开uri和保存uri相同会产生冲突的情况
        File file = FileUtils.createLocalFile(System.currentTimeMillis() + ".jpg", ApiConstants.Paths.IMAGE_PHOTO_CACHE_PATH);
        cropPath = file.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        ((Activity) context).startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void attachView(MineInfoBaseContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}