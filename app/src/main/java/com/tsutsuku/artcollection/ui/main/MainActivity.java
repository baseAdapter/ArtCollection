package com.tsutsuku.artcollection.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.circle.ShareActivity;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.vpMain)
    NoScrollViewPager vpMain;
    @BindView(R.id.tlMain)
    TabLayout tlMain;

    LayoutInflater inflater;
    private static final int TAB_COUNT = 4;
    @BindView(R.id.ibTreasure)
    ImageButton ibTreasure;
    @BindView(R.id.tvTreasure)
    TextView tvTreasure;
    @BindView(R.id.ibIdentify)
    ImageButton ibIdentify;
    @BindView(R.id.tvIdentify)
    TextView tvIdentify;
    @BindView(R.id.flBg)
    FrameLayout flBg;
    @BindView(R.id.ivHome)
    ImageView ivHome;

    private Observable<BusBean> uiObservable;

    private String titles[] = {"首页", "发现", "发布", "拍卖", "我的"};
    private int images[] = {
            R.drawable.icon_main,
            R.drawable.icon_find,
            R.color.transparent,
            R.drawable.icon_auction,
            R.drawable.icon_mine
    };
    private int selectedImages[] = {
            R.drawable.icon_main_selected,
            R.drawable.icon_find_selected,
            R.color.transparent,
            R.drawable.icon_auction_selected,
            R.drawable.icon_mine_selected
    };

    private List<Fragment> fragmentList;

    private boolean isAnimation;
    private boolean curShow;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < TAB_COUNT + 1; i++) {
            tlMain.addTab(tlMain.newTab().setCustomView(getTabView(i)));
        }
    }

    @Override
    public void initListeners() {
        flBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareButton(false);
            }
        });
        uiObservable = RxBus.getDefault().register(BusEvent.HOME_UI, BusBean.class);

        // 异步刷新界面
        // 这里利用了RxJava切换线程的用法，ObserveOn()用于观察者设定观察者运行的线程,subscribeOn()是用于被观察者运行的线程
        uiObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BusBean>() {
            @Override
            public void call(BusBean busBean) {
                switch (busBean.getType()) {
                    case BusEvent.HomeUI.AUCTION: {
                        // 点击拍卖跳转进入拍卖界面
                        tlMain.getTabAt(3).select();
                    }
                    break;
                }
            }
        });


        tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 2) {
                    ((TextView) tab.getCustomView().findViewById(R.id.tvTab)).setTextColor(getResources().getColor(R.color.orange));
                    ((ImageView) tab.getCustomView().findViewById(R.id.ivTab)).setImageResource(selectedImages[position]);
                    vpMain.setCurrentItem(position > 2 ? position - 1 : position, false);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 2) {
                    ((TextView) tab.getCustomView().findViewById(R.id.tvTab)).setTextColor(getResources().getColor(R.color.d));
                    ((ImageView) tab.getCustomView().findViewById(R.id.ivTab)).setImageResource(images[position]);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initData() {
        fragmentList = new ArrayList<>();

        fragmentList.add(new HomeFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new AuctionFragment());
        fragmentList.add(new MineFragment());
        initViewPager();

        tlMain.getTabAt(1).select();
        tlMain.getTabAt(0).select();
    }

    private View getTabView(final int position) {
        View view = inflater.inflate(R.layout.item_main, null);
        TextView tvTab = (TextView) view.findViewById(R.id.tvTab);
//        ((FrameLayout)view).findViewById(R.id.tvTab);
        tvTab.setText(titles[position]);
        ImageView ivTab = (ImageView) view.findViewById(R.id.ivTab);
        ivTab.setImageResource(images[position]);
        if (position == 4) {
            final TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
//            observable = RxBus.getDefault().register("Unread", Boolean.class);
//            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
//                @Override
//                public void call(Boolean o) {
//                    if (o){
//                        int unread = EMClient.getInstance().chatManager().getUnreadMsgsCount();
//                        if (unread > 0){
//                            tvMessage.setVisibility(View.VISIBLE);
//                            tvMessage.setText(String.valueOf(unread));
//                        } else {
//                            tvMessage.setVisibility(View.GONE);
//                        }
//                    }
//                }
//            });
        }

        if (position > 1) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (position == 2) {
                            showShareButton(!curShow);
                        } else if (!TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
                            return false;
                        } else {
                            LoginActivity.launch(context);
                        }
                    }
                    return true;
                }
            });
        }
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    private void initViewPager() {
        vpMain.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        vpMain.setNoScroll(true);
        vpMain.setOffscreenPageLimit(4);
    }

    @OnClick({R.id.ibTreasure, R.id.ibIdentify, R.id.ivHome})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibTreasure:
                ShareActivity.launchTypeTreasure(context);
                break;
            case R.id.ibIdentify:
                ShareActivity.launchTypeIdentify(context);
                break;
            case R.id.ivHome:
                showShareButton(!curShow);
                break;
        }
        showShareButton(false);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(BusEvent.HOME_UI, uiObservable);
    }

    private void showShareButton(boolean isShow) {
        if (isAnimation || isShow == curShow) {
            return;
        } else {
            isAnimation = true;
        }

        // 定义动画集合
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator treasureAnim;
        if (isShow) {
            treasureAnim = ObjectAnimator.ofFloat(ibTreasure, "translationY", 0, DensityUtils.dp2px(-150), DensityUtils.dp2px(-130)).setDuration(500);
        } else {
            treasureAnim = ObjectAnimator.ofFloat(ibTreasure, "translationY", DensityUtils.dp2px(130)).setDuration(300);
        }
        //使用AccelerateDecelerateInterpolator 在动画开始和结束的地方速率变化较慢，中间的时候加速
        treasureAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator identifyAnim;
        if (isShow) {
            identifyAnim = ObjectAnimator.ofFloat(ibIdentify, "translationY", 0, DensityUtils.dp2px(-150), DensityUtils.dp2px(-130)).setDuration(500);
            identifyAnim.setStartDelay(200);
        } else {
            identifyAnim = ObjectAnimator.ofFloat(ibIdentify, "translationY", DensityUtils.dp2px(130)).setDuration(300);
        }
        identifyAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(flBg, "alpha", isShow ? 1 : 0);
        alphaAnim.setDuration(isShow ? 700 : 300);

        RotateAnimation rotate = new RotateAnimation(isShow ? 0 : 135, isShow ? 315 : 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(isShow ? 600 : 300);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillAfter(true);

        ivHome.startAnimation(rotate);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!curShow) {
                    flBg.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (curShow) {
                    flBg.setVisibility(View.GONE);
                    curShow = false;
                } else {
                    curShow = true;
                }
                isAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.playTogether(treasureAnim, identifyAnim, alphaAnim);
        set.start();

//        flBg.animate()
//                .alpha(isShow ? 1 : 0)
//                .setDuration(500)
//                .setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        if (isShow) {
//                            flBg.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        if (!isShow) {
//                            flBg.setVisibility(View.GONE);
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                })
//                .start();
//        ibTreasure.animate()
//                .y(DensityUtils.dp2px(isShow ? -150 : 150))
//                .setInterpolator(new AccelerateDecelerateInterpolator())
//                .setDuration(300)
//                .start();
//        ibIdentify.animate()
//                .y(DensityUtils.dp2px(isShow ? -150 : 150))
//                .setInterpolator(new AccelerateDecelerateInterpolator())
//                .setDuration(300)
//                .setStartDelay(200)
//                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
