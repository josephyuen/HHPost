package com.hhpost.courier

/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述: 这里放置必要的常量啦
 */
class Constants {

    companion object {
        const val API_DEBUG_SERVER_URL = "https://hunanpost.shidaiyoukan.com/"
        const val API_PIC_URL_KEY = "pic_url_key"
        const val API_PIC_URL = "https://api.unsplash.com/"

        const val EXTRA_KEY_EXIT = "extra_key_exit"

        const val DOWNLOAD_SPLASH = "download_splash"
        const val EXTRA_DOWNLOAD = "extra_download"

        //动态开屏序列化地址
        val SPLASH_PATH = App.appContext?.filesDir?.absolutePath + "/alpha/splash"

        const val SPLASH_FILE_NAME = "splash.ssr"



        const val PIC_ACCESS_KEY = "50939c6fa849775c1a2e1bbdd7289a44b86b8eff3e789390116c3c22df8b54a0"

        const val PIC_SECRET_KEY = "fc86c55a48f567c6436b46ae54f8f258adaea5dbb8bac0b962fca6ddb112d016"

    }



}
