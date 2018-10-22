package com.teetaa.wx.lib.i;

import android.os.Bundle;
import androidx.annotation.LayoutRes;

/**
 * WangXu ---- 梯台网络
 * Function: Basis Activity及Fragment通用属性
 * Description:
 * 1、2018-7-23 10:37:39 删除findView 因高版本系统jar已实现相应功能
 */
public interface IBasisView {

    /**
     * 是否注册EventBus
     *
     * @return
     */
    boolean isEventBusEnable();

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    @LayoutRes
    int getContentLayout();

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    void initView(Bundle savedInstanceState);

    /**
     * 执行加载布局文件之前操作方法前调用
     */
    void beforeSetContentView();

    /**
     * 在初始化控件前进行一些操作
     */
    void beforeInitView();

    /**
     * 需要加载数据时重写此方法
     */
    void loadData();
}
