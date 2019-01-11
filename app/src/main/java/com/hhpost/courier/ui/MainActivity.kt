package com.hhpost.courier.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.hhpost.courier.R
import com.hhpost.courier.R.id.home_page
import com.teetaa.wx.lib.basis.BasisActivity
import com.teetaa.wx.lib.util.SizeUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import io.flutter.facade.Flutter
import io.flutter.view.FlutterView


class MainActivity : BasisActivity() {

    override fun getContentLayout(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        mContentView.sample_text.text = "和丰商行"
        home_page.setOnClickListener{ openFlutterPage() }
    }

    private var flutterView: FlutterView? = null


    //  打开 Flutter 页面；目前看来加载过慢，准备等待Flutter的优化
    private fun openFlutterPage() {

        flutterView = Flutter.createView(this,lifecycle,"route1")

        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        layoutParams.topMargin = SizeUtil.getStatusHeight(this)

        (mContentView as RelativeLayout).addView(flutterView,layoutParams)

    }


    override fun onBackPressed() {
        val container = mContentView as RelativeLayout

        if(container.indexOfChild(flutterView) != -1){
            container.removeView(flutterView)
            return
        }
        moveTaskToBack(true)
    }

}
