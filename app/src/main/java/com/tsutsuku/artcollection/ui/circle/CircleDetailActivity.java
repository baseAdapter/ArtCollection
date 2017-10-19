package com.tsutsuku.artcollection.ui.circle;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.ItemCircle;
import com.tsutsuku.artcollection.ui.BannerHolder.BannerImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.comment.CommentFragment;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TimeUtils;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.ShadeNormalTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * @Author Sun Renwei
 * @Create 2017/1/9
 * @Description Content
 */

public class CircleDetailActivity extends BaseFragmentActivity {
    private static final int TYPE_TREASURE = 0;
    private static final int TYPE_IDENTIFY = 1;

    public static final String TYPE = "type";
    public static final String DATA = "data";
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.ivCheck)
    ImageView ivCheck;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.ivPaid)
    ImageView ivPaid;
    @BindView(R.id.tvPaid)
    TextView tvPaid;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.rlUser)
    RelativeLayout rlUser;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.tvCate)
    TextView tvCate;

    private int type;
    private ItemCircle item;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private CommentFragment commentFragment;
    private IdentifyInfoFragment identifyInfoFragment;
    private ShadeNormalTitle shadeTitle;

    public static void launch(Context context, ItemCircle item) {
        context.startActivity(new Intent(context, CircleDetailActivity.class)
                .putExtra(DATA, item));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_circle_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        item = getIntent().getParcelableExtra(DATA);
        for (int i = 0; i < item.getPics().size(); i++) {
            item.getPics().set(i, item.getPics().get(i).replace("_thumb", ""));
        }

        type = Integer.valueOf(item.getCtype());

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
        setType();

        shadeTitle = new ShadeNormalTitle(context, new ShadeNormalTitle.NormalFunctionListener() {
            @Override
            public void back() {
                finish();
            }

            @Override
            public void share() {
                ShareUtils.showShare(context, item.getPics().get(0), item.getTitle(), SharedPref.getSysString(Constants.Share.CIRClE) + item.getMsgId());
            }
        }, 320);
        ctlBase.addView(shadeTitle.getRootView());
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
                    if (fragmentList.size() > 1) {
                        if (vpBase.getCurrentItem() == 1) {
                            commentFragment.showInputBox();
                            identifyInfoFragment.hideIdentifyButton();
                        } else {
                            commentFragment.hideInputBox();
                            identifyInfoFragment.showIdentifyButton();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        setData();

        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    private void setType() {
        switch (type) {
            case TYPE_TREASURE: {

            }
            break;
            case TYPE_IDENTIFY: {
                titles.add(getString(R.string.identify));
                identifyInfoFragment = IdentifyInfoFragment.newInstance(item.getMsgId(), item.getPics().get(0), item.getTitle());
                fragmentList.add(identifyInfoFragment);
            }
            break;
        }

        titles.add(getString(R.string.comment));
        commentFragment = CommentFragment.newTypeUnfoldInstance(item.getMsgId(), titles.size() == 2);
        fragmentList.add(commentFragment);
    }

    private void setData() {
        tvTitle.setText(item.getTitle());
        tvContent.setText(item.getContent());
        Glide.with(context).load(item.getUserPhoto()).placeholder(R.drawable.ic_default_avatar).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvName.setText(item.getUserName());
        tvCate.setText(item.getSpacateName());
        tvTime.setText(TimeUtils.parsePostTime(Long.valueOf(item.getPostTime())));
        cbBase.setPages(
                new CBViewHolderCreator<BannerImageHolder>() {
                    @Override
                    public BannerImageHolder createHolder() {
                        return new BannerImageHolder();
                    }
                }, item.getPics())
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.icon_dot, R.drawable.icon_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (!TextUtils.isEmpty(item.getPics().get(position))) {
                            PhotoViewActivity.launchTypeView(context, position, (ArrayList) item.getPics());
                        }
                    }
                });


        if (type == TYPE_IDENTIFY && "0".equals(item.getIsFree())) {
            ivPaid.setVisibility(View.VISIBLE);
            tvPaid.setVisibility(View.VISIBLE);
        }

        if ("1".equals(item.getCheckState())) {
            ivCheck.setVisibility(View.VISIBLE);
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
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
