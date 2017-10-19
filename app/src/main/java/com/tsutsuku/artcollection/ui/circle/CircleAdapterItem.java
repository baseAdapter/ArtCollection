package com.tsutsuku.artcollection.ui.circle;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemCircle;
import com.tsutsuku.artcollection.presenter.circle.CircleRepository;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.ui.circleBase.CirclePicAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TimeUtils;
import com.tsutsuku.artcollection.utils.TouchDelegateUtils;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.ExpandableTextView;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;
import com.tsutsuku.artcollection.view.PicRecyclerView;
import com.tsutsuku.artcollection.view.RecyclerItemClickListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/4
 * @Description
 */

public class CircleAdapterItem extends BaseAdapterItem<ItemCircle> {
    public static final String TAG = CircleAdapterItem.class.getSimpleName();

    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.etvContent)
    ExpandableTextView etvContent;
    @BindView(R.id.rvPic)
    PicRecyclerView rvPic;
    @BindView(R.id.ivCheck)
    ImageView ivCheck;
    @BindView(R.id.cvCircle)
    CardView cvCircle;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.tvThumb)
    TextView tvThumb;
    @BindView(R.id.ivThumb)
    ImageView ivThumb;

    private SparseBooleanArray mCollapsedStatus;

    public CircleAdapterItem(SparseBooleanArray mCollapsedStatus) {
        this.mCollapsedStatus = mCollapsedStatus;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        rvPic.setLayoutManager(new GridLayoutManager(context, 3));
        rvPic.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(3)));

        rvPic.addOnItemTouchListener(new RecyclerItemClickListener(context, rvPic, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<String> list = new ArrayList<String>();
                for (String pic : curItem.getPics()) {
                    list.add(pic.replace("_thumb", ""));
                }
                PhotoViewActivity.launchTypeView(context, position, (ArrayList<String>) list);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        TouchDelegateUtils.expandViewTouchDelegate(ivThumb, DensityUtils.dp2px(10), DensityUtils.dp2px(10), DensityUtils.dp2px(10), DensityUtils.dp2px(10));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_circle;
    }

    @Override
    public void handleData(ItemCircle item, int position) {
        Glide.with(context).load(item.getUserPhoto()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvName.setText(item.getUserName());
        tvTitle.setText(item.getTitle());
        etvContent.setText(item.getContent(), mCollapsedStatus, position);
        tvTime.setText(TimeUtils.parsePostTime(Long.valueOf(item.getPostTime())));

        rvPic.setAdapter(new CirclePicAdapter(context, item.getPics()));
        ivCheck.setVisibility(item.getCheckState().equals("1") ? View.VISIBLE : View.GONE);
        ivDelete.setVisibility(item.getUserId().equals(SharedPref.getString(Constants.USER_ID)) ? View.VISIBLE : View.GONE);

        tvComment.setText(String.valueOf(item.getCommentCount()));
        tvThumb.setText(String.valueOf(item.getThumbs().size()));
        ivThumb.setImageResource(item.getThumbs().contains(new ItemCircle.ThumbsBean(SharedPref.getString(Constants.USER_ID))) ? R.drawable.icon_like_pressed_xxhdpi : R.drawable.icon_like_normal_xxhdpi);
    }

    @OnClick({R.id.cvCircle, R.id.ivDelete, R.id.ivThumb, R.id.ivShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvCircle: {
                CircleDetailActivity.launch(context, curItem);
            }
            break;
            case R.id.ivDelete: {
                new MaterialDialog.Builder(context)
                        .title("提示")
                        .content("确认删除该信息？")
                        .positiveText(context.getString(R.string.ok))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                CircleRepository.delete(curItem.getMsgId());
                                Log.e(TAG,"---"+curItem.getMsgId());

                                dialog.dismiss();
                            }
                        })
                        .negativeText(context.getString(R.string.cancel))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).show();

            }
            break;
            case R.id.ivThumb: {
                setThumb(curItem);
            }
            break;
            case R.id.ivShare:{
                ShareUtils.showShare(context, curItem.getPics().get(0), curItem.getTitle(), SharedPref.getSysString(Constants.Share.CIRClE));
            }
            break;
        }
    }

    /***
     *
     *点赞数据请求访问
     * @param item
     *
     ***/
    private void setThumb(final ItemCircle item) {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
            return;
        }
        final boolean isThumb = curItem.getThumbs().contains(new ItemCircle.ThumbsBean(SharedPref.getString(Constants.USER_ID)));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", isThumb ? "Circle.cancelThumb" : "Circle.thumbMessage");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("msgId", curItem.getMsgId());
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                Log.e(TAG,"onSuccess---"+data);
                Log.e(TAG,"onSuccess---"+statusCode);

                if (data.getInt("code") == 0) {
                    if (isThumb) {
                        item.getThumbs().remove(new ItemCircle.ThumbsBean(SharedPref.getString(Constants.USER_ID)));
                        Log.e(TAG,"code==0"+data.getInt("code"));
                    } else {
                        Log.e(TAG,"code not 0"+data.getInt("code"));
                        item.getThumbs().add(new ItemCircle.ThumbsBean(SharedPref.getString(Constants.USER_ID)));
                    }
                    ivThumb.setImageResource(isThumb ? R.drawable.icon_like_normal_xxhdpi : R.drawable.icon_like_pressed_xxhdpi);
                    tvThumb.setText(String.valueOf(item.getThumbs().size()));
                }

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {


            }
        });
    }
}
