package com.wanghongli.myapplication.data.okhttp;


import com.wanghongli.myapplication.AppConstant;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.Banner;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.entity.User;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.wxtwo.TabBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @POST(AppConstant.WEB_SITE_REGISTER)
    @FormUrlEncoded
    Observable<HttpResult<User>> register(@FieldMap Map<String, String> params);

    @POST(AppConstant.WEB_SITE_LOGIN)
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@FieldMap Map<String, String> params);
    //    https://www.wanandroid.com/banner/json

    @GET("banner/json")
    Observable<HttpResult<List<Banner>>> getBanners();

    //    https://www.wanandroid.com/article/list/0/json
    @GET("article/list/{page}/json")
    Observable<HttpResult<ArticleData>> getArticles(@Path("page") int page);

    @GET("article/top/json")
    Observable<HttpResult<List<ArticleData.Article>>> getTopArticles();
//    http://wanandroid.com/wxarticle/chapters/json
    @GET("wxarticle/chapters/json")
    Observable<HttpResult<List<WxTabBean>>> getTabDatas();
//    http://wanandroid.com/wxarticle/list/405/1/json
    @GET("wxarticle/list/id/{page}/json")
    Observable<HttpResult<WxArticleBean>> getWxArticles(@Query("id") int id,@Path("page") int page);

    @GET("wxarticle/chapters/json")
    Observable<HttpResult<List<TabBean>>> getTab();

}
