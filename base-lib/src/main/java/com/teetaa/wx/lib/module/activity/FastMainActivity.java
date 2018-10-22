package com.teetaa.wx.lib.module.activity;


import androidx.viewpager.widget.ViewPager;
import com.teetaa.wx.lib.R;
import com.teetaa.wx.lib.basis.BasisActivity;
import com.teetaa.wx.lib.delegate.FastMainTabDelegate;
import com.teetaa.wx.lib.i.IFastMainView;

/**
 * WangXu ---- 梯台网络
 * Function: 快速创建主页Activity布局
 * Description:
 */
public abstract class FastMainActivity extends BasisActivity implements IFastMainView {

    protected FastMainTabDelegate mFastMainTabDelegate;

    @Override
    public void setViewPager(ViewPager mViewPager) {

    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    public int getContentLayout() {
        return isSwipeEnable() ? R.layout.fast_activity_main_view_pager : R.layout.fast_activity_main;
    }

    @Override
    public void beforeInitView() {
        super.beforeInitView();
        mFastMainTabDelegate = new FastMainTabDelegate(mContentView, this, this);
    }


    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onBackPressed() {
        quitApp();
    }
}
