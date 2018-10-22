package com.teetaa.wx.lib.i;

import com.aries.ui.view.title.TitleBarView;

/**
 * WangXu ---- 梯台网络
 * Function: 全局TitleBarView属性控制
 * Description:
 */
public interface TitleBarViewControl {

    /**
     * 全局设置TitleBarView 属性回调
     *
     * @param titleBar
     * @param cls 包含TitleBarView的类
     * @return
     */
    boolean createTitleBarViewControl(TitleBarView titleBar, Class<?> cls);
}
