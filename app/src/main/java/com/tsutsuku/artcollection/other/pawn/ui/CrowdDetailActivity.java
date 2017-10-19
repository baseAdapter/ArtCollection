package com.tsutsuku.artcollection.other.pawn.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.other.pawn.contract.CrowDetailContract;
import com.tsutsuku.artcollection.other.pawn.model.CrowDetailModelImpl;
import com.tsutsuku.artcollection.other.pawn.model.CrowdInfo;
import com.tsutsuku.artcollection.other.pawn.presenter.CrowDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.BannerHolder.BannerImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.base.NestedWebFragment;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.ui.comment.CommentFragment;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.TimeUtils;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * @Author Tsutsuku
 * @Create 2017/6/22
 * @Description
 */

public class CrowdDetailActivity extends BaseFragmentActivity implements CrowDetailContract.View{
    private static final String ID = "id";
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.btnSupport)
    Button btnSupport;
    @BindView(R.id.tvMin)
    TextView tvMin;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvTop)
    TextView tvTop;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.flName)
    FrameLayout flName;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.sbBase)
    SeekBar sbBase;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.tvPerson)
    TextView tvPerson;
    @BindView(R.id.tvCash)
    TextView tvCash;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.flBottom)
    FrameLayout flBottom;

    private CrowDetailContract.Presenter presenter;
    private String[] titles = {"详情", "留言", "进展"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private CommentFragment commentFragment;

    public static void launch(Context context, String id) {
        context.startActivity(new Intent(context, CrowdDetailActivity.class)
                .putExtra(ID, id));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_crow_detail);
    }

    @Override
    public void initViews() {
        initTitle("艺术品众筹", R.drawable.icon_share_xxhdpi);
        ButterKnife.bind(this);

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    public void initListeners() {
        sbBase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

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
                    if (vpBase.getCurrentItem() != 1) {
                        flBottom.setVisibility(View.VISIBLE);
                        commentFragment.hideInputBox();
                    } else {
                        flBottom.setVisibility(View.GONE);
                        commentFragment.showInputBox();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);

        presenter = new CrowDetailPresenterImpl(getIntent().getStringExtra(ID), context, new CrowDetailModelImpl(), this);
        presenter.start();
    }


    @OnClick({R.id.btnSupport, R.id.ivTitleButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSupport:
                presenter.support();
                break;
            case R.id.ivTitleButton:
                presenter.share();
                break;
        }
    }

    @Override
    public void setData(final CrowdInfo info) {
        cbBase.setPages(
                new CBViewHolderCreator<BannerImageHolder>() {
                    @Override
                    public BannerImageHolder createHolder() {
                        return new BannerImageHolder();
                    }
                }, info.getBriefPics())
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.icon_dot, R.drawable.icon_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (!TextUtils.isEmpty(info.getBriefPics().get(position))) {
                            PhotoViewActivity.launchTypeView(context, position, (ArrayList) info.getBriefPics());
                        }
                    }
                }).startTurning(4000);

        switch (info.getStatus()) {
            case "0": {
                tvStatus.setText("预展中");
            }
            break;
            case "1": {
                tvStatus.setText("众筹中");
            }
            break;
            case "2": {
                tvStatus.setText("众筹成功");
            }
            break;
            case "3": {
                tvStatus.setText("众筹失败");
            }
            break;
        }

        tvMin.setText("支持金额" + info.getMinRaised() + "元起");
        tvTop.setText(info.getTitle());
        Glide.with(context).load(info.getAuthorImg()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvName.setText(info.getAuthor());
        tvDesc.setText(info.getAuthorDes());

        tvTime.setText(TimeUtils.getCrowTime(info.getEndIntTime()));
        tvPerson.setText(info.getHaveNum()+"人");
        tvCash.setText(info.getHaveRaised()+"元");

        sbBase.setMax(100);
        int progress = Integer.parseInt(Arith.div(Arith.mul(info.getHaveRaised(), "100"), info.getTotalRaised()));
        if (progress < 100){
            sbBase.setProgress(progress);
            tvProgress.setText(progress + "%");
        } else {
            sbBase.setProgress(100);
            tvProgress.setText(100 + "%");
        }

        fragmentList.add(NestedWebFragment.newInstance(info.getDetailUrl()));
        commentFragment = CommentFragment.newTypeFoldInstance(getIntent().getStringExtra(ID), true, "3", info.getComments());
        fragmentList.add(commentFragment);
        fragmentList.add(NestedWebFragment.newInstance(info.getDetailDoUrl()));

        vpBase.getAdapter().notifyDataSetChanged();
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
