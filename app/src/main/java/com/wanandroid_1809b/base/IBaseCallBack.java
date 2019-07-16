package com.wanandroid_1809b.base;

public interface IBaseCallBack<T> {
    void onSuccess(T data);
    void onFail(String msg);
}
