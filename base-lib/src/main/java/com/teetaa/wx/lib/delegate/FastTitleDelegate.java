package com.teetaa.wx.lib.delegate;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.aries.ui.util.FindViewUtil;
import com.aries.ui.view.title.TitleBarView;
import com.teetaa.wx.lib.FastManager;
import com.teetaa.wx.lib.R;
import com.teetaa.wx.lib.i.IFastTitleView;
import com.teetaa.wx.lib.i.TitleBarViewControl;
import com.teetaa.wx.lib.manager.LoggerManager;
import com.teetaa.wx.lib.util.FastStackUtil;
import com.teetaa.wx.lib.util.FastUtil;
import com.teetaa.wx.lib.util.SizeUtil;

/**
 * WangXu ---- 梯台网络
 * Function: 带TitleBarView 的Activity及Fragment代理类
 * Description:
 * 1、2017-4-20 13:53:57 简化全局属性设置通过接口暴露实现
 * 2、2017-6-22 14:06:50 设置通用基础数据
 * 3、2017-7-23 09:47:16 修改TitleBarView设置主标题逻辑
 * ({@link Activity#getTitle()}获取不和应用名称一致才进行设置-因Manifest未设置Activity的label属性获取的是应用名称)
 */
public class FastTitleDelegate {
    public TitleBarView mTitleBar;

    public FastTitleDelegate(View rootView, IFastTitleView iTitleBarView, Class<?> cls) {
        mTitleBar = rootView.findViewById(R.id.titleBar_headFastLib);
        Context context = rootView.getContext();
        if (mTitleBar == null) {
            mTitleBar = FindViewUtil.getTargetView(rootView, TitleBarView.class);
        }
        if (mTitleBar == null) {
            return;
        }
        LoggerManager.i("class:" + cls.getSimpleName());
        //默认的MD风格返回箭头icon如使用该风格可以不用设置
        Drawable mDrawable = FastUtil.getTintDrawable(context.getResources().getDrawable(R.drawable.fast_ic_back),
                context.getResources().getColor(R.color.white));
        final Activity activity = FastStackUtil.getInstance().getActivity(cls);
        //设置TitleBarView 所有TextView颜色
        mTitleBar.setLeftTextDrawable(activity != null ? mDrawable : null)
                .setOnLeftTextClickListener(activity == null ? null : new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.onBackPressed();
                    }
                })
                .setTextColor(context.getResources().getColor(R.color.colorTitleText))
                .setTitleMainText(getTitle(activity));

        ViewCompat.setElevation(mTitleBar,SizeUtil.dp2px(4));
        TitleBarViewControl titleBarViewControl = FastManager.getInstance().getTitleBarViewControl();
        if (titleBarViewControl != null) {
            titleBarViewControl.createTitleBarViewControl(mTitleBar, cls);
        }
        iTitleBarView.beforeSetTitleBar(mTitleBar);
        iTitleBarView.setTitleBar(mTitleBar);
    }

    /**
     * 获取Activity 标题({@link Activity#getTitle()}获取不和应用名称一致才进行设置-因Manifest未设置Activity的label属性获取的是应用名称)
     *
     * @param activity
     * @return
     */
    private CharSequence getTitle(Activity activity) {
        if (activity != null) {
            CharSequence appName = FastUtil.getAppName(activity);
            CharSequence label = activity.getTitle();
            if (label != null && !label.equals(appName)) {
                return label;
            }
        }
        return "";
    }
}
