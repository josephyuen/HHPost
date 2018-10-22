package com.teetaa.wx.lib.i;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * WangXu ---- 梯台网络
 * Function: 用于全局设置多状态布局
 * Description:
 * 1、修改设置多状态布局方式
 */
public interface MultiStatusView {

    /**
     * 设置多状态布局属性
     *
     * @param statusView
     * @param iFastRefreshLoadView
     */
    void setMultiStatusView(StatusLayoutManager.Builder statusView, IFastRefreshLoadView iFastRefreshLoadView);
}
