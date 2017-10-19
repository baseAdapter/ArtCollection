package com.tsutsuku.artcollection.ui.lesson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.model.lesson.ItemCate;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2016/9/23
 * @Description Content
 */
public class MenuActivity extends BaseActivity {
    private static final String CUR_LIST = "curList";
    private static final String ALL_LIST = "allList";
    private static final String INDEX = "index";
    @BindView(R.id.tvNotice)
    TextView tvNotice;
    @BindView(R.id.ivClick)
    ImageView ivClick;
    @BindView(R.id.rlClick)
    RelativeLayout rlClick;
    @BindView(R.id.tvFinish)
    TextView tvFinish;
    @BindView(R.id.rvMain)
    RecyclerView rvMain;

    private GridLayoutManager layoutManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewDragDropManager dragDropManager;
    private List<ItemCate> curList;
    private List<ItemCate> allList;
    private int index;

    public static void launch(Activity activity, List<ItemCate> curList, List<ItemCate> allList, int index, int requestCode) {
        activity.startActivityForResult(new Intent(activity, MenuActivity.class)
                .putParcelableArrayListExtra(CUR_LIST, (ArrayList)curList)
                .putParcelableArrayListExtra(ALL_LIST, (ArrayList)allList)
                .putExtra(INDEX, index), requestCode);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_menu);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        layoutManager = new GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false);


        dragDropManager = new RecyclerViewDragDropManager();
        dragDropManager.setInitiateOnMove(false);
        dragDropManager.setLongPressTimeout(750);
        dragDropManager.setCheckCanDropEnabled(true);

        curList = getIntent().getParcelableArrayListExtra(CUR_LIST);
        allList = getIntent().getParcelableArrayListExtra(ALL_LIST);
        index = getIntent().getIntExtra(INDEX, 0);

        final MenuAdapter adapter = new MenuAdapter(context, curList, allList, Integer.valueOf(curList.get(index).getCateId())) {
            @Override
            public void setDragMode(boolean setDrag) {
                super.setDragMode(setDrag);
                if (setDrag) {
                    tvFinish.setVisibility(View.VISIBLE);
                    ivClick.setVisibility(View.GONE);
                } else {
                    tvFinish.setVisibility(View.GONE);
                    ivClick.setVisibility(View.VISIBLE);
                }
                dragDropManager.setInitiateOnMove(setDrag);
                notifyDataSetChanged();
            }

            @Override
            public void setIndex(int tagIndex) {
                index = tagIndex;
            }
        };
        mWrappedAdapter = dragDropManager.createWrappedAdapter(adapter);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == curList.size()) {
                    return 4;
                } else if (position == curList.size() + 1) {
                    return 4;
                } else {
                    return 1;
                }
            }
        });
        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(mWrappedAdapter);
        rvMain.setItemAnimator(new RefactoredDefaultItemAnimator());


        dragDropManager.attachRecyclerView(rvMain);

        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setDragMode(false);
            }
        });
        ivClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        dragDropManager.cancelDrag();
        super.onPause();
    }

    @Override
    public void finish() {
        setResult(RESULT_OK, new Intent().putExtra(Intents.CUR_LIST, (Serializable) curList)
                .putExtra(Intents.INDEX, index));
        super.finish();
    }

    @Override
    protected void onDestroy() {
        if (dragDropManager != null) {
            dragDropManager.release();
            dragDropManager = null;
        }
        if (rvMain != null) {
            rvMain.setItemAnimator(null);
            rvMain.setAdapter(null);
            rvMain = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }


        super.onDestroy();
    }
}
