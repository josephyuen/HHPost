package com.hhpost.courier.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSON
import com.hhpost.courier.Constants
import com.hhpost.courier.entity.Splash
import com.hhpost.courier.net.ApiRepository
import com.hhpost.courier.util.SerializableUtils
import com.teetaa.wx.lib.manager.LoggerManager
import com.teetaa.wx.lib.retrofit.FastDownloadObserver
import com.teetaa.wx.lib.retrofit.FastRetrofit
import com.teetaa.wx.lib.util.FastFormatUtil
import com.teetaa.wx.lib.util.SPUtil
import io.reactivex.disposables.CompositeDisposable

import java.io.File
import java.io.IOException

/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述: 阿俊有毒,坑惨老子了... 这是一个闪屏页背景下载服务~~~。
 */
class SplashDownLoadService : IntentService("SplashDownLoad") {

    private var mScreen: Splash? = null

    /**
     * 获取本地的序列化图片信息文件
     * @return Splash图片信息对象  [com.hhpost.courier.entity.Splash]
     */
    private val splashLocal: Splash?
        get() {
            var splash: Splash? = null
            try {
                val splashFile =
                    SerializableUtils.getSerializableFile(Constants.SPLASH_PATH, Constants.SPLASH_FILE_NAME)
                splash = SerializableUtils.readObject<Splash>(splashFile) as Splash?
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return splash
        }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.getStringExtra(Constants.EXTRA_DOWNLOAD)
            val curDate = Integer.parseInt(FastFormatUtil.formatTime(System.currentTimeMillis(), "yyyyMMdd"))
            val lastPostDate = SPUtil.get(baseContext, lastGetSplashBg, Int.MAX_VALUE) as Int
            if (action == Constants.DOWNLOAD_SPLASH && ((lastPostDate < curDate) || lastPostDate == Int.MAX_VALUE)) {
                loadSplashNetDate()
            }
        }
    }

    /**
     * 获取网络图片信息
     */
    private fun loadSplashNetDate() {
        val compositeDisposable = CompositeDisposable()
        val disposable = ApiRepository.getInstance().splashBg
            .subscribe({ responseBody ->
                val curDate = Integer.parseInt(FastFormatUtil.formatTime(System.currentTimeMillis(), "yyyyMMdd"))
                SPUtil.put(baseContext, lastGetSplashBg, curDate)
                compositeDisposable.dispose()
                val result = responseBody.string()
                LoggerManager.e(result)
                val obj = JSON.parseObject(result)
                val bigUrl = obj.getJSONObject("urls").getString("regular")
                val smallUrl = obj.getJSONObject("urls").getString("small")
                val links = obj.getJSONObject("links").getString("html")

                mScreen = Splash(bigUrl, smallUrl, links, Constants.SPLASH_PATH)
                val splashLocal = splashLocal
                if (mScreen != null) {
                    if (splashLocal == null) {
                        startDownLoadSplash(mScreen!!.burl)
                    } else if (isNeedDownLoad(splashLocal.savePath, mScreen!!.burl)) {
                        startDownLoadSplash(mScreen!!.burl)
                    }
                } else {
                    if (splashLocal != null) {
                        val splashFile =
                            SerializableUtils.getSerializableFile(Constants.SPLASH_PATH, Constants.SPLASH_FILE_NAME)
                        if (splashFile.exists()) {
                            splashFile.delete()
                        }
                    }
                }
            }, { throwable ->
                LoggerManager.e("@@@@@" + throwable.message)
                compositeDisposable.dispose()
            })

        compositeDisposable.add(disposable)

    }

    /**
     * @param path 本地存储的图片绝对路径
     * @param url  网络获取url
     * @return 比较储存的 图片名称的哈希值与 网络获取的哈希值是否相同
     */
    private fun isNeedDownLoad(path: String, url: String): Boolean {
        if (TextUtils.isEmpty(path)) {
            return true
        }
        val file = File(path)
        return if (!file.exists()) {
            true
        } else getImageName(path).hashCode() != getImageName(url).hashCode()
    }


    private fun getImageName(url: String): String {
        if (TextUtils.isEmpty(url)) {
            return ""
        }
        val split = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val nameWith = split[split.size - 1]
        val split1 = nameWith.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return split1[0]
    }

    private fun startDownLoadSplash(burl: String) {
        FastRetrofit.getInstance().downloadFile(burl).subscribe(object : FastDownloadObserver(
            Constants.SPLASH_PATH,
            "/splash.png", null
        ) {
            override fun onSuccess(file: File) {
                Log.d(TAG, "闪屏页面下载完成" + file.absolutePath)
                if (mScreen != null) {
                    mScreen!!.savePath = file.absolutePath
                    SerializableUtils.writeObject(mScreen!!, Constants.SPLASH_PATH + "/" + Constants.SPLASH_FILE_NAME)
                }
            }

            override fun onFail(e: Throwable) {
                Log.d(TAG, "闪屏页面下载失败")
            }

            override fun onProgress(progress: Float, current: Long, total: Long) {

            }
        })

    }

    companion object {

        private val TAG = SplashDownLoadService::class.java.simpleName

        fun startDownLoadSplashImage(context: Context, action: String) {
            val intent = Intent(context, SplashDownLoadService::class.java)
            intent.putExtra(Constants.EXTRA_DOWNLOAD, action)
            context.startService(intent)
        }


        private const val lastGetSplashBg = "last_get_splash_bg_time"   //  上一次请求Splash 背景图的时间
    }
}