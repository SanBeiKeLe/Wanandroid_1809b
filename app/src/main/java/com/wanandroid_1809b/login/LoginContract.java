package com.wanandroid_1809b.login;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanandroid_1809b.base.IBaseCallBack;
import com.wanandroid_1809b.data.entity.User;

import java.util.Map;

import jy.com.wanandroid.base.IBaseCallBack;
import jy.com.wanandroid.base.IBasePresenter;
import jy.com.wanandroid.base.IBaseView;
import jy.com.wanandroid.data.entity.User;

/*
 * created by taofu on 2019-06-11
 **/
public interface LoginContract {



    public interface ILoginView extends IBaseView<ILoginPresenter>{

        void onSuccess(User user);

        void onFail(String msg);
    }


    public interface ILoginPresenter extends IBasePresenter<ILoginView>{

        void register(String username, String password, String repassword);
        void login(String username, String password);

    }


    public interface ILoginSource{

        void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void login(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

    }

}
