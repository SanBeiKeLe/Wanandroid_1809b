package com.wanghongli.myapplication.todaynews.presenter;

import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.todaynews.contract.IContract;
import com.wanghongli.myapplication.todaynews.model.IModel;
import com.wanghongli.myapplication.wxtwo.TabBean;

import java.util.List;

public class IPresenter implements IContract.Presenter {
    private static final String TAG = "IPresenter";
    private IContract.Model mIModel;
    private IContract.View mView;

    public IPresenter() {
        mIModel = new IModel();
    }

    @Override
    public void getTabPresenter(int type) {
        mIModel.getTabData((LifecycleProvider) mView, type, new IBaseCallBack<List<TabBean>>() {
            @Override
            public void onSuccess(List<TabBean> data) {
                Log.d(TAG, "onSuccess: "+data.size());
                mView.setTabData(data, null);
            }

            @Override
            public void onFail(String msg) {
                Log.d(TAG, "onFail: "+msg);
                mView.setTabData(null, msg);
            }
        });
    }

    @Override
    public void getArticlePresenter(int type, int page, int id) {

    }

    @Override
    public void getLoadArticlePresenter(int type, int page) {

    }

    @Override
    public void attachView(IContract.View view) {

        mView = view;
    }

    @Override
    public void detachView(IContract.View view) {
        mView = null;
    }
}
