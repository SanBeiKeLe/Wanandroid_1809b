package com.wanghongli.myapplication.home.remote;

import com.wanghongli.myapplication.Wx.contract.WxContract;
import com.wanghongli.myapplication.base.ServerException;
import com.wanghongli.myapplication.data.entity.ArticleData;
import com.wanghongli.myapplication.data.entity.HttpResult;
import com.wanghongli.myapplication.data.entity.WxArticleBean;
import com.wanghongli.myapplication.data.entity.WxTabBean;
import com.wanghongli.myapplication.data.okhttp.WADataService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class WxRemoteSource extends WxContract.DataSource {




    @Override
    public Observable<List<WxTabBean>> getTab() {

        return WADataService.getService().getTabDatas().flatMap(new Function<HttpResult<List<WxTabBean>>, ObservableSource<List<WxTabBean>>>() {
            @Override
            public ObservableSource<List<WxTabBean>> apply(HttpResult<List<WxTabBean>> listHttpResult) throws Exception {
                if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0) {


                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        });

    }

    @Override
    public Observable<WxArticleBean> getWxArticles(int id ,int page) {
      /*  observer(provider, WADataService.getService().getWxArticles(id, page), new Function<HttpResult<WxArticleBean>, ObservableSource<WxArticleBean>>() {
            @Override
            public ObservableSource<WxArticleBean> apply(HttpResult<WxArticleBean> wxArticleBeanHttpResult) throws Exception {
                if (wxArticleBeanHttpResult!=null&&wxArticleBeanHttpResult.data!=null){
                    return Observable.just(wxArticleBeanHttpResult.data);
                }
                return Observable.error(new ServerException(wxArticleBeanHttpResult.errorMsg));
            }
        }, callBack);*/

        return WADataService.getService().getWxArticles(id,page).flatMap(new Function<HttpResult<WxArticleBean>, ObservableSource<WxArticleBean>>() {
            @Override
            public ObservableSource<WxArticleBean> apply(HttpResult<WxArticleBean> wxArticleBeanHttpResult) throws Exception {
                if (wxArticleBeanHttpResult!=null&&wxArticleBeanHttpResult.data!=null){
                    return Observable.just(wxArticleBeanHttpResult.data);
                }
                return Observable.error(new ServerException(wxArticleBeanHttpResult.errorMsg));
            }
        });
    }

    @Override
    public Observable<WxArticleBean> loadMoreArticles(int page) {
        return getWxArticles(0,page);
    }

}
