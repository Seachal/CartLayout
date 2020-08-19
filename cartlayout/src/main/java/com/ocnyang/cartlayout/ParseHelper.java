package com.ocnyang.cartlayout;

import com.ocnyang.cartlayout.bean.ICartItem;
import com.ocnyang.cartlayout.bean.IChildItem;
import com.ocnyang.cartlayout.bean.IGroupItem;

import java.util.ArrayList;
import java.util.List;

public class ParseHelper {

    /**
     * 取出 list 中的 groupBean
     *
     * @param beans
     * @param childPosition
     * @return
     */
    public static IGroupItem<IChildItem> getGroupBean(List<ICartItem> beans, int childPosition) {
        for (int i = childPosition; i >= 0; i--) {
//            sca: 向前查找，遇到的第一个组类型，就是 childe的父类型
            if (beans.get(i).getItemType() == ICartItem.TYPE_GROUP) {
                return ((IGroupItem) beans.get(i));
            }
        }
        return null;
    }

    /**
     * 获取 group 下的 child list
     *
     * @param beans    整个数据 list
     * @param position 当前 group 的 position
     */
    public static List<ICartItem> getChildList(List<ICartItem> beans, int position) {
        List<ICartItem> childList = new ArrayList<>();
        for (int i = position; i < beans.size(); i++) {
//            向后查找，遇到下一个组就结束
            if (beans.get(i).getItemType() == ICartItem.TYPE_GROUP) {
                break;
            } else if (beans.get(i).getItemType() == ICartItem.TYPE_CHILD) {
                childList.add(beans.get(i));
            }
        }

        for (int i = position - 1; i >= 0; i--) {
//            想前查找，遇到前一个组就结束
            if (beans.get(i).getItemType() == ICartItem.TYPE_GROUP) {
                break;
            } else if (beans.get(i).getItemType() == ICartItem.TYPE_CHILD) {
                childList.add(beans.get(i));
            }
        }

        return childList;
    }

    /**
     * 根据 itemId 获取 child 所在的 group 的 position
     *
     * @param beans         整个数据 list
     * @param childPosition child 的 下标
     * @return group 的 position
     */
    public static int getGroupPosition(List<ICartItem> beans, int childPosition) {
        int groupPosition = 0;
        for (int i = childPosition; i >= 0; i--) {
            if (beans.get(i).getItemType() == ICartItem.TYPE_GROUP) {
                groupPosition = i;
                break;
            }
        }
        return groupPosition;
    }
}
