package com.wanghongli.myapplication.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wanghongli.myapplication.LoadingPage;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.base.BaseFragment;
import com.wanghongli.myapplication.navigation.contract.INavigationContract;
import com.wanghongli.myapplication.navigation.presenter.INavigationPresenter;
import com.wanghongli.myapplication.utils.Logger;
import com.wanghongli.myapplication.utils.SystemFacade;

import java.util.ArrayList;

import retrofit2.http.POST;

public class NavigationFragment extends BaseFragment implements INavigationContract.View {
    private static final String TAG = "NavigationFragment";
    private INavigationContract.Presenter mPresenter;
    private RecyclerView mNavigation_rv_left;
    private RecyclerView mNavigation_rv_right;

    private LeftAdapger mLefeAdapger;

    private RightAdapter mRightAdapter;
    private CheckBox mCheckBox;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.navigation_fragment, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mNavigation_rv_left = inflate.findViewById(R.id.navigation_rv_left);
        mNavigation_rv_right = inflate.findViewById(R.id.navigation_rv_right);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        //请求数据
        mPresenter.getPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new INavigationPresenter());
    }

    @Override
    public void onStartRequestServer() {
        showLoadingPage(LoadingPage.MODE_2);
    }

    @Override
    public void onServerReceived(ArrayList<DataBean> list, String msg) {
        if (!SystemFacade.isListEmpty(list)) {
            if (mLefeAdapger == null || mLefeAdapger.getItemCount() == 0) {
                Logger.d("%s 由于之前没有缓存数据，并且服务器回来是有数据，因此需要关闭Loading 页", TAG);
                dismissLoadingPage();
                if (mLefeAdapger == null) {
                    mNavigation_rv_left.setLayoutManager(new LinearLayoutManager(getContext()));
                    mLefeAdapger = new LeftAdapger(list);
                    mNavigation_rv_left.setAdapter(mLefeAdapger);

                } else {
                    mLefeAdapger.setData(list);
                    mLefeAdapger.notifyDataSetChanged();
                }


                if (mRightAdapter == null) {
                    mNavigation_rv_right.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRightAdapter = new RightAdapter(list);
                    mNavigation_rv_right.setAdapter(mRightAdapter);

                } else {
                    mRightAdapter.setData(list);
                    mRightAdapter.notifyDataSetChanged();
                }
            } else {
                Logger.d("%s 由于之前有缓存数据，并且服务器回来是有数据，说明服务器回来的数据和缓存的不一致，因此以服务器为准", TAG);
                mLefeAdapger.setData(list);
                mLefeAdapger.notifyDataSetChanged();

                mRightAdapter.setData(list);
                mRightAdapter.notifyDataSetChanged();
            }
        } else {
            if (mLefeAdapger == null || mLefeAdapger.getItemCount() == 0) {
                Logger.d("%s 由于之前没有缓存数据，并且服务器没有返回数据，所以需要显示错误页面 error = %s", TAG, msg);

                onError("加载失败，请重试！", new LoadingPage.OnReloadListener() {
                    @Override
                    public void reload() {
                        mPresenter.getPresenter();
                    }
                });
            }
        }
    }

    @Override
    public void onCacheReceived(ArrayList<DataBean> data, int type) {
        if (!SystemFacade.isListEmpty(data)) {
            Logger.d("%s 有缓存，关闭 loading 页，显示缓存数据",TAG);
            dismissLoadingPage();
            if (mLefeAdapger == null) {
                mNavigation_rv_left.setLayoutManager(new LinearLayoutManager(getContext()));
                mLefeAdapger = new LeftAdapger(data);
                mNavigation_rv_left.setAdapter(mLefeAdapger);
            } else {
                mLefeAdapger.setData(data);
                mLefeAdapger.notifyDataSetChanged();
            }

            if (mRightAdapter == null) {
                mNavigation_rv_right.setLayoutManager(new LinearLayoutManager(getContext()));
                mRightAdapter = new RightAdapter(data);
                mNavigation_rv_right.setAdapter(mRightAdapter);
            } else {
                mRightAdapter.setData(data);
                mRightAdapter.notifyDataSetChanged();
            }
        }else{
            Logger.d("%s 没有缓存，，等待服务器数据回来",TAG);
        }
    }

    @Override
    public void setPresenter(INavigationContract.Presenter presenter) {
        mPresenter = presenter;

    }

    @Override
    public Context getContextObject() {
        return getContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView(this);
    }


}
