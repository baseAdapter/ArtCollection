package com.tsutsuku.artcollection.ui.circle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.circle.ShareContract;
import com.tsutsuku.artcollection.model.ItemExport;
import com.tsutsuku.artcollection.presenter.circle.SharePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.view.SelectAdapter;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.CacheUtils;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Author SunRenwei
 * @Create 2016/10/15
 * @Description 分享界面
 */
public class ShareActivity extends BaseActivity implements ShareContract.View {
    private static final String TYPE = "type";

    private static final int FROM_GALLERY = 0;
    private static final int FROM_CAMERA = 1;

    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.rvPic)
    RecyclerView rvPic;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.tvPublishType)
    TextView tvPublishType;
    @BindView(R.id.rlPublishType)
    RelativeLayout rlPublishType;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.rlType)
    RelativeLayout rlType;
    @BindView(R.id.ivExpert)
    ImageView ivExpert;
    @BindView(R.id.tvExpert)
    TextView tvExpert;
    @BindView(R.id.tvExpertTitle)
    TextView tvExpertTitle;
    @BindView(R.id.flExpert)
    FrameLayout flExpert;
    @BindView(R.id.tvFee)
    TextView tvFee;
    @BindView(R.id.cbAgree)
    CheckBox cbAgree;
    @BindView(R.id.tvAgree)
    TextView tvAgree;
    @BindView(R.id.llAgree)
    LinearLayout llAgree;
    @BindView(R.id.rlSelectExpert)
    RelativeLayout rlSelectExpert;

    private SharePresenterImpl presenter;
    private static final String strTitle = "发布动态";

    DialogPlus photoDialog;
    DialogPlus typeDialog;
    MaterialDialog progressDialog;
    private String useMoney = "0.0";

    public static void launchTypeTreasure(Context context) {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))){
            LoginActivity.launch(context);
            return;
        }
        context.startActivity(new Intent(context, ShareActivity.class)
                .putExtra(TYPE, SharePresenterImpl.TYPE_TREASURE));
    }

    public static void launchTypeIdentify(Context context) {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))){
            LoginActivity.launch(context);
            return;
        }
        context.startActivity(new Intent(context, ShareActivity.class)
                .putExtra(TYPE, SharePresenterImpl.TYPE_IDENTIFY));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_share);
    }

    @Override
    public void initViews() {
        initTitle(getIntent().getIntExtra(TYPE, 0) == SharePresenterImpl.TYPE_TREASURE ? "发布分享" : "发布鉴定", "完成");
        ButterKnife.bind(this);
        presenter = new SharePresenterImpl(getIntent().getIntExtra(TYPE, 0));
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 5);
        rvPic.setLayoutManager(layoutManager);
        rvPic.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(5)));
        rvPic.setAdapter(presenter.getPicAdapter());

        initDialog();
    }

    private void initDialog() {
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

        final List<String> types;
        types = new ArrayList<>();
        types.add(getString(R.string.identify_paid));
        types.add(getString(R.string.identify_free));
        typeDialog = DialogPlus.newDialog(context)
                .setAdapter(new SelectAdapter(context, types))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        presenter.setPublishType(position);
                        tvPublishType.setText(types.get(position));
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .create();

        progressDialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .content("发布中...")
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheUtils.cleanCustomCache(ApiConstants.Paths.IMAGE_UPLOAD_TEMP_PATH);
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
    public void setTypeIdentify() {
        rlPublishType.setVisibility(View.VISIBLE);
        rlSelectExpert.setVisibility(View.VISIBLE);
        llAgree.setVisibility(View.VISIBLE);
    }

    @Override
    public void setType(String type) {
        tvType.setText(type);
    }

    @Override
    public void setPublishType(boolean isFree) {
        if (isFree) {
            rlSelectExpert.setVisibility(View.GONE);
            llAgree.setVisibility(View.GONE);
            flExpert.setVisibility(View.GONE);
        } else {
            rlSelectExpert.setVisibility(View.VISIBLE);
            llAgree.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setExpert(ItemExport item) {
        flExpert.setVisibility(View.VISIBLE);
        Glide.with(context).load(item.getPhoto()).transform(new CircleTransform(context)).into(ivExpert);
        tvExpert.setText(item.getNickname());
        tvExpertTitle.setText(item.getPersonalSign());
        tvFee.setText("¥" + item.getUseMoney());
        useMoney = item.getUseMoney();
    }

    @OnClick({R.id.tvTitleButton, R.id.rlPublishType, R.id.rlType, R.id.rlSelectExpert, R.id.tvAgree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTitleButton: {
                presenter.share(etTitle.getText().toString().trim(), etContent.getText().toString().trim(), useMoney, cbAgree.isChecked());
            }
            break;
            case R.id.rlPublishType: {
                typeDialog.show();
            }
            break;
            case R.id.rlType: {
                presenter.getType();
            }
            break;
            case R.id.rlSelectExpert: {
                presenter.getExpert(tvPublishType.getText().toString().equals(context.getString(R.string.identify_free)));
            }
            break;
            case R.id.tvAgree: {
                WebActivity.launch(context, SharedPref.getSysString(Constants.JIANDING_URL));
            }
            break;
        }
    }

    @Override
    public void finish() {
        dismissProgressDialog();
        super.finish();
    }
}
