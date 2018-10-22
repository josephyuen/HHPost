package com.teetaa.wx.lib.entity;


import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.teetaa.wx.lib.FastManager;

/**
 * WangXu ---- 梯台网络
 * Function: 主页Tab实体类
 * Description:
 * 1、2017-7-27 17:45:45 修改重载方式
 */
public class FastTabEntity implements CustomTabEntity {
    public String mTitle;
    public int mSelectedIcon;
    public int mUnSelectedIcon;
    public Fragment mFragment;

    public FastTabEntity(String title, int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this.mTitle = title;
        this.mSelectedIcon = selectedIcon;
        this.mUnSelectedIcon = unSelectedIcon;
        this.mFragment = fragment;
    }

    public FastTabEntity(int title, int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this(FastManager.getInstance().getApplication().getString(title), unSelectedIcon, selectedIcon, fragment);
    }

    public FastTabEntity(int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this("", unSelectedIcon, selectedIcon, fragment);
    }

    @Override
    public String getTabTitle() {
        if (TextUtils.isEmpty(mTitle)) {
            return "";
        }
        return mTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mUnSelectedIcon;
    }
}
