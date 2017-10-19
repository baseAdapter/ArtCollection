package com.tsutsuku.artcollection.ui.point;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.PointDetailContract;
import com.tsutsuku.artcollection.model.point.ItemPoint;
import com.tsutsuku.artcollection.presenter.PointDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.view.ShadeNormalTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description 积分兑换详细View
 */

public class PointDetailActivity extends BaseActivity implements PointDetailContract.View {
    private static final String ITEM = "item";
    @BindView(R.id.wvDetail)
    WebView wvDetail;
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPoint)
    TextView tvPoint;
    @BindView(R.id.tbTitle)
    Toolbar tbTitle;
    @BindView(R.id.btnCmd)
    Button btnCmd;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;

    private PointDetailPresenterImpl presenter;
    private PointExchangeDialog dialog;
    private ShadeNormalTitle shadeNormalTitle;

    public static void launch(Context context, ItemPoint item) {
        context.startActivity(new Intent(context, PointDetailActivity.class).putExtra(ITEM, item));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_point_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        presenter = new PointDetailPresenterImpl();
        presenter.attachView(this);

        shadeNormalTitle = new ShadeNormalTitle(context, new ShadeNormalTitle.NormalFunctionListener() {
            @Override
            public void back() {
                finish();
            }

            @Override
            public void share() {

            }
        }, 160);
        ctlBase.addView(shadeNormalTitle.getRootView());
    }

    @Override
    public void initListeners() {
        ablBase.addOnOffsetChangedListener(shadeNormalTitle.getOffsetChangedListener());

    }

    @Override
    public void initData() {
        presenter.parseItem((ItemPoint) getIntent().getParcelableExtra(ITEM));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.btnCmd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCmd:
                if (dialog != null) {
                    dialog.show();
                }
                break;
        }
    }

    @Override
    public void setView(ItemPoint item) {
        Glide.with(context).load(item.getCoverPhoto()).into(ivCover);
        tvName.setText(item.getName());

        SpannableStringBuilder builder = new SpannableStringBuilder(context.getString(R.string.point));
        builder.append(item.getIntegrate());
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(24)), 3, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("分");
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), 3, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPoint.setText(builder);

        wvDetail.loadUrl(getIntent().getStringExtra(item.getDetailUrl()));

        WebSettings settings = wvDetail.getSettings();
        settings.setJavaScriptEnabled(true);

        wvDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        dialog = new PointExchangeDialog(context, item);
    }
}
