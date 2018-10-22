package com.hhpost.courier.ui

import android.os.Bundle
import com.hhpost.courier.R
import com.teetaa.wx.lib.basis.BasisActivity
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : BasisActivity() {

    override fun getContentLayout(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        mContentView.sample_text.text = getJs()
    }

    external fun getJs(): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}
