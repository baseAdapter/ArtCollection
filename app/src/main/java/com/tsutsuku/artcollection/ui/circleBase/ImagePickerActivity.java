package com.tsutsuku.artcollection.ui.circleBase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author SunRenwwei
 * @Create 2016.10.14
 * @Description 文件夹中选择图片
 */

public class ImagePickerActivity extends BaseActivity {
    private static final int SELECT_PHOTOS = 2;
    private static final int OTHER = -1;
    private static final int PHOTO_VIEW = 1;

    public static final String IMAGE_PATHS = "imagePaths";
    public static final String ALL_PATHS = "allPaths";
    public static final String FILE_NAME = "fileName";
    public static final String LEFT_COUNT = "leftCount";

    @BindView(R.id.rvPic)
    RecyclerView rvPic;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.tbBase)
    Toolbar tbBase;
    @BindView(R.id.flFinish)
    FrameLayout flFinish;

    private ArrayList<String> imagePaths;
    private ArrayList<String> allPaths;
    private int leftCount; // 剩余可选图片数,分享总共可传9张
    private Activity curActivity;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_image_picker);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        tbBase.setTitle(getIntent().getStringExtra(FILE_NAME));
        tbBase.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        curActivity = this;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        imagePaths = getIntent().getStringArrayListExtra(IMAGE_PATHS);
        allPaths = getIntent().getStringArrayListExtra(ALL_PATHS);
        leftCount = getIntent().getIntExtra(LEFT_COUNT, 0);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        rvPic.setLayoutManager(layoutManager);
        rvPic.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(2)));

        PicAdapter adapter = new PicAdapter();
        rvPic.setAdapter(adapter);

        reBtnFinish();
        btnFinish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFiles();
            }
        });

    }

    /**
     * 更新完成按键UI
     */
    private void reBtnFinish() {
        if (imagePaths.size() > 0) {
            flFinish.setForeground(new ColorDrawable(Color.parseColor("#00000000")));
            btnFinish.setText("完成" + "(" + imagePaths.size() + "/" + leftCount + ")");
            btnFinish.setClickable(true);
        } else {
            flFinish.setForeground(new ColorDrawable(Color.parseColor("#80000000")));
            btnFinish.setText("完成");
            btnFinish.setClickable(false);
        }
    }

    class PicAdapter extends RecyclerView.Adapter<PicAdapter.ViewHolder> {
        LayoutInflater inflater = LayoutInflater.from(context);

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_image_select, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Glide.with(context).load("file://" + allPaths.get(position)).centerCrop().into(holder.ivPic);
            if (imagePaths.contains(allPaths.get(position))) {
                holder.cbPic.setChecked(true);
                holder.vSelect.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            } else {
                holder.cbPic.setChecked(false);
                holder.vSelect.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
            }
            holder.cbPic.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!imagePaths.contains(allPaths.get(position))) {
                        if (imagePaths.size() == leftCount) {
                            holder.cbPic.setChecked(false);
                            return;
                        }
                        imagePaths.add(allPaths.get(position));
                        holder.vSelect.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
                    } else {
                        imagePaths.remove(allPaths.get(position));
                        holder.vSelect.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                    }
                    reBtnFinish();
                    setResult(SELECT_PHOTOS, new Intent().putExtra(IMAGE_PATHS, imagePaths));
                }
            });
            holder.flPic.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoViewActivity.launchTypeSelect(curActivity, position, imagePaths, allPaths, leftCount, PHOTO_VIEW);
                }
            });
        }

        @Override
        public int getItemCount() {
            return allPaths.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.ivPic)
            ImageView ivPic;
            @BindView(R.id.cbPic)
            CheckBox cbPic;
            @BindView(R.id.flPic)
            FrameLayout flPic;
            @BindView(R.id.vSelect)
            View vSelect;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    /**
     * 只需要在这个方法把选中的文档目录已list的形式传过去即可
     */
    public void sendFiles() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(IMAGE_PATHS, imagePaths);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_VIEW && resultCode != RESULT_CANCELED) {
            imagePaths.clear();
            imagePaths.addAll(data.getStringArrayListExtra(Intents.IMAGE_PATHS));
            switch (resultCode) {
                case RESULT_OK: {
                    rvPic.getAdapter().notifyDataSetChanged();
                }
                break;
                case RESULT_FIRST_USER: {
                    sendFiles();
                }
                break;
                default:
                    break;
            }
        }
    }
}
