package com.hhpost.courier.entity

import java.io.Serializable

/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述: 这是要下载下来的Splash页背景的图的一些信息，使用对象序列化存储，因为方便嘛
 */

class Splash(
    var burl: String  //大图 url
    , var surl: String  //小图url
    , var click_url: String // 点击跳转 URl
    , var savePath: String  //图片的存储地址

) : Serializable {
    var id: Int = 0
    var type: Int = 0   //图片类型 Android 1 IOS 2
    var title: String? = null  //图片的存储地址


    override fun toString(): String {
        return "Splash{" +
                "id=" + id +
                ", burl='" + burl + '\''.toString() +
                ", surl='" + surl + '\''.toString() +
                ", type=" + type +
                ", click_url='" + click_url + '\''.toString() +
                ", savePath='" + savePath + '\''.toString() +
                '}'.toString()
    }

    companion object {

        private const val serialVersionUID = 7382351359868556980L//这里需要写明 序列化Id
    }
}
