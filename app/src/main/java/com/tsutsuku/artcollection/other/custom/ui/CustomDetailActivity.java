package com.tsutsuku.artcollection.other.custom.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.other.custom.contract.CustomDetailContract;
import com.tsutsuku.artcollection.other.custom.model.CustomDetailInfo;
import com.tsutsuku.artcollection.other.custom.model.CustomDetailModelImpl;
import com.tsutsuku.artcollection.other.custom.presenter.CustomDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.UIUtils.WebViewUtils;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.AutoNewLineLayout;
import com.tsutsuku.artcollection.view.ShadeBlankTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/6/8
 * @Description
 */

public class CustomDetailActivity extends BaseActivity implements CustomDetailContract.View {
    private static final String DIY_ID = "diyId";
    @BindView(R.id.wvBase)
    WebView wvBase;
    @BindView(R.id.flChat)
    FrameLayout flChat;
    @BindView(R.id.btnBuy)
    Button btnBuy;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.alKey)
    AutoNewLineLayout alKey;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.tvDesc)
    TextView tvDesc;

    private CustomDetailContract.Presenter presenter;
    private ShadeBlankTitle shadeTitle;

    public static void launch(Context context, String diyId) {
        context.startActivity(new Intent(context, CustomDetailActivity.class)
                .putExtra(DIY_ID, diyId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_custom_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        shadeTitle = new ShadeBlankTitle(context, new ShadeBlankTitle.BlankFunctionListener() {
            @Override
            public void back() {
                finish();
            }
        }, 200);
        ctlBase.addView(shadeTitle.getRootView());
        ablBase.addOnOffsetChangedListener(shadeTitle.getOffsetChangedListener());
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        presenter = new CustomDetailPresenterImpl(context, this, new CustomDetailModelImpl());
        presenter.setId(getIntent().getStringExtra(DIY_ID));

        presenter.start();
    }

    @Override
    public void setInfo(CustomDetailInfo info) {
        Glide.with(context).load(info.getLogo()).into(ivLogo);
        Glide.with(context).load(info.getAuthorImg()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvTitle.setText(info.getTitle());
        tvName.setText(info.getAuthor());
        tvDesc.setText(info.getAuthorDes());

        SpannableStringBuilder builder = new SpannableStringBuilder("定制价：");
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.d)), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("¥" + info.getPrice());
        tvPrice.setText(builder);

        WebViewUtils.loadUrl(wvBase, info.getDetailUrl());

        String[] keys = info.getAuthorKeyWord().split(",");
        for (String key : keys) {
            TextView tvKey = new TextView(context);
            tvKey.setText(key);
            tvKey.setBackgroundResource(R.drawable.btn_little_orange);
            tvKey.setTextColor(getResources().getColor(R.color.white));
            alKey.addView(tvKey);
        }
    }

    @OnClick({R.id.flChat, R.id.btnBuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flChat:
                presenter.chat();
                break;
            case R.id.btnBuy:
                presenter.buy();
                break;
        }
    }
}
