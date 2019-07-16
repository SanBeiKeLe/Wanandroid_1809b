package com.wanghongli.myapplication.base;

import com.wanghongli.myapplication.R;

public interface IBaseCallBack<T> {

    void onSuccess(T data);
    void onFail(String msg);
}
