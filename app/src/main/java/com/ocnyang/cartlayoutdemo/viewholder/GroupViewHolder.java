package com.ocnyang.cartlayoutdemo.viewholder;

import android.view.View;
import android.widget.TextView;

import com.ocnyang.cartlayout.viewholder.CartViewHolder;
import com.ocnyang.cartlayoutdemo.R;

/**
 *  sca: 店铺对应的 ViewHolder
 */
public class GroupViewHolder extends CartViewHolder {
    public TextView textView;

    public GroupViewHolder(View itemView, int chekbox_id) {
        super(itemView, chekbox_id);
        textView = itemView.findViewById(R.id.tv);
    }
}
