package com.hhpost.courier.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.TypedValue
import android.view.View
import android.webkit.WebView
import com.aries.ui.view.title.TitleBarView
import com.aries.ui.widget.action.sheet.UIActionSheetDialog
import com.just.agentweb.AgentWeb
import com.teetaa.wx.lib.FastManager
import com.teetaa.wx.lib.R
import com.teetaa.wx.lib.manager.LoggerManager
import com.teetaa.wx.lib.module.activity.FastWebActivity
import com.teetaa.wx.lib.retrofit.FastDownloadObserver
import com.teetaa.wx.lib.retrofit.FastRetrofit
import com.teetaa.wx.lib.util.FastFileUtil
import com.teetaa.wx.lib.util.FastUtil
import com.teetaa.wx.lib.util.SnackBarUtil
import com.teetaa.wx.lib.util.ToastUtil
import java.io.File

/**
 * 创建者: (WangXu --- The Great God)
 * 简单的WebView singlePage
 */
class SimpleWebPageActivity : FastWebActivity() {

    private val mFilePath = FastFileUtil.getCacheDir()
    private val mFormat = "保存图片<br><small><font color='#2394FE'>图片文件夹路径:%1s</font></small>"

    override fun initView(savedInstanceState: Bundle?) {


    }

    override fun setTitleBar(titleBar: TitleBarView) {
        titleBar.setTitleMainTextMarquee(true).
            setDividerVisible(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        titleBar.setTextColor(resources.getColor(R.color.white))

    }


    override fun getProgressColor(): Int {
        return Color.YELLOW
    }


    override fun setAgentWeb(mAgentWeb: AgentWeb?) {
        super.setAgentWeb(mAgentWeb)
        val mWebView = mAgentWeb?.webCreator?.webView
        mWebView?.setOnLongClickListener { v ->
            val hitTestResult = mWebView.hitTestResult ?: return@setOnLongClickListener false
            if (hitTestResult.type == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                showDownDialog(hitTestResult.extra)
            }
            LoggerManager.d("onLongClick:hitTestResult-Type:" + hitTestResult.type + ";Extra:" + hitTestResult.extra)
            true
        }
    }

    private fun showDownDialog(url: String?) {
        mActionSheetView = UIActionSheetDialog.ListSheetBuilder(mContext)
            .addItem(Html.fromHtml(String.format(mFormat, mFilePath)))
            .setOnItemClickListener { dialog, itemView, i ->
                when (i) {
                    0 -> downImg(url)
                }
            }
            .setCancel("取消")
            .setTextSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
            .create()
        mActionSheetView.show()
    }


    private fun downImg(url: String?) {
        val fileName = "/" + System.currentTimeMillis() + "_" + FastUtil.getRandom(100000) + ".jpg"
        FastRetrofit.getInstance().downloadFile(url)
            .subscribe(object : FastDownloadObserver(mFilePath, fileName) {
                override fun onSuccess(file: File) {
                    SnackBarUtil.with(mContainer)
                        .setMessage("图片已保存至" + mFilePath + "文件夹")
                        .setMessageColor(Color.parseColor("#2394FE"))
                        .setBgColor(Color.WHITE)
                        .show()
                }

                override fun onFail(e: Throwable) {
                    ToastUtil.show("图片保存失败" + e.message)
                }

                override fun onProgress(progress: Float, current: Long, total: Long) {
                    LoggerManager.i(TAG, "progress:$progress")
                }
            })
    }
}
