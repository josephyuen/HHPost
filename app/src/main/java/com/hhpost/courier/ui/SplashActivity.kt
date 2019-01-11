package com.hhpost.courier.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.renderscript.RenderScript
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

import com.hhpost.courier.Constants
import com.hhpost.courier.R
import com.hhpost.courier.R.id.sp_bg
import com.hhpost.courier.UserCenter
import com.hhpost.courier.entity.Splash
import com.hhpost.courier.service.SplashDownLoadService
import com.hhpost.courier.util.SerializableUtils
import com.teetaa.wx.lib.basis.BasisActivity
import com.teetaa.wx.lib.manager.GlideManager
import com.teetaa.wx.lib.util.FastUtil
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_splash.view.*
import java.io.IOException


/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述: 开屏页,能够从网络下载图片序列化本地
 */
class SplashActivity: BasisActivity() {

    override fun getContentLayout(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        mContentView.sp_jump_btn.setOnClickListener { gotoLoginOrMainActivity() }
        showAndDownSplash()
    }

    /*
     * 广告倒计时
     *  起始时间为 5400 是因为 countDownTimer 有延迟
     */
    private val countDownTimer = object : CountDownTimer(5400, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            mContentView.sp_jump_btn.text = "跳过(${millisUntilFinished / 1000}s)"
        }

        override fun onFinish() {
            mContentView.sp_jump_btn.text = "跳过(0s)"
            gotoLoginOrMainActivity()
        }
    }


    private var mSplash: Splash? = null


    /**
     * 背景图不要进行缓存
     */
    private fun getRequestOptions(): RequestOptions {
        return RequestOptions()
            .centerCrop()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
    }

    /*
     * 登录或是进入首页
     */
    private fun gotoLoginOrMainActivity() {
        countDownTimer.cancel()
        if (UserCenter.instance.token == null) {
            gotoLoginActivity()
        } else {
            gotoMainActivity()
        }
        finish()
    }


    /**
     * 前往Web页面
     */
    private fun gotoWebActivity() {
        if (mSplash?.click_url != null) {
            gotoLoginOrMainActivity()
            val bundle = Bundle()
            bundle.putString("url", mSplash?.click_url)
            bundle.putBoolean("fromSplash", true)
            FastUtil.startActivity(this@SplashActivity,SimpleWebPageActivity::class.java,bundle)
            finish()
        }
    }


    private fun showAndDownSplash() {
        sp_bg.setOnClickListener { gotoWebActivity() }
        showSplash()
        startImageDownLoad()
    }

    private fun showSplash() {
        mSplash = getLocalSplash()
        if (mSplash != null && !TextUtils.isEmpty(mSplash?.savePath)) {
            Log.d(TAG, "SplashActivity 获取本地序列化成功$mSplash")

            GlideManager.loadImg(mSplash?.savePath,sp_bg,resources.getDrawable(R.drawable.splash_countdown_bg) ,getRequestOptions())
            startClock()
        } else {
            mContentView.sp_jump_btn.visibility = View.INVISIBLE
            mContentView.sp_jump_btn.postDelayed({ gotoLoginOrMainActivity() }, 1000)
        }
    }


    private fun startImageDownLoad() {
        SplashDownLoadService.startDownLoadSplashImage(this, Constants.DOWNLOAD_SPLASH)
    }

    private fun getLocalSplash(): Splash? {
        var splash: Splash? = null
        try {
            val serializableFile = SerializableUtils.getSerializableFile(
                Constants.SPLASH_PATH,
                Constants.SPLASH_FILE_NAME
            )

            val serialObj = SerializableUtils.readObject<Splash>(serializableFile)
            if(serialObj != null){
                splash = serialObj as Splash
            }
        } catch (e: IOException) {
            Log.d(TAG, "SplashActivity 获取本地序列化闪屏失败 ${e.message}")
        }
        return splash
    }


    private fun startClock() {
        mContentView.sp_jump_btn.visibility = View.VISIBLE
        countDownTimer.start()
    }


    private fun gotoMainActivity() {
        FastUtil.startActivity(this,MainActivity::class.java)
    }

    private fun gotoLoginActivity() {
        FastUtil.startActivity(this,MainActivity::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

}