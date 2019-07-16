package com.wanghongli.myapplication.data.repositories;

import android.text.TextUtils;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.BaseRepository;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.entity.User;
import com.wanghongli.myapplication.data.okhttp.ApiService;
import com.wanghongli.myapplication.data.okhttp.WADataService;
import com.wanghongli.myapplication.login.LoginContract;

import org.w3c.dom.Text;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;



public class LoginRepository extends BaseRepository implements LoginContract.ILoginSource {

    private ApiService mApiService;


    public LoginRepository(){
        mApiService = WADataService.getService();
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
