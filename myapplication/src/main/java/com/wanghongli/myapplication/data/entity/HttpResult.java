package com.wanghongli.myapplication.data.entity;

import androidx.annotation.Keep;



public class HttpResult<T> {
    public int errorCode = -1;
    public String errorMsg;
    public T data;


    @Override
    public String toString() {
        return "HttpResult{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + (data == null ? " null " : data.toString()) +
                '}';
    }
}
