package com.tsutsuku.artcollection.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.ActivityDetailContract;
import com.tsutsuku.artcollection.model.ActivityInfo;
import com.tsutsuku.artcollection.presenter.ActivityDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.base.NestedWebFragment;
import com.tsutsuku.artcollection.ui.comment.CommentFragment;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.ui.wallet.PayTypeDialog;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.ShadeBlankTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description 活动详情
 */

public class ActivityDetailActivity extends BaseFragmentActivity implements ActivityDetailContract.View {
    public static final String ACTIVITY_ID = "activityId";
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvApplyTime)
    TextView tvApplyTime;
    @BindView(R.id.flStatus)
    FrameLayout flStatus;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvFee)
    TextView tvFee;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.llUser)
    LinearLayout llUser;
    @BindView(R.id.flUser)
    FrameLayout flUser;
    @BindView(R.id.ivMerchant)
    ImageView ivMerchant;
    @BindView(R.id.tvMerchantName)
    TextView tvMerchantName;
    @BindView(R.id.tvMerchantInfo)
    TextView tvMerchantInfo;
    @BindView(R.id.ibFollow)
    ImageButton ibFollow;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.flChat)
    FrameLayout flChat;
    @BindView(R.id.btnCmd)
    Button btnCmd;
    @BindView(R.id.flInfo)
    FrameLayout flInfo;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.flShare)
    FrameLayout flShare;
    @BindView(R.id.tvUserNum)
    TextView tvUserNum;

    private ActivityDetailPresenterImpl presenter;
    private List<Fragment> fragmentList;
    private String[] titles = {"详情", "评论"};
    private CommentFragment commentFragment;
    private ShadeBlankTitle shadeTitle;

    private PayTypeDialog dialog;
    private MaterialDialog recordDialog;

    public static void launch(Context context, String activityId) {
        context.startActivity(new Intent(context, ActivityDetailActivity.class).putExtra(ACTIVITY_ID, activityId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_detail_activity);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        presenter = new ActivityDetailPresenterImpl(context, getIntent().getStringExtra(ACTIVITY_ID));
        presenter.attachView(this);

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));

        shadeTitle = new ShadeBlankTitle(context, new ShadeBlankTitle.BlankFunctionListener() {
            @Override
            public void back() {
                finish();
            }
        }, 170);
        ctlBase.addView(shadeTitle.getRootView());

        dialog = new PayTypeDialog(context, new PayTypeDialog.CallBack() {
            @Override
            public void finish(String payType) {
                presenter.apply(payType);
            }
        });

        recordDialog = new MaterialDialog.Builder(context)
                .title("提示")
                .content("是否开启录制?")
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        presenter.stream(true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        presenter.stream(false);
                    }
                }).build();
    }

    @Override
    public void initListeners() {
        ablBase.addOnOffsetChangedListener(shadeTitle.getOffsetChangedListener());

        vpBase.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_IDLE) {
                    if (vpBase.getCurrentItem() == 0) {
                        flInfo.setVisibility(View.VISIBLE);
                        commentFragment.hideInputBox();
                    } else {
                        flInfo.setVisibility(View.GONE);
                        commentFragment.showInputBox();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setViews(ActivityInfo info) {
        Glide.with(context).load(info.getCoverPhoto()).into(ivCover);
        tvStatus.setText("0".equals(info.getStatus()) ? "报名中" : "报名结束");
        if ("0".equals(info.getStatus())) {
            tvApplyTime.setText("截止时间 " + info.getDeadline_time());
        }


        tvName.setText(info.getActivityName());
        if (Float.valueOf(info.getUseMoney()) == 0) {
            tvFee.setTextColor(getResources().getColor(R.color.orange));
            tvFee.setText("免费");
        } else {
            SpannableStringBuilder builder = new SpannableStringBuilder("报名费 ¥");
            builder.append(info.getUseMoney());
            builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), builder.length() - info.getUseMoney().length() - 1, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvFee.setText(builder);
        }

        tvAddress.setText(info.getAddress());
        tvTime.setText(info.getActivityTime());

        if ("1".equals(info.getCtype()) && info.getUserList().size() > 0) {
            flUser.setVisibility(View.VISIBLE);

            tvUserNum.setText("已报名（" + info.getUserList().size() + "）");
            for (ActivityInfo.UserListBean bean : info.getUserList()) {
                llUser.addView(ActivityUserView.getView(context, bean));
                if (info.getUserList().indexOf(bean) == 4) {
                    break;
                }
            }

            if (info.getUserList().size() > 5) {
                flUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtils.showMessage("open users");
                    }
                });
            }
        }

        Glide.with(context).load(info.getFarmInfo().getCoverPic()).transform(new CircleTransform(context)).into(ivMerchant);
        tvMerchantName.setText(info.getFarmInfo().getFarmName());
        tvMerchantInfo.setText(info.getFarmInfo().getBrief());
        ibFollow.setImageResource(info.getFarmInfo().getIsFollow() == 0 ? R.drawable.icon_follow : R.drawable.icon_followed);

        fragmentList = new ArrayList<>();
        fragmentList.add(NestedWebFragment.newInstance(info.getDetailUrl()));
        commentFragment = CommentFragment.newTypeFoldInstance(getIntent().getStringExtra(ACTIVITY_ID), true, "2", info.getComments());
        fragmentList.add(commentFragment);
        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    @Override
    public void setLiveStatus(int status) {
        switch (status) {
            case 0: {
                btnCmd.setText("报名");
                btnCmd.setBackgroundResource(R.color.orange);
            }
            break;
            case 1: {
                btnCmd.setText("您已报名");
                btnCmd.setBackgroundResource(R.color.btn_gray_normal);
            }
            break;
            case 2: {
                btnCmd.setText("开始直播");
                btnCmd.setBackgroundResource(R.color.orange);
            }
            break;
            case 3: {
                btnCmd.setText("进入直播间");
                btnCmd.setBackgroundResource(R.color.orange);
            }
            break;
            case 4: {
                btnCmd.setText("已结束");
                btnCmd.setBackgroundResource(R.color.btn_gray_normal);
            }
        }
    }

    @Override
    public void setFollow(boolean follow) {
        ibFollow.setImageResource(follow ? R.drawable.icon_followed : R.drawable.icon_follow);
    }

    @Override
    public void showPayDialog() {
        dialog.show();
    }

    @Override
    public void showStreamDialog() {
        recordDialog.show();
    }


    @OnClick({R.id.flChat, R.id.btnCmd, R.id.ibFollow, R.id.flShare})
    public void onClick(View view) {
        if (view.getId() != R.id.flShare && TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
            LoginActivity.launch(context);
            return;
        }
        switch (view.getId()) {
            case R.id.flChat: {
                presenter.chat();
            }
            break;
            case R.id.btnCmd: {
                presenter.openLive();
            }
            break;
            case R.id.ibFollow: {
                presenter.follow();
            }
            break;
            case R.id.flShare: {
                presenter.share();
            }
            break;
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
