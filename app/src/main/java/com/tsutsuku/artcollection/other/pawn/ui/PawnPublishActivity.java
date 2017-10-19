package com.tsutsuku.artcollection.other.pawn.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.other.pawn.contract.PawnPublishContract;
import com.tsutsuku.artcollection.other.pawn.model.PawnPublishModelImpl;
import com.tsutsuku.artcollection.other.pawn.presenter.PawnPublishPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.ToastUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;
import com.tsutsuku.artcollection.view.SelectAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/21
 * @Description
 */

public class PawnPublishActivity extends BaseActivity implements PawnPublishContract.View {
    private static final int PHOTO_REQUEST_DELETE = 3; // 删除已选中照片
    private static final int FROM_GALLERY = 0;
    private static final int FROM_CAMERA = 1;

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etType)
    EditText etType;
    @BindView(R.id.etSize)
    EditText etSize;
    @BindView(R.id.etDesc)
    EditText etDesc;
    @BindView(R.id.etTel)
    EditText etTel;
    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.rvPic)
    RecyclerView rvPic;
    @BindView(R.id.btnCmd)
    Button btnCmd;
    private MaterialDialog progressDialog;
    private DialogPlus photoDialog;
    private PublishPicAdapter photoAdapter;

    private PawnPublishPresenterImpl presenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PawnPublishActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pawn_publish);
    }

    @Override
    public void initViews() {
        initTitle("我要典当");
        ButterKnife.bind(this);

        progressDialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .content("发布中...")
                .build();

        photoAdapter = new PublishPicAdapter(context) {
            @Override
            public void showSelectDialog() {
                photoDialog.show();
            }

            @Override
            public void showPicView(int position) {
                PhotoViewActivity.launchTypeDelete((Activity) context, position, (ArrayList) getData(), PHOTO_REQUEST_DELETE);
            }
        };

        List<String> items;
        items = new ArrayList<>();
        items.add("相册");
        items.add("拍照");
        photoDialog = DialogPlus.newDialog(context)
                .setAdapter(new SelectAdapter(context, items))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        switch (position) {
                            case FROM_GALLERY:
                                presenter.getPhotoFromGallery();
                                break;
                            case FROM_CAMERA:
                                presenter.getPhotoFromCamera();
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .create();

        rvPic.setLayoutManager(new GridLayoutManager(context, 3));
        rvPic.setAdapter(photoAdapter);
        rvPic.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(6)));

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        presenter = new PawnPublishPresenterImpl(this, new PawnPublishModelImpl());
        presenter.attachView(this);
        presenter.setAdapter(photoAdapter, photoAdapter.getData(), 6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.parseResult(requestCode, data);
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void showPhotoDialog() {
        photoDialog.show();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void finish() {
        progressDialog.dismiss();
        super.finish();
    }

    @OnClick(R.id.btnCmd)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.showMessage("请输入典当名称");
        } else if (TextUtils.isEmpty(etType.getText().toString().trim())) {
            ToastUtils.showMessage("请输入材质");
        } else if (TextUtils.isEmpty(etSize.getText().toString().trim())) {
            ToastUtils.showMessage("请输入尺寸");
        } else if (TextUtils.isEmpty(etDesc.getText().toString().trim())) {
            ToastUtils.showMessage("请输入当品描述");
        } else if (TextUtils.isEmpty(etTel.getText().toString().trim())) {
            ToastUtils.showMessage("请输入联系方式");
        } else if (TextUtils.isEmpty(etCity.getText().toString().trim())) {
            ToastUtils.showMessage("请输入所在省市");
        } else if (photoAdapter.getData().size() == 0) {
            ToastUtils.showMessage("请选择上传1张图片");
        } else {
            presenter.share(etName.getText().toString().trim(),
                    etType.getText().toString().trim(),
                    etSize.getText().toString().trim(),
                    etDesc.getText().toString().trim(),
                    etTel.getText().toString().trim(),
                    etCity.getText().toString().trim(),
                    photoAdapter.getData());
        }
    }


}
