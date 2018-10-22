package com.teetaa.wx.lib.retrofit;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * WangXu ---- 梯台网络
 * Function: 多BaseUrl解析器--开发者可自定义
 * Description:
 */
public interface FastUrlParser {

    /**
     * 将 {@link FastMultiUrl#mBaseUrlMap} 中映射的 Url 解析成完整的{@link HttpUrl}
     * 用来替换 @{@link Request#url} 里的BaseUrl以达到动态切换 Url的目的
     *
     * @param domainUrl 目标请求(base url)
     * @param url       需要替换的请求(原始url)
     * @return
     */
    HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url);
}
