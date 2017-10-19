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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.base.ImageFile;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.ImageUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author SunRenwwei
 * @Create 2016.5.12
 * @Description 分享中选择图片目录
 */

public class ImageFilesActivity extends BaseActivity {
    private static final int PHOTO_REQUEST_IMG = 1;// 相册

    private static final int SELECT_PHOTOS = 2;

    public static final String LEFT_COUNT = "leftCount";

    @BindView(R.id.rvFile)
    RecyclerView rvFile;
    @BindView(R.id.tbBase)
    Toolbar tbBase;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.flFinish)
    FrameLayout flFinish;

    private List<ImageFile> imageFiles;
    private ArrayList<String> imagePaths;


    List<ItemImageFile> fileList;
    private int leftCount;

    public static void launch(Activity activity, int leftCount, int requestCode) {
        activity.startActivityForResult(new Intent(activity, ImageFilesActivity.class)
                .putExtra(LEFT_COUNT, leftCount), requestCode);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_image_files);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        tbBase.setTitle("图片");
        tbBase.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initListeners() {
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putStringArrayListExtra("imagePaths", imagePaths));
                finish();
            }
        });
        btnFinish.setClickable(false);
    }

    @Override
    public void initData() {
        imagePaths = new ArrayList<>();
        leftCount = getIntent().getIntExtra("leftCount", 0);

        imageFiles = ImageUtils.getInstance().getAllImageFiles();
        fileList = new ArrayList<ItemImageFile>();

        if (imageFiles != null) {
            for (ImageFile file : imageFiles) {
                ItemImageFile item = new ItemImageFile();
                item.setCount(String.valueOf(file.getList().size()));
                item.setPic(file.getList().get(0));
                item.setName(file.getName());
                fileList.add(item);
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        rvFile.setLayoutManager(layoutManager);
        rvFile.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(2)));
        FileAdapter adapter = new FileAdapter();
        rvFile.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, new Intent().putStringArrayListExtra("imagePaths", data.getStringArrayListExtra(ImagePickerActivity.IMAGE_PATHS)));
            finish();
        } else if (resultCode == SELECT_PHOTOS) {
            if (data != null) {
                imagePaths = data.getStringArrayListExtra(ImagePickerActivity.IMAGE_PATHS);
                if (imagePaths.size() > 0) {
                    btnFinish.setText("完成" + "(" + imagePaths.size() + "/" + leftCount + ")");
                    btnFinish.setClickable(true);
                    flFinish.setForeground(new ColorDrawable(Color.parseColor("#00000000")));
                } else {
                    btnFinish.setText("完成");
                    flFinish.setClickable(false);
                    flFinish.setForeground(new ColorDrawable(Color.parseColor("#80000000")));
                }
            }
        }
    }

    class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
        private LayoutInflater inflater = LayoutInflater.from(context);

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_image_file, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final ItemImageFile item = fileList.get(position);
            holder.flPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(context, ImagePickerActivity.class)
                            .putExtra(ImagePickerActivity.FILE_NAME, item.getName())
                            .putExtra(ImagePickerActivity.LEFT_COUNT, leftCount)
                            .putStringArrayListExtra(ImagePickerActivity.ALL_PATHS, imageFiles.get(position).getList())
                            .putStringArrayListExtra(ImagePickerActivity.IMAGE_PATHS, imagePaths), PHOTO_REQUEST_IMG);
                }
            });
            holder.tvFile.setText(item.getName());
            holder.tvCount.setText(item.getCount());
            Glide.with(context).load(item.getPic()).centerCrop().into(holder.ivPic);
        }

        @Override
        public int getItemCount() {
            return fileList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.ivPic)
            ImageView ivPic;
            @BindView(R.id.tvFile)
            TextView tvFile;
            @BindView(R.id.tvCount)
            TextView tvCount;
            @BindView(R.id.flPic)
            FrameLayout flPic;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class ItemImageFile {
        private String count;
        private String name;
        private String pic; // 首张图片路径

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
