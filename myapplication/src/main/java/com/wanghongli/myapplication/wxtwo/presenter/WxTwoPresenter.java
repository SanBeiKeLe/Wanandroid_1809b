package com.wanghongli.myapplication.wxtwo.presenter;

import androidx.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wanghongli.myapplication.base.IBaseCallBack;
import com.wanghongli.myapplication.wxtwo.TabBean;
import com.wanghongli.myapplication.wxtwo.contract.WxTwoContract;
import com.wanghongli.myapplication.wxtwo.model.WxTwoModel;

import java.util.List;

public class WxTwoPresenter implements WxTwoContract.Presenter {

    private WxTwoContract.Model mWxTwoModel;
    private WxTwoContract.View mView;

    public WxTwoPresenter() {
        mWxTwoModel = new WxTwoModel();
    }

    @Override
    public void getTabPresenter(int type) {
        mWxTwoModel.getTabData((LifecycleProvider) mView, type, new IBaseCallBack<List<TabBean>>() {
            @Override
            public void onSuccess(List<TabBean> data) {
                mView.setTabData(data, null);

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    public void getWxArticlesPresenter(int page, int id, int type) {
    }

    @Override
    public void loadMoreArticles(int page, int type) {

    }

    @Override
    public void attachView(WxTwoContract.View view) {

        mView = view;
    }

    @Override
    public void detachView(WxTwoContract.View view) {
        mView = null;
    }
}
