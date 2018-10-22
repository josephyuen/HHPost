package com.hhpost.courier.net;


import android.accounts.NetworkErrorException;
import com.teetaa.wx.lib.retrofit.FastRetryWhen;
import com.teetaa.wx.lib.retrofit.FastTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public abstract class BaseRepository {

    /**
     * @param observable 用于解析 统一返回实体统一做相应的错误码--如登录失效
     * @param <T>
     * @return
     */
    protected <T> Observable<T> transform(Observable<BaseEntity<T>> observable) {
        return FastTransformer.switchSchedulers(
                observable.retryWhen(new FastRetryWhen())
                        .flatMap((Function<BaseEntity<T>, ObservableSource<T>>) result -> {
                            if (result == null) {
                                return Observable.error(new NetworkErrorException());
                            } else {
                                return Observable.just(result.getData());
                            }
                        }));
    }

}
