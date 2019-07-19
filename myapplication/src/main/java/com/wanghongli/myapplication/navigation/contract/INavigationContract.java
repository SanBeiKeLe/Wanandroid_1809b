package com.wanghongli.myapplication.navigation.contract;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBackWithCache;
import com.wanghongli.myapplication.base.IBasePresenter;
import com.wanghongli.myapplication.base.IBaseView;
import com.wanghongli.myapplication.base.IBaseViewWithCache;
import com.wanghongli.myapplication.navigation.DataBean;

import java.util.ArrayList;

public interface INavigationContract {
    interface Model {
        void getNavigationData(LifecycleProvider provider, IBaseCallBackWithCache<ArrayList<DataBean>> callback);
    }

    interface View extends IBaseViewWithCache<Presenter,ArrayList<DataBean>> {
        void onStartRequestServer();
        void onServerReceived(ArrayList<DataBean> list,String msg);
    }

    interface Presenter extends IBasePresenter<View> {
        void getPresenter();
    }
}
