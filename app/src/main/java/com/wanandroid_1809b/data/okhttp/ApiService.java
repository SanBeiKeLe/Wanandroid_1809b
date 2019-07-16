package com.wanandroid_1809b.data.okhttp;


import com.wanandroid_1809b.AppConstant;
import com.wanandroid_1809b.data.entity.HttpResult;
import com.wanandroid_1809b.data.entity.User;

import java.util.Map;

import io.reactivex.Observable;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    @POST(AppConstant.WEB_SITE_REGISTER)
    @FormUrlEncoded
    Observable<HttpResult<User>> register(@FieldMap Map<String, String> params);

    @POST(AppConstant.WEB_SITE_LOGIN)
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@FieldMap Map<String, String> params);



}
