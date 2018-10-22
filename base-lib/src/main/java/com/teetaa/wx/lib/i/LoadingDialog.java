package com.teetaa.wx.lib.i;

import android.app.Activity;

import androidx.annotation.Nullable;
import com.teetaa.wx.lib.widget.FastLoadDialog;

/**
 * WangXu ---- 梯台网络
 * Function: 用于全局配置网络请求登录Loading提示框
 * Description:
 */
public interface LoadingDialog {

    /**
     * @param activity
     * @return
     */
    @Nullable
    FastLoadDialog createLoadingDialog(@Nullable Activity activity);
}
