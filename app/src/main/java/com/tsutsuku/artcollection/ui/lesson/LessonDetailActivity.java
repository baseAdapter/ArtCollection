package com.tsutsuku.artcollection.ui.lesson;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.LessonDetailContract;
import com.tsutsuku.artcollection.model.LessonBean;
import com.tsutsuku.artcollection.presenter.lesson.LessonDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseAppCompatActivity;
import com.tsutsuku.artcollection.ui.base.NestedWebFragment;
import com.tsutsuku.artcollection.ui.comment.CommentFragment;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TimeUtils;
import com.tsutsuku.artcollection.view.BaseScrollView;
import com.tsutsuku.artcollection.view.ShadeBlankTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;

/**
 * @Author Tsutsuku
 * @Create 2017/3/6
 * @Description 讲堂详情
 */

public class LessonDetailActivity extends BaseAppCompatActivity implements LessonDetailContract.View {
    private static final String LESSON_ID = "lessonId";
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.jvpBase)
    JCVideoPlayerStandard jvpBase;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTeacher)
    TextView tvTeacher;
    @BindView(R.id.tvSee)
    TextView tvSee;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.nsvBase)
    BaseScrollView nsvBase;
    @BindView(R.id.ivCollection)
    ImageView ivCollection;
    @BindView(R.id.flCollection)
    FrameLayout flCollection;
    @BindView(R.id.flShare)
    FrameLayout flShare;

    private ShadeBlankTitle shadeTitle;
    private List<Fragment> fragmentList;
    private String[] titles = {"详情", "评论"};
    private LessonDetailPresenterImpl presenter;

    private JCVideoPlayer.JCAutoFullscreenListener autoFullscreenListener;
    private SensorManager sensorManager;

    private CommentFragment commentFragment;

    public static void launch(Context context, String lessonId) {
        context.startActivity(new Intent(context, LessonDetailActivity.class).putExtra(LESSON_ID, lessonId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_lesson_detail);
    }

    @Override
    public void initViews() {

        ButterKnife.bind(this);
        getSupportActionBar().hide();
        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));

        shadeTitle = new ShadeBlankTitle(context, new ShadeBlankTitle.BlankFunctionListener() {
            @Override
            public void back() {
                finish();
            }
        }, 170);
        ctlBase.addView(shadeTitle.getRootView());

        presenter = new LessonDetailPresenterImpl(context, getIntent().getStringExtra(LESSON_ID));
        presenter.attachView(this);
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
                        } else {
                            commentFragment.hideInputBox();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        autoFullscreenListener = new JCVideoPlayer.JCAutoFullscreenListener();
        jvpBase.backButton.setVisibility(View.GONE);


        JCVideoPlayer.ACTION_BAR_EXIST = false;
        JCVideoPlayer.TOOL_BAR_EXIST = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(autoFullscreenListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setViews(LessonBean bean) {
        tvName.setText(bean.getVideoTitle());
        tvTeacher.setText("讲师：" + bean.getLecturer());
        tvTime.setText("时长：" + TimeUtils.secondsFormat(bean.getVideoLength()));
        tvSee.setText(bean.getSeeCount());

        fragmentList = new ArrayList<>();

        fragmentList.add(NestedWebFragment.newInstance(bean.getDetailUrl()));
        commentFragment = CommentFragment.newTypeFoldInstance(getIntent().getStringExtra(LESSON_ID), true, "1", bean.getComments());
        fragmentList.add(commentFragment);

        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);

        Glide.with(context).load(bean.getVideoLogo()).into(jvpBase.thumbImageView);
        jvpBase.setUp(bean.getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL, "");
        setCollect(bean.getIsCollection() == 1);
    }

    @Override
    public void setCollect(boolean isCollect) {
        ivCollection.setImageResource(isCollect ? R.drawable.icon_b_collected : R.drawable.icon_b_collection);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        sensorManager.unregisterListener(autoFullscreenListener);
    }

    @OnClick({R.id.flCollection, R.id.flShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flCollection:
                if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))){
                    LoginActivity.launch(context);
                } else {
                    presenter.collection();
                }
                break;
            case R.id.flShare:
                presenter.share();
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
