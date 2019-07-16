package com.wanghongli.myapplication.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wanghongli.myapplication.LoadingPage;
import com.wanghongli.myapplication.R;
import com.wanghongli.myapplication.utils.Logger;


public class BaseFragment extends RxFragment {

    private BaseActivity mBaseActivity;

    private String TAG;

    public BaseFragment(){
        TAG = getClass().getSimpleName();
    }

    public int enter() {
        if (!isNeedAnimation()) {
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



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Logger.d("%s", TAG);
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }


    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        if (mBaseActivity != null) {
            mBaseActivity.addFragment(manager, aClass, containerId, args);
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
