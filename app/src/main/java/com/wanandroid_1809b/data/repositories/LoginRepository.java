package com.wanandroid_1809b.data.repositories;

import android.text.TextUtils;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanandroid_1809b.base.BaseRepository;
import com.wanandroid_1809b.base.IBaseCallBack;
import com.wanandroid_1809b.data.entity.HttpResult;
import com.wanandroid_1809b.data.entity.User;
import com.wanandroid_1809b.data.okhttp.ApiService;
import com.wanandroid_1809b.login.LoginContract;


import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;



public class LoginRepository extends BaseRepository implements LoginContract.ILoginSource {

    private ApiService mApiService;


    public LoginRepository(){
        mApiService = MyDataService.getService();
    }
    @Override
    public void register(LifecycleProvider provider, Map<String, String> params, final IBaseCallBack<User> callBack) {


        request(provider, mApiService.register(params), callBack);
    }

    @Override
    public void login(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {
        request(provider, mApiService.login(params), callBack);
    }



    private void request(LifecycleProvider provider, Observable<HttpResult<User>> observable, IBaseCallBack<User> callBack){


        observer(provider, observable,new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {
                if(userHttpResult.errorCode == 0 && userHttpResult.data != null){
                    return Observable.just(userHttpResult.data);
                }

                String msg = "服务器返回数据为空";
                if(!TextUtils.isEmpty(userHttpResult.errorMsg)){
                    msg = userHttpResult.errorMsg;
                }

                return Observable.error(new NullPointerException(msg));
            }
        },callBack);
    }
}
