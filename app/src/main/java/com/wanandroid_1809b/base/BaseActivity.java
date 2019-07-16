package com.wanandroid_1809b.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wanandroid_1809b.R;
import com.wanandroid_1809b.utils.Logger;

import java.util.List;


public class BaseActivity extends RxAppCompatActivity {
    private String TAG;
    private LoadingPage mLoadingPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
    }


    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }

    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int rId) {
        Toast.makeText(this, rId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
// 如果 loading页面处于显示状态，拦截 back 事件，不让用户取消。
        if (mLoadingPage.getParent() != null) { // 如果 loading页面处于显示状态，拦截 back 事件，不让用户取消。

            return;
        }
    }

    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        String tag = aClass.getName();

        Logger.d("%s add fragment %s", TAG, aClass.getSimpleName());

        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            try {
                fragment = aClass.newInstance();
                BaseFragment baseFragment = (BaseFragment) fragment;
                //设置fragment  进入退出动画
                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());
                transaction.add(containerId, fragment, tag);
                if (baseFragment.isNeedToAddBackStack()) {
                    transaction.addToBackStack(tag);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment, tag);
            }
        }
        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();

        }

    }

    protected void onError(String msg, LoadingPage.OnReloadListener listener) {
        if (mLoadingPage != null && mLoadingPage.getParent() != null)
            mLoadingPage.onError(msg, listener);
    }

    protected void showLoadingPage(int mode) {
        if (mLoadingPage == null) {
            mLoadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.layout_loading, null);
        }
        if (mLoadingPage.getParent() == null) {
            ViewGroup viewGroup = findViewById(android.R.id.content);
            viewGroup.addView(mLoadingPage);
        }


        mLoadingPage.show(mode);
    }

    protected void dismissLoadingPage() {
        if (mLoadingPage != null) {
            mLoadingPage.dismiss();
        }
    }
}
