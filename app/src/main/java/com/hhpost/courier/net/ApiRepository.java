package com.hhpost.courier.net;


import com.hhpost.courier.Constants;
import com.teetaa.wx.lib.retrofit.FastRetrofit;
import com.teetaa.wx.lib.retrofit.FastRetryWhen;
import com.teetaa.wx.lib.retrofit.FastTransformer;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 *  API请求发起
 */
public class ApiRepository {

    private static volatile ApiRepository instance;
    private ApiService mApiService;

    private ApiRepository() {
        mApiService = getApiService();
    }

    public static ApiRepository getInstance() {
        if (instance == null) {
            synchronized (ApiRepository.class) {
                if (instance == null) {
                    instance = new ApiRepository();
                }
            }
        }
        return instance;
    }

    /**
     * 默认使用API缓存
     * @return
     */
    private ApiService getApiService() {
        mApiService = FastRetrofit.getInstance().createService(ApiService.class);
        return mApiService;
    }


    public Observable<ResponseBody> getSplashBg() {
        return FastTransformer.switchSchedulers(getApiService().getSplashBg(Constants.PIC_ACCESS_KEY).retryWhen(new FastRetryWhen()));
    }



}
