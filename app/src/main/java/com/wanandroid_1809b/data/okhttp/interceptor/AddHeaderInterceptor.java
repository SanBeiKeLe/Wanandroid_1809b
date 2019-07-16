package com.wanandroid_1809b.data.okhttp.interceptor;

import android.text.TextUtils;

import com.wanandroid_1809b.AppConstant;
import com.wanandroid_1809b.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AddHeaderInterceptor implements Interceptor {
    Request request = chain.request();

    String requestUrl = request.url().toString();


    Request.Builder builder = request.newBuilder();

        if(requestUrl.contains(AppConstant.WEB_SITE_COLLECTIONS) || requestUrl.contains(AppConstant.WEB_SITE_UNCOLLECTIONS)
            || requestUrl.contains(AppConstant.WEB_SITE_ARTICLE)){

        String cookies = SPUtils.getValue(AppConstant.LoginParamsKey.SET_COOKIE_KEY);

        if(!TextUtils.isEmpty(cookies)){
            builder.addHeader(AppConstant.LoginParamsKey.SET_COOKIE_NAME, cookies);
        }
    }

        return chain.proceed(builder.build());


}

