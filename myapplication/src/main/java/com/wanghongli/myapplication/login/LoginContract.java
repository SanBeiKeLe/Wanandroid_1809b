package com.wanghongli.myapplication.login;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.base.IBasePresenter;
import com.wanghongli.myapplication.base.IBaseView;
import com.wanghongli.myapplication.data.entity.User;

import java.util.Map;


public interface LoginContract {



    public interface ILoginView extends IBaseView<ILoginPresenter> {

        void onSuccess(User user);

        void onFail(String msg);
    }


    public interface ILoginPresenter extends IBasePresenter<ILoginView> {

        void register(String username, String password, String repassword);
        void login(String username, String password);

    }


    public interface ILoginSource{

        void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void login(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

    }

}
