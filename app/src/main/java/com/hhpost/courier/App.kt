package com.hhpost.courier

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.teetaa.wx.lib.FastManager
import com.teetaa.wx.lib.manager.LoggerManager
import com.teetaa.wx.lib.retrofit.FastRetrofit

/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述:这个没啥好说的，可以进行数据库等的一些初始化操作
 */
class App: Application() {

    override fun attachBaseContext(base: Context?) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            MultiDex.install(base)
        }

        appContext = base
        super.attachBaseContext(base)
    }

    companion object {
        var appContext: Context? = null

    }


    override fun onCreate() {
        super.onCreate()
        LoggerManager.init("HHPost", BuildConfig.DEBUG)   // 日志初始化
        UserCenter.initInstance(this)   //  用户数据初始化

        FastManager.init(this)
        FastRetrofit.getInstance().setBaseUrl(Constants.API_DEBUG_SERVER_URL)
        FastRetrofit.getInstance().putBaseUrl(Constants.API_PIC_URL_KEY,Constants.API_PIC_URL)


    }


}