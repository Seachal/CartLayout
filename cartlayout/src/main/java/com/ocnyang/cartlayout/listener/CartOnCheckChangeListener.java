package com.ocnyang.cartlayout.listener;

import android.support.v7.widget.RecyclerView;

import com.ocnyang.cartlayout.CartAdapter;
import com.ocnyang.cartlayout.ParseHelper;
import com.ocnyang.cartlayout.bean.ICartItem;
import com.ocnyang.cartlayout.bean.IGroupItem;

import java.util.List;

/**
 * CartOnCheckChangeListener 定义为 abstract 是因为 没有实现  onCalculateChanged， 在作为匿名内部类时实现
 */
public abstract class CartOnCheckChangeListener implements OnCheckChangeListener, OnItemChangeListener {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;

    public CartOnCheckChangeListener(RecyclerView recyclerView, CartAdapter adapter) {
        this.recyclerView = recyclerView;
        this.cartAdapter = adapter;
    }

    @Override
    public void onCheckedChanged(List<ICartItem> beans, int position, boolean isChecked, int itemType) {
        switch (itemType) {
//            什么时候触发的选中？  根据代码   CartAdapter 中  if (holder.mCheckBox != null) 知道，
                //            此案例 不会执行normalCheckChange，
            //<editor-fold desc="Description">
            //            @Override
//            @CallSuper
//            public void onBindViewHolder(@NonNull final VH holder, final int position) {
//                holder.bindData(mDatas.get(position));
//                if (holder.mCheckBox != null) {
//                    holder.mCheckBox.setOnClickListener(new OnCheckBoxClickListener(position,
//                            mDatas.get(position).getItemType()));
//                    if (holder.mCheckBox.isChecked() != mDatas.get(position).isChecked()) {
//                        holder.mCheckBox.setChecked(mDatas.get(position).isChecked());
//                    }
//                }
//            }
            //</editor-fold>
            case ICartItem.TYPE_NORMAL:
                normalCheckChange(beans, position, isChecked);
                break;
            case ICartItem.TYPE_GROUP:
                groupCheckChange(beans, position, isChecked);
                break;
            case ICartItem.TYPE_CHILD:
                childCheckChange(beans, position, isChecked);
                break;
            default:
                break;
        }
    }

    /**
     * normal 选中状态变化
     *
     * @param beans     数据
     * @param position  group position
     * @param isChecked 选中状态
     */
    @Override
    public void normalCheckChange(List<ICartItem> beans, int position, boolean isChecked) {
//        [(1条消息)RecyclerView 刷新问题 adpater.notifyDataSetChanged() have problem_Jason_HD的博客-CSDN博客_observablearraylist recyclerview不刷新](https://blog.csdn.net/Jason_HD/article/details/80288167)
        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                && !recyclerView.isComputingLayout()) {// 避免滑动时刷新数据
            beans.get(position).setChecked(isChecked);
        }
    }

    /**
     * group 选中状态变化
     *
     * @param beans     数据
     * @param position  group position
     * @param isChecked 选中状态
     */
    @Override
    public void groupCheckChange(List<ICartItem> beans, int position, boolean isChecked) {
        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                && !recyclerView.isComputingLayout()) {// 避免滑动时刷新数据
            beans.get(position).setChecked(isChecked);
            setChildCheck(beans, position, isChecked);
        }
    }

    /**
     * child 选中状态变化
     *
     * @param beans     数据
     * @param position  child position
     * @param isChecked 选中状态
     */
    @Override
    public void childCheckChange(List<ICartItem> beans, int position, boolean isChecked) {

        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                && !recyclerView.isComputingLayout()) {// 避免滑动时刷新数据

            beans.get(position).setChecked(isChecked);

            IGroupItem groupBean = ParseHelper.getGroupBean(beans, position);
            List<ICartItem> childList = ParseHelper.getChildList(beans, position);

            if (!isChecked) {
                //group 为选中状态
                if (groupBean.isChecked()) {
                    int groupPosition = ParseHelper.getGroupPosition(beans,
                            position);
                    setGroupCheck(beans, groupPosition, false);
                    cartAdapter.notifyItemChanged(groupPosition, CartAdapter.PAYLOAD_CHECKBOX);
                }
            } else {
                for (int i = 0; i < childList.size(); i++) {
                    // 只要有一个 child 没有选中，group 就不是选中
                    if (!childList.get(i).isChecked()) {
                        return;
                    }
                }
                //child 全部选中，group 设置选中
                int groupPosition = ParseHelper.getGroupPosition(beans, position);

                setGroupCheck(beans, groupPosition, true);
                cartAdapter.notifyItemChanged(groupPosition, CartAdapter.PAYLOAD_CHECKBOX);
            }
        }
    }

    /**
     * 一次设置 group 下所有 child item 选中状态
     *
     * @param beans     整个数据 list
     * @param position  group position
     * @param isChecked 设置选中状态
     */
    private void setChildCheck(List<ICartItem> beans, int position, boolean isChecked) {
        for (int i = (position + 1); i < beans.size(); i++) {
            if (beans.get(i).getItemType() == ICartItem.TYPE_GROUP) {
                break;
            } else if (beans.get(i).getItemType() == ICartItem.TYPE_CHILD) {
                if (beans.get(i).isChecked() != isChecked) {
                    beans.get(i).setChecked(isChecked);
                    cartAdapter.notifyItemChanged(i, CartAdapter.PAYLOAD_CHECKBOX);
                }
            }
        }
    }

    /**
     * 设置 group item 选中状态
     *
     * @param beans         整个数据 list
     * @param groupPosition group 的 下标
     * @param isChecked     设置选中状态
     */
    private void setGroupCheck(List<ICartItem> beans, int groupPosition, boolean isChecked) {
        beans.get(groupPosition).setChecked(isChecked);
    }
}