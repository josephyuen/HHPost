package com.teetaa.wx.lib.module.activity;


import com.aries.ui.view.title.TitleBarView;
import com.teetaa.wx.lib.basis.BasisActivity;
import com.teetaa.wx.lib.delegate.FastTitleDelegate;
import com.teetaa.wx.lib.i.IFastTitleView;

/**
 * WangXu ---- 梯台网络
 * Function: 包含TitleBarView的Activity
 * Description:
 */
public abstract class FastTitleActivity extends BasisActivity implements IFastTitleView {

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
