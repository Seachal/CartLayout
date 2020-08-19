package com.ocnyang.cartlayoutdemo.bean;

import com.ocnyang.cartlayout.bean.CartItemBean;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/6/23 14:03.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

/**
 * 在列表头部，购物车中有 * 件商品已经降价，快去看看
 */
public class NormalBean extends CartItemBean{
    /**
     * 降价数量
     */
    int markdownNumber;

    public int getMarkdownNumber() {
        return markdownNumber;
    }

    public void setMarkdownNumber(int markdownNumber) {
        this.markdownNumber = markdownNumber;
    }
}
