package com.hhpost.courier.net

import com.hhpost.courier.Constants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 *
 * 网络请求接口定义
 *
 */
interface ApiService {

    /**
     * 获取一张随机图片
     * @param clientId
     * @return
     */
    @GET(Constants.API_PIC_URL + "photos/random")
    fun getSplashBg(@Query("client_id") clientId: String): Observable<ResponseBody>


    @GET()
    fun getHomePageData()

}
