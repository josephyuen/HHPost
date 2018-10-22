package com.teetaa.wx.lib.i;

import com.aries.ui.view.title.TitleBarView;

/**
 * WangXu ---- 梯台网络
 * Function:包含TitleBarView接口
 * Description:
 * 1、2017-4-20 10:15:01 去掉isLightStatusBarEnable通过{@link TitleBarView#setStatusBarLightMode(boolean)}
 * 去掉getLeftIcon控制通过{@link TitleBarView#setLeftTextDrawable(int)}设置
 * 2、2017-6-22 14:05:50 去掉返回键设置属性
 */
public interface IFastTitleView {
    /**
     * 子类回调setTitleBar之前执行用于app设置全局Base控制统一TitleBarView
     *
     * @param titleBar
     */
    void beforeSetTitleBar(TitleBarView titleBar);

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    void setTitleBar(TitleBarView titleBar);

}
