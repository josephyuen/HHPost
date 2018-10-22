package com.teetaa.wx.lib.module.fragment;


import com.aries.ui.view.title.TitleBarView;
import com.teetaa.wx.lib.delegate.FastTitleDelegate;
import com.teetaa.wx.lib.i.IFastTitleView;

/**
 * WangXu ---- 梯台网络
 * Function: 设置有TitleBar及下拉刷新Fragment
 * Description:
 */
public abstract class FastTitleRefreshLoadFragment<T> extends FastRefreshLoadFragment<T> implements IFastTitleView {

    protected FastTitleDelegate mFastTitleDelegate;
    protected TitleBarView mTitleBar;

    @Override
    public void beforeSetTitleBar(TitleBarView titleBar) {
    }

    @Override
    public void beforeInitView() {
        super.beforeInitView();
        mFastTitleDelegate = new FastTitleDelegate(mContentView, this, this.getClass());
        mTitleBar = mFastTitleDelegate.mTitleBar;
    }
}
