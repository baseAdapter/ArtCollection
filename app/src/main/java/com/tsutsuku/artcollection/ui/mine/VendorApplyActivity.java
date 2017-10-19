package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.contract.shopping.VendorApplyContract;
import com.tsutsuku.artcollection.presenter.VendorApplyPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.CacheUtils;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.view.SelectAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/8
 * @Description
 */

public class VendorApplyActivity extends BaseActivity implements VendorApplyContract.View {
    private static final String TYPE = "type";
    private static final String ATYPE = "atype";

    private static final String TYPE_PERSON = "2";
    private static final String TYPE_ENTERPRISE = "1";

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.tvHint)
    TextView tvHint;
    @BindView(R.id.ivPicOne)
    ImageView ivPicOne;
    @BindView(R.id.ivPicTwo)
    ImageView ivPicTwo;

    private VendorApplyPresenterImpl presenter;
    private DialogPlus photoDialog;
    private int curImage;
    private MaterialDialog progressDialog;

    public static void launchTypePerson(Context context) {
        context.startActivity(new Intent(context, VendorApplyActivity.class)
                .putExtra(ATYPE, "1")
                .putExtra(TYPE, TYPE_PERSON));
    }

    public static void launchTypeEnterprise(Context context) {
        context.startActivity(new Intent(context, VendorApplyActivity.class)
                .putExtra(ATYPE, "1")
                .putExtra(TYPE, TYPE_ENTERPRISE));
    }

    public static void launchTypeIdentify(Context context) {
        context.startActivity(new Intent(context, VendorApplyActivity.class)
                .putExtra(ATYPE, "2")
                .putExtra(TYPE, TYPE_PERSON));
    }

    public static void launchTypeArt(Context context) {
        context.startActivity(new Intent(context, VendorApplyActivity.class)
                .putExtra(ATYPE, "3")
                .putExtra(TYPE, TYPE_PERSON));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vendor_apply);
    }

    @Override
    public void initViews() {
        switch (getIntent().getStringExtra(ATYPE)) {
            case "1": {
                initTitle(R.string.mine_apply);
            }
            break;
            case "2": {
                initTitle(R.string.mine_identify_apply);
            }
            break;
            case "3": {
                initTitle(R.string.mine_art_apply);
            }
            break;
        }

        ButterKnife.bind(this);
        presenter = new VendorApplyPresenterImpl(context, getIntent().getStringExtra(TYPE), getIntent().getStringExtra(ATYPE));
        presenter.attachView(this);

        if (TYPE_PERSON.equals(getIntent().getStringExtra(TYPE))) {
            tvName.setText(R.string.person_name);
            etName.setHint(R.string.person_name_hint);
            tvCode.setText(R.string.person_code);
            etCode.setHint(R.string.person_code_hint);
            tvHint.setText(R.string.person_pic);
        } else {
            tvName.setText(R.string.enterprise_name);
            etName.setHint(R.string.enterprise_name_hint);
            tvCode.setText(R.string.enterprise_code);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.leftMargin = DensityUtils.dp2px(120);
            etCode.setLayoutParams(layoutParams);
            etCode.setHint(R.string.enterprise_code_hint);
            tvHint.setText(R.string.enterprise_pic);
        }

        progressDialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .content("申请中...")
                .build();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
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
                            case 0:
                                presenter.getPhotoFromGallery(curImage);
                                break;
                            case 1:
                                presenter.getPhotoFromCamera(curImage);
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            presenter.parseResult(requestCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheUtils.cleanCustomCache(ApiConstants.Paths.IMAGE_UPLOAD_TEMP_PATH);
        presenter.detachView();
    }

    @OnClick({R.id.ivPicOne, R.id.ivPicTwo, R.id.btnCmd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPicOne: {
                curImage = 0;
                presenter.selectPic(curImage);
            }
            break;
            case R.id.ivPicTwo: {
                curImage = 1;
                presenter.selectPic(curImage);
            }
            break;
            case R.id.btnCmd:
                presenter.apply(etName.getText().toString().trim(),
                        etCode.getText().toString().trim(),
                        etMobile.getText().toString().trim(),
                        etAddress.getText().toString().trim());
                break;
        }
    }

    @Override
    public String getNameHint() {
        return etName.getHint().toString();
    }

    @Override
    public String getCodeHint() {
        return etCode.getHint().toString();
    }

    @Override
    public String getHint() {
        return tvHint.getText().toString();
    }

    @Override
    public void showDialog() {
        photoDialog.show();
    }

    @Override
    public void setPic(String path) {
        if (path == null) {
            Glide.with(context).load(R.drawable.icon_add_picture).into(curImage == 0 ? ivPicOne : ivPicTwo);
        } else {
            Glide.with(context).load(path).into(curImage == 0 ? ivPicOne : ivPicTwo);
        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }
}
