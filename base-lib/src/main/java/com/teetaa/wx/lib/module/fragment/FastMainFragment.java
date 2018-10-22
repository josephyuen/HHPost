package com.teetaa.wx.lib.module.fragment;


import androidx.viewpager.widget.ViewPager;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.teetaa.wx.lib.R;
import com.teetaa.wx.lib.basis.BasisFragment;
import com.teetaa.wx.lib.delegate.FastMainTabDelegate;
import com.teetaa.wx.lib.i.IFastMainView;

/**
 * WangXu ---- 梯台网络
 * Function: 快速创建主页布局
 * Description:
 */
public abstract class FastMainFragment extends BasisFragment implements IFastMainView, OnTabSelectListener {

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
}
