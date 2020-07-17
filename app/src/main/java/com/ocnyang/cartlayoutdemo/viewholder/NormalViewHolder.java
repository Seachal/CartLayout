package com.ocnyang.cartlayoutdemo.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocnyang.cartlayout.viewholder.CartViewHolder;
import com.ocnyang.cartlayoutdemo.R;


/**
 *    正常的 viewHolder   ,  activity_main_item_normal
 */
public class NormalViewHolder extends CartViewHolder {
    public TextView textView;
    public ImageView imgViewClose;

    public NormalViewHolder(View itemView, int chekbox_id) {
        super(itemView, chekbox_id);
        textView = itemView.findViewById(R.id.tv);
        imgViewClose = itemView.findViewById(R.id.img_close);
    }
}
