package com.tsutsuku.artcollection.ui.circle;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @Author SunRenwei
 * @Create 2016/6/13
 * @Description 图片界面
 */
public class PhotoViewActivity extends BaseActivity {
    public static final int TYPE_VIEW = 0; // just view
    public static final int TYPE_DELETE = 1; // used for delete
    public static final int TYPE_SELECT = 2; // used for select

    private static final String TYPE = "type";
    private static final String PIC_LIST = "picList";
    private static final String ALL_LIST = "allList";
    private static final String CUR_POSITION = "curPosition";
    private static final String LEFT_COUNT = "leftCount";

    private static final String EXTRA_IMAGE = "PhotoViewActivity:image";
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.flFinish)
    FrameLayout flFinish;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.tbBase)
    Toolbar tbBase;
    @BindView(R.id.vpPhoto)
    ViewPager vpPhoto;
    @BindView(R.id.flSelect)
    FrameLayout flSelect;
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;

    private List<String> picList; // 选中的图片
    private List<String> allList; // 所有的图片
    private int position;
    private int type;
    private int leftCount;

    private String picPath;
    private PicPagerAdapter adapter;

    public static void launchTypeView(Context context, int position, ArrayList<String> picList) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(TYPE, TYPE_VIEW);
        intent.putExtra(CUR_POSITION, position);
        intent.putStringArrayListExtra(PIC_LIST, picList);
        context.startActivity(intent);
    }

    public static void launchTypeDelete(Activity activity, final String imagePath, int requestCode) {
        launchTypeDelete(activity, 0, new ArrayList<String>() {{
            add(imagePath);
        }}, requestCode);
    }

    public static void launchTypeDelete(Activity activity, int position, ArrayList<String> picList, int requestCode) {
        Intent intent = new Intent(activity, PhotoViewActivity.class);
        intent.putExtra(TYPE, TYPE_DELETE);
        intent.putExtra(CUR_POSITION, position);
        intent.putStringArrayListExtra(PIC_LIST, picList);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void launchTypeSelect(Activity activity, int position, ArrayList<String> picList, ArrayList<String> allList, int leftCount, int requestCode) {
        Intent intent = new Intent(activity, PhotoViewActivity.class);
        intent.putExtra(TYPE, TYPE_SELECT);
        intent.putExtra(CUR_POSITION, position);
        intent.putStringArrayListExtra(PIC_LIST, picList);
        intent.putStringArrayListExtra(ALL_LIST, allList);
        intent.putExtra(LEFT_COUNT, leftCount);
        activity.startActivityForResult(intent, requestCode);
    }

    private void parseIntent(Intent intent) {
        type = intent.getIntExtra(TYPE, TYPE_VIEW);
        picList = intent.getStringArrayListExtra(PIC_LIST);
        position = intent.getIntExtra(CUR_POSITION, 0);
        if (type == TYPE_SELECT) {
            allList = intent.getStringArrayListExtra(ALL_LIST);
            leftCount = intent.getIntExtra(LEFT_COUNT, 0);
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_photo_view);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {
        tbBase.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        this.overridePendingTransition(0, 0);
        parseIntent(getIntent());

        switch (type) {
            case TYPE_VIEW: {

            }
            break;
            case TYPE_DELETE: {
                tbBase.setVisibility(View.VISIBLE);
                tbBase.setTag(true);

                final MaterialDialog dialog = new MaterialDialog.Builder(context)
                        .title("提示")
                        .content("确认删除该图片么？")
                        .backgroundColor(Color.WHITE)
                        .positiveText("确认")
                        .positiveColor((Color.GREEN))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                int position = vpPhoto.getCurrentItem();
                                picList.remove(position);
                                adapter = new PicPagerAdapter();
                                vpPhoto.setAdapter(adapter);
                                if (position != picList.size()) {
                                    vpPhoto.setCurrentItem(position);
                                } else {
                                    vpPhoto.setCurrentItem(position - 1);
                                }
                                setResult(RESULT_OK, new Intent().putStringArrayListExtra(Intents.IMAGE_PATHS, (ArrayList<String>) picList));
                                dialog.dismiss();

                                if (picList.size() == 0) {
                                    finish();
                                }
                            }
                        })
                        .negativeText("取消")
                        .negativeColor(Color.DKGRAY)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).build();

                ivDelete.setVisibility(View.VISIBLE);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                    }
                });
            }
            break;
            case TYPE_SELECT: {
                tbBase.setVisibility(View.VISIBLE);
                tbBase.setTag(true);

                flSelect.setVisibility(View.VISIBLE);
                vpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        if (state == ViewPager.SCROLL_STATE_IDLE) {
                            cbCheck.setChecked(picList.contains(allList.get(vpPhoto.getCurrentItem())));
                        }
                    }
                });

                flFinish.setVisibility(View.VISIBLE);
                btnFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (picList.size() == 0) {
                            picList.add(allList.get(vpPhoto.getCurrentItem()));
                        }
                        setResult(RESULT_FIRST_USER, new Intent()
                                .putStringArrayListExtra(Intents.IMAGE_PATHS, (ArrayList<String>) picList));
                        finish();
                    }
                });

                cbCheck.setChecked(picList.contains(allList.get(position)));
                cbCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (picList.size() < leftCount || !cbCheck.isChecked()) {
                            if (cbCheck.isChecked()) {
                                picList.add(allList.get(vpPhoto.getCurrentItem()));
                            } else {
                                picList.remove(allList.get(vpPhoto.getCurrentItem()));
                            }
                            reBtnFinish();
                            setResult(RESULT_OK, new Intent().putStringArrayListExtra(Intents.IMAGE_PATHS, (ArrayList<String>) picList));
                        } else if (cbCheck.isChecked()) {
                            cbCheck.setChecked(false);
                        }
                    }
                });
            }
            break;
        }

        reBtnFinish();

        adapter = new PicPagerAdapter();
        vpPhoto.setAdapter(adapter);
        vpPhoto.setCurrentItem(position);
    }

    /**
     * 更新完成按键UI
     */
    private void reBtnFinish() {
        if (picList.size() > 0) {
            btnFinish.setText("完成" + "(" + picList.size() + "/" + leftCount + ")");
        } else {
            btnFinish.setText("完成");
        }
    }

    private class PicPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return type == TYPE_SELECT ? allList.size() : picList.size();
        }

        @Override
        public View instantiateItem(final ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            photoView.setBackgroundColor(Color.BLACK);
            Glide.with(context).load(type == TYPE_SELECT ? allList.get(position) : picList.get(position)).into(photoView);

            // Now just add PhotoView to ViewPager and return it
            LinearLayout.LayoutParams params;
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(photoView, params);
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    switch (type) {
                        case TYPE_VIEW: {
                            finish();
                        }
                        break;
                        case TYPE_DELETE:
                        case TYPE_SELECT: {
                            if ((Boolean) tbBase.getTag()) {
                                tbBase.animate().translationY(0)
                                        .alpha(1)
                                        .setDuration(300);
                                tbBase.setTag(false);

                                if (type == TYPE_SELECT) {
                                    if (type == TYPE_SELECT) {
                                        flSelect.animate()
                                                .alpha(1)
                                                .setDuration(300)
                                                .setListener(new Animator.AnimatorListener() {
                                                    @Override
                                                    public void onAnimationStart(Animator animation) {
                                                        flSelect.setVisibility(View.VISIBLE);
                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {

                                                    }

                                                    @Override
                                                    public void onAnimationCancel(Animator animation) {

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animator animation) {

                                                    }
                                                }).start();
                                    }
                                }
                            } else {
                                tbBase.animate().translationY(-tbBase.getHeight())
                                        .alpha(0)
                                        .setDuration(300);
                                tbBase.setTag(true);


                                if (type == TYPE_SELECT) {
                                    flSelect.animate()
                                            .alpha(0)
                                            .setDuration(300)
                                            .setListener(new Animator.AnimatorListener() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    flSelect.setVisibility(View.GONE);
                                                }

                                                @Override
                                                public void onAnimationCancel(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationRepeat(Animator animation) {

                                                }
                                            }).start();
                                }
                            }
                        }
                        break;
                    }
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }

}
