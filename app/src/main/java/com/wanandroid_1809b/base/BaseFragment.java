package com.wanandroid_1809b.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wanandroid_1809b.R;

public class BaseFragment extends RxFragment {
    private BaseActivity mBaseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public int enter(){
        if (!isNeedAnimation()){
            return 0;
        }
        return R.anim.common_page_right_in;
    }
    public int exit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_out;
    }

    public int popEnter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_in;
    }

    public int popExit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_out;
    }


    public boolean isNeedAnimation() {
        return true;
    }
    protected boolean isNeedToAddBackStack() {
        return true;
    }
    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        if (mBaseActivity != null) {
            mBaseActivity.addFragment(manager, aClass, containerId, args);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }

    protected void showToast(String msg){
        mBaseActivity.showToast(msg);
    }

    protected void showToast(@StringRes int resId){
        mBaseActivity.showToast(resId);
    }


    protected void showLoadingPage(int mode) {
        mBaseActivity.showLoadingPage(mode);
    }

    protected void dismissLoadingPage(){
        mBaseActivity.dismissLoadingPage();
    }

    protected void onError(String msg, LoadingPage.OnReloadListener listener){
        mBaseActivity.onError(msg, listener);
    }
}
