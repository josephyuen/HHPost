package com.teetaa.wx.lib.i;

import android.view.View;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * WangXu ---- 梯台网络
 * Function: StatusLayoutManager 属性控制
 * Description:
 */
public interface IMultiStatusView {
    /**
     * 设置StatusLayoutManager 的目标View
     *
     * @return
     */
    View getMultiStatusContentView();

    /**
     * 设置StatusLayoutManager属性
     *
     * @param statusView
     */
    void setMultiStatusView(StatusLayoutManager.Builder statusView);

    /**
     * 获取空布局里点击View回调
     *
     * @return
     */
    View.OnClickListener getEmptyClickListener();

    /**
     * 获取错误布局里点击View回调
     *
     * @return
     */
    View.OnClickListener getErrorClickListener();

    /**
     * 获取自定义布局里点击View回调
     *
     * @return
     */
    View.OnClickListener getCustomerClickListener();
}
