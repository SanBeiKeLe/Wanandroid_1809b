package com.wanghongli.myapplication.navigation.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBackWithCache;
import com.wanghongli.myapplication.navigation.DataBean;
import com.wanghongli.myapplication.navigation.contract.INavigationContract;
import com.wanghongli.myapplication.navigation.model.INavigationModel;

import java.util.ArrayList;

public class INavigationPresenter implements INavigationContract.Presenter {

    private INavigationContract.Model mModel;
    private INavigationContract.View mView;

    public INavigationPresenter() {
        mModel = new INavigationModel();
    }

    @Override
    public void getPresenter() {
        mModel.getNavigationData((LifecycleProvider) mView, new IBaseCallBackWithCache<ArrayList<DataBean>>() {
            @Override
            public void onStartRequestServer() {
                if (mView != null) {
                    mView.onStartRequestServer();
                }
            }

            @Override
            public void onCacheBack(ArrayList<DataBean> list, int type) {
                if (mView != null) {
                    mView.onCacheReceived(list, type);
                }
            }

            @Override
            public void onSuccess(ArrayList<DataBean> data) {
                if (data.size() > 0) {
                    mView.onServerReceived(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                mView.onServerReceived(null, msg);
            }
        });
    }

    @Override
    public void attachView(INavigationContract.View view) {
        mView = view;
    }

    @Override
    public void detachView(INavigationContract.View view) {
        mView = null;
    }
}
