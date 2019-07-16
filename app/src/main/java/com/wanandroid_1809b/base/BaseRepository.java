package com.wanandroid_1809b.base;


import android.annotation.SuppressLint;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.BuildConfig;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wanandroid_1809b.utils.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BaseRepository {
    @SuppressLint("CheckResult")
    public static <T, R> void observer(LifecycleProvider provider, Observable<T> observable, Function<T, ObservableSource<R>> function, final IBaseCallBack<R> callBack) {
        observable.flatMap(function)
                .subscribeOn(.Schedulersio())
                .unsubscribeOn(Schedulers.io())
                .compose(provider instanceof RxAppCompatActivity ? ((RxAppCompatActivity) provider).<R>bindUntilEvent(ActivityEvent.DESTROY)
                        : ((RxFragment) provider).<R>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(R r) {
                        if (BuildConfig.DEBUG) {
                            Logger.d("%s onNext = ", getClass().getSimpleName(), r.toString());
                        }
                        callBack.onSuccess(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) {
                            e.printStackTrace();
                            Logger.d("%s onError = ", getClass().getSimpleName(), e.getMessage());
                        }
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
