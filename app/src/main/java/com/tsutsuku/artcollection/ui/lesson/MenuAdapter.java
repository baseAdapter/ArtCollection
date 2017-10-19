package com.tsutsuku.artcollection.ui.lesson;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.lesson.ItemCate;

import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2016/9/23
 * @Description Content
 */
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DraggableItemAdapter<RecyclerView.ViewHolder> {
    private final static int SELECTED_ITEM = 0; // 选中的项
    private final static int NORMAL_ITEM = 1; // 待选的项
    private final static int DRAG = 2; // 排序提示
    private final static int HINT = 3; // 选择提示

    private List<ItemCate> menuList; // 选中的项
    private List<ItemCate> allList; // 所有的项
    private Context context;
    private LayoutInflater inflater;
    private int selectedId;
    public boolean DRAG_MODE = false;


    public MenuAdapter(Context context, List<ItemCate> menuList, List<ItemCate> allList, int selectedId) {
        this.menuList = menuList;
        this.allList = allList;
        this.context = context;
        this.selectedId = selectedId;
        inflater = LayoutInflater.from(context);

        allList.removeAll(menuList);
        allList.addAll(0, menuList);

        setHasStableIds(true);
    }

    public void setDragMode(boolean setDrag) {
        DRAG_MODE = setDrag;
    }

    public void setIndex(int tagIndex){}
    @Override
    public int getItemViewType(int position) {
        if (position < menuList.size()) {
            return SELECTED_ITEM;
        } else if (position == menuList.size()) {
            return DRAG;
        } else if (position == menuList.size() + 1) {
            return HINT;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public long getItemId(int position) {
        switch (getItemViewType(position)) {
            case DRAG: {
                return 998;
            }
            case HINT: {
                return 999;
            }
            case SELECTED_ITEM: {
                return Long.valueOf(menuList.get(position).getCateId());
            }
            default:
                return Long.valueOf(allList.get(position - 2).getCateId());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SELECTED_ITEM: {
                View view = inflater.inflate(R.layout.item_menu_button, parent, false);
                final MenuViewHolder viewHolder = new MenuViewHolder(view);
                viewHolder.btnMenu.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!DRAG_MODE){
                            setDragMode(true);
                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });
                viewHolder.btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setIndex(viewHolder.getAdapterPosition());
                        ((Activity) context).finish();
                    }
                });

                viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemCate item = menuList.remove(viewHolder.getAdapterPosition());
                        allList.remove(viewHolder.getAdapterPosition());
                        allList.add(item);
                        notifyDataSetChanged();
                    }
                });
                return viewHolder;
            }
            case DRAG: {
                View view = inflater.inflate(R.layout.item_menu_drag, parent, false);
                return new ViewHolder(view);
            }
            case HINT: {
                View view = inflater.inflate(R.layout.item_menu_hint, parent, false);
                return new ViewHolder(view);
            }
            default: {
                View view = inflater.inflate(R.layout.item_menu_button, parent, false);
                MenuViewHolder viewHolder = new MenuViewHolder(view);
                return viewHolder;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case SELECTED_ITEM: {
                MenuViewHolder itemHolder = (MenuViewHolder) holder;
                if (getItemId(position) == selectedId) {
                    itemHolder.btnMenu.setTextColor(context.getResources().getColor(R.color.white));
                    itemHolder.btnMenu.setBackgroundResource(R.drawable.btn_orange);
                    setIndex(position);
                } else {
                    itemHolder.btnMenu.setTextColor(context.getResources().getColor(R.color.c));
                    itemHolder.btnMenu.setBackgroundResource(R.drawable.box_little_gray);
                }

                if (DRAG_MODE) {
                    if (Integer.valueOf(menuList.get(position).getCateId()) >= 0) {
                        itemHolder.btnDelete.setVisibility(View.VISIBLE);
                    } else {
                        itemHolder.btnDelete.setVisibility(View.GONE);
                    }
                    itemHolder.btnMenu.setEnabled(false);
                } else {
                    itemHolder.btnDelete.setVisibility(View.GONE);
                    itemHolder.btnMenu.setEnabled(true);
                }

                itemHolder.btnMenu.setText(menuList.get(position).getCateName());
            }
            break;
            case NORMAL_ITEM: {
                MenuViewHolder itemHolder = (MenuViewHolder) holder;
                itemHolder.btnMenu.setText(allList.get(position - 2).getCateName());
                itemHolder.btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemCate item = allList.remove(position - 2);
                        allList.add(menuList.size(), item);
                        menuList.add(item);
                        notifyDataSetChanged();
                    }
                });
            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        if (menuList.size() == allList.size()) {
            return allList.size() + 1;
        } else {
            return allList.size() + 2;
        }
    }

    @Override
    public boolean onCheckCanStartDrag(RecyclerView.ViewHolder holder, int position, int x, int y) {
        if (getItemViewType(position) != SELECTED_ITEM) {
            return false;
        }
        if (Integer.valueOf(menuList.get(position).getCateId()) < 0) {
            return false;
        }
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(RecyclerView.ViewHolder holder, int position) {
        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }
        final ItemCate item = menuList.remove(fromPosition);
        menuList.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        if (getItemViewType(dropPosition) != SELECTED_ITEM) {
            return false;
        }
        if (Integer.valueOf(menuList.get(dropPosition).getCateId()) < 0) {
            return false;
        }
        return true;
    }

    class MenuViewHolder extends AbstractDraggableItemViewHolder {
        Button btnMenu;
        Button btnDelete;

        public MenuViewHolder(View itemView) {
            super(itemView);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnMenu = (Button) itemView.findViewById(R.id.btnMenu);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
