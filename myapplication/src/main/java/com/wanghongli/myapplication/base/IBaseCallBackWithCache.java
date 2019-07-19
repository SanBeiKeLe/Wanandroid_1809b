package com.wanghongli.myapplication.base;

public abstract class IBaseCallBackWithCache<T> implements IBaseCallBack<T>,IBaseCacheCallBack<T>{
    public abstract void onStartRequestServer(); // 开始请求服务器 主要用于通知view 显示loading页面，
}
