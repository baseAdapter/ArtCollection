package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.SysUtils;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.utils.CacheUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2016/10/16
 * @Description 设置界面
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.llClean)
    LinearLayout llClean;
    @BindView(R.id.llOpinion)
    LinearLayout llOpinion;
    @BindView(R.id.llAbout)
    LinearLayout llAbout;
    @BindView(R.id.tvClean)
    TextView tvClean;

    private MaterialDialog clearDialog;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initViews() {
        initTitle(R.string.mine_setting);
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        tvClean.setText(CacheUtils.getCacheSize(context.getCacheDir()));

        clearDialog = new MaterialDialog.Builder(context)
                .title("提示")
                .content("确认清除缓存？")
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        CacheUtils.cleanInternalCache(context);
                        tvClean.setText(CacheUtils.getCacheSize(context.getCacheDir()));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();
    }

    @OnClick({R.id.llClean, R.id.llOpinion, R.id.llAbout, R.id.llMine, R.id.btnLogout, R.id.llHelp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llClean:
                clearDialog.show();
                break;
            case R.id.llOpinion:
                startActivity(new Intent(context, FeedbackActivity.class));
                break;
            case R.id.llAbout:
                startActivity(new Intent(context, WebActivity.class).putExtra("linkUrl", SharedPref.getSysString(Constants.ABOUT_ME_URL)));
                break;
            case R.id.llMine:
                MineInfoActivity.launch(context);
                break;
            case R.id.btnLogout:
                SysUtils.logout();
                break;
            case R.id.llHelp:
                HelpListActivity.launch(context);
                break;
        }
    }
}
