package com.teetaa.wx.lib.i;

import android.app.Activity;

/**
 * WangXu ---- 梯台网络
 * Function: 首页调用返回键执行相关操作
 * Description:
 */
public interface QuitAppControl {

    /**
     * 退出程序提示回调
     *
     * @param isFirst  是否首次提示
     * @param activity 操作的Activity
     * @return 延迟间隔--如不需要设置两次提示可设置0--最佳方式是直接在回调中执行你想要的操作
     */
    long quipApp(boolean isFirst, Activity activity);
}
